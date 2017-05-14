package config;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * it still needs debugging; 
 * and not recommend to use the config file to transfer running info; use database 
 * this file is to encapsulate the writing method of config files
 * @author drift
 */
public class WriteConfig {
	private Properties properties;
	private String pathName;

	// constructors
	public WriteConfig(String apathName) {
		pathName = apathName;
	}

	// set or add new properties into the config files
	public void setProperties(String parameterName, String parameterValue) {
		properties = new Properties();
		try {
			InputStream fis = new FileInputStream(pathName);
			properties.load(fis); 			// read the map from the input stream
			OutputStream fos = new FileOutputStream(pathName);
			if(properties.containsKey(parameterName))
				properties.setProperty(parameterName, parameterValue);
			else
				properties.put(parameterName, parameterValue);
			properties.store(fos, "Update '" + parameterName + "' value");
			fis.close();
			fos.close();
		} catch (IOException e) {
			System.err.println("Visit " + pathName + " for updating " + parameterName + " value error");
		}
	}
	
	public static void main(String args[]) throws IOException{
		WriteConfig writeConfig = new WriteConfig("config.properties");
		writeConfig.setProperties("hello", "world");
		writeConfig.setProperties("SystemState", "inline");
		ReadConfig readConfig = new ReadConfig("config.properties");
		System.out.println(readConfig.getSystemState());
	}
}
