package config;

import java.io.*;
import java.util.Properties;

/**
 * this file is to read the config info
 * @author drift
 *
 */
public class ReadConfig {
	private Properties properties;
	private String pathName;
	// constructor
	public ReadConfig(String pathName) throws IOException{
		this.pathName = pathName;
		InputStream inputStream = 
				this.getClass().getClassLoader().getResourceAsStream(
						pathName);
		properties = new Properties();
		try{										// read the config file
			properties.load(inputStream);
		}catch (Exception e){
			System.err.println("The config file is not found!");
		}
		finally { 	// read the info into the private field, and close the tube
			inputStream.close();
		}
	}
	
	public String getSystemState(){	// get the value to the key "WeatherWeb"
		if(properties != null)
			return properties.getProperty("SystemState");
		return null;
	}
	
	public String getWeatherWeb(){	// get the value to the key "WeatherWeb"
		if(properties != null)
			return properties.getProperty("WeatherWeb");
		return null;
	}
	
	public String getWeatherKey(){	// get the weather api key
		if(properties != null)
			return properties.getProperty("WeatherKey");
		return null;		
	}
	
	public String getLocation(){	// get the location
		if(properties != null)
			return properties.getProperty("Location");
		return null;		
	}
	
	public String getQueryMode(){	// get the queryMode
		if(properties != null)
			return properties.getProperty("QueryMode");
		return null;		
	}
	
	public String getMailTo(){	
		if(properties != null)
			return properties.getProperty("MailTo");
		return null;		
	}
	
	public String getMailFrom(){
		if(properties != null)
			return properties.getProperty("MailFrom");
		return null;		
	}
	
	public String getMailPassword(){
		if(properties != null)
			return properties.getProperty("MailPassword");
		return null;		
	}
	
	public String getDataUser(){
		if(properties!=null)
			return properties.getProperty("DataUser");
		return null;
	}
	
	public String getDataPassword(){
		if(properties!=null)
			return properties.getProperty("DataPassword");
		return null;
	}
	
	public String getDriveName(){
		if(properties!=null)
			return properties.getProperty("DriveName");
		return null;
	}
	
	public String getDataUrl(){
		if(properties!=null)
			return properties.getProperty("DataUrl");
		return null;
	}
	
	public static void main(String args[]) throws IOException{
		ReadConfig config = new ReadConfig("config.properties");
		System.out.println(config.getWeatherWeb());
	}
}
