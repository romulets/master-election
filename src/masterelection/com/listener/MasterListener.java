package masterelection.com.listener;

import java.io.IOException;

import masterelection.com.AbstractCommunicator;
import masterelection.com.Message;
import masterelection.com.MessageType;
import masterelection.domain.Node;
import masterelection.domain.NodeState;

public class MasterListener extends AbstractCommunicator {

	private boolean runListener = true;

	public MasterListener(Node node) {
		super(node);
	}

	@Override
	public void run() {
		while (runListener) {
			if (getNode().getState() != NodeState.IN_ELECTION)
				break;
			
			Message msg;
			try {
				msg = receiveFromGroup();

				if (msg.getType() == MessageType.I_AM_MASTER) {
					getNode().setState(NodeState.SLAVE);
					getNode().setMasterAddr(msg.getAddr());
					getNode().setMasterPort(msg.getPort());
					break;
				}
			} catch (IOException e) {
				continue;
			}
		}
	}

	public void stopListener() {
		this.runListener = false;
	}

}
