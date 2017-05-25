package Panels;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.sun.mail.imap.protocol.Item;

import beans.TimeSlice;
import beans.WebWeather;
import util.WeatherWeb;

public class WeatherTable extends JPanel {
	// 定义表格
	JTable table;
	// 定义滚动条面板(用以使表格可以滚动)
	JScrollPane scrollPane;
	// 定义数据模型类的对象(用以保存数据)，
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public WeatherTable() throws IOException {

		scrollPane = new JScrollPane();

		// 定义表格列名数组
		String[] columnNames = { "时间(MM-DD-HH)", "白天天气","夜晚天气", "气温", "风速" };
		// 定义表格数据数组
		// 4小时预报一次，每天6个点，包含今天，共三天信息
		String[][] tableValues = new String[18][5];
		for(int iter=0;iter!=3;iter++){
			String date = TimeSlice.getFutureDate(iter);
			Map<String, WebWeather> weatherMap = WeatherWeb.getAllWeather();
			WebWeather weather = weatherMap.get(date);
			for(int jter=0; jter!=6;jter++){
				String[] tableItem = {date+"-"+jter*4,weather.getDay(),weather.getNight(),
						Double.toString(weather.getSpecificTemperature(jter*240)),
						Integer.toString(weather.getWindSpeed())};
				tableValues[iter*6+jter]=tableItem;			
			}
		}
		// 创建指定表格列名和表格数据的表格模型类的对象
		tableModel = new DefaultTableModel(tableValues, columnNames);
		// 创建指定表格模型的表格
		table = new JTable(tableModel);
		// 设置 RowSorter(RowSorter 用于提供对 JTable 的排序和过滤)。
		table.setRowSorter(new TableRowSorter<DefaultTableModel>(tableModel));
		scrollPane.setViewportView(table);
		add(scrollPane, BorderLayout.CENTER);
		setVisible(true);
	}

}
