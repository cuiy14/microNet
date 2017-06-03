package Panels;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
/**
 * this file is to fulfill the need of ForecastPanel, to shwo the history data in a curvePanel
 * it contains some constants, which should be moved to a new file
 */
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import com.sun.prism.impl.Disposer.Record;

public class RecordPanel extends JPanel{
	/**
	 * constrctor
	 */
	String path;
	String title;
	String type;
	int time;
	
	public RecordPanel(String atype, int atime){
		type=atype;
		time=atime;
		XYDataset dataset= createDataset(type,time);
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500,270));
		this.add(chartPanel);
       } 
	// create the dataset
	public XYDataset createDataset(String type, int time){
		if(type.equals("wind")){
			path="/home/drift/Documents/eclipseMars/microNet/src/DataSet/WindHistory.csv";
			title="风机";
			}
		else if(type.equals("photo")){
			path="/home/drift/Documents/eclipseMars/microNet/src/DataSet/photoHistory.csv";
			title="光伏";
			}
		else{
			path="/home/drift/Documents/eclipseMars/microNet/src/DataSet/loadHistory.csv";	
			title="负荷";
		}
		String ti = new String();
		if(time==1){
			ti="今天";
			title=title+"今天负荷记录";
		}
		else if(time==0){
			ti="明天";
			title=title+"明天负荷预测";
		}
		else {
			ti="后天";
			title=title+"后天负荷预测";
			}
		// read the data
		XYSeries day = new XYSeries(ti);
		int count=0;
		   try { 
               BufferedReader reader = new BufferedReader(new FileReader(path));
               reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
               String line = null; 
               while((line=reader.readLine())!=null){ 
                   String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分,注意中英文  
                   String last = item[time];//这就是你要的数据了
                   double value = Double.parseDouble(last);//如果是数值，可以转化为数值
                   day.add((double) count, value);
                   count=count+1;
               } 
           } catch (Exception e) { 
               e.printStackTrace(); 
           } 
		   XYSeriesCollection dataset= new XYSeriesCollection();
			dataset.addSeries(day);
		 return dataset;
	}
	// create the chart
	public JFreeChart createChart(XYDataset dataset){
		// create the chart
		JFreeChart chart= ChartFactory.createXYLineChart(
				title, 
				"时间点/分钟",
				"负荷水平", 
				(XYDataset) dataset,
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
	// 
	public JPanel createPanel() throws IOException {
	JFreeChart chart = createChart(createDataset(type, time));
	JPanel pp= new ChartPanel(chart);
//	pp.setSize(500,350);
	return pp;
	}
	
	public static void main(String args[]) throws IOException{
		JFrame frame=new JFrame();
		frame.getContentPane().add((new RecordPanel("wind", 2)).createPanel());
		frame.setVisible(true);
		frame.pack();
	}
}
