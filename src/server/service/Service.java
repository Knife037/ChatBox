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
	 * 已创建的用户头像
	 */
	private HashMap<String, Integer> usersPortrait;
	
	/**
	 * 已创建的用户的密码
	 */
	private HashMap<String, String> passwords;
	
	/**
	 * 昵称
	 */
	private HashMap<String, String> nickNames;
	
	/**
	 * 暂存的输出流
	 */
	private HashMap<String, ObjectOutputStream> outputStreams;
	
	/**
	 * 在线用户
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
			System.out.println("端口被占用");
			return ;
		}
		
		isOpen = true;
		
		frmServer.printMsg("启动服务端");
		
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
		
		System.out.println(outputStreams.size() + "发送");
		
		Message msg = new Message("server", "All Member", 0, "updateUsersList", content);
		Iterator<String> its = outputStreams.keySet().iterator();
		while(its.hasNext()) {
			ObjectOutputStream oos = outputStreams.get(its.next());
			try {
				oos.writeObject(msg);
			} catch (IOException e) {
				frmServer.printMsg("发送群体消息失败");
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
				frmServer.printMsg(client.getInetAddress().toString() + " 已连入");
				new Thread(new Listen(client, frmServer)).start();
			} catch (IOException e) {
				frmServer.printMsg("发生未知错误！客户端连接失败");
				return ;
			}
		}
	}
	
	public void requestLogin(Message msg, String clientName) {
		String[] content = msg.getContent().split("\\|");
		String userName = content[0];
		String password = content[1];
		if(!isUserExist(userName)) {
			sendMessage(new Message("server", msg.getSender(), 0, "rejectLogin", "用户不存在"));
			return ;
		}
		if(!passwords.get(userName).equals(password)) {
			sendMessage(new Message("server", msg.getSender(), 0, "rejectLogin", "密码错误"));
			return ;
		}
		
		onlineUsers.put(userName, usersPortrait.get(userName));
		outputStreams.put(userName, outputStreams.get(clientName));
		removeOutputStream(clientName);
		
		sendMessage(new Message("server", userName, 0, "permitLogin", userName + "|" + usersPortrait.get(userName)));
		
		frmServer.printMsg(userName + " 已登录");
		
		updateUserList();
	}
	
	public void requestRegister(Message msg, String clientName) {
		String[] content = msg.getContent().split("\\|");
		String userName = content[0];
		String nickName = content[1];
		String password = content[2];
		if(isUserExist(userName)) {
			sendMessage(new Message("server", msg.getSender(), 1, "rejectRegister", "用户名已存在"));
			return ;
		}
		
		// 初始化用户头像为1
		passwords.put(userName, password);
		nickNames.put(userName, nickName);
		usersPortrait.put(userName, 1);
		sendMessage(new Message("server", msg.getSender(), 1, "permitRegister", "注册成功"));
		frmServer.printMsg(userName + " 已注册");
	}
	
	private void sendMessage(Message msg) {
		String receiver = msg.getReceiver();
		ObjectOutputStream oos = outputStreams.get(receiver);
		try {
			oos.writeObject(msg);
		} catch (IOException e) {
			frmServer.printMsg("发送消息失败");
			return ;
		}
		
	}
	
	public void normal(Message msg, String clientName) {
		sendMessage(msg);
	}
	
	public class Listen implements Runnable {
		
		/**
		 * 窗口
		 */
		private IFrmServer frmServer;
		
		/**
		 * 用户接口
		 */
		private Socket client;
		
		/**
		 * 是否登录
		 */
		private boolean isLogin;
		
		/**
		 * 用户名
		 */
		private String userName;
		
		/**
		 * 输入流
		 */
		private ObjectInputStream ois;
		
		public Listen(Socket client, IFrmServer frmServer) {
			this.frmServer = frmServer;
			this.client = client;
			// 获取用户名
			userName = client.getInetAddress().toString();
			// 获取输入流
			getInputStream();
		}
		
		/**
		 * 获取输入流
		 */
		private void getInputStream() {
			try {
				ois = new ObjectInputStream(client.getInputStream());
			} catch (IOException e1) {
				frmServer.printMsg(userName + " 获取输入流失败");
				return;
			}
		}
		
		@Override
		public void run() {
			// TODO 可能要改掉isOpen
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
				frmServer.printMsg(userName + " 反射方法失败");
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
					frmServer.printMsg(userName + " 已退出");
					removeUser(userName);
					return null;
				} else {
					frmServer.printMsg(userName + " 已退出");
					removeOutputStream(userName);
					return null;
				}
			}
			return msg;
		}
	}
	
}
