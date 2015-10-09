package com.wml.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.wml.service.FindService;

public class MyProgressBar1 extends JFrame {
	private Object[][] rowData;
	private static final long serialVersionUID = 6823378424400186364L;
	private FindTable mf;
	static MyProgressBar1 frame;
	int width;
	int height;

	public MyProgressBar1(FindTable mf) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) (dimension.getWidth() / 3);
		height = (int) (dimension.getHeight() / 3);
		this.mf = mf;
		this.setLocation(width+width/3, height+height/3);
		// TODO Auto-generated constructor stub
		JLabel label = new JLabel("欢迎使用!");
		JProgressBar progressBar = new JProgressBar();
		JButton button = new JButton("完成");
		button.setEnabled(false);
		Container container = getContentPane();
		container.setLayout(new GridLayout(3, 1));
		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel1.add(label);
		panel2.add(progressBar);
		panel3.add(button);
		container.add(panel1);
		container.add(panel2);
		container.add(panel3);

		progressBar.setStringPainted(true);
		progressBar.setString("更新中...");
		// progressBar.setIndeterminate(true);
		new Progress(progressBar, button).start();

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// dispose();
				// System.out.println(rowData);
				MyProgressBar1.this.mf.doStringBuild(rowData);
				dispose();
			}

		});
	}

	private class Progress extends Thread {
		JProgressBar progressBar;
		JButton button;
		int[] progressValues = { 30, 60, 80, 100 };

		Progress(JProgressBar progressBar, JButton button) {
			this.progressBar = progressBar;
			this.button = button;
		}

		public void run() {
			try{
			rowData = FindService.getStudentInfo();
			}catch(Exception e){
				MyProgressBar1.this.mf.showDialog();
			}
			for (int i = 0; i < progressValues.length; i++) {
				progressBar.setValue(progressValues[i]);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			progressBar.setIndeterminate(false);
			progressBar.setString("处理完成！");
			button.setEnabled(true);
		}
	}
}