package masterelection.com.message;

import java.io.IOException;

import masterelection.com.AbstractCommunicator;
import masterelection.com.MessageType;
import masterelection.com.listener.MasterListener;
import masterelection.com.listener.PollsListener;
import masterelection.domain.Node;
import masterelection.domain.NodeState;

public class StartElectionMessage extends AbstractCommunicator {

	public StartElectionMessage(Node node) {
		super(node);
	}
	
	@Override
	public void run() {
		try {
			boolean amIElected = amIElected();
			
			PollsListener pollsListener = new PollsListener(getNode());
			pollsListener.start();
			
			MasterListener masterListener = new MasterListener(getNode());
			masterListener.start();
			
			if (amIElected) {
				getNode().setState(NodeState.MASTER);
				send(MessageType.I_AM_MASTER);
			} 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	private boolean amIElected() throws IOException {
		send(MessageType.START_ELECTION);
		
		boolean iAmMaster = true;
		
		try {
			while (true) {
				receiveDirectMessage();
				iAmMaster = false;
			}
		} catch (IOException e) {
			return iAmMaster;
		}
	}
	
}
