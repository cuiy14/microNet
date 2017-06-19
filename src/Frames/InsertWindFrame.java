package Frames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import Panels.BasicWindPanel;
import beans.WindMachine;
import dao.WindMachineDao;

public class InsertWindFrame extends JFrame{
//	static int count= 14;	// 目前已有的风机数	
	WindMachineDao windMachineDao= new WindMachineDao();
	int count=windMachineDao.getMaxId();
	BasicWindPanel basicWindPanel=new BasicWindPanel();
	JPanel southPanel= new JPanel();
	public InsertWindFrame(){
		count++;
		basicWindPanel.nameTextField.setText(count+"");
		basicWindPanel.nameTextField.setEditable(false);
		this.setLayout(new BorderLayout());
		this.add(basicWindPanel, BorderLayout.NORTH);
		JButton insert= new JButton("添加");
		insert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					doInsertion();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		southPanel.add(insert);
		JButton close = new JButton("关闭");
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doClose();
			}
		});
		southPanel.add(close);
		this.add(southPanel, BorderLayout.SOUTH);	
		this.setTitle("添加风机");
		this.pack();
		this.setVisible(true);
		this.validate();
	}
	/**
	 * do the actionListener
	 * @throws ParseException 
	 */
	public void doInsertion() throws ParseException{
		// validate the info
		String id=basicWindPanel.nameTextField.getText();
		String serial = basicWindPanel.serialTextField.getText();
		String power = basicWindPanel.powerTextField.getText();
		String low=basicWindPanel.lowField.getText();
		String high= basicWindPanel.highField.getText();
		String time=basicWindPanel.time.getText();
		if((id.equals(""))||(serial.equals(""))||(power.equals(""))||(low.equals(""))||(high.equals(""))||(time.equals(""))){
			JOptionPane.showMessageDialog(getParent(), "请填充空缺数据", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		WindMachine wm=new WindMachine();
		wm.setId(Integer.parseInt(id));
		wm.setSerial(serial);
		wm.setNormalPower(Integer.parseInt(power));
		wm.setLowWindScale(Integer.parseInt(low));
		wm.setHighWindScale(Integer.parseInt(high));
		Date date= Date.valueOf(time);
		wm.setDate(date);
		WindMachineDao wmd=new WindMachineDao();
		if(wmd.selectById(wm.getId())==null){
			wmd.insertWindMachine(wm);
			JOptionPane.showMessageDialog(getParent(), "添加成功！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(getParent(), "该ID已存在，请重新填写", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}
	public void doClose(){
		this.dispose();
	}
	public static void main(String args[]) throws ParseException {
		InsertWindFrame insertWindFrame = new InsertWindFrame();

	}
}
