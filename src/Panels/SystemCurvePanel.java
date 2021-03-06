package Panels;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
/**
 * this panel is to put in the main page, show the power data with curve
 */
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

import DynamicInput.AllInput;
import jdk.internal.dynalink.beans.StaticClass;

public class SystemCurvePanel extends JPanel{
	AllInput allInput=new AllInput(1440);
	public SystemCurvePanel(){
		super(new BorderLayout());
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(allInput.windGeneration);
		timeseriescollection.addSeries(allInput.photoGeneration);
		timeseriescollection.addSeries(allInput.loadGeneration);
		DateAxis dateaxis = new DateAxis("Time");
		NumberAxis numberaxis = new NumberAxis("负荷水平");
		dateaxis.setTickLabelFont(new Font("SansSerif", 0, 12));
		numberaxis.setTickLabelFont(new Font("SansSerif", 0, 12));
		dateaxis.setLabelFont(new Font("SansSerif", 0, 14));
		numberaxis.setLabelFont(new Font("SansSerif", 0, 14));
		XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(true, false);
		xylineandshaperenderer.setSeriesPaint(0, Color.red);
		xylineandshaperenderer.setSeriesPaint(1, Color.green);
		xylineandshaperenderer.setStroke(new BasicStroke(3F, 0, 2));
		XYPlot xyplot = new XYPlot(timeseriescollection, dateaxis, numberaxis, xylineandshaperenderer);
		xyplot.setBackgroundPaint(Color.lightGray);
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setRangeGridlinePaint(Color.white);
		xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
		dateaxis.setAutoRange(true);
		dateaxis.setLowerMargin(0.0D);
		dateaxis.setUpperMargin(0.0D);
		dateaxis.setTickLabelsVisible(true);
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		JFreeChart jfreechart = new JFreeChart("系统各种负荷水平实时演示图", new Font("SansSerif", 1, 24), xyplot, true);
		jfreechart.setBackgroundPaint(Color.white);
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4),
				BorderFactory.createLineBorder(Color.black)));
		add(chartpanel);	
		this.setSize(600, 500);
	}
	public static void main(String args[]){
		JFrame frame=new JFrame();
		frame.getContentPane().add(new SystemCurvePanel());
		frame.pack();
		frame.setVisible(true);
		frame.validate();
	}
}
