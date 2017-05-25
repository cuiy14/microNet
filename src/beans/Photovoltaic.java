package beans;

import java.io.IOException;
import java.util.ArrayList;

import dao.MotorDao;
import dao.PhotovoltaicDao;

public class Photovoltaic extends BaseModel {
	// average efficiency of transforming light into electricity
	/**
	 * the private fields include
	 * 
	 * protected int id; // primary key
	 * protected String serial; // serial name
	 * protected int normalPower; // normal power, output power
	 * protected Date date; // the date of the machine put into service
	 * double efficiency;
	 */
	private double efficiency; 	

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}
	// the below are the time sequence method
	// @ param, current time index(min), day_text
	// fit with sinusoidal curve
	public double getCurrentGeneration() throws IOException{
		int minute = TimeSlice.getCurrentMinute();
		if((minute<6*60)||(minute>18*60))
			return Math.random();	// at night, return a noise near to 0
		WebWeather webWeather = (new WebWeather()).getCurrentDay();
		int dayText = webWeather.getDayText();
		double level = normalPower*dayText/3.5;
		double d = (Math.random()-0.5)/0.5;
		double ratio = 0.1;		// 波动方差
		double noise = ratio*level;
		return level*Math.cos((minute-12*60)/12/60*Math.PI)+noise; 	
	}
	// get the total generation
	public static double getTotal() throws IOException{
		PhotovoltaicDao pd = new PhotovoltaicDao();
		ArrayList<Photovoltaic> mdList = pd.selectPhotovoltaic();
		double sum = 0;
		for(int iter=0;iter!=mdList.size();iter++)
			sum+=mdList.get(iter).getCurrentGeneration();
		return sum;
	}
}
