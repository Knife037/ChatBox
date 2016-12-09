package ui;

import java.awt.Font;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.ClientSimulate;
import Client.IClient;

public class FrmMain extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 背景图片
	 */
	private static final Image IMG_BG = new ImageIcon("graphics/background/register.jpg").getImage();
	
	/**
	 * 窗口宽度
	 */
	private static final int WINDOW_WIDTH = 300;
	
	/**
	 * 窗口高度
	 */
	private static final int WINDOW_HEIGHT = 800;

	/**
	 * 头像标签
	 */
	private LabelPortrait portraitLabel;

	/**
	 * 用户信息标签
	 */
	private JLabel textUserInfo;
	
	/**
	 * 在线用户列表
	 */
	private ListUsers usersList;
	
	/**
	 * 用户列表数据模型
	 */
	private DefaultListModel listModel;
	
	/**
	 * 客户端接口
	 */
	private IClient client;
	
	public FrmMain(IClient client) {
		this.client = client;
		
		// 初始化窗以及对象
		initialized();
		
		JPanel headerPanel = new JPanel();
		// 滚动条
		ScrollPane scrollPane = new ScrollPane();
		
		//设置头像标签位置以及大小
		portraitLabel.setBounds(10, 10, 100, 100);
		textUserInfo.setBounds(120, 40, 150, 20);
		textUserInfo.setFont(new Font("default", Font.BOLD, 20));
		scrollPane.setBounds(10, 140, 275, 600);
		scrollPane.add(usersList);
		
		// 添加到frame
		add(scrollPane);
		add(portraitLabel);
		add(textUserInfo);
		
		portraitLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 创建修改个人信息窗口
			}
			
		});
		
		this.setVisible(true);
	}
	
	public void initialized() {
		// 设置显示背景的Panel
		this.setContentPane(new PanelBackground(IMG_BG));
		// 设置窗体标题
		this.setTitle("ChatBox");
		// 设置窗体大小不可变
		this.setResizable(false);
		// 设置窗口大小
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		// 设置窗口位置
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width - WINDOW_WIDTH) >> 1, (height - WINDOW_HEIGHT) >> 1);
		// TODO 设置默认关闭操作
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置布局
		this.setLayout(null);
		
		// TODO
		portraitLabel = new LabelPortrait(new ImageIcon("graphics/portrait/0000.gif").getImage());
		textUserInfo = new JLabel("Knife037");
		listModel = new DefaultListModel();
		usersList = new ListUsers(listModel);
		
		listModel.addElement(new Object[]{new ImageIcon("graphics/portrait/0000.gif"), "Friend001"});
		listModel.addElement(new Object[]{new ImageIcon("graphics/portrait/0002.gif"), "Friend003"});
		listModel.addElement(new Object[]{new ImageIcon("graphics/portrait/0003.gif"), "Friend004"});
		listModel.addElement(new Object[]{new ImageIcon("graphics/portrait/0004.gif"), "Friend005"});
		listModel.addElement(new Object[]{new ImageIcon("graphics/portrait/0005.gif"), "Friend006"});
		listModel.addElement(new Object[]{new ImageIcon("graphics/portrait/0006.gif"), "Friend007"});
		listModel.addElement(new Object[]{new ImageIcon("graphics/portrait/0000.gif"), "Friend008"});
		listModel.addElement(new Object[]{new ImageIcon("graphics/portrait/0001.gif"), "Friend009"});
		listModel.addElement(new Object[]{new ImageIcon("graphics/portrait/0002.gif"), "Friend010"});
		listModel.addElement(new Object[]{new ImageIcon("graphics/portrait/0003.gif"), "Friend011"});
		
	}
	
	public static void main(String[] args) {
		new FrmMain(new ClientSimulate());
	}
}
