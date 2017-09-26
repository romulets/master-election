package masterelection.com.message;

import java.io.IOException;

import masterelection.com.AbstractCommunicator;
import masterelection.com.MessageType;
import masterelection.domain.Node;

public class StartElectionMessage extends AbstractCommunicator {

	public StartElectionMessage(Node node) {
		super(node);
	}
	
	@Override
	public void run() {
		try {
			getNode().setAwaitingMessage(MessageType.I_AM_BIGGER);
			send(MessageType.START_ELECTION);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
}
