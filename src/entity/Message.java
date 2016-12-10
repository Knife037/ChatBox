package entity;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 发送者
	 */
	public String sender;
	
	/**
	 * 发送者头像
	 */
	public int senderPortrait;

	/**
	 * 接收者
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
	 * 消息类型
	 */
	public String type;
	
	/**
	 * 消息类型
	 */
	public String content;
	
	/**
	 * 构造器。
	 * @param sender 发送者
	 * @param receiver 接受者
	 * @param senderPortrait 发送者头像
	 * @param type 消息类型。
	 * @param content 文本内容
	 */
	public Message(String sender, String receiver, int senderPortrait, String type, String content) {
		this.sender = sender;
		this.receiver = receiver;
		this.senderPortrait = senderPortrait;
		this.type = type;
		this.content = content;
	}

}
