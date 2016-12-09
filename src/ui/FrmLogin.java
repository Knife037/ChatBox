package ui;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Client.ClientSimulate;
import Client.IClient;
import entity.Message;

public class FrmLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * ����ͼƬ
	 */
	private static final Image IMG_BG = new ImageIcon("graphics/background/login.jpg").getImage();
	
	/**
	 * ������
	 */
	private static final int WINDOW_WIDTH = 300;
	
	/**
	 * ����߶�
	 */
	private static final int WINDOW_HEIGHT = 150;
	
	/**
	 * �û��������
	 */
	private JTextField textUserName;
	
	/**
	 * ���������
	 */
	private JPasswordField textPassword;
	
	/**
	 * ע�ᰴť
	 */
	private JButton btnRegister;
	
	/**
	 * ��¼��ť
	 */
	private JButton btnLogin;
	
	/**
	 * �ͻ��˽ӿ�
	 */
	private IClient client;
	
	public FrmLogin(IClient client) {
		this.client = client;
		
		// ��ʼ�������Լ�����
		initialized();
		
		// ����panel����
		JPanel panel0 = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		// ����Panel͸��
		panel0.setBackground(null);
		panel0.setOpaque(false);
		panel1.setBackground(null);
		panel1.setOpaque(false);
		panel2.setBackground(null);
		panel2.setOpaque(false);
		
		// ���panel��frame
		add(panel0);
		add(panel1);
		add(panel2);
		// ��������panel
		panel0.add(new JLabel("�û���"));
		panel0.add(textUserName);
		panel1.add(new JLabel("  ����  "));
		panel1.add(textPassword);
		panel2.add(btnRegister);
		panel2.add(btnLogin);
		
		// ����¼�����
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName = textUserName.getText();
				String password = new String(textPassword.getPassword());
				Message msg = new Message("anonymous", "server", "requestLogin", userName + "|" + password);
				client.send(msg);				
			}
			
		});
		
		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new FrmRegister(client);			
			}
			
		});
		
		// ���ÿɼ�
		this.setVisible(true);
	}
	
	/**
	 * ��ʼ�������Լ�����
	 */
	private void initialized() {
		// ������ʾ������Panel
		this.setContentPane(new PanelBackground(IMG_BG));
		// ���ô������
		this.setTitle("Login");
		// ���ô����С���ɱ�
		this.setResizable(false);
		// ���ô��ڴ�С
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		// ���ô���λ��
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width - WINDOW_WIDTH) >> 1, (height - WINDOW_HEIGHT) >> 1);
		// ����Ĭ�Ϲرղ���
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ò���
		this.setLayout(new GridLayout(3, 1));
		
		// ��ʼ��������밴ť
		textUserName = new JTextField(10);
		textPassword = new JPasswordField(10);
		btnRegister = new JButton("ע��");
		btnLogin = new JButton("��¼");
	}
	
	public static void main(String[] args) {
		new FrmLogin(new ClientSimulate()); 
	}

}
