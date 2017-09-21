package masterelection.com.message;

import java.io.IOException;

import masterelection.com.AbstractCommunicator;
import masterelection.com.Message;
import masterelection.com.MessageType;
import masterelection.domain.Node;
import masterelection.domain.NodeState;

public class SetupMasterMessage extends AbstractCommunicator {

	public SetupMasterMessage(Node node) {
		super(node);
	}

	@Override
	public void run() {
		try {
			send(MessageType.IS_THERE_MASTER);
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}

		waitMastersMessage();
	}

	private void waitMastersMessage() {
		Node node = getNode();
		
		while (node.getState() != NodeState.SLAVE && node.getState() != NodeState.MASTER) {

			try {
				Message msg = receiveDirectMessage();

				if (msg.getType() == MessageType.I_AM_MASTER) {
					node.setState(NodeState.SLAVE);
					node.setMasterAddr(msg.getAddr());
					node.setMasterPort(msg.getPort());
				}
			} catch (IOException e) {
				getNode().setState(NodeState.MASTER);
			}
		}
	}

}
