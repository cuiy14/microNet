package beans;

import java.io.IOException;
import java.util.ArrayList;

import dao.PhotovoltaicDao;
import dao.WindMachineDao;

public class WindMachine extends BaseModel {
	/**
	 * the private fields include
	 * 
	 * protected int id; // primary key
	 * protected String serial; // serial name
	 * protected int normalPower; // normal power, output power
	 * protected Date date; // the date of the machine put into service
	 * private int lowWindScale
	 * private int highWindScale
	 * 
	 */
	private int lowWindScale; // the lowest wind scale to generate electricity
	private int highWindScale; // the highest ***

	public int getLowWindScale() {
		return lowWindScale;
	}

	public void setLowWindScale(int lowWindScale) {
		this.lowWindScale = lowWindScale;
	}

	public int getHighWindScale() {
		return highWindScale;
	}

	public void setHighWindScale(int highWindScale) {
		this.highWindScale = highWindScale;
	}
	
	//the below are the time sequence methods
	// 实际功率和风速三次方成正比
	// the param windScale in the WindMachine is the actual windSpeed
	public double getCurrentGeneration() throws IOException{
		WebWeather webWeather = (new WebWeather()).getCurrentDay();
		double ws = webWeather.getCurrentWindSpeed();
		double ratio =0.9;
		double balance = ratio*highWindScale+(1-ratio)*lowWindScale;
		if((ws<lowWindScale) || (ws>highWindScale))
			return 0.0;				// the wind speed is too small or too strong
		else if(ws>balance)			// enter the balanced area
			return (double) normalPower;
		else 
			return Math.pow((ws-lowWindScale)/(balance-lowWindScale),3)*normalPower;			
	}

	// get the total generation
	public static double getTotal() throws IOException{
		WindMachineDao pd = new WindMachineDao();
		ArrayList<WindMachine> mdList = pd.selectWindMachine();
		double sum = 0;
		for(int iter=0;iter!=mdList.size();iter++)
			sum+=mdList.get(iter).getCurrentGeneration();
		return sum;
	}
	
	public static void main(String args[]) {
		WindMachine machine = new WindMachine();
		machine.setLowWindScale(13);
		System.out.println("low " + machine.getLowWindScale());
		System.out.println(Math.pow(2, 3));
	}

}
