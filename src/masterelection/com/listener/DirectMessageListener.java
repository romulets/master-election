package masterelection.com.listener;

import java.io.IOException;

import masterelection.com.AbstractCommunicator;
import masterelection.com.Message;
import masterelection.domain.Node;

public class DirectMessageListener extends AbstractCommunicator {

	public DirectMessageListener(Node node) {
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
		
		Message msg = receiveDirectMessage();
		
	}

}
