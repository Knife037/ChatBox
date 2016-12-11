package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;

/**
 * ͷ���ǩ
 * @author Administrator
 *
 */
public class LabelPortrait extends JLabel {

	/**
	 * ��ǩ�߶�
	 */
	private static final int LABEL_WIDTH = 100;
	
	/**
	 * ��ǩ���
	 */
	private static final int LABEL_HEIGHT = 100;
	
	/**
	 * ͷ��
	 */
	private Image protrait;
	
	/**
	 * ͼƬ���
	 */
	private int imgWidth;
	
	/**
	 * ͼƬ�߶�
	 */
	private int imgHeight;
	
	/**
	 * ������
	 * @param protrait ͷ��
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
