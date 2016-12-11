package server.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import server.service.Service;

public class FrmServer extends JFrame implements IFrmServer {
	
	private static final int WINDOW_WIDTH = 600;
	
	private static final int WINDOW_HEIGHT = 400;
	
	private JTextArea text; 
	
	private JScrollPane scrollPane;
	
	private JTextField textCommand;
	
	public FrmServer() {
		
		initialized();
		
		scrollPane.setBounds(10, 10, 570, 315);
		text.setBackground(Color.BLACK);
		text.setForeground(Color.WHITE);
		text.setFont(new Font("default", Font.BOLD, 15));
		text.setEnabled(false);
		
		textCommand.setCaretColor(Color.WHITE);
		textCommand.setFont(new Font("default", Font.BOLD, 15));
		textCommand.setBackground(Color.BLACK.brighter());
		textCommand.setForeground(Color.WHITE);
		textCommand.setBounds(10, 335, 570, 25);
		textCommand.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		add(scrollPane);
		add(textCommand);
		
		textCommand.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				printMsg("> " + textCommand.getText());
			}
			
		});
		
		this.setVisible(true);
		
		new Service(this);

	}
	
	private void initialized() {
		this.getContentPane().setBackground(Color.BLACK.brighter());
		// ���ô������
		this.setTitle("Server");
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
		this.setLayout(null);

		text = new JTextArea();
		scrollPane = new JScrollPane(text);
		textCommand = new JTextField();
	}
	
	@Override
	public void printMsg(String msg) {
		text.append(msg + "\n");
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		new FrmServer();
	}
	
}
