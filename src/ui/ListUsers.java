package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * 列表类，色彩搭配较差，需要调整
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
public class ListUsers extends JList {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public ListUsers(DefaultListModel model) {
		super(model);
		this.setCellRenderer(new IconCellRenderer());
		setBackground(null);
	}
	
	@SuppressWarnings("serial")
	private class IconCellRenderer extends JLabel implements ListCellRenderer {

		public Component getListCellRendererComponent (
				JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) {
			Object[] cell = (Object[]) obj;
			setIcon((Icon)cell[0]);
			setText(cell[1].toString());
			setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			
			if(isSelected) {
				setBackground(Color.cyan);
				setForeground(Color.blue);
			} else {
				setBackground(null);
				setForeground(Color.blue);
			}
			setEnabled(list.isEnabled());
			setFont(new Font("default", Font.ROMAN_BASELINE, 13));
			setOpaque(true);
			return this;
		}
		
	}
	
}
