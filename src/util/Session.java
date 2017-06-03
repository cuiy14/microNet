package util;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

/**
 * record the info of the current user
 * a method to record the global variables
 */
import beans.User;

public class Session {
	private static User user;	
	private static String password;
	private static boolean conSend=false; 	// see the details in EmailFrame2
	public static User getUser() {			
		return user;
	}
	public static void setUser( User user) {	
		 Session.user = user;	
	}	
	public static void setSudo(String password){
		Session.password=password;
	}
	public static String getPassword(){
		return password;
	}
	public static void setConSend(boolean send){
		conSend=send;
	}
	public static boolean getConSend(){
		return conSend;
	}
}