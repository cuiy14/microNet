package Frames;

import javax.swing.JFrame;

import Panels.UtilityPanel2;

public class UtilityFrame extends JFrame{
	public UtilityFrame(){
		this.getContentPane().add(new UtilityPanel2());
		this.setVisible(true);
		this.setSize(600, 500);
		this.validate();
	}
}
