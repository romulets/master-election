package masterelection.com;

import java.net.InetAddress;

public class Message {
	
	private MessageType type;
	private String message;
	private InetAddress addr;
	private int port;

	public Message(MessageType type, String message) {
		this.type = type;
		this.message = message;
	}
	
	public Message(MessageType type) {
		this(type, "");
	}
	
	public Message(String typeMessage) {
		String[] params = typeMessage.split(":");
		type = MessageType.valueOf(params[0]);
		
		if (params.length > 1) {
			message = params[1];
		} else {
			message = "";
		}	
	}
	
	@Override
	public String toString() {
		return String.format("%s:%s", type.toString(), message);
	}
	
	public MessageType getType() {
		return type;
	}
	
	public String getMessage() {
		return message;
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
