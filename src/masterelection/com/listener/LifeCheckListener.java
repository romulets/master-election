package masterelection.com.listener;

import java.io.IOException;

import masterelection.com.AbstractCommunicator;
import masterelection.com.Message;
import masterelection.com.MessageType;
import masterelection.com.message.IAmAliveMessage;
import masterelection.domain.Node;
import masterelection.domain.NodeState;

public class LifeCheckListener extends AbstractCommunicator {

	public LifeCheckListener(Node node) {
		super(node);
	}

	@Override
	public void run() {
		while (true) {

			try {
				checkLifeCheckMessages();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	private void checkLifeCheckMessages() throws InterruptedException {
		if (getNode().getState() != NodeState.MASTER) {
			sleep(1000);
			return;
		}
		
		Message msg;
		try {
			msg = receiveDirectMessage();
		} catch (IOException e) {
			msg = new Message(MessageType.I_AM_MASTER);
		}
		
		if (msg.getType() == MessageType.ARE_YOU_ALIVE) {
			IAmAliveMessage iaaMsg = new IAmAliveMessage(getNode(), msg.getAddr(), msg.getPort());
			iaaMsg.start();
		}
	}

}
