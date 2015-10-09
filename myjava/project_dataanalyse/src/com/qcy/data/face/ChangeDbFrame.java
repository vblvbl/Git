package com.qcy.data.face;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ChangeDbFrame {
	public static void main(String[] args) {
		new FirstFrame();
	}
}

class FirstFrame implements ActionListener {
	JFrame jf = new JFrame("请选择数据库");
	private final JLabel jldb = new JLabel("数据库名称");
	private final JLabel jluser = new JLabel("用户名");
	private final JLabel jlpwd = new JLabel("密码");
	private final JLabel jlgrant = new JLabel("数据库类型");
	private final JButton jbtn1 = new JButton("确定");
	private final JButton jbtnexit = new JButton("退出");
	private final JTextField jdb = new JTextField(20);
	private final JTextField juser = new JTextField(20);
	private final JPasswordField jpwd = new JPasswordField(20);
	String[] strgrant = { "mysql", "sqlserver" };
	private final JComboBox<String> jgrant = new JComboBox<String>(strgrant);

	public FirstFrame() {

		Container c = jf.getContentPane();
		c.setLayout(new GridLayout(5, 1));
		
		JPanel p0 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p0.add(jldb);
		p0.add(jdb);

		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p1.add(jluser);
		p1.add(juser);

		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p2.add(jlpwd);
		p2.add(jpwd);

		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		Dimension maximumSize = new Dimension(225, 25);
		jgrant.setPreferredSize(maximumSize);
		p3.add(jlgrant);
		p3.add(jgrant);

		JPanel p4 = new JPanel();
		p4.add(jbtn1);
		p4.add(jbtnexit);

		c.add(p3);
		c.add(p0);
		c.add(p1);
		c.add(p2);
		c.add(p4);
		jbtnexit.addActionListener(this);
		jbtn1.addActionListener(this);

		jf.pack();
		jf.setLocation(500, 200);
		jf.setResizable(false);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {

		if ("确定" == e.getActionCommand()) {

			if (juser.getText().equals("")||jpwd.getText().equals("")||jdb.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "信息未填写完整", "信息提示",
						JOptionPane.INFORMATION_MESSAGE);
				jdb.requestFocus(true);
			} else {
				try{
					new JMainFrame(jgrant.getSelectedItem()+"",jdb.getText(),juser.getText(),jpwd.getText());
					jf.dispose();
				}catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "信息输入错误，数据库未能链接", "信息提示",
							JOptionPane.ERROR_MESSAGE);
				}
			} // else end
		} else {
			jf.dispose();
		}

	}
}