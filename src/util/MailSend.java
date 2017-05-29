package util;
import java.io.IOException;
/**
 * this file is to send mail to somewhere. 
 * formatting the content,subject & get the info from the config
 */
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import com.sun.xml.internal.ws.resources.SenderMessages;

import config.ReadConfig;

public class MailSend {
	private ReadConfig readConfig;
	// constructors
	public MailSend(String config){		// this parameter is the path of config file
		try {
			this.readConfig = new ReadConfig(config);
		} catch (IOException e) {
			System.err.println("config files for emailsend not found!");
		}
	}
	
	public void SendMessages(String user, String password, String mailTo,String subject, String text){
//		String user = readConfig.getMailFrom(); // get the mail sender
		String receiver =null;	// get the mail receiver
		if(mailTo.equals(null))
			receiver = readConfig.getMailTo();
		else
			receiver = mailTo;		  
		// Get the session object
		Session session = mailSettings(user,password);
		// Compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(message, "text/html");
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, 
					new InternetAddress(receiver));
			message.setSubject(subject);
			message.setText(text);
			// send the message
			Transport.send(message);
			JOptionPane.showMessageDialog(null, "邮件发送成功！");
		} catch (MessagingException e) {
			e.printStackTrace();
			// give the warning
			JOptionPane.showMessageDialog(null, "邮件发送失败！");
		}  		  
	}
	
	public Session mailSettings(String user, String password)
	{
		String host="smtp.gmail.com"; 	// using google mail server
		String SMTP_PORT = "465";
		String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", "false");
		Session session = Session.getDefaultInstance(props,  
			    new javax.mail.Authenticator() {
			        
			    protected PasswordAuthentication getPasswordAuthentication() {  
			    return new PasswordAuthentication(user,password);  
			      }  
			    });
		return session;	
	}
	public Session mailSettings() { // some basic mail sending settings
		String user = readConfig.getMailFrom(); // get the mail sender
		String password = readConfig.getMailPassword(); // get the password
		return mailSettings(user,password);		
	}
	
//	public static void main(String args[]) {
//		MailSend mailSend = new MailSend("config.properties");
//		mailSend.SendMessages("thulyang14@163.com","ttt", "echo");
//		
//	}
}
