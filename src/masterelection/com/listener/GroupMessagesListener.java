package masterelection.com.listener;

import java.io.IOException;

import masterelection.com.AbstractCommunicator;
import masterelection.com.Message;
import masterelection.com.MessageType;
import masterelection.com.message.IAmMasterMesssage;
import masterelection.domain.Node;
import masterelection.domain.NodeState;

public class GroupMessagesListener extends AbstractCommunicator {

	public GroupMessagesListener(Node node) {
		super(node);
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				receiveMessages();
			} catch (IOException e) {
				System.out.println("No messages received");
			}
		}
	}
	
	private void receiveMessages() throws IOException {
		
		Message msg = receiveFromGroup();
		
		if (msg.getType() == MessageType.IS_THERE_MASTER && getNode().getState() == NodeState.MASTER) {
			IAmMasterMesssage iamMsg = new IAmMasterMesssage(getNode(), msg.getAddr(), msg.getPort());
			iamMsg.start();
			return;
		}
		
		
		
	}

}
