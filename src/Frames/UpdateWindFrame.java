package Frames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Panels.BasicWindPanel;
import beans.WindMachine;
import dao.WindMachineDao;

public class UpdateWindFrame extends JFrame{
		BasicWindPanel basicWindPanel;
		JPanel southPanel= new JPanel();
		public UpdateWindFrame(WindMachine wm){
			this.setLayout(new BorderLayout());
			basicWindPanel=new BasicWindPanel(wm);
			basicWindPanel.nameTextField.setEditable(false);;
			this.add(basicWindPanel, BorderLayout.NORTH);
			JButton modify= new JButton("保存");
			modify.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
						doModify();
				}
			});
			southPanel.add(modify);
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
			this.setTitle("修改风机信息");
			this.pack();
			this.setVisible(true);
			this.validate();
		}
		/**
		 * do the actionListener
		 * @throws ParseException 
		 */
		public void doModify() {
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
				wmd.updateWindMachine(wm);
				JOptionPane.showMessageDialog(getParent(), "修改添加成功！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
		}
		public void doClose(){
			this.dispose();
		}
		public static void main(String args[]){
			InsertWindFrame insertWindFrame = new InsertWindFrame();
		}
	}


