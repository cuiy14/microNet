package Panels;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.sun.mail.imap.protocol.Item;

import beans.TimeSlice;
import beans.WebWeather;
import javafx.scene.layout.Border;
import sun.awt.DesktopBrowse;
import util.WeatherWeb;

public class WeatherTable extends JPanel {
	// 定义表格
	JTable table;
	// 定义滚动条面板(用以使表格可以滚动)
	JScrollPane scrollPane;
	// 定义数据模型类的对象(用以保存数据)，
	DefaultTableModel tableModel;
	// panels
	private JPanel northPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	private JPanel content = new JPanel();

	/**
	 * Create the panel.
	 * 
	 * @throws IOException
	 */
	public WeatherTable() throws IOException {
		// northPanel
		northPanel.add(new JLabel("未来3天天气预报"), BorderLayout.CENTER);
//		JLabel label = new JLabel("数据来源：");
		JButton label2 = new JButton("心知天气");
		label2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doBrowse();
			}
		});
//		northPanel.add(label);
		northPanel.add(label2,BorderLayout.EAST);
		// centerPanel
		scrollPane = new JScrollPane();
		// 定义表格列名数组
		String[] columnNames = { "时间(MM-DD)", "白天天气", "夜晚天气", "气温", "风速" };
		// 定义表格数据数组
		// 4小时预报一次，每天6个点，包含今天，共三天信息
		String[][] tableValues = new String[3][5];
		for (int iter = 0; iter != 3; iter++) {
			String date = TimeSlice.getFutureDate(iter);
			Map<String, WebWeather> weatherMap = WeatherWeb.getAllWeather();
			WebWeather weather = weatherMap.get(date);
			String[] tableItem = { date, weather.getDay(), weather.getNight(),
					Integer.toString(weather.getLow()) + "~" + Integer.toString(weather.getLow()),
					Integer.toString(weather.getWindSpeed()) };
			tableValues[iter] = tableItem;
		}
		// 创建指定表格列名和表格数据的表格模型类的对象
		tableModel = new DefaultTableModel(tableValues, columnNames);
		// 创建指定表格模型的表格
		table = new JTable(tableModel);
		table.setSize(500, 380);
		// 设置 RowSorter(RowSorter 用于提供对 JTable 的排序和过滤)。
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		table.setRowSorter(new TableRowSorter<DefaultTableModel>(tableModel));
		scrollPane.setViewportView(table);
		scrollPane.setSize(500, 380);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		centerPanel.setSize(500,380);
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		// southPanel
//		JLabel label = new JLabel("数据来源：");
//		JButton label2 = new JButton("心知天气");
//		label2.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				doBrowse();
//			}
//		});
//		southPanel.setSize(500, 100);
//		southPanel.add(label);
//		southPanel.add(label2);
		content.setSize(500, 400);
		content.setLayout(new BorderLayout());
		content.add(northPanel, BorderLayout.NORTH);
		content.add(centerPanel, BorderLayout.CENTER);
//		content.add(southPanel, BorderLayout.SOUTH);
		this.add(content, BorderLayout.CENTER);
		this.setSize(500, 400);
		setVisible(true);
	}

	// action listener
	public void doBrowse() {
		String uriString = null;
		if (!Desktop.isDesktopSupported()) {
			// 测试当前平台是否支持此类
			JOptionPane.showMessageDialog(null, "浏览器设置不支持，请手动输入链接：\n https://www.seniverse.com/");
			return;
		}
		// 用来打开系统默认浏览器浏览指定的URL
		Desktop desktop = Desktop.getDesktop();
		try {
			// 创建URI统一资源标识符
			URI uri = new URI("https://www.seniverse.com/");
			// 使用默认浏览器打开超链接
			desktop.browse(uri);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String args[]) throws IOException {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new WeatherTable());
		// frame.pack();
		frame.setSize(500, 400);
		frame.setVisible(true);
		frame.validate();
	}
}
