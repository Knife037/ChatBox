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
	 * 背景图片
	 */
	private static final Image IMG_BG = new ImageIcon("graphics/background/login.jpg").getImage();
	
	/**
	 * 窗体宽度
	 */
	private static final int WINDOW_WIDTH = 300;
	
	/**
	 * 窗体高度
	 */
	private static final int WINDOW_HEIGHT = 150;
	
	/**
	 * 用户名输入框
	 */
	private JTextField textUserName;
	
	/**
	 * 密码输入框
	 */
	private JPasswordField textPassword;
	
	/**
	 * 注册按钮
	 */
	private JButton btnRegister;
	
	/**
	 * 登录按钮
	 */
	private JButton btnLogin;
	
	/**
	 * 客户端接口
	 */
	private IClient client;
	
	public FrmLogin(IClient client) {
		this.client = client;
		
		// 初始化窗体以及对象
		initialized();
		
		// 创建panel对象
		JPanel panel0 = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		// 设置Panel透明
		panel0.setBackground(null);
		panel0.setOpaque(false);
		panel1.setBackground(null);
		panel1.setOpaque(false);
		panel2.setBackground(null);
		panel2.setOpaque(false);
		
		// 添加panel到frame
		add(panel0);
		add(panel1);
		add(panel2);
		// 添加组件到panel
		panel0.add(new JLabel("用户名"));
		panel0.add(textUserName);
		panel1.add(new JLabel("  密码  "));
		panel1.add(textPassword);
		panel2.add(btnRegister);
		panel2.add(btnLogin);
		
		// 添加事件监听
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
		
		// 设置可见
		this.setVisible(true);
	}
	
	/**
	 * 初始化窗体以及对象
	 */
	private void initialized() {
		// 设置显示背景的Panel
		this.setContentPane(new PanelBackground(IMG_BG));
		// 设置窗体标题
		this.setTitle("Login");
		// 设置窗体大小不可变
		this.setResizable(false);
		// 设置窗口大小
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		// 设置窗口位置
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width - WINDOW_WIDTH) >> 1, (height - WINDOW_HEIGHT) >> 1);
		// 设置默认关闭操作
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置布局
		this.setLayout(new GridLayout(3, 1));
		
		// 初始化输入框与按钮
		textUserName = new JTextField(10);
		textPassword = new JPasswordField(10);
		btnRegister = new JButton("注册");
		btnLogin = new JButton("登录");
	}
	
	public static void main(String[] args) {
		new FrmLogin(new ClientSimulate()); 
	}

}
