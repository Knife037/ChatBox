package entity;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ������
	 */
	public String sender;
	
	/**
	 * ������ͷ��
	 */
	public int senderPortrait;

	/**
	 * ������
	 */
	public String receiver;

	public int getSenderPortrait() {
		return senderPortrait;
	}

	public void setSenderPortrait(int senderPortrait) {
		this.senderPortrait = senderPortrait;
	}
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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
	 * @param senderPortrait ������ͷ��
	 * @param type ��Ϣ���͡�
	 * @param content �ı�����
	 */
	public Message(String sender, String receiver, int senderPortrait, String type, String content) {
		this.sender = sender;
		this.receiver = receiver;
		this.senderPortrait = senderPortrait;
		this.type = type;
		this.content = content;
	}

}
