package ui;

import javax.swing.ImageIcon;

public class Img {

	public static ImageIcon[] portrait;
	
	static {
		portrait = new ImageIcon[7];
		for (int i = 0; i < 7; i++) {
			portrait[i] = new ImageIcon("graphics/portrait/" + Integer.toString(i) + ".gif");
		}
	}
	
}
