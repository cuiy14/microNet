package DynamicInput;
/**
 * this is a demo, to show how to read the history data into a real time series
 * the file used in mainpage is AllInput.java
 */
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

public class WindMachineInput {
	static private String path = "/home/drift/Documents/eclipseMars/microNet/src/DataSet/WindHistory.csv";
	public static TimeSeries windGeneration = new TimeSeries("风机出力");
	private static Vector<Double> series;
	static int index = 0;
	static DataGenerator dataGenerator;

	/**
	 * 
	 * @param interval,
	 *            the time to roll back
	 */
	public WindMachineInput(int interval) {
		series = new Vector<Double>(1440);
		// do the dataset into a Vector
		int count = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			reader.readLine();// 第一行信息，为标题信息，不用，如果需要，注释掉
			String line = null;
			while (count != 1440) {
				line = reader.readLine();
				String item[] = line.split(",");// CSV格式文件为逗号分隔符文件，这里根据逗号切分,注意中英文
				String last = item[1]; // read the second column data
				double value = Double.parseDouble(last);// 如果是数值，可以转化为数值
				count = count + 1;
				series.addElement(value);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		windGeneration.setMaximumItemCount(interval);
		dataGenerator = new DataGenerator(1000);
		dataGenerator.start();
	}

	// return the real time value
	public static double realtimeInput() {
		return series.elementAt(index);
	}

	public class DataGenerator extends Timer implements ActionListener {

		public void actionPerformed(ActionEvent actionevent) {
			double value = series.get(index);
			windGeneration.add(new Millisecond(), value);
			// refresh index
			if (index == series.size() - 1)
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
