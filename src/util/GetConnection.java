package util;
/**
 * this file is to encapsulate the connection to the database.
 * still needs testing
 */
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sun.org.apache.regexp.internal.RE;

import config.ReadConfig;

public class GetConnection {
	private Connection connection; 	// the connection object to the database
	private PreparedStatement preparedStatement; 	
	private String user; 	// for logging in the database
	private String password; 	// like above
	private String driveName; 	// the database drive name
	private String dataUrl; 	// the url for connecting to the database
	public GetConnection() {
		ReadConfig readConfig;
		try {
			readConfig = new ReadConfig("config.properties");
			user=readConfig.getDataUser();
			password = readConfig.getDataPassword();
			dataUrl = readConfig.getDataUrl(); 	// this may needed changing for multi-users
			driveName = readConfig.getDriveName();
			try {
				Class.forName(driveName); 	// load the drive
			} catch (ClassNotFoundException e) {
				System.out.println("加载数据库驱动失败！");
				e.printStackTrace();
			}
		} catch (IOException e1) {
			System.out.println("驱动配置文件查找出错");
		}		
	}
	// set up the connection to the database
	public Connection getCon() {
		try {
			connection = (Connection) DriverManager.getConnection(
					dataUrl, user, password); // get  connection
		} catch (SQLException e) {
			System.out.println("创建数据库连接失败！");
			connection = null;
			e.printStackTrace();
		}
		return connection; // return connection
	}
	
	
	public void doPstm(String sql, Object[] params) {
		if (sql != null && !sql.equals("")) {
			if (params == null)
				params = new Object[0];
			getCon();
			if (connection != null) {
				try {
					System.out.println(sql);
					preparedStatement = (PreparedStatement) 
							connection.prepareStatement(sql,
									ResultSet.TYPE_SCROLL_INSENSITIVE, 
									ResultSet.CONCUR_READ_ONLY);
					for (int i = 0; i < params.length; i++) {
						preparedStatement.setObject(i + 1, params[i]);
					}
					preparedStatement.execute();
				} catch (SQLException e) {
					System.out.println("doPstm()方法出错！");
					e.printStackTrace();
				}
			}
		}
	}

	public ResultSet getRs() throws SQLException {
		return preparedStatement.getResultSet();
	}

	public int getCount() throws SQLException {
		return preparedStatement.getUpdateCount();
	}

	public void closed() {
		try {
			if (preparedStatement != null)
				preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("关闭pstm对象失败！");
			e.printStackTrace();
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println("关闭con对象失败！");
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		
	}
	
}
