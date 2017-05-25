package main;

import java.awt.Graphics;
import java.awt.Image;


import javax.swing.JPanel;



/**
 * the background panel for log in frame
 * @author drift
 *
 */
public class BackgroundPanel extends JPanel{
	private Image image; // background picture
	// constructor
	public BackgroundPanel(){
		setOpaque(false);	
		setLayout(null); 	// use absolute layout
	}
	/**
	 * set image for the background
	 */
	public void setImage(Image image){
		this.image = image;
	}
	/**
	 * paint the background
	 */
	protected void paintComponent(Graphics g){
		if(image != null)
			g.drawImage(image, 0, 0, this);
		super.paintComponent(g);
	}
	/**
	 * get the width and height of image
	 */


}
