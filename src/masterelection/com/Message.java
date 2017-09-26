package masterelection.com;

import java.net.InetAddress;

public class Message {
	
	private MessageType type;
	private InetAddress addr;
	private int port;
	
	public Message(MessageType type) {
		this.type = type;
	}
	
	public Message(String message) {
		type = MessageType.valueOf(message);
	}
	
	@Override
	public String toString() {
		return String.format("%s", type.toString());
	}
	
	public MessageType getType() {
		return type;
	}

	public InetAddress getAddr() {
		return addr;
	}

	public int getPort() {
		return port;
	}

	public void setAddr(InetAddress addr) {
		this.addr = addr;
	}

	public void setPort(int port) {
		this.port = port;
	}
		
	
}
