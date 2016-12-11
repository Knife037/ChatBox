package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;

/**
 * 头像标签
 * @author Administrator
 *
 */
public class LabelPortrait extends JLabel {

	/**
	 * 标签高度
	 */
	private static final int LABEL_WIDTH = 100;
	
	/**
	 * 标签宽度
	 */
	private static final int LABEL_HEIGHT = 100;
	
	/**
	 * 头像
	 */
	private Image protrait;
	
	/**
	 * 图片宽度
	 */
	private int imgWidth;
	
	/**
	 * 图片高度
	 */
	private int imgHeight;
	
	/**
	 * 构造器
	 * @param protrait 头像
	 */
	public LabelPortrait(Image protrait) {
		setProtrait(protrait);
	}
	
	public void setProtrait(Image protrait) {
		this.protrait = protrait;
		this.imgWidth = protrait.getWidth(null);
		this.imgHeight = protrait.getHeight(null);
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(protrait, 0, 0, LABEL_WIDTH, LABEL_HEIGHT, 0, 0, imgWidth, imgHeight, null);
	}

}
