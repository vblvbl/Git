package com.qcy.data.face;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.likang.data.domain.Student;
import com.qcy.data.service.DbData;
import com.qcy.data.utils.MyKmeans;

public class MyProgressBar extends JInternalFrame {
	StringBuilder sbineed;
	private static final long serialVersionUID = 6823378424400186364L;
	private ShowTableFrame St;
	static MyProgressBar frame;

	public MyProgressBar(ShowTableFrame St) {
		this.St = St;
		// TODO Auto-generated constructor stub
		JLabel label = new JLabel("欢迎使用聚类算法功能！");
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
		progressBar.setString("算法进行中...");
		// progressBar.setIndeterminate(true);
		new Progress(progressBar, button).start();

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				dispose();
				MyProgressBar.this.St.DoStringBuild(sbineed.toString());
				dispose();
			}

		});
	}

	private class Progress extends Thread {
		JProgressBar progressBar;
		JButton button;
		int[] progressValues = { 30, 60, 80,100 };

		Progress(JProgressBar progressBar, JButton button) {
			this.progressBar = progressBar;
			this.button = button;
		}

		public void run() {
			sbineed = new StringBuilder();
			List<Student> st = DbData.getStudentList();
			MyKmeans<Student> kmeans = new MyKmeans<Student>(st, 4);
			List<Student>[] results = kmeans.comput();
			for (int i = 0; i < results.length; i++) {
				StringBuilder sb = new StringBuilder();
				sb.append("===========类别" + (i+1) + "================" + "\r\n");
				List<Student> list = results[i];
				progressBar.setValue(progressValues[i]);
				for (Student p : list) {
					sb.append(p.getId() + "--->" + p.getNumber() + ","
							+ p.getName() + "," + p.getAge() + "," + p.getSex()
							+ "\r\n");
				}
				sbineed.append(sb.toString()+"\r\n");
				try {
					Thread.sleep(1000);
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