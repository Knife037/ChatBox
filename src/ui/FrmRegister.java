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

import Client.IClient;
import entity.Message;

public class FrmRegister extends JFrame {

private static final long serialVersionUID = 1L;
	
	/**
	 * ����ͼƬ
	 */
	private static final Image IMG_BG = new ImageIcon("graphics/background/register.jpg").getImage();
	
	/**
	 * ͼƬ���
	 */
	private static final int IMG_BG_W = IMG_BG.getWidth(null);
	
	/**
	 * ͼƬ�߶�
	 */
	private static final int IMG_BG_H = IMG_BG.getHeight(null);
	
	/**
	 * ������
	 */
	private static final int WINDOW_WIDTH = 300;
	
	/**
	 * ����߶�
	 */
	private static final int WINDOW_HEIGHT = 200;
	
	/**
	 * �û��������
	 */
	private JTextField textUserName;
	
	/**
	 * �ǳ������
	 */
	private JTextField textNickName;
	
	/**
	 * ���������
	 */
	private JPasswordField textPassword;
	
	/**
	 * ע�ᰴť
	 */
	private JButton btnRegister;
	
	/**
	 * �ͻ��˽ӿ�
	 */
	private IClient client;
	
	public FrmRegister(IClient client) {
		this.client = client;
		
		initialized();
		
		// ����panel����
		JPanel panel0 = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		// ����Panel͸��
		panel0.setBackground(null);
		panel0.setOpaque(false);
		panel1.setBackground(null);
		panel1.setOpaque(false);
		panel2.setBackground(null);
		panel2.setOpaque(false);
		panel3.setBackground(null);
		panel3.setOpaque(false);
		
		// ���panel��frame
		add(panel0);
		add(panel1);
		add(panel2);
		add(panel3);
		// ��������panel
		panel0.add(new JLabel("�û���"));
		panel0.add(textUserName);
		panel1.add(new JLabel("  �ǳ�  "));
		panel1.add(textNickName);
		panel2.add(new JLabel("  ����  "));
		panel2.add(textPassword);
		panel3.add(btnRegister);
		
		// ����¼�����
		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String userName = textUserName.getText();
				String nickName = textNickName.getText();
				String password = new String(textPassword.getPassword());
				Message msg = new Message("anonymous", "server", "requestRegister", 
						userName + "|" + nickName + "|" + password);
				client.send(msg);
				FrmRegister.this.setVisible(false);
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
		this.setTitle("Register");
		// ���ô����С���ɱ�
		this.setResizable(false);
		// ���ô��ڴ�С
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		// ���ô���λ��
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width - WINDOW_WIDTH) >> 1, (height - WINDOW_HEIGHT) >> 1);
		// ���ò���
		this.setLayout(new GridLayout(4, 1));
		
		// ��ʼ��������밴ť
		textUserName = new JTextField(10);
		textNickName = new JTextField(10);
		textPassword = new JPasswordField(10);
		btnRegister = new JButton("ע��");
	}
	
}
