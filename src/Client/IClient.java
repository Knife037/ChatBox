package Client;

import entity.Message;

public interface IClient {

	/**
	 * ������Ϣ
	 * @param msg Ҫ���͵���Ϣ 
	 */
	public boolean send(Message msg);
	
}
