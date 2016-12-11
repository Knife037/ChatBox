package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * ø…“‘œ‘ æ±≥æ∞µƒPanel
 * @author Administrator
 *
 */
public class PanelBackground extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * ±≥æ∞Õº∆¨
	 */
	private Image img;
	
	/**
	 * Õº∆¨øÌ∂»
	 */
	private int width;
	
	/**
	 * Õº∆¨∏ﬂ∂»
	 */
	private int height;
	
	public PanelBackground(Image img) {
		this.img = img;
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
	}
	
	@Override
	public void paintComponent(Graphics g) {		
		super.paintComponents(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0, width, height, null);
	}
}
