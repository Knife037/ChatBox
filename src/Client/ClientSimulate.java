package Client;

import entity.Message;

public class ClientSimulate implements IClient {

	@Override
	public boolean send(Message msg) {
		return true;
	}

}
