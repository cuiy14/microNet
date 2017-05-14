package beans;

import java.io.Serializable;
import java.util.Date;



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
}
