package Panels;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class WeatherCurve extends JPanel {
//	ChartFrame frame;
	/**
	 * Create the panel.
	 */
	public WeatherCurve() {
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("Category 1", 43.2);
		data.setValue("Category 2", 27.9);
		data.setValue("Category 3", 79.5);
		// create a chart...
		JFreeChart chart = ChartFactory.createPieChart(
		"Sample Pie Chart",
		data,
		true,
		 // legend?
		true,
		 // tooltips?
		false
		 // URLs?
		);
		//create and display a frame...
		JPanel frame = new ChartPanel(chart);
//		frame.pack();
//		frame.setVisible(true);
		this.add(frame);
		this.setVisible(true);
	}
	public static void main(String args[]){
		WeatherCurve weatherCurve= new WeatherCurve();
	}

}
