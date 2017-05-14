package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import beans.Battery;
import beans.Motor;
import util.GetConnection;

/**
 * the database operation of Motor
 * 
 * @author drift
 *
 */
public class MotorDao {
	GetConnection connection = new GetConnection();
	Connection conn = null;

	// define the inserting method
	// insert a new record item for motor
	public void insertMotor(Motor motor) {
		conn = connection.getCon();
		try {
			String sql = "insert into tb_motors ( serial, normalPower, date, "
					+ ") values(?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, motor.getSerial());
			statement.setInt(2, motor.getNormalPower());
			statement.setDate(3, motor.getDate());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// test
		System.out.println("Insert Motor successfully!");
	}

	// define the query method
	// query all the data in tb_Motors
	public ArrayList<Motor> selectMotor() {
		ArrayList<Motor> array = new ArrayList<Motor>();
		conn = connection.getCon();
		try {
			PreparedStatement statement = conn.prepareStatement(
					"select * from tb_motors ");
			ResultSet rest = statement.executeQuery();
			while (rest.next()) {
				Motor motor = new Motor();
				motor.setId(rest.getInt(1));
				motor.setSerial(rest.getString(2));
				motor.setNormalPower(rest.getInt(3));
				motor.setDate(rest.getDate(4));
				array.add(motor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}
	// query by id
	public Motor selectById(int id){
		Motor motor = new Motor();
		conn = connection.getCon();
		try{
			String sql = "select * from tb_motors where id = " + id;
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rest = statement.executeQuery();
			while(rest.next()){
				motor.setId(rest.getInt(1));
				motor.setSerial(rest.getString(2));
				motor.setNormalPower(rest.getInt(3));
				motor.setDate(rest.getDate(4));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return motor;
	}
	// query by String serial
	public ArrayList<Motor> selectBySerial(String serial) {
		ArrayList<Motor> array = new ArrayList<Motor>();
		conn = connection.getCon();
		try {
			PreparedStatement statement = conn.prepareStatement(
					"select * from tb_motors where serial = " + serial);
			ResultSet rest = statement.executeQuery();
			while (rest.next()) {
				Motor motor = new Motor();
				motor.setId(rest.getInt(1));
				motor.setSerial(rest.getString(2));
				motor.setNormalPower(rest.getInt(3));
				array.add(motor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}
	// define the modification method
	public void updateMotor(Motor motor){
		conn = connection.getCon();
		try {
			String sql = "update tb_motors set serial = ?, normalPower = ?,"
					+ "date = ?, where id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, motor.getSerial());
			statement.setInt(2, motor.getNormalPower());
			statement.setDate(3, motor.getDate());
			statement.setInt(4, motor.getId());
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
					"delete * from tb_motors where id = " + id);
			statement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}	
	}
	
}
