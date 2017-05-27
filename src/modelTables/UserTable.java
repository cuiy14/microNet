package modelTables;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import beans.User;
import dao.UserDao;

public class UserTable extends AbstractTableModel{
	Vector<User> userVector;
	String[] colomnHeader = {"Id","姓名","密码","性别","是否为管理员" };
	public UserTable() {
		// get the info of users from dataset
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
			return user.getPassword();
		else if(col==3)
			return new Boolean(user.getGender());
		else 
			return new Boolean(user.getIsRoot());
}
	
}
