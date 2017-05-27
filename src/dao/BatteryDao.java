package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import beans.Battery;
import beans.WindMachine;
import util.GetConnection;

/**
 * the database operation of Battery
 * 
 * @author drift
 *
 */
public class BatteryDao {
	GetConnection connection = new GetConnection();
	Connection conn = null;

	// define the inserting method
	// insert a new record item for battery
	public void insertBattery(Battery battery) {
		conn = connection.getCon();
		try {
			String sql = "insert into tb_batteries ( serial, normalPower, date, "
					+ "capacity) values(?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, battery.getSerial());
			statement.setInt(2, battery.getNormalPower());
			statement.setDate(3, battery.getDate());
			statement.setInt(4, battery.getCapacity());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// test
		System.out.println("Insert Battery successfully!");
	}

	// define the query method
	// query all the data in tb_Batterys
	public ArrayList<Battery> selectBattery() {
		ArrayList<Battery> array = new ArrayList<Battery>();
		conn = connection.getCon();
		try {
			PreparedStatement statement = conn.prepareStatement(
					"select * from tb_batteries ");
			ResultSet rest = statement.executeQuery();
			while (rest.next()) {
				Battery battery = new Battery();
				battery.setId(rest.getInt(1));
				battery.setSerial(rest.getString(2));
				battery.setNormalPower(rest.getInt(3));
				battery.setDate(rest.getDate(4));
				battery.setCapacity(rest.getInt(5));
				array.add(battery);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}
	// count
	public int count(){
		return selectBattery().size();
	}
	// query by id
	public Battery selectById(int id){
		Battery battery = new Battery();
		conn = connection.getCon();
		try{
			String sql = "select * from tb_batteries where id = " + id;
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rest = statement.executeQuery();
			while(rest.next()){
				battery.setId(rest.getInt(1));
				battery.setSerial(rest.getString(2));
				battery.setNormalPower(rest.getInt(3));
				battery.setDate(rest.getDate(4));
				battery.setCapacity(rest.getInt(5));	
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return battery;
	}
	// query by String serial
	public ArrayList<Battery> selectBySerial(String serial) {
		ArrayList<Battery> array = new ArrayList<Battery>();
		conn = connection.getCon();
		try {
			PreparedStatement statement = conn.prepareStatement(
					"select * from tb_batteries where serial = " + serial);
			ResultSet rest = statement.executeQuery();
			while (rest.next()) {
				Battery battery = new Battery();
				battery.setId(rest.getInt(1));
				battery.setSerial(rest.getString(2));
				battery.setNormalPower(rest.getInt(3));
				battery.setDate(rest.getDate(4));
				battery.setCapacity(rest.getInt(5));
				array.add(battery);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}
	// define the modification method
	public void updateBattery(Battery battery){
		conn = connection.getCon();
		try {
			String sql = "update tb_batteries set serial = ?, normalPower = ?,"
					+ "date = ?, capacity = ? where id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, battery.getSerial());
			statement.setInt(2, battery.getNormalPower());
			statement.setDate(3, battery.getDate());
			statement.setInt(4, battery.getCapacity());
			statement.setInt(5, battery.getId());
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
					"delete * from tb_batteries where id = " + id);
			statement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}	
	}
	
}
