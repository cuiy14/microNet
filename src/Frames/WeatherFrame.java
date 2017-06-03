package Frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import Panels.WeatherCurve;
import Panels.WeatherTable;

public class WeatherFrame extends JDialog {
	JTabbedPane tp = new JTabbedPane();
//	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WeatherFrame dialog = new WeatherFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @throws IOException 
	 */
	public WeatherFrame() throws IOException {
		setModal(true);
		this.setBounds(450, 100, 500, 300);
		setTitle("天气预报");
		setResizable(false);
		tp.add("表格", new WeatherTable());
		tp.add("曲线", new WeatherCurve());
		this.getContentPane().add(tp);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setVisible(true);
	}

}
