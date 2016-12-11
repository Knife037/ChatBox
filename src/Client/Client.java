package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
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
		client = new Socket("101.76.224.19", 5678);
		ois = new ObjectInputStream(client.getInputStream());
		oos = new ObjectOutputStream(client.getOutputStream());
	}
	
	@Override
	public boolean send(Message msg) {
		try {
			oos.writeObject(msg);
		} catch (IOException e) {
			System.out.println("发送失败");
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
			System.out.println("读取失败！主机可能已关闭");
			return null;
		}
		return msg;
	}

	@Override
	public void listen() {		
		new Thread(new Listen()).start();
	}
	
	@Override
	public String getName() {
		return client.getInetAddress().toString();
	}
	
	public class Listen implements Runnable {
		
		@Override
		public void run() {
			
			Message msg = null;
			
			while(true) {
				try {
					msg = (Message) ois.readObject();
					Method method = coreControl.getClass().getMethod(msg.getType(), Message.class);
					method.invoke(coreControl, msg);
				} catch (Exception e) {
					System.out.println("主机已下线");
					e.printStackTrace();
					break;
				}
				coreControl.MessageHandle(msg);
			}
		}
	}

}
