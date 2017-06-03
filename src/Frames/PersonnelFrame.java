package Frames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.sun.scenario.effect.DropShadow;

import Panels.PersonnelPanel;
import util.Session;

public class PersonnelFrame extends JFrame {
	JPanel northPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel southPanel= new JPanel();
	JPanel content = new JPanel();
	public PersonnelFrame() {
		// northPanel
		super("人事管理");
		northPanel.add(new JLabel("用户一览表"), BorderLayout.CENTER);
		// centerPanel
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add((new PersonnelPanel().getPanel(Session.getUser().getIsRoot()
				)),BorderLayout.CENTER);
		// southPanel
		if(!Session.getUser().getIsRoot()){
		JButton subs= new JButton("显示用户密码");
		subs.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				doShow();	
			}
		});
		southPanel.add(subs, BorderLayout.CENTER);
		}
		else 
		{
			JButton subs=new JButton("隐藏用户密码");
			subs.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					doHide();
				}
			});
			southPanel.add(subs, BorderLayout.CENTER);
		}
		// set content panel
		content.setLayout(new BorderLayout());
		content.add(northPanel, BorderLayout.NORTH);
		content.add(centerPanel, BorderLayout.CENTER);
		content.add(southPanel, BorderLayout.SOUTH);
		this.setSize(500, 300);
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.setVisible(true);
	}
	public void doShow(){
		JFrame frame = new JFrame("输入");
		String title="键入";
		String message="您还是普通用户，请键入管理员密码";
		int messageType = JOptionPane.QUESTION_MESSAGE;
		String result="";
		result= JOptionPane.showInputDialog(frame,message,title,messageType);
		if(result.equals(Session.getPassword()))
		{	
			centerPanel.remove(0);
			centerPanel.add((new PersonnelPanel()).getPanel(true),BorderLayout.CENTER);
			southPanel.remove(0);
			JButton subs=new JButton("隐藏用户密码");
			subs.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					doHide();
				}
			});
			southPanel.add(subs, BorderLayout.CENTER);
		this.validate();
		}
		else 
		{
			JOptionPane.showMessageDialog(frame, "输入密码错误");
		}		
	}
	public void doHide(){
		centerPanel.remove(0);
		centerPanel.add((new PersonnelPanel()).getPanel(false),BorderLayout.CENTER);
		southPanel.remove(0);
		JButton subs= new JButton("显示用户密码");
		subs.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				doShow();	
			}
		});
		southPanel.add(subs, BorderLayout.CENTER);
		this.validate();
	}
	public static void main(String args[]){
		new PersonnelFrame();
	}
}
