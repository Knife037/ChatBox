package ui;

import java.util.List;

import entity.Message;

public interface IFrmMain {

	public void showMsg(Message msg); 
	
	public void updateUsersList(List<String> users, List<Integer> usersPortrait);
}
