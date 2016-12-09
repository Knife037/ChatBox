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
	 * �ͻ��˽ӿ�
	 */
	private IClient client;
	
	public FrmMain(IClient client) {
		this.client = client;
		
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
		
		this.setVisible(true);
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
