package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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

import config.ReadConfig;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * this file is to read the weather from the web api,
 * and store the info into a txt file
 * @author drift
 *
 */
public class WeatherWeb {
    private ReadConfig readConfig;	// a config read class
    private URLConnection connectionData;  
    private StringBuilder sb;  
    private BufferedReader br;// read the data stream   
    private JSONObject jsonData;   
    private JSONObject location;
    private JSONArray daily;
    // constructor
    public WeatherWeb() throws IOException{
    	readConfig = new ReadConfig("config.properties");
    	String urlString = readConfig.getWeatherWeb()+"?"+"key="+
    			readConfig.getWeatherKey()+"&location="+readConfig.getLocation()+
    			"&"+readConfig.getQueryMode();
    	//System.out.println(urlString);
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
		// System.out.println(jsonData.toString());
		
		// get the JSON object from the receiving data
		JSONObject result = new JSONObject();	// the first
		result = jsonData.getJSONArray("results").getJSONObject(0);
		location = result.getJSONObject("location"); 	// containing the location info
		daily = result.getJSONArray("daily");	// containing the daily weather info
		
		//print the weather info & location
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int iter=0; iter!=3; iter++){ 	// the api provides only three days info
			JSONObject dayInfo = daily.getJSONObject(iter);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, iter);
			Date date = calendar.getTime();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy年MM月dd日");
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("city", location.getString("name").toString());		// city
			map.put("date", simpleDateFormat.format(date)); 	// dates
			map.put("text_day", dayInfo.getString("text_day")); 
			map.put("code_day", dayInfo.getString("code_day")); 
			map.put("text_night", dayInfo.getString("text_night")); 
			map.put("code_night", dayInfo.getString("code_night")); 
			map.put("text_day", dayInfo.getString("text_day")); 
			map.put("high", dayInfo.getString("high")); 
			map.put("low", dayInfo.getString("low")); 
			map.put("precip", dayInfo.getString("precip")); 
			map.put("wind_direction", dayInfo.getString("wind_direction")); 
			map.put("wind_direction_degree", dayInfo.getString(
					"wind_direction_degree")); 
			map.put("wind_speed", dayInfo.getString("wind_speed")); 
			map.put("wind_scale",dayInfo.getString("wind_scale"));
			list.add(map);
		}
		
		// print out the weather info
		System.out.println("city" + "\t" + "date" + "\t\t" + "day" + "\t" + 
				"night" + "\t" + "high" + "\t" + "low" +
				"\t" + "wind direction" + "\t"+"wind scale");
		for (int j = 0; j < list.size(); j++) {
			Map<String, Object> wMap = list.get(j);
			System.out.println(wMap.get("city") + "\t" + wMap.get("date") + "\t" +
					wMap.get("text_day") + "\t"	+ wMap.get("text_night") + "\t" +
					wMap.get("high") + "\t" + wMap.get("low") + "\t"+
					wMap.get("wind_direction") + "\t\t" + wMap.get("wind_scale"));			
		}
		
    }
    
    public static void main(String args[]){
    	try {
			WeatherWeb weatherWeb = new WeatherWeb();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }   
}
