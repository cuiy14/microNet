package mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import Frames.WeatherFrame;

public class MainPage extends JFrame {

	private JPanel jcontentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage frame = new MainPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public MainPage() {
		setForeground(Color.BLACK);
		setTitle("微电网管理系统");
		setResizable(false);
//		setIconImage(Toolkit.getDefaultToolkit().getImage(
//				MainPage.class.getResource("/imgs/log.png")));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		
		jcontentPane = new JPanel();
		jcontentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		jcontentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(jcontentPane);
		/**
		 *  customize the contentPane
		 */
		BackgroundPanel contentPane = new BackgroundPanel();
		contentPane.setImage(getToolkit().getImage(
				getClass().getResource("/mainFrame/Background.png")));// 设置背景面板的图片
		jcontentPane.add(contentPane, BorderLayout.CENTER);// 添加背景面板到内容面板
		// add the weather button
		JButton btnWeatherButton = new JButton("");					
		btnWeatherButton.setIcon(new ImageIcon(MainPage.class
				.getResource("/mainFrame/buttonW.png")));
		btnWeatherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					do_btnWeatherButton_actionPerformed(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnWeatherButton.setBounds(15, 114, 135, 43);
		contentPane.add(btnWeatherButton);
		// add the Email button
		JButton btnEmailButton = new JButton("");					
		btnEmailButton.setIcon(new ImageIcon(MainPage.class
				.getResource("/mainFrame/buttonE.png")));
		btnEmailButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnEmailButton_actionPerformed(e);
			}
		});
		btnEmailButton.setBounds(15, 154, 135, 43);
		contentPane.add(btnEmailButton);
		// add the Curve button
		JButton btnCurveButton = new JButton("");					
		btnCurveButton.setIcon(new ImageIcon(MainPage.class
				.getResource("/mainFrame/buttonC.png")));
		btnCurveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnCurveButton_actionPerformed(e);
			}
		});
		btnCurveButton.setBounds(15, 194, 135, 43);
		contentPane.add(btnCurveButton);
		// add the Utility button
		JButton btnUtilityButton = new JButton("");					
		btnUtilityButton.setIcon(new ImageIcon(MainPage.class
				.getResource("/mainFrame/buttonU.png")));
		btnUtilityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnUtilityButton_actionPerformed(e);
			}
		});
		btnUtilityButton.setBounds(15, 234, 135, 43);
		contentPane.add(btnUtilityButton);
		// add the Personnel button
		JButton btnPersonnelButton = new JButton("");					
		btnPersonnelButton.setIcon(new ImageIcon(MainPage.class
				.getResource("/mainFrame/buttonP.png")));
		btnPersonnelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnPersonnelButton_actionPerformed(e);
			}
		});
		btnPersonnelButton.setBounds(15, 274, 135, 43);
		contentPane.add(btnPersonnelButton);
		// add the quit button
		JButton btnQuitButton = new JButton("");					
		btnQuitButton.setIcon(new ImageIcon(MainPage.class
				.getResource("/mainFrame/buttonQ.png")));
		btnQuitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnQuitButton_actionPerformed(e);
			}
		});
		btnQuitButton.setBounds(15, 314, 135, 43);
		contentPane.add(btnQuitButton);
		/**
		 * 增加运行监控图
		 */
		JPanel supervisePane = new JPanel();
		supervisePane.setBounds(160, 65, 775, 400);
		contentPane.add(supervisePane);
	}


	
	/**
	 * actionListeners
	 * @param e
	 * @throws IOException 
	 */
	// weather
	protected void do_btnWeatherButton_actionPerformed(ActionEvent e) throws IOException{
		WeatherFrame weatherFrame = new WeatherFrame();
		weatherFrame.setVisible(true);
	}
	// Email
	protected void do_btnEmailButton_actionPerformed(ActionEvent e){
		
	}
	// utility curve
	protected void do_btnCurveButton_actionPerformed(ActionEvent e){
		
	}
	// utility managements
	protected void do_btnUtilityButton_actionPerformed(ActionEvent e){
		
	}
	// Personnel managements
	protected void do_btnPersonnelButton_actionPerformed(ActionEvent e){
		
	}
	// quit the system
	protected void do_btnQuitButton_actionPerformed(ActionEvent e){
		
	}

}
