package Frames;

import java.awt.Color;

import javax.swing.JFrame;

import Panels.ForecastPanel;

public class ForecastFrame extends JFrame{
	public ForecastFrame(){
		super("运行指导");
		this.setBackground(new Color(23,119,178));
		this.getContentPane().add(new ForecastPanel());
		this.setSize(500,450);
		this.setVisible(true);
		this.validate();		
	}
	public static void main(String args[]){
		new ForecastFrame();
	}
}
