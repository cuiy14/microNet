package Panels;
/**
 * the panel for forecasting photovoltaic using R
 * put into the forecast frame
 */
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

public class PhotoFuturePanel extends JPanel{
	String 	path="/home/drift/Documents/eclipseMars/microNet/src/DataSet/photoHistory.csv";
	public double [] coef;
	/**
	 * constructors
	 */
	public PhotoFuturePanel(int day){
		coef=j2i(day);
		XYDataset xydataset = createDataset(coef);
        JFreeChart jfreechart = createChart(xydataset);
        ChartPanel chartpanel = new ChartPanel(jfreechart);
//        chartpanel.setPreferredSize(new Dimension(500, 270));
//        setContentPane(chartpanel);
        this.add(chartpanel);
        this.setVisible(true);
	}
	/**
	 * create the dataset with the data file & the coef
	 */
	public XYDataset createDataset( double[] coef){
        TimeSeries timeseries = new TimeSeries("下5%分位数");
        TimeSeries timeseries1 = new TimeSeries("下95%分位数");
        Minute minute = new Minute(0, 0, 1, 1, 2017);
        // do the dataset
        int count=0;
		   try { 
            BufferedReader reader = new BufferedReader(new FileReader(path));
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null; 
            while(count!=1441){ 
            	line=reader.readLine();
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分,注意中英文  
                String last = item[0];
                double value = Double.parseDouble(last);//如果是数值，可以转化为数值
                count=count+1;
                double generation=coef[0]+coef[1]*value;
                double generation1=coef[4]+coef[5]*value;
                timeseries.add(minute, generation);
                timeseries1.add(minute, generation1);
                minute=(Minute)minute.next();
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
	        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
	        timeseriescollection.addSeries(timeseries);
	        timeseriescollection.addSeries(timeseries1);
	        return timeseriescollection;      
	}
	/**
	 * create the chart
	 */
    private static JFreeChart createChart(XYDataset xydataset)
    {
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("未来一日风机出力预测", "Time", "风机出力水平", xydataset, true, true, false);
        jfreechart.setBackgroundPaint(Color.white);
        XYPlot xyplot = jfreechart.getXYPlot();
        xyplot.setRenderer(new XYDifferenceRenderer(Color.green, Color.red, false));
        xyplot.setBackgroundPaint(Color.lightGray);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        DateAxis dateaxis = new DateAxis("Time");
        dateaxis.setLowerMargin(0.0D);
        dateaxis.setUpperMargin(0.0D);
        xyplot.setDomainAxis(dateaxis);
        xyplot.setForegroundAlpha(0.5F);
        return jfreechart;
    }
	/**
	 * communication with R
	 */
	public double[] j2i(int day){
//		Rengine engine = new Rengine(new String[] { "--no-save" }, false, null);
		// to remove some problems of engine.end()"R is already initialized", use the below two sentences
		Rengine engine = Rengine.getMainEngine();
		if(engine == null)
		    engine = new Rengine(new String[] {"--vanilla"}, false, null);
	    engine.eval("source('/home/drift/Documents/eclipseMars/microNet/src/forecast/quantreg.R')");
	    engine.eval("filepath<-'/home/drift/Documents/eclipseMars/microNet/src/DataSet/photoHistory.csv'");
	    engine.eval("result <- quantreg(filepath,"+day+")");
	    engine.eval("print('hello!')");
	    REXP coef = engine.eval("result");
	    double [] coeff= coef.asDoubleArray();
	    engine.end();
	    return coeff;

	}
	// main
	public static void main(String args[]){
		PhotoFuturePanel aFuturePanel = new PhotoFuturePanel(2);
		JFrame frame = new JFrame();
		frame.getContentPane().add(new PhotoFuturePanel(2));
		frame.pack();
		frame.setVisible(true);
		frame.validate();
	}
}
