package modelTables;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import beans.User;
import dao.UserDao;
import util.Session;

public class UserTable extends AbstractTableModel{
	Vector<User> userVector;
	String[] colomnHeader = {"Id","姓名","密码","性别","是否为管理员" };
	boolean booo=false;
	public UserTable(boolean boo) {	// if boo is true, show the password, else not
		// get the info of users from dataset
		if(Session.getUser().getIsRoot())
			this.booo = true;
		else if( boo)
			booo=true;
		else 
			booo=false;
		ArrayList<User> userArray = (new UserDao()).selectUser();
		userVector = new Vector<User>();
		for (int count = 0; count != userArray.size(); count++) {
			User user = userArray.get(count);
			userVector.addElement(user);
			System.out.println(count);
		}
	}
	
	public int getColumnCount(){
		return colomnHeader.length;
	}
	
	public int getRowCount(){
		return userVector.size();
	}
	
	public String getColumnName(int col){
		return colomnHeader[col];
	}
	
	public Class getColumnClass(int col){
		return getValueAt(0, col).getClass();
	}
	
	public Object getValueAt(int row, int col){
		User user = userVector.get(row);
		if(col==0)
			return new Integer(user.getId());
		else if(col==1)
			return user.getUserName();
		else if(col==2)
		{	if(booo)
			return user.getPassword();
		else 
			return "***" ;
		}
		else if(col==3)
//			return new Boolean(user.getGender());
		{	if(new Boolean(user.getGender()))
				return "男";
			else return "女";
		}
		else 
			return new Boolean(user.getIsRoot());
}
	
}
