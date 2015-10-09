package com.wml.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.wml.domain.StudentBean;
import com.wml.service.InsertService;

public class StudentRegistFrame extends JFrame {
	private JLabel jlb1;
	private JLabel jlb2;
	private JLabel jlb3;
	private JLabel jlb4;
	private JLabel jlb5;
	private JLabel jlb6;
	private JTextField jtf1;
	private JTextField jtf2;
	private JTextField jtf3;
	private JTextField jtf4;
	private JTextField jtf5;
	private JTextField jtf6;
	private JButton jb1;
	private int width;
	private int height;

	public StudentRegistFrame() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) (dimension.getWidth() / 2);
		height = (int) (dimension.getHeight() / 3);
		this.setTitle("注册学生信息");
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(width / 2, height / 2, width, height);
		init();
		setEvent();
	}

	private void setEvent() {
		jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String num = jtf1.getText().trim();
				String passwd = jtf2.getText().trim();
				String name = jtf3.getText().trim();
				String inschool = jtf4.getText().trim();
//				System.out.println(num);
				String academy = jtf5.getText().trim();
				String state = jtf6.getText().trim();
				if (num.equals("") || passwd.equals("") || name.equals("")
						|| inschool.equals("") || academy.equals("")
						|| state.equals("")) {
					JOptionPane jOptionPane = new JOptionPane();
					jOptionPane.showMessageDialog(StudentRegistFrame.this,
							"各字段不能为空", "warn", JOptionPane.INFORMATION_MESSAGE);
				} else {
					Date indateSchool = null;
					try {
						indateSchool = new SimpleDateFormat("yyyy-MM-dd")
								.parse(inschool);
					} catch (ParseException pe) {
						// TODO Auto-generated catch block
						pe.printStackTrace();
					}
					StudentBean studentBean = new StudentBean(num, passwd,
							name, indateSchool, academy, state);
					try {
						InsertService.insertStudent(studentBean);
					} catch (Exception e1) {
						JOptionPane jOptionPane = new JOptionPane();
						jOptionPane.showMessageDialog(StudentRegistFrame.this,
								"数据库异常", "error",
								JOptionPane.INFORMATION_MESSAGE);
					}
					JOptionPane jOptionPane = new JOptionPane();
					jOptionPane.showMessageDialog(StudentRegistFrame.this,
							"注册成功", "good", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

	}

	private void init() {
		jlb1 = new JLabel("登录账号");
		jlb1.setBounds(20, 0, width / 6, 30);
		jtf1 = new JTextField();
		jtf1.setBounds(width / 6 + 20, 0, width / 6 * 4, 30);
		this.add(jlb1);
		this.add(jtf1);

		jlb2 = new JLabel("登录密码");
		jlb2.setBounds(20, 35, width / 6, 30);
		jtf2 = new JTextField();
		jtf2.setBounds(width / 6 + 20, 35, width / 6 * 4, 30);
		this.add(jlb2);
		this.add(jtf2);

		jlb3 = new JLabel("用户姓名");
		jlb3.setBounds(20, 70, width / 6, 30);
		jtf3 = new JTextField();
		jtf3.setBounds(width / 6 + 20, 70, width / 6 * 4, 30);
		this.add(jlb3);
		this.add(jtf3);

		jlb4 = new JLabel("入学时间");
		jlb4.setBounds(20, 105, width / 6, 30);
		jtf4 = new JTextField();
		jtf4.setBounds(width / 6 + 20, 105, width / 6 * 4, 30);
		jtf4.setText("1992-9-15");
		this.add(jlb4);
		this.add(jtf4);

		jlb5 = new JLabel("所在系院");
		jlb5.setBounds(20, 140, width / 6, 30);
		jtf5 = new JTextField();
		jtf5.setBounds(width / 6 + 20, 140, width / 6 * 4, 30);
		this.add(jlb5);
		this.add(jtf5);

		jlb6 = new JLabel("申请状态");
		jlb6.setBounds(20, 175, width / 6, 30);
		jtf6 = new JTextField();
		jtf6.setBounds(width / 6 + 20, 175, width / 6 * 4, 30);
		this.add(jlb6);
		this.add(jtf6);

		jb1 = new JButton("注册");
		jb1.setBounds(width / 2 - 25, 210, 50, 30);
		this.add(jb1);
	}
}
