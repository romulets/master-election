package masterelection.com.listener;

import java.io.IOException;
import java.net.InetAddress;

import masterelection.com.AbstractCommunicator;
import masterelection.com.Message;
import masterelection.com.MessageType;
import masterelection.com.message.IAmAliveMessage;
import masterelection.com.message.IAmMasterMesssage;
import masterelection.com.message.StartElectionMessage;
import masterelection.domain.Node;
import masterelection.domain.NodeState;

public class DirectMessageListener extends AbstractCommunicator {
	
	public DirectMessageListener(Node node) {
		super(node);
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				receiveMessages();
			} catch (IOException e) {
				
				if (getNode().getState() == NodeState.IN_ELECTION && getNode().getAwaitingMessage() == MessageType.I_AM_MASTER) {
					getNode().setState(NodeState.MASTER);
				} else if (getNode().getAwaitingMessage() == MessageType.I_AM_ALIVE) {
					beginElection();
				} else if (getNode().getAwaitingMessage() == MessageType.I_AM_BIGGER) {
					getNode().setState(NodeState.MASTER);
					IAmMasterMesssage iamMsg = new IAmMasterMesssage(getNode());
					iamMsg.start();
				}		
				
				getNode().setAwaitingMessage(MessageType.IDLE);
			}
		}
	}
	
	private Message receiveDirectMessage() throws IOException  {
		return receive(getNode().getUniSocket());
	}
	
	private void receiveMessages() throws IOException {
		
		Message msg = receiveDirectMessage();
		
		
		switch(getNode().getState()) {
		case MASTER:
			handleMaster(msg);
			break;
			
		case SLAVE:
			handleSlave(msg);
			break;
			
		case IN_ELECTION:
			handleInElection(msg);
			break;
		}
		
	}
	
	private void handleMaster(Message msg) {
		if (msg.getType() == MessageType.ARE_YOU_ALIVE) {
			IAmAliveMessage iaaMsg = new IAmAliveMessage(getNode(), msg.getAddr(), msg.getPort());
			iaaMsg.start();	
		}
	}
	
	private void handleInElection(Message msg) {
		if (msg.getType() == MessageType.I_AM_MASTER || msg.getType() == MessageType.I_AM_BIGGER) {
			getNode().setState(NodeState.SLAVE);
			getNode().setMasterAddr(msg.getAddr());
			getNode().setMasterPort(msg.getPort());
			getNode().setAwaitingMessage(MessageType.IDLE);
		} else if (getNode().getAwaitingMessage() == MessageType.I_AM_MASTER) {
			getNode().setState(NodeState.MASTER);
		}
	}
	
	private void handleSlave(Message msg) {
		InetAddress masterAddr = getNode().getMasterAddr();
		int masterPort = getNode().getMasterPort();
		
		if (masterAddr == null)
			return;
		
		if (msg.getType() == MessageType.I_AM_ALIVE && msg.getPort() == masterPort) {
			getNode().setAwaitingMessage(MessageType.IDLE);
		}
	}
	
	private void beginElection() {
		getNode().setState(NodeState.IN_ELECTION);
		StartElectionMessage electionMsg = new StartElectionMessage(getNode());
		electionMsg.start();
	}
	
	
}
