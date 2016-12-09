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
	 * 背景图片
	 */
	private static final Image IMG_BG = new ImageIcon("graphics/background/register.jpg").getImage();
	
	/**
	 * 图片宽度
	 */
	private static final int IMG_BG_W = IMG_BG.getWidth(null);
	
	/**
	 * 图片高度
	 */
	private static final int IMG_BG_H = IMG_BG.getHeight(null);
	
	/**
	 * 窗体宽度
	 */
	private static final int WINDOW_WIDTH = 300;
	
	/**
	 * 窗体高度
	 */
	private static final int WINDOW_HEIGHT = 200;
	
	/**
	 * 用户名输入框
	 */
	private JTextField textUserName;
	
	/**
	 * 昵称输入框
	 */
	private JTextField textNickName;
	
	/**
	 * 密码输入框
	 */
	private JPasswordField textPassword;
	
	/**
	 * 注册按钮
	 */
	private JButton btnRegister;
	
	/**
	 * 客户端接口
	 */
	private IClient client;
	
	public FrmRegister(IClient client) {
		this.client = client;
		
		initialized();
		
		// 创建panel对象
		JPanel panel0 = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		// 设置Panel透明
		panel0.setBackground(null);
		panel0.setOpaque(false);
		panel1.setBackground(null);
		panel1.setOpaque(false);
		panel2.setBackground(null);
		panel2.setOpaque(false);
		panel3.setBackground(null);
		panel3.setOpaque(false);
		
		// 添加panel到frame
		add(panel0);
		add(panel1);
		add(panel2);
		add(panel3);
		// 添加组件到panel
		panel0.add(new JLabel("用户名"));
		panel0.add(textUserName);
		panel1.add(new JLabel("  昵称  "));
		panel1.add(textNickName);
		panel2.add(new JLabel("  密码  "));
		panel2.add(textPassword);
		panel3.add(btnRegister);
		
		// 添加事件监听
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
		this.setTitle("Register");
		// 设置窗体大小不可变
		this.setResizable(false);
		// 设置窗口大小
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		// 设置窗口位置
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width - WINDOW_WIDTH) >> 1, (height - WINDOW_HEIGHT) >> 1);
		// 设置布局
		this.setLayout(new GridLayout(4, 1));
		
		// 初始化输入框与按钮
		textUserName = new JTextField(10);
		textNickName = new JTextField(10);
		textPassword = new JPasswordField(10);
		btnRegister = new JButton("注册");
	}
	
}
