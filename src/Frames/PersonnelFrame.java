package Frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import Panels.PersonnelPanel;

public class PersonnelFrame extends JFrame{
	public PersonnelFrame(){
	this.setSize(500, 400);
	this.getContentPane().add((new PersonnelPanel()).getPanel(), BorderLayout.CENTER);
	this.setVisible(true);
	}
}
