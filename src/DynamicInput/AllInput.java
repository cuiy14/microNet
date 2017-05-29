package DynamicInput;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import javax.swing.Timer;

import org.apache.commons.collections.map.StaticBucketMap;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.omg.CORBA.FREE_MEM;

public class AllInput {
	static private String pathWind = "/home/drift/Documents/eclipseMars/microNet/src/DataSet/WindHistory.csv";
	static private String pathPhoto="/home/drift/Documents/eclipseMars/microNet/src/DataSet/photoHistory.csv";
	static private String pathLoad="/home/drift/Documents/eclipseMars/microNet/src/DataSet/loadHistory.csv";
	public static TimeSeries windGeneration = new TimeSeries("风机出力");
	public static TimeSeries photoGeneration = new TimeSeries("光伏出力");
	public static TimeSeries loadGeneration = new TimeSeries("负荷水平");
	public static Vector<Double> seriesWind;
	public static Vector<Double> seriesPhoto;
	public static Vector<Double> seriesLoad;
	static int index = 0;
	static DataGenerator dataGenerator;

	/**
	 * 
	 * @param interval,
	 *            the time to roll back
	 */
	public AllInput(int interval) {
		// do the dataset into a Vector
		seriesWind=readData(pathWind);
		seriesPhoto=readData(pathPhoto);
		seriesLoad=readData(pathLoad);
		windGeneration.setMaximumItemCount(interval);
		photoGeneration.setMaximumItemCount(interval);
		loadGeneration.setMaximumItemCount(interval);
		dataGenerator = new DataGenerator(1000);
		dataGenerator.start();
	}
	// read data from file into vector
	public Vector<Double> readData(String pathName){
		Vector<Double> series=new Vector<Double>(1440);
		int count = 0;
		try {
			BufferedReader readerWind = new BufferedReader(new FileReader(pathName));
			readerWind.readLine();// 第一行信息，为标题信息，不用，如果需要，注释掉
			String line = null;
			while (count != 1440) {
				line = readerWind.readLine();
				String item[] = line.split(",");// CSV格式文件为逗号分隔符文件，这里根据逗号切分,注意中英文
				String last = item[1]; // read the second column data
				double value = Double.parseDouble(last);// 如果是数值，可以转化为数值
				count = count + 1;
				series.addElement(value);
			}
			readerWind.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return series;
	}
	// return the real time value
	public static double getWindInput() {
		return seriesWind.elementAt(index);
	}
	public static double getPhotoInput(){
		return seriesPhoto.elementAt(index);
	}
	public static double getLoadInput(){
		return seriesLoad.elementAt(index);
	}

	public class DataGenerator extends Timer implements ActionListener {

		public void actionPerformed(ActionEvent actionevent) {
			double value = seriesWind.get(index);
			double value2=seriesPhoto.get(index);
			double value3 = seriesLoad.get(index);
			windGeneration.add(new Millisecond(), value);
			photoGeneration.add(new Millisecond(), value2);
			loadGeneration.add(new Millisecond(), value3);
			// refresh index
			if (index == seriesWind.size() - 1)
				index = 0;
			else
				index++;
		}

		// the interval between two events
		public DataGenerator(int interval) {
			super(interval, null);
			addActionListener(this);
		}
	}

	// test
	public static void main(String args[]) {
		WindMachineInput windMachineInput = new WindMachineInput(1440);
		double value=0;
		 while(true){
			 if(value!=windMachineInput.realtimeInput()){
				 value=windMachineInput.realtimeInput();
				 System.out.println(windMachineInput.realtimeInput()); 
			 }
		 }

	}

}
