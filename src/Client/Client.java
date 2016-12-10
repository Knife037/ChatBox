package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import control.ICoreControl;
import entity.Message;

public class Client implements IClient {

	private Socket client;
	
	private ObjectInputStream ois;
	
	private ObjectOutputStream oos;
	
	private ICoreControl coreControl;
	
	public Client(ICoreControl coreControl) throws UnknownHostException, IOException {
		this.coreControl = coreControl;
		client = new Socket("172.25.128.57", 5678);
		ois = new ObjectInputStream(client.getInputStream());
		oos = new ObjectOutputStream(client.getOutputStream());
	}
	
	@Override
	public boolean send(Message msg) {
		try {
			oos.writeObject(msg);
		} catch (IOException e) {
			System.out.println("����ʧ��");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Message readMessage() {
		Message msg;
		try {
			msg = (Message) ois.readObject();
		} catch (Exception e) {
			System.out.println("��ȡʧ�ܣ����������ѹر�");
			return null;
		}
		return msg;
	}

	@Override
	public void listen() {		
		new Thread(new Listen()).start();
	}
	
	public class Listen implements Runnable {
		
		@Override
		public void run() {
			
			Message msg = null;
			
			while(true) {
				try {
					msg = (Message) ois.readObject();
				} catch (Exception e) {
					System.out.println("����������");
					break;
				}
				coreControl.MessageHandle(msg);
			}
		}
	}

}
