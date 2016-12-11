package server.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import entity.Message;
import server.ui.IFrmServer;

public class Service {

	private List<String> usersList;
	
	/**
	 * �Ѵ������û�ͷ��
	 */
	private HashMap<String, Integer> usersPortrait;
	
	/**
	 * �Ѵ������û�������
	 */
	private HashMap<String, String> passwords;
	
	/**
	 * �ǳ�
	 */
	private HashMap<String, String> nickNames;
	
	/**
	 * �ݴ�������
	 */
	private HashMap<String, ObjectOutputStream> outputStreams;
	
	/**
	 * �����û�
	 */
	private HashMap<String, Integer> onlineUsers;
	
	private boolean isOpen;
	
	private ServerSocket server;
	
	private IFrmServer frmServer;
	
	
	
	public Service(IFrmServer frmServer) {
		
		init();
		
		this.frmServer = frmServer;
		
		try {
			server = new ServerSocket(5678);
		} catch (IOException e) {
			System.out.println("�˿ڱ�ռ��");
			return ;
		}
		
		isOpen = true;
		
		frmServer.printMsg("���������");
		
		accept();
	}
	
	private void init() {
		outputStreams = new HashMap<String, ObjectOutputStream>();
		usersList = new LinkedList<String>();
		passwords = new HashMap<String, String>();
		usersPortrait = new HashMap<String, Integer>();
		onlineUsers = new HashMap<String, Integer>();
		nickNames = new HashMap<String, String>();
	}
	
	private boolean isUserExist(String userName) {
		if(passwords.containsKey(userName)) {
			return true;
		}
		return false;
	}
	
	private synchronized void updateUserList() {
		Iterator<String> it = usersPortrait.keySet().iterator();
		String content = "";
		while(it.hasNext()) {
			String userName = it.next();
			content += userName + "|" + usersPortrait.get(userName) + "||";
		}
		
		System.out.println(outputStreams.size() + "����");
		
		Message msg = new Message("server", "All Member", 0, "updateUsersList", content);
		Iterator<String> its = outputStreams.keySet().iterator();
		while(its.hasNext()) {
			ObjectOutputStream oos = outputStreams.get(its.next());
			try {
				oos.writeObject(msg);
			} catch (IOException e) {
				frmServer.printMsg("����Ⱥ����Ϣʧ��");
				return ;
			}
		}
	}
	
	private void addUser(String userName) {
		usersList.add(userName);
	}
	
	private void removeUser(String userName) {
		usersPortrait.remove(userName);
		removeOutputStream(userName);
	}
	
	
	private void removeOutputStream(String userName) {
		outputStreams.remove(userName);
	}
	
	private void addOutputStream(String userName, ObjectOutputStream oos) {
		outputStreams.put(userName, oos);
	}
	
	private void accept() {
		
		while(isOpen) {
			Socket client;
			try {
				client = server.accept();
				String clientName = client.getInetAddress().toString();
				addOutputStream(clientName, new ObjectOutputStream(client.getOutputStream()));
				frmServer.printMsg(client.getInetAddress().toString() + " ������");
				new Thread(new Listen(client, frmServer)).start();
			} catch (IOException e) {
				frmServer.printMsg("����δ֪���󣡿ͻ�������ʧ��");
				return ;
			}
		}
	}
	
	public void requestLogin(Message msg, String clientName) {
		String[] content = msg.getContent().split("\\|");
		String userName = content[0];
		String password = content[1];
		if(!isUserExist(userName)) {
			sendMessage(new Message("server", msg.getSender(), 0, "rejectLogin", "�û�������"));
			return ;
		}
		if(!passwords.get(userName).equals(password)) {
			sendMessage(new Message("server", msg.getSender(), 0, "rejectLogin", "�������"));
			return ;
		}
		
		onlineUsers.put(userName, usersPortrait.get(userName));
		outputStreams.put(userName, outputStreams.get(clientName));
		removeOutputStream(clientName);
		
		sendMessage(new Message("server", userName, 0, "permitLogin", userName + "|" + usersPortrait.get(userName)));
		
		frmServer.printMsg(userName + " �ѵ�¼");
		
		updateUserList();
	}
	
	public void requestRegister(Message msg, String clientName) {
		String[] content = msg.getContent().split("\\|");
		String userName = content[0];
		String nickName = content[1];
		String password = content[2];
		if(isUserExist(userName)) {
			sendMessage(new Message("server", msg.getSender(), 1, "rejectRegister", "�û����Ѵ���"));
			return ;
		}
		
		// ��ʼ���û�ͷ��Ϊ1
		passwords.put(userName, password);
		nickNames.put(userName, nickName);
		usersPortrait.put(userName, 1);
		sendMessage(new Message("server", msg.getSender(), 1, "permitRegister", "ע��ɹ�"));
		frmServer.printMsg(userName + " ��ע��");
	}
	
	private void sendMessage(Message msg) {
		String receiver = msg.getReceiver();
		ObjectOutputStream oos = outputStreams.get(receiver);
		try {
			oos.writeObject(msg);
		} catch (IOException e) {
			frmServer.printMsg("������Ϣʧ��");
			return ;
		}
		
	}
	
	public void normal(Message msg, String clientName) {
		sendMessage(msg);
	}
	
	public class Listen implements Runnable {
		
		/**
		 * ����
		 */
		private IFrmServer frmServer;
		
		/**
		 * �û��ӿ�
		 */
		private Socket client;
		
		/**
		 * �Ƿ��¼
		 */
		private boolean isLogin;
		
		/**
		 * �û���
		 */
		private String userName;
		
		/**
		 * ������
		 */
		private ObjectInputStream ois;
		
		public Listen(Socket client, IFrmServer frmServer) {
			this.frmServer = frmServer;
			this.client = client;
			// ��ȡ�û���
			userName = client.getInetAddress().toString();
			// ��ȡ������
			getInputStream();
		}
		
		/**
		 * ��ȡ������
		 */
		private void getInputStream() {
			try {
				ois = new ObjectInputStream(client.getInputStream());
			} catch (IOException e1) {
				frmServer.printMsg(userName + " ��ȡ������ʧ��");
				return;
			}
		}
		
		@Override
		public void run() {
			// TODO ����Ҫ�ĵ�isOpen
			while(isOpen) {
				
				Message msg = null;
				if((msg = readMessage()) == null) {
					return;
				}
				
				reflect(msg);
			}
			
		}
		
		private void reflect(Message msg) {
			Method method;
			try {
				method = Service.this.getClass().getMethod(msg.type, Message.class, String.class);
				method.invoke(Service.this, msg, userName);
			} catch (Exception e) {
				frmServer.printMsg(userName + " ���䷽��ʧ��");
				e.printStackTrace();
				return;
			}
		}
		
		private Message readMessage() {
			Message msg = null;
			try {
				msg = (Message) ois.readObject();
			} catch (Exception e) {
				if(isLogin) {
					frmServer.printMsg(userName + " ���˳�");
					removeUser(userName);
					return null;
				} else {
					frmServer.printMsg(userName + " ���˳�");
					removeOutputStream(userName);
					return null;
				}
			}
			return msg;
		}
	}
	
}
