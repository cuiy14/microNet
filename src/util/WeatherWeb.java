package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.security.auth.Refreshable;

import java.io.File;

import beans.WebWeather;
import config.ReadConfig;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * this file is to read the weather from the web api, and store the info into a
 * txt file
 * 
 * @author drift
 *
 */
public class WeatherWeb {
	private static ReadConfig readConfig; // a config read class
	static private URLConnection connectionData;
	private StringBuilder sb;
	private  BufferedReader br;// read the data stream
	private JSONObject jsonData;
	private JSONObject location;
	private JSONArray daily;

	// constructor
	public WeatherWeb() throws IOException {
		readConfig = new ReadConfig("config.properties");
		String urlString = readConfig.getWeatherWeb() + "?" + "key=" + readConfig.getWeatherKey() + "&location="
				+ readConfig.getLocation() + "&" + readConfig.getQueryMode();
		// System.out.println(urlString);
		URL url = new URL(urlString);
		connectionData = url.openConnection();
		connectionData.setConnectTimeout(1000);
		try {
			br = new BufferedReader(new InputStreamReader(connectionData.getInputStream(), "UTF-8"));
			sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (SocketTimeoutException e) {
			System.out.println("The connection is time out!");
		} catch (FileNotFoundException e) {
			System.out.println("Error happens when loading the data!");
		}
		String datas = sb.toString();
		jsonData = JSONObject.fromObject(datas);
		// get the JSON object from the receiving data
		JSONObject result = new JSONObject(); // the first
		result = jsonData.getJSONArray("results").getJSONObject(0);
		location = result.getJSONObject("location"); // containing the location info
		daily = result.getJSONArray("daily"); // containing the daily weather info
		// read the info from the history file into weatherMap
		Map<String, WebWeather> weatherMap = new HashMap<String, WebWeather>();
		try {
			File weatherFile = new File(readConfig.getWeatherFile());
			if (weatherFile.exists()) {
				FileInputStream fis = new FileInputStream(readConfig.getWeatherFile());
				ObjectInputStream ois = new ObjectInputStream(fis);
				weatherMap = (HashMap<String, WebWeather>) ois.readObject();
				fis.close();
				ois.close();
			} else {
				weatherFile.createNewFile();
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		// read the info into the webweather class
		for (int iter = 0; iter != 3; iter++) {
			JSONObject dayInfo = daily.getJSONObject(iter);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, iter);
			Date date = calendar.getTime();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			WebWeather webWeather = new WebWeather();
			webWeather.setDate(simpleDateFormat.format(date)); // dates
			webWeather.setDay(dayInfo.getString("text_day"));
			webWeather.setNight(dayInfo.getString("text_night"));
			webWeather.setHigh(Integer.valueOf(dayInfo.getString("high")));
			webWeather.setLow(Integer.valueOf(dayInfo.getString("low")));
			webWeather.setWindSpeed(Integer.valueOf(dayInfo.getString("wind_speed")));
			weatherMap.put(webWeather.getDate(), webWeather);
		}
		// write the info into a system file
		try {
			FileOutputStream fos = new FileOutputStream(readConfig.getWeatherFile());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(weatherMap);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// refresh the weather info from the internet
	public static void refresh() throws IOException{
		readConfig = new ReadConfig("config.properties");
		String urlString = readConfig.getWeatherWeb() + "?" + "key=" + readConfig.getWeatherKey() + "&location="
				+ readConfig.getLocation() + "&" + readConfig.getQueryMode();
		// System.out.println(urlString);
		URL url = new URL(urlString);
		connectionData = url.openConnection();
		connectionData.setConnectTimeout(1000);
		BufferedReader abr;
		StringBuilder asb= new StringBuilder();
		try {
			abr = new BufferedReader(new InputStreamReader(connectionData.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = abr.readLine()) != null) {
				asb.append(line);
			}
		} catch (SocketTimeoutException e) {
			System.out.println("The connection is time out!");
		} catch (FileNotFoundException e) {
			System.out.println("Error happens when loading the data!");
		}
		String datas = asb.toString();
		JSONObject ajsonData = JSONObject.fromObject(datas);
		// get the JSON object from the receiving data
		JSONObject result = new JSONObject(); // the first
		result = ajsonData.getJSONArray("results").getJSONObject(0);
		JSONObject alocation = result.getJSONObject("location"); // containing the location info
		JSONArray adaily = result.getJSONArray("daily"); // containing the daily weather info
		// read the info from the history file into weatherMap
		Map<String, WebWeather> weatherMap = new HashMap<String, WebWeather>();
		try {
			File weatherFile = new File(readConfig.getWeatherFile());
			if (weatherFile.exists()) {
				FileInputStream fis = new FileInputStream(readConfig.getWeatherFile());
				ObjectInputStream ois = new ObjectInputStream(fis);
				weatherMap = (HashMap<String, WebWeather>) ois.readObject();
				fis.close();
				ois.close();
			} else {
				weatherFile.createNewFile();
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		// read the info into the webweather class
		for (int iter = 0; iter != 3; iter++) {
			JSONObject dayInfo = adaily.getJSONObject(iter);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, iter);
			Date date = calendar.getTime();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			WebWeather webWeather = new WebWeather();
			webWeather.setDate(simpleDateFormat.format(date)); // dates
			webWeather.setDay(dayInfo.getString("text_day"));
			webWeather.setNight(dayInfo.getString("text_night"));
			webWeather.setHigh(Integer.valueOf(dayInfo.getString("high")));
			webWeather.setLow(Integer.valueOf(dayInfo.getString("low")));
			webWeather.setWindSpeed(Integer.valueOf(dayInfo.getString("wind_speed")));
			weatherMap.put(webWeather.getDate(), webWeather);
		}
		// write the info into a system file
		try {
			FileOutputStream fos = new FileOutputStream(readConfig.getWeatherFile());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(weatherMap);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// return all the info in the weather file
	public static Map<String, WebWeather> getAllWeather() throws IOException {
		refresh();
		ReadConfig readConfig = new ReadConfig("config.properties");
		Map<String, WebWeather> weatherMap = new HashMap<String, WebWeather>();
		try {
			File weatherFile = new File(readConfig.getWeatherFile());
			if (weatherFile.exists()) {
				FileInputStream fis = new FileInputStream(readConfig.getWeatherFile());
				ObjectInputStream ois = new ObjectInputStream(fis);
				weatherMap = (HashMap<String, WebWeather>) ois.readObject();
				fis.close();
				ois.close();
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return weatherMap;
	}

	public static void main(String args[]) throws IOException {
		try {
			WeatherWeb weatherWeb = new WeatherWeb();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, WebWeather> weatherMap = getAllWeather();
		if (!weatherMap.isEmpty()) {
			for (Entry<String, WebWeather> entry : weatherMap.entrySet()) {
				WebWeather webWeather = entry.getValue();
				String string = "date " + entry.getKey() + "\tday" + webWeather.getDay() + "\tHigh"
						+ webWeather.getHigh() + "\tLow" + webWeather.getLow() + "\t night" + webWeather.getNight()
						+ "\t windspeed" + webWeather.getWindSpeed();
				System.out.println(string);
				for (int iter = 0; iter <= 24 * 60; iter += 60) {
					System.out
							.println("" + iter / 60 + " hour\t temperature" + webWeather.getSpecificTemperature(iter));
				}
			}
		}
	}
}
