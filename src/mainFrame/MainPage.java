package mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import Frames.PersonnelFrame;
import Frames.SystemFrame;
import Frames.UtilityFrame;
import Frames.WeatherFrame;
import Panels.PersonnelPanel;
import Panels.WeatherCurve;
import javafx.scene.image.Image;
import Frames.EmailFrame;
import Frames.ForecastFrame;


public class MainPage extends JFrame {
	JDesktopPane desktop;
	JMenuBar menuBar;
	JToolBar toolBar;
	JPanel content;
	
	public MainPage(){
		super("微电网管理系统");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(300,100,800,600);
		buildContent();
		buildMenu();
		buildToolBar();
		
		// exit the system when closing
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				quit();
			}
		});
	}
	// build the content
	protected void buildContent(){
		desktop = new JDesktopPane();
		desktop.setLayout(new BorderLayout());
		// build the real time data panel
		JPanel dataPanel = dataPanel();
		desktop.add(dataPanel,BorderLayout.WEST);
		content = new WeatherCurve();
		desktop.add(content, BorderLayout.CENTER);
		getContentPane().add(desktop);
	}
	// build the dataPanel
	protected JPanel dataPanel(){
		/**
		 * write a thread, to refresh the number every minute**************************************************
		 */
		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(BorderFactory.createEtchedBorder());
		dataPanel.setBounds(0,30,500,50);
		dataPanel.setLayout(new GridLayout(8,1));
		// wind machine
		Label windOut = new Label("风机总出力");
		JButton windNum = new JButton("");
		windNum.setText("123");
		windNum.setEnabled(false);
		// photovoltaic
		Label photoOut = new Label("光伏总出力");
		JButton photoNum = new JButton("");
		photoNum.setText("456");
		photoNum.setEnabled(false);
		// load
		Label load = new Label("系统总负荷");
		JButton loadNum = new JButton("");
		loadNum.setText("789");
		loadNum.setEnabled(false);
		// 总剩余功率
		Label remain = new Label("净发电功率");
		JButton remainNum= new JButton("");
		double renum= Double.parseDouble(windNum.getText())+Double.parseDouble(
				photoNum.getText())-Double.parseDouble(loadNum.getText());
		remainNum.setText(Double.toString(renum));
		remainNum.setEnabled(false);
		// add the component	
		dataPanel.add(windOut);
		dataPanel.add(windNum);
		dataPanel.add(photoOut);
		dataPanel.add(photoNum);
		dataPanel.add(load);
		dataPanel.add(loadNum);
		dataPanel.add(remain);
		dataPanel.add(remainNum);
		
		dataPanel.setVisible(true);		
		return dataPanel;
	}
	// build the menubar
	protected void buildMenu(){
		menuBar = new JMenuBar();
//		menuBar.setLayout(new GridLayout(1,2));
		menuBar.setOpaque(true);
		// add the administration menu
		JMenu administration = buildAdministration();
		menuBar.add(administration);
		
		// add the supervision menu
		JMenu supervision = buildSupervision();
		menuBar.add(supervision);
		// set the menubar
		menuBar.setBorder(BorderFactory.createEtchedBorder());
		setJMenuBar(menuBar);		
	}
	// build the administration menu
	protected JMenu  buildAdministration(){
		JMenu administration = new JMenu("管理");
		JMenuItem personnel = new JMenuItem("人事档案");
		JMenuItem utility = new JMenuItem("设备档案");
		// set the shortcuts
		personnel.setMnemonic('P');
		personnel.setAccelerator(KeyStroke.getKeyStroke(
				'P', java.awt.Event.CTRL_MASK,false));
		personnel.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new PersonnelFrame();
			}
		});
		utility.setMnemonic('U');
		utility.setAccelerator(KeyStroke.getKeyStroke(
				'U', java.awt.Event.CTRL_MASK,false));
		utility.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new UtilityFrame();			
			}
		});
		// add the components
		administration.add(personnel);
		administration.addSeparator();
		administration.add(utility);

		return administration;
	}

	// build the supervision menu
	protected JMenu  buildSupervision(){
		JMenu supervision = new JMenu("运行");
		JMenuItem weather = new JMenuItem("天气预报");
		JMenuItem system = new JMenuItem("系统状态查询");
		JMenuItem prophet = new JMenuItem("明日预测");
		// set the shortcuts
		weather.setMnemonic('W');
		weather.setAccelerator(KeyStroke.getKeyStroke(
				'W', java.awt.Event.CTRL_MASK,false));
		weather.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new WeatherFrame();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}
		});
		system.setMnemonic('S');
		system.setAccelerator(KeyStroke.getKeyStroke(
				'S', java.awt.Event.CTRL_MASK,false));
		system.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new SystemFrame();			
			}
		});
		prophet.setMnemonic('F');
		prophet.setAccelerator(KeyStroke.getKeyStroke(
				'F', java.awt.Event.CTRL_MASK,false));
		weather.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ForecastFrame();			
			}
		});
		supervision.add(weather);
		supervision.addSeparator();
		supervision.add(system);
		supervision.addSeparator();
		supervision.add(prophet);
		return supervision;
	}
	// build the toolbar
	protected void buildToolBar(){
		toolBar= new JToolBar();
		toolBar.setFloatable(true);
		/**
		 *  new icon
		 */
		// personnel
		 JButton btpersonnel = new JButton("");
		 ImageIcon perIcon = new ImageIcon(MainPage.class.getResource(
				 "/icon/personnelS.png"));	// relative path
		 btpersonnel.setIcon(perIcon);
		 btpersonnel.setSize(10, 10);
		 btpersonnel.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TeODO Auto-generated method stub
				setCenterPanel((new PersonnelPanel()).getPanel());
			}
		});
		 toolBar.add(btpersonnel);
		 // utility
		 JButton btmachine = new JButton("");
		 btmachine.setIcon(new ImageIcon(MainPage.class.getResource("../icon/machineS.png")));
		 btmachine.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 	}
		 });
		 btmachine.setSize(10, 10);
		 btmachine.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TeODO Auto-generated method stub
				new UtilityFrame();
			}
		});
		 toolBar.add(btmachine);
		 // weather
		 JButton btweather = new JButton("");
		 btweather.setIcon(new ImageIcon(MainPage.class.getResource("../icon/weatherS.png")));
		 btweather.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 	}
		 });
		 btweather.setSize(10, 10);
		 btweather.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TeODO Auto-generated method stub
				try {
					new WeatherFrame();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		 toolBar.add(btweather);
		 // forecast
		 JButton btforecast = new JButton("");
		 btforecast.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 	}
		 });
		 btforecast.setIcon(new ImageIcon(MainPage.class.getResource("../icon/forecastS.png")));
		 btforecast.setSize(5, 5);
		 btforecast.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TeODO Auto-generated method stub
				new ForecastFrame();
			}
		});
		 toolBar.add(btforecast);
		 // email
		 JButton btemail = new JButton("");
		 ImageIcon emaIcon = new ImageIcon(MainPage.class.getResource(
				 "/icon/emailS.png"));	// relative path
		 btemail.setIcon(emaIcon);
		 btemail.setSize(5, 5);
		 btemail.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TeODO Auto-generated method stub
				new EmailFrame();
			}
		});
		 toolBar.add(btemail);
		 desktop.add(toolBar, BorderLayout.NORTH);
		 
	}
	// things to do when trying to close the main page
	protected void quit(){
		String title = "确认窗口";
		String message="确认退出系统？";
		int result = JOptionPane.showConfirmDialog(this, message,title,
				JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
		if(result == JOptionPane.YES_OPTION)
			System.exit(0);			
	}
	// set the given Jpanel to the center of contentpanel
	protected void setCenterPanel(JPanel panel){
		desktop.remove(content);
		content = panel;	
		desktop.add(content,BorderLayout.CENTER);
		desktop.validate();
//		this.validate();
	}
	
	public static void main(String args[]){
		MainPage mainPage = new MainPage();
		mainPage.setVisible(true);
	}

}
