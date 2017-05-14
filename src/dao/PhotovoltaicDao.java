package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import beans.Photovoltaic;
import beans.WindMachine;
import util.GetConnection;

/**
 * the database operation of Photovoltaic
 * 
 * @author drift
 *
 */
public class PhotovoltaicDao {
	GetConnection connection = new GetConnection();
	Connection conn = null;

	// define the inserting method
	// insert a new record item for photovoltaic
	public void insertPhotovoltaic(Photovoltaic photovoltaic) {
		conn = connection.getCon();
		try {
			String sql = "insert into tb_photovoltaics ( serial, normalPower, date, "
					+ "efficiency) values(?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, photovoltaic.getSerial());
			statement.setInt(2, photovoltaic.getNormalPower());
			statement.setDate(3, photovoltaic.getDate());
			statement.setDouble(4, photovoltaic.getEfficiency());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// test
		System.out.println("Insert Photovoltaic successfully!");
	}

	// define the query method
	// query all the data in tb_Batterys
	public ArrayList<Photovoltaic> selectPhotovoltaic() {
		ArrayList<Photovoltaic> array = new ArrayList<Photovoltaic>();
		conn = connection.getCon();
		try {
			PreparedStatement statement = conn.prepareStatement(
					"select * from tb_photovoltaics ");
			ResultSet rest = statement.executeQuery();
			while (rest.next()) {
				Photovoltaic photovoltaic = new Photovoltaic();
				photovoltaic.setId(rest.getInt(1));
				photovoltaic.setSerial(rest.getString(2));
				photovoltaic.setNormalPower(rest.getInt(3));
				photovoltaic.setDate(rest.getDate(4));
				photovoltaic.setEfficiency(rest.getDouble(5));
				array.add(photovoltaic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}
	// query by id
	public Photovoltaic selectById(int id){
		Photovoltaic photovoltaic = new Photovoltaic();
		conn = connection.getCon();
		try{
			String sql = "select * from tb_photovoltaics where id = " + id;
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rest = statement.executeQuery();
			while(rest.next()){
				photovoltaic.setId(rest.getInt(1));
				photovoltaic.setSerial(rest.getString(2));
				photovoltaic.setNormalPower(rest.getInt(3));
				photovoltaic.setDate(rest.getDate(4));
				photovoltaic.setEfficiency(rest.getDouble(5));	
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return photovoltaic;
	}
	// query by String serial
	public ArrayList<Photovoltaic> selectBySerial(String serial) {
		ArrayList<Photovoltaic> array = new ArrayList<Photovoltaic>();
		conn = connection.getCon();
		try {
			PreparedStatement statement = conn.prepareStatement(
					"select * from tb_photovoltaics where serial = " + serial);
			ResultSet rest = statement.executeQuery();
			while (rest.next()) {
				Photovoltaic photovoltaic = new Photovoltaic();
				photovoltaic.setId(rest.getInt(1));
				photovoltaic.setSerial(rest.getString(2));
				photovoltaic.setNormalPower(rest.getInt(3));
				photovoltaic.setDate(rest.getDate(4));
				photovoltaic.setEfficiency(rest.getDouble(5));
				array.add(photovoltaic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}
	// define the modification method
	public void updatePhotovoltaic(Photovoltaic photovoltaic){
		conn = connection.getCon();
		try {
			String sql = "update tb_photovoltaics set serial = ?, normalPower = ?,"
					+ "date = ?, efficiency=? where id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1,photovoltaic.getSerial());
			statement.setInt(2, photovoltaic.getNormalPower());
			statement.setDate(3,photovoltaic.getDate());
			statement.setDouble(4, photovoltaic.getEfficiency());
			statement.setInt(5, photovoltaic.getId());
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
					"delete * from tb_photovoltaics where id = " + id);
			statement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}	
	}
	
}
