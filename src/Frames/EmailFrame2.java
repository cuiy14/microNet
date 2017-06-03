package Frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import util.Session;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class EmailFrame2 extends EmailFrame {
	private static Thread timer = new Thread() {
		public void run() {
			// do the below thing all the time
			while (true) {
				try {
					sleep(30000);
					if(Session.getConSend()){
					SendMessages();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	public EmailFrame2() {
		super();
		if(!Session.getConSend()){
			JButton contiSend = new JButton("自动发送");
			contiSend.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					doContiSend();
				}
			});
			southPanel.add(contiSend);
			timer.start();	
		}
		else{
			addStopSend();
			validate();
//			timer.wait();
			Session.setConSend(false);
		}

		validate();
	}

	// actionListener
	public void doContiSend() {
		Session.setConSend(true);
		southPanel.remove(1);
		addStopSend();
		validate();
		SendMessages();
//		if (!timer.isAlive())
//			timer.start();
//		else
//			timer.notify();
		JOptionPane.showMessageDialog(southPanel, "自动发送已开启");
	}

	public void doStopSend() throws InterruptedException {
		southPanel.remove(1);
		addContiSend();
		validate();
//		timer.wait();
		Session.setConSend(false);
		JOptionPane.showMessageDialog(southPanel, "自动发送已停止,已成功发送"+send+"封提醒！");
	}

	public void addContiSend() {
		JButton contiSend = new JButton("自动发送");
		contiSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doContiSend();
			}
		});
		southPanel.add(contiSend);
	}

	public void addStopSend() {
		JButton stopSend = new JButton("停止发送");
		stopSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					doStopSend();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		southPanel.add(stopSend);
	}

	public static void main(String args[]) {
		EmailFrame2 haha = new EmailFrame2();
		haha.setVisible(true);
	}
}
