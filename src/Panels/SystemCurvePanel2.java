package Panels;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

import DynamicInput.AllInput;

/**
 * this panel is to replace the systemcurvepanel.java. its plot is moving
 * instead of resizing
 * 
 * @author drift
 *
 */
public class SystemCurvePanel2 extends JPanel {
	AllInput allInput = new AllInput(1440);

	public SystemCurvePanel2() {
		super(new BorderLayout());
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(allInput.windGeneration);
		timeseriescollection.addSeries(allInput.photoGeneration);
		timeseriescollection.addSeries(allInput.loadGeneration);
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("系统各种负荷水平实时演示图", "时间", "负荷水平",
				timeseriescollection, true, true, false);
		jfreechart.setBackgroundPaint(Color.white);
		XYPlot xyplot = jfreechart.getXYPlot();
		xyplot.setBackgroundPaint(Color.lightGray);
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setRangeGridlinePaint(Color.white);
		xyplot.setAxisOffset(new RectangleInsets(4D, 4D, 4D, 4D));
		ValueAxis valueaxis = xyplot.getDomainAxis();
		valueaxis.setAutoRange(true);
		valueaxis.setFixedAutoRange(60000D);
		xyplot.setDataset(1, timeseriescollection);
		NumberAxis numberaxis = new NumberAxis("负荷水平");
		numberaxis.setAutoRangeIncludesZero(false);
		xyplot.setRenderer(1, new DefaultXYItemRenderer());
		xyplot.setRangeAxis(1, numberaxis);
		xyplot.mapDatasetToRangeAxis(1, 1);
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		add(chartpanel);
	}

	// test
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new SystemCurvePanel2());
		frame.pack();
		frame.setVisible(true);
		frame.validate();
	}
}
