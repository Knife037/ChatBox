package client;

import entity.Message;

public interface IClient {

	/**
	 * 发送消息
	 * @param msg 要发送的消息 
	 */
	public boolean send(Message msg);
	
	public Message readMessage();
	
	public void listen();
	
	public String getName();
	
}
