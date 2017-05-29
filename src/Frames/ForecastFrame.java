package Frames;

import javax.swing.JFrame;

import Panels.ForecastPanel;

public class ForecastFrame extends JFrame{
	public ForecastFrame(){
		this.getContentPane().add(new ForecastPanel());
		this.setSize(700, 600);
		this.setVisible(true);
		this.validate();		
	}

}
