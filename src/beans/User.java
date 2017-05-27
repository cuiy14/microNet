package beans;

public class User {	
									// the super user has some privilege
	private int id;				// primary key
	private String userName;	// userName
	private String password;	// password
	private int gender;			// 1 represents man, 0 the other
	private boolean isRoot = false; // whether the user is superUser; 
	
	public boolean isRoot(){
		return isRoot;
	}
	public int getId() {		
		return id;
	}
	public void setId(int id) {	
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getGender(){
		return gender==1?true:false;
	}
	public void setGender(int gender){
		this.gender=gender;
	}
	public boolean getIsRoot(){
		return isRoot;
	}
	public void setIsRoot(boolean b){
		isRoot = b;
	}
	
}
