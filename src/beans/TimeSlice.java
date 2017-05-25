package beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.omg.CORBA.TIMEOUT;

import sun.security.jca.GetInstance;

/**
 * treatingd 1 minute as a unit, give the current time index from the beginning of this day
 * @author drift
 *
 */
public class TimeSlice {
//	private static int year;
//	private static int month;
//	private static int date;
//	private static int hour;
//	private static int minute;
//	private static int second;
	// return the current time slice index
	public static int getCurrentMinute(){
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		return 3600*hour+minute;
	}
	// return the current date
	public static String getCurrentDate(){
//		Calendar calendar = Calendar.getInstance();
//		Date date = calendar.getTime();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		return sdf.format(date);	
		return getFutureDate(0);
	}
	// return the jter day from now
	public static String getFutureDate(int offSet){
		Date date=new Date();
		Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE,offSet);//把日期往后增加一天.整数往后推,负数往前移动
		 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 String dateString = formatter.format(date);
		 return dateString;
	}
	public static void main(String args[]){
		Calendar calendar = Calendar.getInstance();
		System.out.println("" + calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.MONTH));
		System.out.println("Now is the "+TimeSlice.getCurrentMinute() +" minute");
		System.out.println(TimeSlice.getCurrentDate());
		System.out.println(getFutureDate(2));
	}
	
}
