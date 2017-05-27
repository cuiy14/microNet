package Panels;

import static javax.swing.BorderFactory.createTitledBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sun.jmx.snmp.tasks.ThreadService;

import beans.WindMachine;
import dao.WindMachineDao;

public class UtilityPanel extends JPanel {
	private JPanel upperpanel = new JPanel();
	private JPanel lowerpanel = new JPanel();
	JComboBox uNameComboBox;
    BasicWindPanel bpanel = new BasicWindPanel();
    WeatherCurve ppanel = new WeatherCurve();
	private JComboBox idComboBox = new JComboBox();

	/**
	 * constructor
	 */
	public UtilityPanel() {
		// the whole panel
		this.setBorder(createTitledBorder(null, "设备管理", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
				new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
		setSize(631, 427);
		setLayout(new BorderLayout());
		this.setBackground(new Color(71, 201, 223));
		// upper panel
		upperpanel.setBackground(new Color(71, 201, 223));
		upperpanel.setBorder(BorderFactory.createTitledBorder(null, "设备信息", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.ABOVE_TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
		// add the comboBox
		JLabel idLabel = new JLabel("ID:");
		idLabel.setBounds(262, 35, 54, 15);
		upperpanel.add(idLabel);
		idComboBox.setBounds(310, 32, 101, 21);
		upperpanel.add(idComboBox);
		JLabel uNameLabel = new JLabel("种类：");
		uNameLabel.setBounds(70, 35, 54, 15);
		upperpanel.add(uNameLabel);
		String uName[] = { "风机", "光伏板", "蓄电池", "电动机" };
		JComboBox uNameComboBox = new JComboBox(uName);
//		lowerpanel = buildWindMachine();
		lowerpanel.setLayout(new BorderLayout());
		lowerpanel.add(bpanel,BorderLayout.CENTER);
		this.add(lowerpanel,BorderLayout.SOUTH);
		uNameComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				idComboBox.removeAllItems();
				String uSelectedName = uNameComboBox.getSelectedItem().toString(); // get the selected name
				String searchName = translate(uSelectedName);
				if (searchName == "WindMachine")
//					lowerpanel = buildWindMachine();
					buildWindMachine();
				validate();
				System.out.println("finish the unameListener");
				// *********************************************************************
				// else if(searchName=="Photovoltaic")
				// panel_2=buildPhotovoltaic();
				// else if(searchName=="Battery")
				// panel_2=buildBattery();
				// else
				// panel_2=buildMotor();
			}
		});
		upperpanel.setLayout(new GridLayout(1, 4));
		upperpanel.add(uNameLabel);
		upperpanel.add(uNameComboBox);
		upperpanel.add(idLabel);
		upperpanel.add(idComboBox);
		add(upperpanel, BorderLayout.NORTH);
//		add(lowerpanel, BorderLayout.CENTER);
		validate();
		System.out.println("finish the constructor");
	}

	/**
	 * 四种panel,根据不同类型而进行修改
	 * 
	 * @param chinese
	 * @return
	 */
	public void buildWindMachine() {
		WindMachineDao windMachineDao = new WindMachineDao();
		// modify the id comboBox
		ArrayList<WindMachine> windMachines = windMachineDao.selectWindMachine();
		int total = windMachines.size();
		for (int iter = 0; iter != total; iter++) {
			idComboBox.addItem(Integer.toString(iter + 1));
		}
		repaint();
		validate();
		// do the lower panel
//		JPanel lpanel = new JPanel();
		
		lowerpanel.setBorder(BorderFactory.createTitledBorder(null, "设备信息", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.ABOVE_TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
		lowerpanel.setSize(572,310);
		lowerpanel.setLayout(new BorderLayout());
		// left panel
		JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(createTitledBorder(null, "设备信息", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
                new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
        scrollPane.setSize(109, 218);
        lowerpanel.add(scrollPane,BorderLayout.WEST);
        String name[] = { "基本信息", "发电历史" };
        final JList jlist = new JList(name);
        BasicWindPanel bpanel = new BasicWindPanel();
        WeatherCurve ppanel = new WeatherCurve();
        lowerpanel.add(bpanel, BorderLayout.CENTER);
        ListSelectionModel listSelectionModel = jlist.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {    
            	ListSelectionModel lsm = (ListSelectionModel)e.getSource();
            	 int firstIndex = e.getFirstIndex();
//                if (!e.getValueIsAdjusting()) {
                	System.out.println("e.getValuechanged");
//                    if ((uNameComboBox.getSelectedItem() == null) || (idComboBox.getSelectedItem() == null)) {
//                        JOptionPane.showMessageDialog(getParent(), "没有选择查询的设备！", "信息提示框",
//                                JOptionPane.INFORMATION_MESSAGE);
////                        return;
//                    }
                	System.out.println("enter the list");
//                    JList list = (JList) e.getSource();// 获取事件源
//                    // 获取列表选项并转换为字符串
//                    String value = (String) list.getSelectedValue();
                	
//                    System.out.print("get the value"+value);
//                    if (value.equals("基本信息")) { // 判断用户是否选择了“基本信息”
                	if(firstIndex==0){
                    	System.out.println("enter 基本信息");
                        String idString = idComboBox.getSelectedItem().toString(); // 获取用户选择的ID
                        int idSelected=Integer.parseInt(idString);
//                        bpanel.setBounds(140, 53, 409, 208);
                        WindMachineDao windMachineDao= new WindMachineDao();
                       WindMachine wm = windMachineDao.selectById(idSelected);
                       System.out.println("start to set text");
                       bpanel.setId(Integer.toString(wm.getId()));
                       bpanel.setSerial(wm.getSerial());
                       bpanel.setLow(Integer.toString(wm.getLowWindScale()));
                       bpanel.setHigh(Integer.toString(wm.getHighWindScale()));
                       bpanel.setPower(Integer.toString(wm.getNormalPower()));
                       lowerpanel.remove(ppanel); // 移除显示ID详细信息的面板
                       lowerpanel.add(bpanel,BorderLayout.CENTER);
                       validate();
                       System.out.println("jlist listener");
                    }
//                    if (value.equals("发电历史")) {
                	if(firstIndex==1){
                		lowerpanel.remove(bpanel);
                		lowerpanel.add(ppanel, BorderLayout.CENTER);
//                        repaint();
                        validate();
                    }
                    validate();
                }
//           }
        });
        scrollPane.setViewportView(jlist);
        System.out.println("finish the BuildFunction");
        lowerpanel.setVisible(true);
//        this.add(lpanel, BorderLayout.CENTER);
        this.validate();
//		return lpanel;
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
		frame.getContentPane().add(new UtilityPanel());
		frame.setVisible(true);
		frame.setSize(700, 600);
	}
}
