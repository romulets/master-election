package masterelection.com.verifier;

import java.io.IOException;
import java.net.InetAddress;

import masterelection.com.AbstractCommunicator;
import masterelection.com.Message;
import masterelection.com.MessageType;
import masterelection.com.message.StartElectionMessage;
import masterelection.domain.Node;
import masterelection.domain.NodeState;

public class LifeCheckVerifier extends AbstractCommunicator{

	public LifeCheckVerifier(Node node) {
		super(node);
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				checkMastersLife();				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void checkMastersLife() throws InterruptedException {
		sleep(4000);
		
		if (getNode().getState() != NodeState.SLAVE)
			return;
		
		InetAddress masterAddr = getNode().getMasterAddr();
		int masterPort = getNode().getMasterPort();
		
		if (masterAddr == null)
			return;
		
		try {
			send(MessageType.ARE_YOU_ALIVE, masterAddr, masterPort);
			
			Message msg = receiveDirectMessage();
			
			if (msg.getType() != MessageType.I_AM_ALIVE && msg.getPort() != masterPort) {
				startElection();
			}
			
		} catch (IOException e) {
			startElection();
		}
	}
	
	private void startElection() {
		getNode().setState(NodeState.IN_ELECTION);
		
		StartElectionMessage msg = new StartElectionMessage(getNode());
		msg.start();
	}

	
	
}
