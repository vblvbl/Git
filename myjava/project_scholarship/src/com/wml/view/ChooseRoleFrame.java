package com.wml.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.wml.dao.AdmDao;
import com.wml.domain.AdmBean;
import com.wml.domain.StudentBean;
import com.wml.service.LoginService;

public class ChooseRoleFrame extends JFrame {
	private JComboBox<String> box;
	private int width;
	private int height;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JTextField jtfField1;
	private JPasswordField jtfField2;
	private JButton jButton1;
	private JButton jButton2;
	String select;

	public ChooseRoleFrame() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) (dimension.getWidth() / 3);
		height = (int) (dimension.getHeight() / 3);
		this.setTitle("何庄子奖学金申请系统");
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(width, height, width, height);
		init();
		setEvent();
	}

	private void setEvent() {
		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				select = box.getSelectedItem().toString();
				JOptionPane.showMessageDialog(null, "您选择了:{  " + select
						+ "  }角色去登录", "角色选择", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String num = jtfField1.getText().trim();
				String pass = jtfField2.getText().trim();
				if (num == null || num.equals("") || pass.equals("")
						|| pass == null) {
					JOptionPane.showMessageDialog(null, "账户或密码不能为空", "错误提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (select == null || select.equals("")) {
					JOptionPane.showMessageDialog(null, "请选择登录角色", "错误提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				// 闯过前两关后，开始按照选择的角色查询数据库登录
				switch (select) {
				case "管理员":
					AdmBean ab = new AdmBean();
					ab.setNum(num);
					ab.setPass(pass);
					// 查询管理员的表，获取返回值
					if (LoginService.getAdmLogin(ab)) {
						AdminFram sf = new AdminFram();
						sf.setVisible(true);
						ChooseRoleFrame.this.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(null, "<管理员>账户或者密码有误",
								"错误提示", JOptionPane.INFORMATION_MESSAGE);
					}
					break;
				case "学生":
					StudentBean sb = new StudentBean();
					sb.setNum(num);
					sb.setPasswd(pass);
					// 查询学生的表，获取返回值
					String name = LoginService.getStudentLogin(sb);
					if (name != null) {
						// 进入一个新的界面
						StudentFrame sf = new StudentFrame(LoginService
								.giveStudentState(sb));
						sf.setVisible(true);
						ChooseRoleFrame.this.setVisible(false);
//						System.out.println("登录用户：" + name);
					} else {
						JOptionPane.showMessageDialog(null, "<学生>账户或者密码有误",
								"错误提示", JOptionPane.INFORMATION_MESSAGE);
					}
					break;
				case "委员会":
					// 查询委员会的表，获取返回值
					if (true) {

					} else {
						JOptionPane.showMessageDialog(null, "<委员会>账户或者密码有误",
								"错误提示", JOptionPane.INFORMATION_MESSAGE);
					}
					break;

				}

			}
		});
		jButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChooseRoleFrame.this.dispose();

			}
		});
	}

	private void init() {
		jLabel1 = new JLabel("账户");
		jLabel1.setBounds(width / 4, 30, width / 10, 30);
		jLabel1.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
		jtfField1 = new JTextField();
		jtfField1.setBounds(width / 4 + width / 10 + 10, 30, width / 5 * 2, 30);
		jLabel2 = new JLabel("密码");
		jLabel2.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
		jLabel2.setBounds(width / 4, 80, width / 10, 30);
		jtfField2 = new JPasswordField();
		jtfField2.setBounds(width / 4 + width / 10 + 10, 80, width / 5 * 2, 30);
		box = new JComboBox<String>();
		box.addItem("管理员");
		box.addItem("学生");
		box.addItem("委员会");
		box.setBounds(width / 3, 130, width / 3, 40);
		jButton1 = new JButton("登录");
		jButton1.setBounds(width / 3, 210, width / 3 / 3, 30);
		jButton2 = new JButton("退出");
		jButton2.setBounds(width / 3 + width / 3 / 3 + width / 3 / 3, 210,
				width / 3 / 3, 30);
		this.add(box);
		this.add(jLabel1);
		this.add(jtfField1);
		this.add(jLabel2);
		this.add(jtfField2);
		this.add(jButton1);
		this.add(jButton2);
	}
}
