package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import beans.WindMachine;
import util.GetConnection;

/**
 * the database operation of WindMachine
 * 
 * @author drift
 *
 */
public class WindMachineDao {
	GetConnection connection = new GetConnection();
	Connection conn = null;

	// define the inserting method
	// insert a new record item for wind machine
	public void insertWindMachine(WindMachine windMachine) {
		conn = connection.getCon();
		try {
			String sql = "insert into tb_windMachines ( serial, normalPower, date, "
					+ "lowWindScale, highWindScale) values(?,?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			// statement.setInt(1, windMachine.getId()); // id is auto_increment
			statement.setString(1, windMachine.getSerial());
			statement.setInt(2, windMachine.getNormalPower());
			statement.setDate(3, windMachine.getDate());
			statement.setInt(4, windMachine.getLowWindScale());
			statement.setInt(5, windMachine.getHighWindScale());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// test
		System.out.println("Insert windmachine successfully!");
	}

	// define the query method
	// query all the data in tb_windMachines
	public ArrayList<WindMachine> selectWindMachine() {
		ArrayList<WindMachine> array = new ArrayList<WindMachine>();
		conn = connection.getCon();
		try {
			PreparedStatement statement = conn.prepareStatement(
					"select * from tb_windMachines ");
			ResultSet rest = statement.executeQuery();
			while (rest.next()) {
				WindMachine wm = new WindMachine();
				wm.setId(rest.getInt(1));
				wm.setSerial(rest.getString(2));
				wm.setNormalPower(rest.getInt(3));
				wm.setDate(rest.getDate(4));
				wm.setLowWindScale(rest.getInt(5));
				wm.setHighWindScale(rest.getInt(6));
				array.add(wm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}
	// query by id
	public WindMachine selectById(int id){
		WindMachine wm = new WindMachine();
		conn = connection.getCon();
		try{
			String sql = "select * from tb_windMachines where id = " + id;
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rest = statement.executeQuery();
			while(rest.next()){
				wm.setId(rest.getInt(1));
				wm.setSerial(rest.getString(2));
				wm.setNormalPower(rest.getInt(3));
				wm.setDate(rest.getDate(4));
				wm.setLowWindScale(rest.getInt(5));
				wm.setHighWindScale(rest.getInt(6));	
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return wm;
	}
	// query by String serial
	public ArrayList<WindMachine> selectBySerial(String serial) {
		ArrayList<WindMachine> array = new ArrayList<WindMachine>();
		conn = connection.getCon();
		try {
			PreparedStatement statement = conn.prepareStatement(
					"select * from tb_windMachines where serial = " + serial);
			ResultSet rest = statement.executeQuery();
			while (rest.next()) {
				WindMachine wm = new WindMachine();
				wm.setId(rest.getInt(1));
				wm.setSerial(rest.getString(2));
				wm.setNormalPower(rest.getInt(3));
				wm.setDate(rest.getDate(4));
				wm.setLowWindScale(rest.getInt(5));
				wm.setHighWindScale(rest.getInt(6));
				array.add(wm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}
	// define the modification method
	public void updateWindMachine(WindMachine wm){
		conn = connection.getCon();
		try {
			String sql = "update tb_windMachines set serial = ?, normalPower = ?,"
					+ "date = ?, lowWindScale=?,highWindScale=? where id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, wm.getSerial());
			statement.setInt(2, wm.getNormalPower());
			statement.setDate(3, wm.getDate());
			statement.setInt(4, wm.getLowWindScale());
			statement.setInt(5, wm.getHighWindScale());
			statement.setInt(6, wm.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// define the delete method
	public void deleteById(int id){
		conn = connection.getCon();
		try{
			PreparedStatement statement = conn.prepareStatement(
					"delete * from tb_windMachines where id = " + id);
			statement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}	
	}
	
}
