package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;


import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

import beans.User;
import dao.UserDao;
import mainFrame.MainPage;
import util.Session;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private BackgroundPanel contentPane;
	private JTextField userNameTextField;
	private JPasswordField passwordField;
	private Point spoint;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				try {
					UIManager.setLookAndFeel(new NimbusLookAndFeel());
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							Login frame = new Login();
							frame.setVisible(true);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 418, 270);
		setLocationRelativeTo(null);// 窗体居中
		setTitle("登录窗体");
		contentPane = loginPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	/**
	 * 初始化登录面板
	 */
	private BackgroundPanel loginPanel() {
		if (contentPane == null) {
			contentPane = new BackgroundPanel();
			contentPane.setImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/main/login.png")));// 设置面板背景图片
			contentPane.setLayout(null);
			// username
			JLabel userNameLabel = new JLabel("用户名：");
			userNameLabel.setBounds(94, 116, 54, 15);
			contentPane.add(userNameLabel);
			userNameTextField = new JTextField();
			userNameTextField.setBounds(177, 111, 139, 25);
			contentPane.add(userNameTextField);
			userNameTextField.setColumns(10);
			// password
			JLabel passWordLabel = new JLabel("密  码：");
			passWordLabel.setBounds(94, 158, 54, 15);
			contentPane.add(passWordLabel);
			passwordField = new JPasswordField();
			passwordField.setBounds(177, 153, 139, 25);
			contentPane.add(passwordField);

			JButton btnQue = new JButton("确认");
			btnQue.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					UserDao userDao = new UserDao(); // 创建保存有操作数据库类对象
					User user = userDao.getUser(userNameTextField.getText(), new String(passwordField.getPassword())); // 以用户添加的用户名与密码为参数调用查询用户方法
					if (user.getId() > 0) { // 判断用户编号是否大于0
						Session.setUser(user); // 设置Session对象的User属性值
						MainPage mainPage = new MainPage(); // 创建主窗体对象
						mainPage.setVisible(true); // 显示主窗体
						Login.this.dispose(); // 销毁登录窗体
					} else { // 如果用户输入的用户名与密码错误
						JOptionPane.showMessageDialog(getContentPane(), "用户名或密码错误"); // 给出提示信息
						userNameTextField.setText(""); // 用户名文本框设置为空
						passwordField.setText(""); // 密码文本框设置为空
					}

				}
			});
			btnQue.setBounds(177, 190, 102, 28);
			contentPane.add(btnQue);
		}
		return contentPane;
	}
}
