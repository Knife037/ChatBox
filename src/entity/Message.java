package entity;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ������
	 */
	public String sender;
	
	/**
	 * ������
	 */
	public String receiver;

	/**
	 * ��Ϣ����
	 */
	public String type;
	
	/**
	 * ��Ϣ����
	 */
	public String content;
	
	/**
	 * ��������
	 * @param sender ������
	 * @param receiver ������
	 * @param type ��Ϣ���͡�
	 * @param content �ı�����
	 */
	public Message(String sender, String receiver, String type, String content) {
		this.sender = sender;
		this.receiver = receiver;
		this.type = type;
		this.content = content;
	}

}
