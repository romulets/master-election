package masterelection.com.listener;

import java.io.IOException;

import masterelection.com.AbstractCommunicator;
import masterelection.com.Message;
import masterelection.com.MessageType;
import masterelection.com.message.IAmMasterMesssage;
import masterelection.domain.Node;
import masterelection.domain.NodeState;

public class AmIMasterListener extends AbstractCommunicator {

	public AmIMasterListener(Node node) {
		super(node);
	}

	@Override
	public void run() {
		while (true) {
			Message msg;

			try {
				msg = receiveFromGroup();
			} catch (IOException e) {
				continue;
			}

			if (msg.getType() == MessageType.IS_THERE_MASTER && getNode().getState() == NodeState.MASTER) {
				IAmMasterMesssage iamMsg = new IAmMasterMesssage(getNode(), msg.getAddr(), msg.getPort());
				iamMsg.start();
			}
		}
	}

}
