package util;
/**
 * record the info of the current user
 */
import beans.User;

public class Session {
	private static User user;		
	public static User getUser() {			
		return user;
	}
	public static void setUser( User user) {	
		 Session.user = user;	
	}	
}