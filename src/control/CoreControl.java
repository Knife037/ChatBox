package control;

import javax.swing.JOptionPane;

import Client.Client;
import Client.IClient;
import entity.Message;
import ui.FrmLogin;

public class CoreControl implements ICoreControl {

	private IClient client;
	
	public CoreControl() {
		
		try {
			client = new Client(this);
		} catch (Exception e) {
			System.out.println("��������ʧ��");
			return ;
		}
		
		FrmLogin frmLogin = new FrmLogin(client);
		
		while(true) {
			Message msg = client.readMessage();
			if(msg.getType().equals("permitLogin")) {
				break;
			} else {
				JOptionPane.showMessageDialog(frmLogin.getContentPane(),
						msg.getContent(), "��������Ϣ", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		client.listen();
		
	}
	
	@Override
	public void MessageHandle(Message msg) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new CoreControl();
	}

}
