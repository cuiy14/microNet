package Panels;

import javax.swing.JLabel;
/**
 * this is an empty panel, to fill up some gap
 */
import javax.swing.JPanel;

import com.sun.jmx.snmp.tasks.ThreadService;

public class EmptyPanel extends JPanel{
	public EmptyPanel(){
		JLabel label= new JLabel("请选择操作");
		this.add(label);
		this.setVisible(true);
		this.validate();
	}
}
