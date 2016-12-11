package ui;

import entity.Message;

public interface IFrmChat {

	public void showMsg(Message msg);
	
	public void getFocus();
	
	public boolean getVis();
	
	public void setVis(boolean b);
	
}
