package masterelection;

import masterelection.com.listener.AmIMasterListener;
import masterelection.com.listener.LifeCheckListener;
import masterelection.com.message.SetupMasterMessage;
import masterelection.com.verifier.LifeCheckVerifier;
import masterelection.domain.Node;
import masterelection.domain.NodeState;

public class Instance extends Thread {
	
	AmIMasterListener aimListener;
	LifeCheckListener lcListener;
	LifeCheckVerifier lcVerifier;
	
	private Node node;
	
	public Instance () {
		node = new Node();
		aimListener = new AmIMasterListener(node);
		lcListener = new LifeCheckListener(node);
		lcVerifier = new LifeCheckVerifier(node);
	}
	
	@Override
	public void run() {
		aimListener.start();
		lcListener.start();
		lcVerifier.start();
		verifyCoordination();
	}
	
	private void verifyCoordination() {
		SetupMasterMessage msg = new SetupMasterMessage(node);
		
		msg.start();
		try {
			msg.join();
		} catch (InterruptedException e) {
			node.setState(NodeState.SLAVE);
		}
	}
}
