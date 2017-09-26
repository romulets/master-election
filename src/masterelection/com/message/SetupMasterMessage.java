package masterelection.com.message;

import java.io.IOException;

import masterelection.com.AbstractCommunicator;
import masterelection.com.MessageType;
import masterelection.domain.Node;

public class SetupMasterMessage extends AbstractCommunicator {

	public SetupMasterMessage(Node node) {
		super(node);
	}

	@Override
	public void run() {
		try {
			send(MessageType.IS_THERE_MASTER);
			getNode().setAwaitingMessage(MessageType.I_AM_MASTER);
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
	}
	
}
