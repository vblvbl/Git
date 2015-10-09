package com.wml.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StudentFrame extends JFrame {
	int width;
	int height;
	private JButton jb1;
	private JButton jb2;
	private JButton jb3;
	private String state;

	public StudentFrame(String state) {
		this.state = state;
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) (dimension.getWidth() / 4);
		height = (int) (dimension.getHeight() / 4);
		this.setTitle("学生信息与申请");
		this.setLayout(null);
		this.setBounds(width * 4 / 3, height * 4 / 3, width, height * 2 / 3);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		init();
		setEvent();
	}

	private void setEvent() {
		jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Runtime.getRuntime().exec("sudo su");
					Runtime.getRuntime().exec("java");
					Runtime.getRuntime().exec(
							"D:\\WPS Office\\9.1.0.4953\\office6 C:\\Users\\lele\\Desktop.docx");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		jb3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane jp = new JOptionPane();
				if(state==null||state.equals("")){
					state="未注册";
				}
				jp.showMessageDialog(null, "您提交的状态为:" + state, "提交状态",
						JOptionPane.INFORMATION_MESSAGE);

			}
		});
		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(StudentFrame.this);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				File file = chooser.getSelectedFile();
				JOptionPane jp = new JOptionPane();
				jp.showMessageDialog(null, "文件路径下:\"" + file.getParent()
						+ "\"的:<" + file.getName() + ">\n\r      上传成功!!!!",
						"上传信息", JOptionPane.INFORMATION_MESSAGE);
			}
		});

	}

	private void init() {
		jb1 = new JButton("填写申请表");
		jb1.setFont(new Font(Font.DIALOG, Font.BOLD, 10));
		jb1.setBounds(width / 7, 20, width / 7 * 2, 30);
		jb2 = new JButton("提交申请表");
		jb2.setFont(new Font(Font.DIALOG, Font.BOLD, 10));
		jb2.setBounds(width / 7 * 4, 20, width / 7 * 2, 30);
		jb3 = new JButton("查看进度");
		jb3.setBounds(width / 9 * 3, 70, width / 9 * 3, 30);
		jb3.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		this.add(jb1);
		this.add(jb2);
		this.add(jb3);
	}
}
