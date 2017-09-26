package masterelection.com.verifier;

import java.io.IOException;
import java.net.InetAddress;

import masterelection.com.AbstractCommunicator;
import masterelection.com.MessageType;
import masterelection.domain.Node;
import masterelection.domain.NodeState;

public class LifeCheckVerifier extends AbstractCommunicator{

	public LifeCheckVerifier(Node node) {
		super(node);
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				checkMastersLife();				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void checkMastersLife() throws InterruptedException {
		sleep(4000);
		
		if (getNode().getState() != NodeState.SLAVE)
			return;
		
		InetAddress masterAddr = getNode().getMasterAddr();
		int masterPort = getNode().getMasterPort();
		
		if (masterAddr == null)
			return;
		
		try {
			getNode().setAwaitingMessage(MessageType.I_AM_ALIVE);
			send(MessageType.ARE_YOU_ALIVE, masterAddr, masterPort);			
		} catch (IOException e) {
			return;
		}
	}	
	
}
