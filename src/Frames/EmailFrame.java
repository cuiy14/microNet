package Frames;
/**
 * the frame for sending emails. the defaulting setting message should be more specific with real data
 * need to imporve -- once got wrong , it won't work any more except for a new Frame
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sun.xml.internal.ws.resources.SenderMessages;

import javafx.scene.layout.Border;

import util.MailSend;

public class EmailFrame extends JFrame{
	static int send=0;		// record the mails sent
//	MailSend mailSend;
	JPanel northPanel=new JPanel();
	JPanel centerPanel=new JPanel();
	JPanel southPanel=new JPanel();
	JPanel contentPanel=new JPanel();
	static JTextField textAd;
	static JPasswordField textPs;
	static JTextField textTo;
	static JTextField textSub;
	static JTextArea textMes;
	public EmailFrame(){
		this.setBackground(new Color(23,119,178));
		int distance=30;
//		mailSend = new MailSend("config.properties");
		contentPanel.setLayout(new BorderLayout());
		// northPanel
		northPanel.setLayout(new GridLayout(4, 1));
		JLabel address = new JLabel("寄信人");
		textAd= new JTextField();
		textAd.setColumns(30);
		Box box1=Box.createHorizontalBox();
		box1.add(address);
		box1.add(Box.createHorizontalStrut(distance));
		box1.add(textAd);
		northPanel.add(box1);
		JLabel password=new JLabel("密码");
		textPs= new JPasswordField(30);
		textPs.setEditable(true);
		Box box2=Box.createHorizontalBox();
		box2.add(password);
		box2.add(Box.createHorizontalStrut(distance+13));
		box2.add(textPs);
		northPanel.add(box2);
		JLabel sendto= new JLabel("收信人");
		textTo= new JTextField();
		textTo.setColumns(30);
		Box box3=Box.createHorizontalBox();
		box3.add(sendto);
		box3.add(Box.createHorizontalStrut(distance));
		box3.add(textTo);
		northPanel.add(box3);
		JLabel subject= new JLabel("主题");
		textSub = new JTextField(30);
		Box box4=Box.createHorizontalBox();
		box4.add(subject);
		box4.add(Box.createHorizontalStrut(distance+13));
		box4.add(textSub);
		northPanel.add(box4);
		contentPanel.add(northPanel, BorderLayout.NORTH);
		// center panel
		JLabel message=new JLabel("消息");
		centerPanel.add(message);
		textMes = new JTextArea(10, 50);
		centerPanel.add(Box.createHorizontalStrut(43));
		centerPanel.add(textMes);

		contentPanel.add(centerPanel, BorderLayout.CENTER);
		// south panel
		JButton confirm= new JButton("发送");
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SendMessages();
			}
		});
		southPanel.add(confirm);
		contentPanel.add(southPanel, BorderLayout.SOUTH);
		setDefaultMessage();
		this.getContentPane().add(contentPanel);
		this.setTitle("发送邮件");
		this.pack();
		this.setVisible(true);
		this.validate();
	}
	// action listener
	public static  void SendMessages(){
		String user = textAd.getText();
		String password= new String(textPs.getPassword());
		String mailTo=textTo.getText();
		String subject= textSub.getText();
		String text=textMes.getText();
		System.out.println(password);
		MailSend mailSend = new MailSend("config.properties");
		mailSend.SendMessages(user, password, mailTo, subject, text);
		send++;
	}
	
	// set the default message
	public void setDefaultMessage(){
		textAd.setText("cuiy379@gmail.com");
		textTo.setText("18813119878@163.com");
		textSub.setText("微电网信息日常提醒");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		textMes.setText("微电网运行一切正常！\n"+df.format(new Date()));
	}
	public void setMessage(String message){
		textMes.setText(message);
	}
	public void setSubject(String subject){
		textSub.setText(subject);
	}
	// test
	public static void main(String args[]){
		EmailFrame emailFrame=new EmailFrame();
	}
}
