package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.User;
import beans.WindMachine;
import util.GetConnection;

/**
 * the class for enquiring users, for login in frame
 * @author drift
 *
 */

public class UserDao {
	GetConnection connection = new GetConnection();
	Connection conn = null;
	// the method for searching user with username & password
	public User getUser(String username, String password){
		User user = new User();		// javabean object
		conn = connection.getCon();	// connect to the database
		try{
			String sql = "Select * from tb_users where userName = ? and password = ?";
			PreparedStatement statement = (PreparedStatement)
					conn.prepareStatement(sql); 	// do the parepare statements
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet rest = statement.executeQuery();	// execute the query
			while(rest.next()){
				user.setId(rest.getInt(1));		// the field starts from 1
				user.setUserName(rest.getString(2));
				user.setPassword(rest.getString(3));
				user.setIsRoot(rest.getBoolean(4));
				user.setGender(rest.getInt(5));
			}		
		}catch(SQLException e){
			e.printStackTrace();
		}
		return user;		// give back the result		
	}
	
	// query all the data in tb_users
	public ArrayList<User> selectUser() {
		ArrayList<User> array = new ArrayList<User>();
		conn = connection.getCon();
		try {
			PreparedStatement statement = conn.prepareStatement(
					"select * from tb_users ");
			ResultSet rest = statement.executeQuery();
			while (rest.next()) {
				User user = new User();
				user.setId(rest.getInt(1));
				user.setUserName(rest.getString(2));
				user.setPassword(rest.getString(3));
				user.setIsRoot(rest.getBoolean(4));
				user.setGender(rest.getInt(5));
				array.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}
	
	public static void main(String args[]){
		User user = new User();
		UserDao userDao = new UserDao();
		user = userDao.getUser("root", "root");
		System.out.println(user.getId());
		System.out.println(user.getUserName());
		System.out.println(user.getPassword());
		System.out.println(user.getIsRoot()? 1:0);
	}
	
}
