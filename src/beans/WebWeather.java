package beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import config.ReadConfig;



/**
 * this class is the primary info offered by weather web
 * @author drift
 *
 */
public class WebWeather implements Serializable{
	protected String date;
	protected String day;
	protected String night;
	protected int high;
	protected int low;
	protected int windSpeed;
	private final int highMinute=13*60;
	private final int lowMinute = 5*60;
	// the setter & getter methods
	public void setDate( String date){
		this.date = date;
	}
	public String getDate(){
		return date;
	}
	public void setDay(String day){
		this.day = day;
	}
	public String getDay(){
		return this.day;
	}
	public void setNight(String night){
		this.night = night;
	}
	public String getNight(){
		return night;
	}
	public void setHigh( int high) {
		this.high = high;
	}
	public int getHigh(){
		return high;
	}
	public void setLow(int low){
		this.low = low;
	}
	public int getLow(){
		return low;
	}
	public void setWindSpeed(int windSpeed){
		this.windSpeed = windSpeed;
	}
	public int getWindSpeed(){
		return windSpeed;
	}
	/**
	 * the below are the time sequence method
	 * provide the specific weather info on  a given point in a day
	 * @return
	 */
	// get the current wind speed, for the parameter of wind machines
		public double getCurrentWindSpeed(){
			double d = (Math.random()-0.5)/0.5;
			double base = (double) windSpeed;
			double ratio = 0.1;		// 波动方差
			return base+d*ratio*base;		
		}
		// get the temperature at a specific time point with the minute unit
		// @param minute should be less than 24*60
		public double getSpecificTemperature(int minute){
			double d = (Math.random()-0.5)/0.5;
			double ratio = 0.1;		// 波动方差
			double noise = d*(high-low)*ratio;
			if(minute<lowMinute){
				double tem =low+(lowMinute-minute)/16.0/60.0*(high-low)+noise;
				return Double.parseDouble(String.format("%.1f",tem));
			}
			else if(minute>highMinute){
				double tem = high-(minute-highMinute)/16.0/60.0*(high-low)+noise;
				return Double.parseDouble(String.format("%.1f", tem));
			}
			else{
				double tem = low + (minute-lowMinute)/8.0/60.0*(high-low)+noise;
				return Double.parseDouble(String.format("%.1f", tem));
			}	
		}
		// get the current temperature; fitting with sinusoidal curve
		public double getCurrentTemperature(){
			int current = TimeSlice.getCurrentMinute();
			return getSpecificTemperature(current);	
		}
		// get the text of day; 3:sunny,2:cloudy,1:others
		public int getDayText(){
			if(day.equals("晴"))
				return 3;
			else if(day.equals("多云"))
				return 2;
			else 
				return 1;
		}
		// give a instance of a specific day
		// @ param date is the form of YYYY-MM-DD
		public WebWeather getSpecificDay(String date) throws IOException{
			ReadConfig readConfig = new ReadConfig("config.properties");
			Map<String, WebWeather> weatherMap = new HashMap<String,WebWeather>();
			try{
				File weatherFile = new File(readConfig.getWeatherFile());
				if(weatherFile.exists()){
					FileInputStream fis = new FileInputStream(
							readConfig.getWeatherFile());
					ObjectInputStream ois = new ObjectInputStream(fis);
					weatherMap = (HashMap<String, WebWeather>)ois.readObject();				
					fis.close();
					ois.close();
				}else {
					weatherFile.createNewFile();
				}
			}catch(Exception ee){
				ee.printStackTrace();
			}
			return weatherMap.get(date);
		}
		// give the current day
		public WebWeather getCurrentDay() throws IOException{
			String date = TimeSlice.getCurrentDate();
			return getSpecificDay(date);
		}

}
