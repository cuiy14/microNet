package Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import beans.TimeSlice;
import beans.WebWeather;
import util.WeatherWeb;

public class WeatherCurve extends JPanel {
	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public WeatherCurve() throws IOException{
		XYDataset dataset= createDataset();
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500,270));
		this.add(chartPanel);	
	}
	/**
	 * create the dataset
	 * @return
	 * @throws IOException 
	 */
	public static XYDataset createDataset() throws IOException{
		Map<String, WebWeather> weatherMap = WeatherWeb.getAllWeather();
		XYSeries day1 = new XYSeries("今天");
		String date = TimeSlice.getFutureDate(0);
		WebWeather weather = weatherMap.get(date);
		for(int iter=1;iter!=25;iter++){
			day1.add((double)iter, weather.getSpecificTemperature(iter*60));
		}
		
		XYSeries day2 = new XYSeries("明天");
		date = TimeSlice.getFutureDate(1);
		weather = weatherMap.get(date);
		for(int iter=1;iter!=25;iter++){
			day2.add((double)iter, weather.getSpecificTemperature(iter*60));
		}
		
		XYSeries day3 = new XYSeries("后天");
		date = TimeSlice.getFutureDate(1);
		weather = weatherMap.get(date);
		for(int iter=1;iter!=25;iter++){
			day3.add((double)iter, weather.getSpecificTemperature(iter*60));
		}
		
		XYSeriesCollection dataset= new XYSeriesCollection();
		dataset.addSeries(day1);
		dataset.addSeries(day2);
		dataset.addSeries(day3);
		
		return dataset;
	}
	
	@SuppressWarnings("deprecation")
	public static JFreeChart createChart(XYDataset dataset){
		// create the chart
		JFreeChart chart= ChartFactory.createXYLineChart(
				"未来三天气温走势", 
				"时间点/小时",
				"气温/摄氏度", 
				dataset,
				PlotOrientation.VERTICAL,
				true,		// include legend
				true,		// tooltips
				false);		// urls
		// some customizing settings
		chart.setBackgroundPaint(Color.WHITE);
		// get a reference to the plot for further customisation...
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		XYLineAndShapeRenderer renderer= (XYLineAndShapeRenderer) plot.getRenderer();
		renderer.setShapesVisible(true);
		renderer.setShapesFilled(true);
		// change the auto tick unit selection to integer units only...
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// OPTIONAL CUSTOMISATION COMPLETED.
		return chart;
	}

	public static JPanel createPanel() throws IOException {
	JFreeChart chart = createChart(createDataset());
	return new ChartPanel(chart);
	}

	public static void main(String args[]) throws IOException{
		JFrame frame=new JFrame();
		frame.getContentPane().add(new WeatherCurve());
		frame.setVisible(true);
		frame.pack();
	}

}
