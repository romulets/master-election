package masterelection.domain;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import masterelection.com.MessageType;

public class Node {

	private int id;
	private NodeState state;
	private MessageType awaitingMessage;
	private MulticastSocket multiSocket;
	private DatagramSocket uniSocket;

	private InetAddress masterAddr;
	private int masterPort;

	public Node() {
		state = NodeState.IN_ELECTION;
		initMultiSocket();
		initUniSocket();
		id = uniSocket.getLocalPort();
		awaitingMessage = MessageType.IDLE;

		System.out.println("I AM " + id);
	}

	private void initMultiSocket() {
		try {
			multiSocket = new MulticastSocket(getGroupPort());
			multiSocket.joinGroup(getGroupAddress());
			multiSocket.setSoTimeout(1000);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(9);
		}
	}

	private void initUniSocket() {
		try {
			uniSocket = new DatagramSocket();
			uniSocket.setSoTimeout(1000);
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(9);
		}
	}

	public int getId() {
		return id;
	}

	public MulticastSocket getMultiSocket() {
		return multiSocket;
	}

	public DatagramSocket getUniSocket() {
		return uniSocket;
	}

	public NodeState getState() {
		return state;
	}

	public void setState(NodeState state) {
		this.state = state;

		switch (state) {
		
		case IN_ELECTION:
			System.out.println("~ I AM IN ELECTION ~");
			break;
		case MASTER:
			System.out.println("~ I AM MASTER ~");
			break;
		case SLAVE:
			System.out.println("~ I AM SLAVE ~");
			break;
			
		}
	}

	public InetAddress getMasterAddr() {
		return masterAddr;
	}

	public int getMasterPort() {
		return masterPort;
	}

	public void setMasterAddr(InetAddress masterAddr) {
		this.masterAddr = masterAddr;
	}

	public void setMasterPort(int masterPort) {
		this.masterPort = masterPort;
	}

	public static InetAddress getGroupAddress() throws UnknownHostException {
		return InetAddress.getByName("224.0.0.1");
	}

	public static int getGroupPort() {
		return 8083;
	}

	public MessageType getAwaitingMessage() {
		return awaitingMessage;
	}

	public void setAwaitingMessage(MessageType awaitingMessage) {
		this.awaitingMessage = awaitingMessage;
	}
	
}
