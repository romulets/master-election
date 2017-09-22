package masterelection;

import masterelection.com.listener.DirectMessageListener;
import masterelection.com.listener.GroupMessagesListener;
import masterelection.com.message.SetupMasterMessage;
import masterelection.com.verifier.LifeCheckVerifier;
import masterelection.domain.Node;

public class Instance extends Thread {
	
	DirectMessageListener directListener;
	GroupMessagesListener groupListener;
	LifeCheckVerifier lcVerifier;
	
	private Node node;
	
	public Instance () {
		node = new Node();
		directListener = new DirectMessageListener(node);
		groupListener = new GroupMessagesListener(node);
		lcVerifier = new LifeCheckVerifier(node);
	}
	
	@Override
	public void run() {
		directListener.start();
		groupListener.start();
		lcVerifier.start();
		verifyCoordination();
	}
	
	private void verifyCoordination() {
		SetupMasterMessage msg = new SetupMasterMessage(node);
		msg.start();
	}
}
