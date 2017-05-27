package Panels;

import static javax.swing.BorderFactory.createTitledBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.sun.jmx.snmp.tasks.ThreadService;

import Frames.InsertWindFrame;
import beans.WindMachine;
import dao.BatteryDao;
import dao.MotorDao;
import dao.PhotovoltaicDao;
import dao.WindMachineDao;

public class UtilityPanel2 extends JPanel {
	private JPanel northPanel = new JPanel();
	private JPanel westPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	private JComboBox typeComboBox = new JComboBox();
	private JComboBox idComboBox = new JComboBox();
	private JRadioButton basic;
	private JRadioButton history;

	/**
	 * constructors
	 */
	public UtilityPanel2() {
		// main border
		this.setBorder(createTitledBorder(null, "设备管理", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
				new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
		setSize(631, 427);
		setLayout(new BorderLayout());
		this.setBackground(new Color(71, 201, 223));
		// north panel
		northPanel.setBackground(new Color(71, 201, 223));
		northPanel.setBorder(BorderFactory.createTitledBorder(null, "设备信息", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.ABOVE_TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
		northPanel.setLayout(new GridLayout(1, 4));
		JLabel typeLabel = new JLabel("种类：");
		northPanel.add(typeLabel);
		String type[] = { "风机", "光伏板", "蓄电池", "电动机" };
		typeComboBox = new JComboBox(type);
		typeComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// init the idComboBox
				idComboBox.removeAllItems();
				String uSelectedName = typeComboBox.getSelectedItem().toString(); // get
																					// the
																					// selected
																					// name
				String searchName = translate(uSelectedName);
				int count = 0;
				if (searchName == "WindMachine")
					count = (new WindMachineDao()).count();
				else if (searchName == "Photovoltaic")
					count = (new PhotovoltaicDao()).count();
				else if (searchName == "Battery")
					count = (new BatteryDao()).count();
				else
					count = (new MotorDao()).count();
				for (int iter = 0; iter != count; iter++) {
					idComboBox.addItem(Integer.toString(iter + 1));
				}
				repaint();
				validate();
			}
		});
		northPanel.add(typeComboBox);
		JLabel idLabel = new JLabel("ID:");
		northPanel.add(idLabel);
		northPanel.add(idComboBox);
		this.add(northPanel, BorderLayout.NORTH);
		// do the westPanel
		westPanel.setLayout(new GridLayout(2, 1));
		basic = new JRadioButton("基本参数");
		westPanel.add(basic);
		history = new JRadioButton("历史发电");
		westPanel.add(history);
		ButtonGroup group = new ButtonGroup();
		group.add(basic);
		group.add(history);
		this.add(westPanel, BorderLayout.WEST);
		// do the south panel
		JButton enquerybt = new JButton("查询");
		enquerybt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doEnquery();
			}
		});
		southPanel.add(enquerybt);
		JButton insertbt = new JButton("添加");
		insertbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doInsert();
			}
		});
		southPanel.add(insertbt);
		JButton modifybt = new JButton("修改");
		modifybt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doModify();
			}
		});
		southPanel.add(modifybt);
		JButton deletebt = new JButton("删除");
		deletebt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doDelete();
			}
		});
		southPanel.add(deletebt);
		this.add(southPanel, BorderLayout.SOUTH);
		centerPanel.add(new EmptyPanel());
		this.add(centerPanel, BorderLayout.CENTER);
		this.setVisible(true);
		this.setSize(600, 500);
		validate();
	}

	/**
	 * actionlistener for the buttons
	 * 
	 * @return
	 */
	public void doEnquery() {	//*****************************add the other types; add other search function
		if (typeComboBox.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(getParent(), "没有选择查询的设备种类！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String typeSelected = translate((String) typeComboBox.getSelectedItem());
		if (idComboBox.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(getParent(), "没有选择查询的设备编号！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int idSelected = Integer.parseInt((String) idComboBox.getSelectedItem());
		if(typeSelected.equals("WindMachine")){
		if (basic.isSelected()) {
			centerPanel.remove(centerPanel.getComponent(0));

			WindMachineDao windMachineDao = new WindMachineDao();
			WindMachine wm = windMachineDao.selectById(idSelected);
			BasicWindPanel basicWindPanel = new BasicWindPanel(wm);
			centerPanel.add(basicWindPanel);
			this.validate();
		} else if (history.isSelected()) {
			centerPanel.remove(centerPanel.getComponent(0));
			centerPanel.add(new WeatherCurve());
			this.validate();
		} else {
			JOptionPane.showMessageDialog(getParent(), "没有选择查询类型！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		}
	}

	public void doInsert() {
		if (typeComboBox.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(getParent(), "没有选择查询的设备种类！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String typeSelected = translate((String) typeComboBox.getSelectedItem());
		if(typeSelected.equals("WindMachine")){
			Frame popFrame = new InsertWindFrame();
		}
	}

	public void doModify() {
	}

	public void doDelete() {
	}

	// translate the chinese into english
	private String translate(String chinese) {
		if (chinese.equals("风机"))
			return "WindMachine";
		else if (chinese.equals("光伏板"))
			return "Photovoltaic";
		else if (chinese.equals("蓄电池"))
			return "Battery";
		else
			return "Motor";
	}

	// main
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new UtilityPanel2());
		frame.setVisible(true);
		frame.setSize(600, 500);
	}
}
