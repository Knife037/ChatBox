package entity;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 发送者
	 */
	public String sender;
	
	/**
	 * 接收者
	 */
	public String receiver;

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
	 * @param type 消息类型。
	 * @param content 文本内容
	 */
	public Message(String sender, String receiver, String type, String content) {
		this.sender = sender;
		this.receiver = receiver;
		this.type = type;
		this.content = content;
	}

}
