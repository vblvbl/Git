package com.wml.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import com.wml.domain.StudentBean;
import com.wml.service.DeleteService;
import com.wml.service.FindService;
import com.wml.service.UpdateService;

public class FindTable extends JFrame implements CallBack {
	private String tableName;
	private JLabel jLabel;
	private JTextField jTextField;
	private JButton jButton;
	private JTable jTable;
	private JScrollPane jScrollPane;
	private int width;
	private int height;
	private JPopupMenu popupMenu;
	private String[] columnNames;
	private Object[][] rowData;
	private DefaultTableModel model;
	private JMenuItem deleteItem;
	private JMenuItem tableNameItem;
	private JMenuItem updatetItem;

	 public static void main(String[] args) {
	FindTable findTable=new FindTable();
	findTable.setVisible(true);
	 }

	public FindTable() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) (dimension.getWidth() / 3);
		height = (int) (dimension.getHeight() / 3);
		this.setTitle("查询信息");
		this.setLayout(null);
		this.setBounds(width, height, width, height);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		init();
		setEvent();
	}

	private void setEvent() {
		updatetItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel table = jTable.getModel();
				int i = 0;
				Object[] obj = new Object[jTable.getColumnCount()];
				while (i < jTable.getColumnCount()) {
					obj[i] = table.getValueAt(jTable.getSelectedRow(), i);
					i++;
				}
				StudentBean studentBean = new StudentBean();
				studentBean.setNum((String) obj[0]);
				studentBean.setPasswd((String) obj[1]);
				studentBean.setName((String) obj[2]);
//				SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
//				Date date = null;
//				try {
//					date = simpleDateFormat.parse((String) obj[3]);
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				studentBean.setInSchool((Date) obj[3]);
				studentBean.setAcademy((String) obj[4]);
				studentBean.setState((String) obj[5]);
				UpdateService.updateStudent(studentBean);
				JOptionPane jOptionPane = new JOptionPane();
				jOptionPane.showMessageDialog(FindTable.this, "修改成功！", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DeleteService.deleteStudent(tableName);
				JOptionPane jOptionPane = new JOptionPane();
				jOptionPane.showMessageDialog(FindTable.this, "删除成功！", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				MyProgressBar1 frame = new MyProgressBar1(FindTable.this);
				frame.setTitle("使用进度条");
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
			}
		});
		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jTextField.getText().trim().equals("")) {
					doStringBuild(FindService.getStudentInfo());
				} else {
					if (!jTextField.getText()
							.matches("([a-z0-9]+)=([a-z0-9]+)")) {
						JOptionPane jPane = new JOptionPane();
						jPane.showMessageDialog(FindTable.this, "输入格式有误",
								"错误提示", JOptionPane.INFORMATION_MESSAGE);
					} else {
						MyProgressBar frame = new MyProgressBar(FindTable.this,
								jTextField.getText().trim());
						frame.setTitle("使用进度条");
						frame.setVisible(true);
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.pack();
					}
				}
			}
		});

	}

	private void init() {
		jLabel = new JLabel("格式：key＝value。默认查询所有学生信息");
		jLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		jLabel.setBounds(5, 5, width, 20);
		jTextField = new JTextField();
		jTextField.setBounds(5, 30, width / 3 * 2, 30);
		jButton = new JButton("查询");
		jButton.setBounds(40 + width / 3 * 2, 30, width / 5, 30);
		this.add(jLabel);
		this.add(jTextField);
		this.add(jButton);
		popupMenu = new JPopupMenu();
		tableNameItem = new JMenuItem("");
		deleteItem = new JMenuItem("删除");
		updatetItem = new JMenuItem("修改");
	}

	@Override
	public void doStringBuild(Object[][] stmp) {
		rowData = stmp;
		columnNames = new String[] { "学号", "密码", "姓名", "入学时间", "学院", "状态" };
		model = new DefaultTableModel(rowData, columnNames);
		jTable = new JTable(model);/*
									 * { public boolean isCellEditable(int row,
									 * int column) { return false; } };
									 */
		final MouseInputListener mouseInputListener = FindTable.this
				.getMouseInputListener(jTable);
		jTable.addMouseListener(mouseInputListener);
		jTable.addMouseMotionListener(mouseInputListener);

		jTable.setRowSelectionAllowed(true);
		jTable.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		jTable.setRowHeight(30);
		jTable.setRowMargin(6);
		jTable.setGridColor(Color.RED);
		// jTable.set
		// jtb.setSize(600, 310);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		jTable.setBorder(BorderFactory.createEtchedBorder());
		jScrollPane = new JScrollPane();
		jScrollPane.setBounds(0, 60, width, height);
		jScrollPane.setViewportView(jTable);
		this.add(jScrollPane);
	}

	private MouseInputListener getMouseInputListener(final JTable jTable2) {
		return new MouseInputListener() {
			public void mouseClicked(MouseEvent e) {
				processEvent(e);
			}

			public void mousePressed(MouseEvent e) {
				processEvent(e);
			}

			public void mouseReleased(MouseEvent e) {
				processEvent(e);
				if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0
						&& !e.isControlDown() && !e.isShiftDown()) {
					popupMenu.add(deleteItem);
					popupMenu.add(updatetItem);
					popupMenu.add(tableNameItem);
					// popupMenu.show(e.getComponent(), e.getX(), e.getY());
					showPopupMenu(e);
				}
			}

			public void mouseEntered(MouseEvent e) {
				processEvent(e);
			}

			public void mouseExited(MouseEvent e) {
				processEvent(e);
			}

			public void mouseDragged(MouseEvent e) {
				processEvent(e);
			}

			public void mouseMoved(MouseEvent e) {
				processEvent(e);
			}

			private void processEvent(MouseEvent e) {
				if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
					int modifiers = e.getModifiers();
					modifiers -= MouseEvent.BUTTON3_MASK;
					modifiers |= MouseEvent.BUTTON1_MASK;
					MouseEvent ne = new MouseEvent(e.getComponent(), e.getID(),
							e.getWhen(), modifiers, e.getX(), e.getY(),
							e.getClickCount(), false);
					jTable.dispatchEvent(ne);
				}
			}
		};
	}

	private void showPopupMenu(MouseEvent e) {
		int row = e.getY() / jTable.getRowHeight();

		/**
		 * 取得是表名的那一列
		 */
		int tableNameColumn = 0;

		/**
		 * 取得表名并弹出菜单
		 */
		if (tableNameColumn != -1) {
			/**
			 * 修改菜单首条的名称
			 */
			tableName = (String) jTable.getValueAt(row, tableNameColumn);
			tableNameItem.setText(tableName);

			// 弹出菜单
			popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	@Override
	public void showDialog() {
		JOptionPane jPane = new JOptionPane();
		jPane.showMessageDialog(FindTable.this, "数据库中不存在该字段", "错误提示",
				JOptionPane.INFORMATION_MESSAGE);

	}
}
