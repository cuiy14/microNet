package Panels;

/**
 * this implements the utility archive button in the mainPage
 */
import static javax.swing.BorderFactory.createTitledBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

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
import javax.swing.text.AbstractDocument.Content;

import com.sun.jmx.snmp.tasks.ThreadService;

import Frames.InsertWindFrame;
import Frames.UpdateWindFrame;
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
	// private int countId;

	/**
	 * constructors
	 */
	public UtilityPanel2() {
		// main border
		this.setBorder(createTitledBorder(null, "设备管理", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
				new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
		// setSize(631, 427);
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
				String[] idarray = countID();
				refresh(idarray);
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
				try {
					doEnquery();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		JButton refreshbt = new JButton("更新");
		refreshbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] idarray = countID();
				refresh(idarray);
				idComboBox.repaint();
				validate();
			}
		});
		southPanel.add(refreshbt);
		this.add(southPanel, BorderLayout.SOUTH);
		centerPanel.setSize(400,300);
		centerPanel.add(new EmptyPanel());
		this.add(centerPanel, BorderLayout.CENTER);
		this.setVisible(true);
		this.setSize(600, 500);
		// this.pack();
		validate();
	}

	/**
	 * actionlistener for the buttons
	 * 
	 * @return
	 * @throws IOException
	 */
	public void doEnquery() throws IOException { // *****************************add
													// the other types; add
													// other search function
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
		if (typeSelected.equals("WindMachine")) {
			if (basic.isSelected()) {
				centerPanel.remove(centerPanel.getComponent(0));

				WindMachineDao windMachineDao = new WindMachineDao();
				WindMachine wm = windMachineDao.selectById(idSelected);
				BasicWindPanel basicWindPanel = new BasicWindPanel(wm);
				centerPanel.add(basicWindPanel);
				this.validate();
			} else if (history.isSelected()) {
				centerPanel.remove(centerPanel.getComponent(0));
				if (typeSelected.equals("WindMachine"))
					centerPanel.add(new CurvePanel(0));
				else if (typeSelected.equals("Photovoltaic"))
					centerPanel.add(new CurvePanel(1));
				else {
					centerPanel.add(new CurvePanel(2));
				}
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
		if (typeSelected.equals("WindMachine")) {
			Frame popFrame = new InsertWindFrame();
		}
	}

	public void doModify() {
		if (typeComboBox.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(getParent(), "没有选择待修改的设备种类！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String typeSelected = translate((String) typeComboBox.getSelectedItem());
		if (idComboBox.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(getParent(), "没有选择待修改的设备编号！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int idSelected = Integer.parseInt((String) idComboBox.getSelectedItem());
		if (typeSelected.equals("WindMachine")) {
			WindMachineDao wmd = new WindMachineDao();
			WindMachine wm = wmd.selectById(idSelected);
			Frame popFrame = new UpdateWindFrame(wm);
		}
	}

	public void doDelete() {
		if (typeComboBox.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(getParent(), "没有选择待删除的设备种类！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String typeSelected = translate((String) typeComboBox.getSelectedItem());
		if (idComboBox.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(getParent(), "没有选择待删除的设备编号！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int idSelected = Integer.parseInt((String) idComboBox.getSelectedItem());
		if (typeSelected.equals("WindMachine")) {
			WindMachineDao wmd = new WindMachineDao();
			wmd.deleteById(idSelected);
			JOptionPane.showMessageDialog(getParent(), "删除成功！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
			try {
				WindMachine wm = wmd.selectWindMachine().get(0);
				BasicWindPanel basicWindPanel = new BasicWindPanel(wm);
				centerPanel.remove(0);
				centerPanel.add(basicWindPanel);
				String[] idarray = countID();
				refresh(idarray);
				this.validate();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(getParent(), "该类设备已全部删除！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
			}
		}
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

	// count the id range
	public String[] countID() {
		String uSelectedName = typeComboBox.getSelectedItem().toString(); // get
																			// the
																			// selected
																			// item
		String searchName = translate(uSelectedName);
		int count = (new WindMachineDao()).count();
		String[] idarray = new String[count];
		if (searchName == "WindMachine") {
			ArrayList<WindMachine> wma = (new WindMachineDao()).selectWindMachine();
			int i = 0;
			for (i = 0; i != count; i++) {
				idarray[i] = Integer.toString(wma.get(i).getId());
			}
		}
		return idarray;
		// else if (searchName == "Photovoltaic")
		// count = (new PhotovoltaicDao()).count();
		// else if (searchName == "Battery")
		// count = (new BatteryDao()).count();
		// else
		// count = (new MotorDao()).count();
	}

	// ID comboBox refresher
	public void refresh(String[] idarray) {
		idComboBox.removeAllItems();
		for (String string : idarray) {
			idComboBox.addItem(string);
		}
		validate();
	}

	// main
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new UtilityPanel2());
		frame.setVisible(true);
		frame.setSize(600, 500);
	}
}
