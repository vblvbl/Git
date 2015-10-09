package com.wml.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class AdminFram extends JFrame {
	public static void main(String[] args) {
		AdminFram afAdminFram=new AdminFram();
		afAdminFram.setVisible(true);
	}
	int width;
	int height;
	private JButton jb1;
	private JButton jb2;

	public AdminFram() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) (dimension.getWidth() / 4);
		height = (int) (dimension.getHeight() / 4);
		this.setTitle("管理员管理信息");
		this.setLayout(null);
		this.setBounds(width * 4 / 3, height * 4 / 3, width, height/5*4);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		init();
		setEvent();
	}

	private void init() {
		jb1 = new JButton("查询");
		jb1.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
		jb1.setBounds(width / 7, 40, width / 7 * 3/2, 30);
		jb2 = new JButton("注册");
		jb2.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
		jb2.setBounds(width / 7 * 4, 40, width / 7 * 3/2, 30);
		this.add(jb1);
		this.add(jb2);
		
	}

	private void setEvent() {
		jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FindTable findTable = new FindTable();
				findTable.setVisible(true);
				AdminFram.this.dispose();
			}
		});
		jb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				StudentRegistFrame registFrame = new StudentRegistFrame();
				registFrame.setVisible(true);
				AdminFram.this.dispose();
			}
		});
		
	}
}