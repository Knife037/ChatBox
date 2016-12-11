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
	 * ����ͼƬ
	 */
	private static final Image IMG_BG = new ImageIcon("graphics/background/register.jpg").getImage();
	
	/**
	 * ���ڿ��
	 */
	private static final int WINDOW_WIDTH = 300;
	
	/**
	 * ���ڸ߶�
	 */
	private static final int WINDOW_HEIGHT = 800;

	/**
	 * ͷ���ǩ
	 */
	private LabelPortrait portraitLabel;

	/**
	 * �û���Ϣ��ǩ
	 */
	private JLabel textUserInfo;
	
	/**
	 * �����û��б�
	 */
	private ListUsers usersList;
	
	/**
	 * �û��б�����ģ��
	 */
	private DefaultListModel listModel;
	
	/**
	 * �û�s��
	 */
	private List<String> usersName; 
	
	/**
	 * �û��������ڼ�ֵ��
	 */
	private HashMap<String, IFrmChat> frmChats;
	/**
	 * �ͻ��˽ӿ�
	 */
	private IClient client;
	
	/**
	 * �û�
	 */
	private String user;
	
	/**
	 * �û�ͷ��
	 */
	private int userPortrait;
	
	public FrmMain(IClient client, String user, int userPortrait) {
		this.client = client;
		this.user = user;
		this.userPortrait = userPortrait;
		
		// ��ʼ�����Լ�����
		initialized();
		
		JPanel headerPanel = new JPanel();
		// ������
		ScrollPane scrollPane = new ScrollPane();
		
		//����ͷ���ǩλ���Լ���С
		portraitLabel.setBounds(10, 10, 100, 100);
		textUserInfo.setBounds(120, 40, 150, 20);
		textUserInfo.setFont(new Font("default", Font.BOLD, 20));
		scrollPane.setBounds(10, 140, 275, 600);
		scrollPane.add(usersList);
		
		// ��ӵ�frame
		add(scrollPane);
		add(portraitLabel);
		add(textUserInfo);
		
		portraitLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO �����޸ĸ�����Ϣ����
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
		// ������ʾ������Panel
		this.setContentPane(new PanelBackground(IMG_BG));
		// ���ô������
		this.setTitle("ChatBox");
		// ���ô����С���ɱ�
		this.setResizable(false);
		// ���ô��ڴ�С
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		// ���ô���λ��
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width - WINDOW_WIDTH) >> 1, (height - WINDOW_HEIGHT) >> 1);
		// TODO ����Ĭ�Ϲرղ���
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ò���
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
