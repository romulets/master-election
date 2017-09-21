package masterelection.com.listener;

import java.io.IOException;

import masterelection.com.AbstractCommunicator;
import masterelection.com.Message;
import masterelection.com.MessageType;
import masterelection.domain.Node;
import masterelection.domain.NodeState;

public class PollsListener extends AbstractCommunicator {

	public PollsListener(Node node) {
		super(node);
	}

	@Override
	public void run() {

		while (true) {
			if (getNode().getState() != NodeState.IN_ELECTION)
				break;
			
			try {
				Message msg = receiveFromGroup();

				if (msg.getType() == MessageType.START_ELECTION && getNode().getId() > msg.getPort()) {
					send(MessageType.I_AM_BIGGER);
				}
			} catch (IOException e) {
				break;
			}
		}

	}

}
