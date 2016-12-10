package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import entity.Message;

public class Server {
	
	private ServerSocket server;
	
	
	public Server() throws IOException, ClassNotFoundException {
		try {
			server = new ServerSocket(5678);
		} catch (IOException e) {
			System.out.println("�˿ڱ�ռ��");
			return;
		}
		
		System.out.println("���������");
		
		Socket client = server.accept();
		
		ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
		
		ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
		
		Message msg = (Message) ois.readObject();
		
		System.out.println("�յ���Ϣ");
		
		System.out.println(msg.getSender() + "\n" + msg.getReceiver() + "\n" + msg.getType() + "\n" + msg.getContent() + "\n");
		
		oos.writeObject(new Message("server", "Knife037", 1, "permitLogin", "permit"));

	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		new Server();
	}
	
}
