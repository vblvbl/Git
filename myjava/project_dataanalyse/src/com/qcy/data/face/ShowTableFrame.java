package com.qcy.data.face;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.qcy.data.service.CallBack;
import com.qcy.data.service.DbData;

public class ShowTableFrame extends JFrame implements CallBack {

	/**
	 * 
	 */
	private JPanel jp0211;
	private JDesktopPane jdpe = new JDesktopPane();
	private static final long serialVersionUID = 1L;
	private JPanel jp021;
	private final JLabel jldb = new JLabel("选择表名");
	private JComboBox<String> jtable = null;
	private DefaultTableModel model;
	private JTable jt = null;
	Container c = getContentPane();
	private JScrollPane scpDemo = new JScrollPane();
	private JButton nullBtn = new JButton("按空缺率过滤");
	private JButton repeatBtn = new JButton("筛掉重复数据");
	private JButton kmeans = new JButton("聚类算法分类");
	private JButton standardBtn = new JButton("分类范围数据");
	// private JButton setBtn=new JButton("设置不规范数据范围");
	private JComboBox<String> combox = new JComboBox<String>();
	private JTextField txtup = new JTextField(10);
	private JTextField txtdown = new JTextField(10);
	private JTextArea txtup1 = new JTextArea(18,7);
	
	// private JTextField txtup1=new JTextField(10);
	// private JTextField txtdown1=new JTextField(10);

	public ShowTableFrame() throws Exception {

		String[] strArray = DbData.getAllTableInfo();
		jtable = new JComboBox<String>(strArray);

		// 事件监
		jtable.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String tableName = jtable.getSelectedItem().toString();

				setComboxInfo();
				drawTable(tableName, false);
			}

		});
		JPanel jp0 = new JPanel(new BorderLayout());

		JPanel jp00 = new JPanel();
		jp00.add(jldb);// 选择表名
		jp00.add(jtable);// 选择框checkbox
		jp0.add(jp00, BorderLayout.NORTH);

		JPanel jp1 = new JPanel(new BorderLayout());
		JPanel jp01 = new JPanel();
		jp01.add(nullBtn);// 按空缺率过滤

		JPanel jp02 = new JPanel(new BorderLayout());

		setComboxInfo();
		JPanel jp020 = new JPanel();// 小的JComboBox－020
		jp020.add(combox);

		jp021 = new JPanel(new BorderLayout());// 放文本框和内嵌jframe的jpanel－021

		JPanel jp0210 = new JPanel();// 放两个文本框的jpanel－0210
		jp0210.add(txtup);
		jp0210.add(txtdown);

		Component cp = this.getContentPane().add(jdpe);

		JPanel jp0212 = new JPanel();
		JScrollPane scroll = new JScrollPane(txtup1); 
		//把定义的JTextArea放到JScrollPane里面去 
		//分别设置水平和垂直滚动条自动出现 
		scroll.setHorizontalScrollBarPolicy( 
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		scroll.setVerticalScrollBarPolicy( 
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		jp0212.add(scroll);

		jp021.add(jp0210, BorderLayout.NORTH);
		jp021.add(cp, BorderLayout.CENTER);
		jp021.add(jp0212, BorderLayout.WEST);

		JPanel jp022 = new JPanel();// 放塞选重复数据的jpanel－022
		jp022.add(repeatBtn);

		jp02.add(jp020, BorderLayout.NORTH);
		jp02.add(jp021, BorderLayout.CENTER);
		jp02.add(jp022, BorderLayout.SOUTH);

		JPanel jp031 = new JPanel();
		jp031.add(kmeans);
		JPanel jp032 = new JPanel();
		jp032.add(standardBtn);

		JPanel jp03 = new JPanel();
		jp03.add(jp031, BorderLayout.NORTH);
		jp03.add(jp032, BorderLayout.SOUTH);

		jp1.add(jp01, BorderLayout.NORTH);
		jp1.add(jp02, BorderLayout.CENTER);
		jp1.add(jp03, BorderLayout.SOUTH);

		nullBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawTable(jtable.getSelectedItem().toString(), true);
			}
		});

		// 筛选重复数据
		repeatBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				drawTable(jtable.getSelectedItem().toString(), 1, -1, -1);
			}
		});
		kmeans.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyProgressBar frame = new MyProgressBar(ShowTableFrame.this);
				frame.setTitle("使用进度条");
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				jdpe.add(frame);
			}
		});

		// 筛选不规范数据
		standardBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String up = txtup.getText();
				String down = txtdown.getText();
				if (up.equals("") || down.equals("")) {
					JOptionPane.showMessageDialog(null, "请先输入范围信息", "信息提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					int upint = 0;
					int downint = 0;
					try {
						upint = Integer.parseInt(up);
						downint = Integer.parseInt(down);
						drawTable(jtable.getSelectedItem().toString(), -1,
								upint, downint);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "输入范围信息有误,请重新输入",
								"信息提示", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		jp0.add(jp1, BorderLayout.CENTER);

		c.setLayout(new BorderLayout());
		c.add(jp0, BorderLayout.WEST);

		drawTable(jtable.getSelectedItem().toString(), false);

		// 显示
		setSize(800, 478);
		setLocation(500, 200);
		setResizable(false);
		setVisible(true);
		setTitle("查看表数据");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	private void setComboxInfo() {
		try {
			combox.removeAllItems();
			String[] ss = DbData.getAllFiledInTable(jtable.getSelectedItem()
					.toString(), false);

			for (String string : ss) {
				combox.addItem(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按照不规范范围过滤 去除重复数据
	 * 
	 * @param tableName
	 * @param flag
	 */
	private void drawTable(String tableName, int flag, int up, int down) {
		clearAllColumAndRow();
		String[] columnNames = null;
		Object[][] rowData = null;

		String fieldName = "";
		if (flag != 1) {
			fieldName = combox.getSelectedItem().toString();
		}

		try {
			columnNames = DbData.getAllFiledInTable(tableName, false);

			rowData = DbData.getAllFiledDataInTable(tableName, flag, fieldName,
					up, down);
			// 多次加载的时候显示对应表中数据
			model.setDataVector(rowData, columnNames);
			this.setTitle("查看表中数据,总计" + DbData.countRow + "条");

		} catch (ArrayIndexOutOfBoundsException ex) {
			if (rowData == null) {
				rowData = new Object[1][columnNames.length];
			}
			if (columnNames == null) {
				columnNames = new String[1];
			}
			model.setDataVector(rowData, columnNames);
			this.setTitle("查看表中数据,总计" + 0 + "条");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("加载数据出错");
		}
	}

	private void drawTable(String tableName, Boolean isFilter) {

		if (jt != null) {
			clearAllColumAndRow();
		}

		String[] columnNames = null;
		Object[][] rowData = null;

		try {
			columnNames = DbData.getAllFiledInTable(tableName, isFilter);

			rowData = DbData.getAllFiledDataInTable(tableName, isFilter);

			this.setTitle("查看表中数据,总计" + DbData.countRow + "条");
			// 第一次加载的时候
			if (jt == null) {
				model = new DefaultTableModel(rowData, columnNames);
				jt = new JTable(model);

				jt.setBorder(BorderFactory.createEtchedBorder());
				jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// 设置为滚动的表格

				scpDemo.getViewport().add(jt);

				jt.getTableHeader().setVisible(true);
				JPanel jp1 = new JPanel();
				jp1.add(scpDemo, BorderLayout.CENTER);
				c.add(jp1);
			} else {
				// 多次加载的时候显示对应表中数据
				model.setDataVector(rowData, columnNames);
			}

		} catch (ArrayIndexOutOfBoundsException ex) {
			if (rowData == null) {
				rowData = new Object[1][columnNames.length];
			}
			if (columnNames == null) {
				columnNames = new String[1];
			}
			if (jt == null) {
				model = new DefaultTableModel(rowData, columnNames);
				jt = new JTable(model);
				jt.setBorder(BorderFactory.createEtchedBorder());
				jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// 设置为滚动的表格

				scpDemo.getViewport().add(jt);

				jt.getTableHeader().setVisible(true);
				JPanel jp1 = new JPanel();
				jp1.add(scpDemo, BorderLayout.CENTER);
				c.add(jp1);
			} else {
				model.setDataVector(new Object[1][], columnNames);
			}
			this.setTitle("查看表中数据,总计0条");

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("加载数据出错");
		}
	}

	private void clearAllColumAndRow() {

		// 删除所有的行
		int rowcount = model.getRowCount();
		for (int i = rowcount - 1; i >= 0; i--) {
			model.removeRow(i);
		}

		// 删除所有的咧
		TableColumnModel tcm = jt.getColumnModel();

		for (int i = 0; i < jt.getColumnCount(); i++) {
			TableColumn ff = jt.getColumn(jt.getColumnName(i));
			tcm.removeColumn(ff);
		}

	}

	@Override
	public void DoStringBuild(String s) {
		txtup1.setText(s);
	}

}
