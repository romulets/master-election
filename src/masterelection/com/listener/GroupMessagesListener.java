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
				continue;
			}
		}
	}

	private Message receiveFromGroup() throws IOException {
		return receive(getNode().getMultiSocket());
	}

	private void receiveMessages() throws IOException {

		Message msg = receiveFromGroup();

		if (msg.getType() == MessageType.IS_THERE_MASTER && getNode().getState() == NodeState.MASTER) {
			IAmMasterMesssage iamMsg = new IAmMasterMesssage(getNode(), msg.getAddr(), msg.getPort());
			iamMsg.start();
			return;
		}

		if (getNode().getState() == NodeState.IN_ELECTION && msg.getType() == MessageType.I_AM_MASTER) {
			getNode().setState(NodeState.SLAVE);
			getNode().setMasterAddr(msg.getAddr());
			getNode().setMasterPort(msg.getPort());
			return;
		}

		if (msg.getType() == MessageType.START_ELECTION && getNode().getId() > msg.getPort()) {
			send(MessageType.I_AM_BIGGER, msg.getAddr(), msg.getPort());
			return;
		}
		
	}

}
