package beans;

import java.io.IOException;
import java.util.ArrayList;

import org.jfree.data.xy.NormalizedMatrixSeries;

import dao.BatteryDao;
import dao.MotorDao;
import dao.PhotovoltaicDao;
import dao.WindMachineDao;
import jdk.nashorn.internal.runtime.events.RecompilationEvent;

public class Motor extends BaseModel{
	// assume the load is constant at day for the time being
	// need to add the temperature factor into it
	public double getCurrentLoad() throws IOException{
		WebWeather webWeather = (new WebWeather()).getCurrentDay();
		double temp = webWeather.getCurrentTemperature();
		int minute = TimeSlice.getCurrentMinute();
		double ratio = 0.1;
		double noise =(Math.random()-0.5)/0.5*ratio*normalPower;
		if((minute<6*60)||(minute>23*60))
			return Math.abs(noise);
		else
			return normalPower+noise;	
	}
	// get the total load 
	public static double getTotal() throws IOException{
		MotorDao md = new MotorDao();
		ArrayList<Motor> mdList = md.selectMotor();
		double sum = 0;
		for(int iter=0;iter!=mdList.size();iter++)
			sum+=mdList.get(iter).getCurrentLoad();
		return sum;
	}
}
