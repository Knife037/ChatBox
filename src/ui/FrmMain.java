package ui;

import java.awt.Font;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.IClient;
import entity.Message;

public class FrmMain extends JFrame implements IFrmMain {

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
	 * 用户s名
	 */
	private List<String> usersName; 
	
	/**
	 * 用户名、窗口键值对
	 */
	private HashMap<String, IFrmChat> frmChats;
	/**
	 * 客户端接口
	 */
	private IClient client;
	
	/**
	 * 用户
	 */
	private String user;
	
	/**
	 * 用户头像
	 */
	private int userPortrait;
	
	public FrmMain(IClient client, String user, int userPortrait) {
		this.client = client;
		this.user = user;
		this.userPortrait = userPortrait;
		
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
		
		usersList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String userName = usersName.get(usersList.getSelectedIndex());
				if(e.getClickCount() == 2) {
					showChatWindow(userName);
				}
			}
		});
		
		
		this.setVisible(true);
	}
	
	private void showChatWindow(String userName) {
		if(!frmChats.containsKey(userName)) {
			IFrmChat frmChat = new FrmChat(user, userName, userPortrait, client);
			frmChats.put(userName, frmChat);
		} else {
			IFrmChat frmChat = frmChats.get(userName);
			if(!frmChat.getVis()) {
				frmChat.setVis(true);
			} else {
				frmChat.getFocus();
			}
		}
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
		portraitLabel = new LabelPortrait(new ImageIcon("graphics/portrait/0.gif").getImage());
		textUserInfo = new JLabel("Knife037");
		listModel = new DefaultListModel();
		usersList = new ListUsers(listModel);
		usersName = new ArrayList<String>();
		frmChats = new HashMap<String, IFrmChat>();
	}
	
	@Override
	public void showMsg(Message msg) {
		
		String sender = msg.getSender();
		showChatWindow(sender);
		frmChats.get(sender).showMsg(msg);
	}

	@Override
	public void updateUsersList(List<String> users, List<Integer> usersPortrait) {
		listModel.clear();
		usersName.clear();
		
		for (int i = 0; i < users.size(); i++) {
			user = users.get(i);
			userPortrait = usersPortrait.get(i);
			listModel.addElement(new Object[]{new ImageIcon("graphics/portrait/" + userPortrait +".gif"), user});
			usersName.add(user);
		}
		
	}
	
}
