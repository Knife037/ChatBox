package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import client.IClient;
import entity.Message;

public class FrmChat extends JFrame implements IFrmChat {

	/**
	 * ���ڿ��
	 */
	private static final int WINDOW_WIDTH = 700;
	
	/**
	 * ���ڸ߶�
	 */
	private static final int WINDOW_HEIGHT = 500;
	
	/**
	 * ����ͼƬ
	 */
	private static final Image IMG_BG = new ImageIcon("graphics/background/chat.jpg").getImage();
	
	private JTextPane textChat;
	
	private Document doc;
	
	private JTextField textMsg;
	
	private JButton btnSend;
	
	private String sender;
	
	private int senderPortrait;
	
	private String receiver;
	
	private IClient client;
	
	public FrmChat(String sender, String receiver, int senderPortrait, IClient client) {
		this.sender = sender;
		this.receiver = receiver;
		this.client = client;
		this.senderPortrait = senderPortrait;
		// ��ʼ��
		initialized();
		
		textChat.setBackground(null);
		textChat.setOpaque(false);
		textChat.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textChat.setBounds(50, 60,600, 320);
		textChat.setEnabled(false);
		textChat.setFont(new Font("default", Font.ROMAN_BASELINE, 20));
		textChat.setForeground(Color.BLACK);
		
		textMsg.setFont(new Font("default", Font.BOLD, 18));
		textMsg.setBounds(50, 390, 490, 30);
		textMsg.setBackground(null);
		textMsg.setOpaque(false);
		textMsg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btnSend.setBounds(550, 390, 100, 30);
		
		add(textChat);
		add(btnSend);
		add(textMsg);
		
		// ����¼�����
		btnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Message msg = new Message(sender, receiver, senderPortrait, "normal", textMsg.getText());
				client.send(msg);
				FrmChat.this.showMsg(msg);
				textMsg.setText("");
			}
			
		});
		
		textMsg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Message msg = new Message(sender, receiver, senderPortrait, "normal", textMsg.getText());
				client.send(msg);
				FrmChat.this.showMsg(msg);
				textMsg.setText("");
			}
		});
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				FrmChat.this.setVisible(false);
			}
			
		});
		
		this.setVisible(true);
	}
	
	/**
	 * ��ʼ�������Լ�����
	 */
	public void initialized() {
		// ������ʾ������Panel
		this.setContentPane(new PanelBackground(IMG_BG));
		// ���ô������
		this.setTitle(receiver);
		// ���ô����С���ɱ�
		this.setResizable(false);
		// ���ô��ڴ�С
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		// ���ô���λ��
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width - WINDOW_WIDTH) >> 1, (height - WINDOW_HEIGHT) >> 1);
		// ���ò���
		this.setLayout(null);
		
		textChat = new JTextPane();
		doc = textChat.getDocument();
		textMsg = new JTextField();
		btnSend = new JButton("����");
	}
	
	@Override
	public void showMsg(Message msg) {
		int senderPortrait = msg.getSenderPortrait();
		String content = msg.getContent();
		String sender = msg.getSender();
		
		insertIcon(Img.portrait[senderPortrait]);
		insertString(sender);
		insertString(content);
		
	}
	
	/**
	 * ����ͼƬ
	 * @param portrait
	 */
	private void insertIcon(ImageIcon img) {
		textChat.setCaretPosition(doc.getLength());
		textChat.insertIcon(img);
	}
	
	/**
	 * �����ı�
	 * @param msg
	 */
	private void insertString(String msg) {
		try {
			doc.insertString(doc.getLength(), "\t" + msg + "\n", null);
		} catch (BadLocationException e) {
			System.out.println("��Ϣ����ʧ��");
			e.printStackTrace();
			return;
		}
	}
	
	@Override
	public void getFocus() {
		this.requestFocus();
	}
	
	@Override
	public boolean getVis() {
		return this.isVisible();
	}
	@Override
	public void setVis(boolean b) {
		this.setVisible(b);
		
	}
}
