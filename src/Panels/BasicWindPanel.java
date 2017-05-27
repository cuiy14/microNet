package Panels;

import static javax.swing.BorderFactory.createTitledBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import beans.WindMachine;

public class BasicWindPanel extends JPanel {
	public JTextField nameTextField;
	public JTextField serialTextField;
	public JTextField powerTextField;
	public JTextField lowField;
	public JTextField highField;
	public JTextField time;
	
	public BasicWindPanel(){
		// TODO Auto-generated constructor stub
		this.setLayout(new GridLayout(3, 4));
		JLabel nameLabel = new JLabel("id：");
		this.add(nameLabel);

		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		this.add(nameTextField);
		
		JLabel seriallabel = new JLabel("品牌：");
		this.add(seriallabel);

		serialTextField = new JTextField();
		serialTextField.setColumns(10);
		this.add(serialTextField);

		JLabel normalPower = new JLabel("额定功率：");
		this.add(normalPower);

		powerTextField = new JTextField();
		powerTextField.setColumns(10);
		this.add(powerTextField);

		JLabel lowSpeed = new JLabel("切入风速：");
		this.add(lowSpeed);

		lowField = new JTextField();
		this.add(lowField);

		JLabel highSpeed = new JLabel("最大风速：");
		this.add(highSpeed);

		highField = new JTextField();
		this.add(highField);
		
		JLabel age= new JLabel("投入时间：");
		this.add(age);
		
		time = new JTextField();
		DateFormat dFormat=new SimpleDateFormat("yyyy-mm-dd");
		this.add(time);
		this.setBorder(createTitledBorder(null, "基本信息", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
				new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
		this.setSize(500,400);
		this.setVisible(true);
		this.validate();
	}
	public BasicWindPanel(WindMachine wm) {	// this constructor can use the other constructor to init
		// TODO Auto-generated constructor stub
		this.setLayout(new GridLayout(3, 4));
		JLabel nameLabel = new JLabel("id：");
		this.add(nameLabel);

		nameTextField = new JTextField();
		nameTextField.setText(Integer.toString(wm.getId()));
		nameTextField.setColumns(10);
		this.add(nameTextField);
		
		JLabel seriallabel = new JLabel("品牌：");
		this.add(seriallabel);

		serialTextField = new JTextField();
		serialTextField.setText(wm.getSerial());
		serialTextField.setColumns(10);
		this.add(serialTextField);

		JLabel normalPower = new JLabel("额定功率：");
		this.add(normalPower);

		powerTextField = new JTextField();
		powerTextField.setText(Integer.toString(wm.getNormalPower()));
		powerTextField.setColumns(10);
		this.add(powerTextField);

		JLabel lowSpeed = new JLabel("切入风速：");
		this.add(lowSpeed);

		lowField = new JTextField();
		lowField.setText(Integer.toString(wm.getLowWindScale()));
		this.add(lowField);

		JLabel highSpeed = new JLabel("最大风速：");
		this.add(highSpeed);

		highField = new JTextField();
		highField.setText(Integer.toString(wm.getHighWindScale()));
		this.add(highField);
		
		JLabel age= new JLabel("投入时间：");
		this.add(age);
		
		time = new JTextField();
		DateFormat dFormat=new SimpleDateFormat("yyyy-mm-dd");
		time.setText(dFormat.format(wm.getDate()));
		this.add(time);
		this.setBorder(createTitledBorder(null, "基本信息", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
				new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
		this.setSize(500,400);
		this.setVisible(true);
		this.validate();
	}
	// constructor

	public void setSerial(String name) {
		this.nameTextField.setText(name);
		repaint();
	}

	public void setId(String name) {
		this.serialTextField.setText(name);
		repaint();
	}

	public void setPower(String name) {
		this.powerTextField.setText(name);
		repaint();
	}

	public void setLow(String name) {
		this.lowField.setText(name);
		repaint();
	}

	public void setHigh(String name) {
		this.highField.setText(name);
		repaint();
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new BasicWindPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
