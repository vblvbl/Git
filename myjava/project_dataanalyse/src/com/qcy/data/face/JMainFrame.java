package com.qcy.data.face;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.qcy.data.dao.Db;

public class JMainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JMenuBar jb = new JMenuBar();
	JMenu menu = new JMenu("数据清洗");
	JMenu menu2 = new JMenu("数据集成");
	JMenuItem showTable=new JMenuItem("查看表中信息");
	JMenuItem newTable=new JMenuItem("合成新表");
	JMenuItem newTable1=new JMenuItem("数据冲突");
	JMenuItem nullLv = new JMenuItem("设置空缺率");
	//JMenuItem exit = new JMenuItem("退出");

	Container c = this.getContentPane();

	public JMainFrame() {
	}

	public JMainFrame(String typename, String dbname, String username,
			String password) throws Exception {
		int type = 0;
		if (typename.indexOf("mysql") > -1) {
			type = 0;
		} else if (typename.indexOf("sqlserver") > -1) {
			type = 1;
		} else {
			type = 2;
		}
		new Db(type, dbname, username, password);
		showFrame();
	}

	private void showFrame() {
		setTitle("主界面");
		c.setLayout(new BorderLayout());
		setJMenuBar(jb);
		jb.add(menu);
		jb.add(menu2);
		
		menu.add(showTable);
		menu.add(nullLv);
		menu2.add(newTable);
		menu2.add(newTable1);
		//menu.add(exit);

		
	/*	exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});*/
		showTable.addActionListener(new clickMenu());
		newTable.addActionListener(new clickMenu());
		newTable1.addActionListener(new clickMenu());
		nullLv.addActionListener(new clickMenu());

		// 显示
		setSize(700,340);
		setLocation(500, 200);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private class clickMenu implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			 
			 if(e.getSource().equals(showTable)){
				try{
					new ShowTableFrame();
				}catch(Exception ex){
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "数据库访问时出错，请重启再试", "信息提示",
							JOptionPane.ERROR_MESSAGE);
				}
			}else if(e.getSource().equals(nullLv)){
				new ConfigNull();
			}else if(e.getSource().equals(newTable)){
				try {
					new DataIntegrationFrame();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource().equals(newTable1)){
				try {
					new CreateDataFrame();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
	
		}
	}

}
