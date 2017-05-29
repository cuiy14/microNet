package Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import beans.User;
import dao.UserDao;
import modelTables.UserTable;

public class PersonnelPanel extends JPanel {
	JPanel personnelPanel;

	public JPanel getPanel() {
		JPanel personnelPanel = new JPanel();
		UserTable userTable = new UserTable();
		JTable table = new JTable(userTable);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		// table.setPreferredScrollableViewportSize(new Dimension(550, 30));
		JScrollPane scrollPane = new JScrollPane(table);
		personnelPanel.add(scrollPane);
		personnelPanel.setSize(500, 400);
		personnelPanel.setVisible(true);
		return personnelPanel;
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setSize(500, 400);
		frame.getContentPane().add((new PersonnelPanel()).getPanel(), BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
