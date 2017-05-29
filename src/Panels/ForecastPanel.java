package Panels;
/** 
 * put into the forecast frame
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
/**
 * this panel implements the button for forecast button in the main page
 */
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.sun.jmx.snmp.internal.SnmpOutgoingRequest;
import com.sun.jmx.snmp.tasks.ThreadService;

import DynamicInput.AllInput;
import Frames.EmailFrame;

public class ForecastPanel extends JPanel{
	AllInput allInput = new AllInput(1440);
	JPanel northPanel= new JPanel();
	JPanel southPanel=new JPanel();
	JRadioButton radioWind=new JRadioButton("风机");
	JRadioButton radioPhoto= new JRadioButton("光伏");
	JRadioButton radioLoad= new JRadioButton("负荷");
	JRadioButton radioHistory=new JRadioButton("历史记录");
	JRadioButton radioFuture=new JRadioButton("未来预测");
	ButtonGroup type=new ButtonGroup();
	ButtonGroup time=new ButtonGroup();
	JButton guide=new JButton("运行指导");
	public ForecastPanel(){
		// north panel
		radioWind.setSelected(true);
		type.add(radioWind);
		type.add(radioPhoto);
		type.add(radioLoad);
		northPanel.add(radioWind);
		northPanel.add(radioPhoto);
		northPanel.add(radioLoad);
		radioHistory.setSelected(true);
		time.add(radioHistory);
		time.add(radioFuture);
		northPanel.add(radioHistory);
		northPanel.add(radioFuture);
		JButton confirm=new JButton("查询");
		confirm.addActionListener(new ActionListener() {
			
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
		northPanel.add(confirm);
		guide.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doGuide();
			}
		});
		northPanel.add(guide);
		// south panel
		southPanel.add(new EmptyPanel());
		southPanel.setSize(600, 500);
		// the whole
		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.NORTH);
		this.add(southPanel, BorderLayout.SOUTH);
		this.setVisible(true);		
	}
	// action listener
	public void doEnquery() throws IOException{
		int ty=0;
		if(radioWind.isSelected())
			ty=0;
		else if(radioPhoto.isSelected())
			ty=1;
		else 
			ty=2;
		int ti=0;
		if(radioHistory.isSelected())
			ti=1;
		else 
			ti=0;
		if(ty==0){
			substitute((new RecordPanel("wind",ti)).createPanel());	// a chart panel using the given winddata 1
		}
		else if(ty==1){
			if(ti==1){
				substitute((new RecordPanel("photo",ti)).createPanel());
			}
			else if(ti==0){
				substitute(new PhotoFuturePanel(1));
			}
		}
		else {
			substitute((new RecordPanel("load",ti)).createPanel());
		}
	}
	// substitute the southPanel content
	public void substitute(JPanel panel){
		southPanel.remove(0);
		southPanel.add(panel);
		validate();
	}
	public void doGuide(){
		double windSum= getWindSum();
		double photoSum= getPhotoSum();
		double loadSum= getLoadSum();
		double difference=windSum+photoSum-loadSum;
		String message = "据预计\n明天风电出力"+windSum+"kwh\n光伏出力"+photoSum+"kwh\n负荷消耗"+
		loadSum+"kwh\n "+"本微电网净发电出力为"+difference+"kwh。\n请提前做好设备管理与运行负荷调控。";
		String subject="微电网运行指导";
		EmailFrame emailFrame=new EmailFrame();
		emailFrame.setMessage(message);
		emailFrame.setSubject(subject);
	}
	// do the sum
	public double getWindSum(){
		double sum=0;
		for(int iter=0;iter!=1440;iter++)
			sum+=allInput.seriesWind.get(iter);
		return sum/60.0;
	}
	public double getPhotoSum(){
		double sum=0;
		for(int iter=0;iter!=1440;iter++)
			sum+=allInput.seriesPhoto.get(iter);
		return sum/60.0;
	}
	public double getLoadSum(){
		double sum=0;
		for(int iter=0;iter!=1440;iter++)
			sum+=allInput.seriesLoad.get(iter);
		return sum/60.0;
	}
	// test
	public static void main(String args[]){
		JFrame frame = new JFrame();
		frame.getContentPane().add(new ForecastPanel());
//		frame.pack();
		frame.setSize(700, 600);
		frame.setVisible(true);
		frame.validate();
	}
}
