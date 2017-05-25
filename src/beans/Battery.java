package beans;

import java.io.IOException;
import java.util.ArrayList;

import dao.BatteryDao;
import dao.MotorDao;
import dao.PhotovoltaicDao;
import dao.WindMachineDao;
import sun.awt.image.IntegerInterleavedRaster;

public class Battery extends BaseModel {
	/**
	 * the private fields include
	 * 
	 * protected int id; // primary key
	 * protected String serial; // serial name
	 * protected int normalPower; // normal power, output power
	 * protected Date date; // the date of the machine put into service
	 * int capacity
	 */
	private double storage; // state variables, changing with time, *kwh
	private int capacity; // the capacity of the battery, *kwh
	public int getCapacity(){
		return capacity;
	}
	public void setCapacity(int capacity){
		this.capacity = capacity;
	}
	
	public double getStorage(){
		return storage;
	}
	
	public void setStorage(double storage){
		this.storage = storage;
	}
	// the return value is the input value(support) of net
	// it needs to update every minute
	// due to it's varying, no need to write into database
	public double updateStorage() throws IOException{
		double input = getCurrentPower();
		if(input>=0)	// the battery is charging
		{
			double ps = input*1/60/1000;
			if(ps+storage<capacity)
				storage+=ps;
			else
				storage = capacity;
			return 0.0;
		}
		else		// the battery is discharging
		{
			double ps = input*1/60/1000;
			if(ps+storage>0)
				{
				storage+=ps;
				return 0.0;
				}
			else{
				storage = 0;
				return Math.abs(storage+ps);
				}						
		}
	}

	// the input is positive
	// the value is sum(windmachines + photovoltaics - motors)/num(battery)
	// use the info in database
	public double getCurrentPower() throws IOException{
		BatteryDao bd = new BatteryDao();
		ArrayList<Battery> bdList = bd.selectBattery();
		return getTotal()/bdList.size();		
	}
	
	// the input power, not storage
	public static double getTotal() throws IOException{
		BatteryDao bd = new BatteryDao();
		ArrayList<Battery> bdList = bd.selectBattery();
		double sum = 0;
		sum += WindMachine.getTotal();
		sum += Photovoltaic.getTotal();
		sum -= Motor.getTotal();
		return sum;
	}

}
