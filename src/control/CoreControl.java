package control;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import client.Client;
import client.IClient;
import entity.Message;
import ui.FrmLogin;
import ui.FrmMain;
import ui.IFrmMain;

public class CoreControl implements ICoreControl {

	private IClient client;
	
	private FrmLogin frmLogin;
	
	private IFrmMain frmMain;
	
	public CoreControl() {
		
		connect();
		
		frmLogin = new FrmLogin(client);
		
		client.listen();
		
	}
	
	private void connect() {
		try {
			client = new Client(this);
		} catch (Exception e) {
			System.out.println("连接主机失败");
			return ;
		}
	}
	
	public void rejectLogin(Message msg) {
		JOptionPane.showMessageDialog(frmLogin.getContentPane(),
				msg.getContent(), "服务器信息", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	@Override
	public void MessageHandle(Message msg) {
		try {
			this.getClass().getMethod(msg.getType(), Message.class);
		} catch (Exception e) {
			System.out.println("反射失败");
			return ;
		}
	}
	
	public void normal(Message msg) {
		frmMain.showMsg(msg);
	}
	
	public void updateUsersList(Message msg) {
		String content = msg.getContent();
		System.out.println(content);
		String[] info = content.split("\\|\\1");
		List<String> usersName = new ArrayList<String>();
		List<Integer> usersPortrait = new ArrayList<Integer>();
		for(String str : info) {
			String[] user = str.split("\\|");
			usersName.add(user[0]);
			usersPortrait.add(Integer.parseInt(user[1]));
		}
		frmMain.updateUsersList(usersName, usersPortrait);
	}
	
	public void permitLogin(Message msg) {
		frmLogin.setVisible(false);
		String content = msg.getContent();
		String[] info = content.split("\\|");
		frmMain = new FrmMain(client, info[0], Integer.parseInt(info[1]));
	}
	
	public void permitRegister(Message msg) {
		JOptionPane.showMessageDialog(frmLogin.getContentPane(),
				msg.getContent(), "服务器信息", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main(String[] args) {
		new CoreControl();
	}

}
