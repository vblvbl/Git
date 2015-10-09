package com.qcy.data.face;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.qcy.data.config.Config;

public class ConfigNull extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JTextField rowtxt = new JTextField(20);
	JLabel rowlabel = new JLabel("行空缺率：");
	
	private final JTextField columntxt = new JTextField(20);
	JLabel columnlabel = new JLabel("列空缺率：");
	
	private final JButton jbtn1 = new JButton("确定");
	private final JButton jbtnexit = new JButton("取消");
	Container c =  getContentPane();
	
	public ConfigNull() {
		c.setLayout(new GridLayout(3, 1));
		
		JPanel p0 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p0.add(rowlabel);
		p0.add(rowtxt);
		rowtxt.setText(Config.rowNull+"");

		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p1.add(columnlabel);
		p1.add(columntxt);
		columntxt.setText(Config.columnNull+"");
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p2.add(jbtn1);
		p2.add(jbtnexit);

		c.add(p0);
		c.add(p1);
		c.add(p2);
		
		jbtnexit.addActionListener(this);
		jbtn1.addActionListener(this);
		
		pack();
		setLocation(500, 200);
		setResizable(false);
		setTitle("设置空缺率");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ("确定" == e.getActionCommand()) {
			try{
				Config.rowNull=Double.parseDouble(rowtxt.getText());
				Config.columnNull=Double.parseDouble(columntxt.getText());
				JOptionPane.showMessageDialog(null, "修改信息成功", "信息提示",
						JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, "检查你输入的信息是否正确", "错误提示",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			this.dispose();
		}
	}

}
