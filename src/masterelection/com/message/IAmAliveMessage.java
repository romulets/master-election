package masterelection.com.message;

import java.io.IOException;
import java.net.InetAddress;

import masterelection.com.AbstractCommunicator;
import masterelection.com.MessageType;
import masterelection.domain.Node;

public class IAmAliveMessage extends AbstractCommunicator {

	private InetAddress addr;
	private int port;
	
	public IAmAliveMessage(Node node, InetAddress addr, int port) {
		super(node);
		this.addr = addr;
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			send(MessageType.I_AM_ALIVE, addr, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
