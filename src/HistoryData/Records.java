package HistoryData;

import java.io.IOException;
import java.util.ArrayList;

import beans.Battery;
import beans.Motor;
import beans.Photovoltaic;
import beans.TimeSlice;
import beans.WebWeather;
import beans.WindMachine;
import dao.BatteryDao;
import dao.MotorDao;
import dao.PhotovoltaicDao;
import dao.WindMachineDao;
import sun.nio.cs.ext.TIS_620;

/**
 * this file is to record the historical state of the system
 * @author drift
 *
 */
public class Records {
	private String date;
	private double windSpeed;
	private double windGeneration; 	// the sum of windMachines generations
	private int minute;
	private int dayText;
	private double solarGeneration;
//	private double batteryStorage; // for the time being , not include the state of battery
	private double temperature;
	private double motorLoad;
	// constructors
	// real time settings
	// if @param current=1,return current data; else null;
	public Records(int current) throws IOException{
		if(current ==1){
		date = TimeSlice.getCurrentDate();
		minute = TimeSlice.getCurrentMinute();
		WebWeather webWeather = new WebWeather();
		windSpeed = webWeather.getCurrentWindSpeed();
		windGeneration = WindMachine.getTotal();
		dayText = webWeather.getDayText();
		solarGeneration = Photovoltaic.getTotal();
//		batteryStorage = 
		temperature = webWeather.getCurrentTemperature();
		motorLoad = Motor.getTotal();	
		}
		else{}
	}
	// setter & getter methods
	public void setDate(String date){
		this.date = date;
	}
	public String getDate(){
		return date;
	}
	public void setMinute(int minute){
		this.minute =minute;
	}
	public int getMinute(){
		return minute;
	}
	public void setWindSpeed(double windSpeed){
		this.windSpeed = windSpeed;
	}
	public double getWindSpeed(){
		return windSpeed;
	}
	public void setWindGeneration(double windGeneration){
		this.windGeneration = windGeneration;
	}
	public double getWindGeneration(){
		return windGeneration;
	}
	public void setDayText(int dayText){
		this.dayText = dayText;
	}
	public int getDayText(){
		return dayText;
	}
	public void setSolarGeneration(double solarGeneration){
		this.solarGeneration = solarGeneration;
	}
	public double getSolarGeneration(){
		return solarGeneration;
	}
	public void setTemperature(double temperature){
		this.temperature = temperature;
	}
	public double getTemperature(){
		return temperature;
	}
	public void setMotorLoad(double motorLoad){
		this.motorLoad = motorLoad;
	}
	public double getMotoLoad(){
		return motorLoad;
	}
	
}
