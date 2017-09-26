package masterelection.com;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import masterelection.domain.Node;

public abstract class AbstractCommunicator extends Thread {

	private Node node;

	public AbstractCommunicator(Node node) {
		this.node = node;
	}

	protected void send(MessageType message) throws IOException {
		send(message, Node.getGroupAddress(), Node.getGroupPort());
	}

	protected void send(MessageType msgType, InetAddress addr, int port) throws IOException {
		Message msg = new Message(msgType);
		String msgStr = msg.toString();

		DatagramPacket packet = new DatagramPacket(msgStr.getBytes(), msgStr.length(), addr, port);

		node.getUniSocket().send(packet);
		System.out.println(String.format("SEND \t\t [%s:%d] \t %s", addr.toString(), port, msgStr));
	}
	
	protected Message receive(DatagramSocket socket) throws IOException {
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

		socket.receive(packet);
		String typeMessage = new String(packet.getData(), packet.getOffset(), packet.getLength());

		Message msg = new Message(typeMessage);
		msg.setAddr(packet.getAddress());
		msg.setPort(packet.getPort());

		System.out.println(
				String.format("RECEIVE \t [%s:%d] \t %s", msg.getAddr().toString(), msg.getPort(), msg.toString()));

		return msg;
	}

	protected Node getNode() {
		return node;
	}

}
