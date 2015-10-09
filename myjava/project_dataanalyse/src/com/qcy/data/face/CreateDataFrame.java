package com.qcy.data.face;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.qcy.data.dao.JDBCUtil;
import com.qcy.data.service.DbData;

@SuppressWarnings("serial")
public class CreateDataFrame extends JFrame {

	JLabel blnew = new JLabel("新表名：");
	private final JTextField newTableNameText = new JTextField(8);
	private final JLabel oldName = new JLabel("表名");
	private final JTextField oldNameText = new JTextField(8);
	private final JLabel digtal = new JLabel("字段");
	private final JTextField digtalText = new JTextField(8);
	private final JLabel  type= new JLabel("修改类型");
	private final JTextField typeText = new JTextField(8);
	private final JButton jbn = new JButton("修改");
	private final JTextField whereText = new JTextField(100);
	JButton ok = new JButton("确定");

	private DefaultTableModel model1;
	JLabel jbl1 = new JLabel("表格1：");
	JComboBox<String> jbox1 = null;
	JButton btn1 = new JButton("查看表");
	JButton btnadd1 = new JButton("添加");
	JButton btndelete1 = new JButton("移除");
	JScrollPane scpDemo1 = new JScrollPane();
	JTable jt1 = null;
	DefaultListModel<String> listModel1 = new DefaultListModel<String>();
	JList<String> list1 = new JList<String>(listModel1);
	private final JTextField jtxt1 = new JTextField(10);

	private DefaultTableModel model2;
	JLabel jbl2 = new JLabel("表格2：");
	JComboBox<String> jbox2 = null;
	JButton btn2 = new JButton("查看表");
	JButton btnadd2 = new JButton("添加");
	JButton btndelete2 = new JButton("移除");
	JScrollPane scpDemo2 = new JScrollPane();
	JTable jt2 = null;
	DefaultListModel<String> listModel2 = new DefaultListModel<String>();
	JList<String> list2 = new JList<String>(listModel2);
	private final JTextField jtxt2 = new JTextField(10);

	Container c = getContentPane();

	public CreateDataFrame() throws Exception {
		String[] strArray = DbData.getAllTableInfo();
		c.setLayout(new BorderLayout());

		// 控件设置
		whereText.setBorder(BorderFactory.createTitledBorder("关联条件:"));
		whereText.setEnabled(false);

		String[] columnNames = { "列名", "数据类型" };
		Object[][] rowData = new Object[1][1];

		model1 = new DefaultTableModel(rowData, columnNames);
		jt1 = new JTable(model1);
		jt1.setEnabled(false);
		jt1.setBorder(BorderFactory.createEtchedBorder());
		jt1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// 设置为滚动的表格
		jt1.setSize(10, 300);
		scpDemo1.getViewport().add(jt1);
		scpDemo1.setPreferredSize(new Dimension(150, 250));
		list1.setBorder(BorderFactory.createEtchedBorder());

		model2 = new DefaultTableModel(rowData, columnNames);
		jt2 = new JTable(model2);
		jt2.setEnabled(false);
		jt2.setBorder(BorderFactory.createEtchedBorder());
		jt2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// 设置为滚动的表格
		jt2.setSize(10, 300);
		scpDemo2.getViewport().add(jt2);
		scpDemo2.setPreferredSize(new Dimension(150, 250));
		list2.setBorder(BorderFactory.createEtchedBorder());

		// 首行左侧面板
		JPanel jp0 = new JPanel(new GridLayout(1, 2));
		JPanel jp00 = new JPanel(new BorderLayout());
		JPanel jp000 = new JPanel();
		JPanel jp002 = new JPanel();
		JPanel jp003 = new JPanel(new BorderLayout());

		jp000.add(jbl1);
		jbox1 = new JComboBox<String>(strArray);
		jp000.add(jbox1);
		jp000.add(btn1);

		jp002.add(scpDemo1);

		JPanel jp0030 = new JPanel();
		jp0030.add(jtxt1);
		jp0030.add(btnadd1);

		jp003.add(jp0030, BorderLayout.NORTH);
		jp003.add(list1, BorderLayout.CENTER);
		jp003.add(btndelete1, BorderLayout.SOUTH);

		jp00.add(jp000, BorderLayout.NORTH);
		jp00.add(jp002, BorderLayout.WEST);
		jp00.add(jp003, BorderLayout.EAST);

		// 首行右侧面板
		JPanel jp01 = new JPanel(new BorderLayout());
		JPanel jp010 = new JPanel();
		JPanel jp012 = new JPanel();
		JPanel jp013 = new JPanel(new BorderLayout());

		jp010.add(jbl2);
		jbox2 = new JComboBox<String>(strArray);
		jp010.add(jbox2);
		jp010.add(btn2);

		jp012.add(scpDemo2);

		JPanel jp0131 = new JPanel();
		jp0131.add(jtxt2);
		jp0131.add(btnadd2);

		jp013.add(jp0131, BorderLayout.NORTH);
		jp013.add(list2, BorderLayout.CENTER);
		jp013.add(btndelete2, BorderLayout.SOUTH);

		jp01.add(jp010, BorderLayout.NORTH);
		jp01.add(jp012, BorderLayout.WEST);
		jp01.add(jp013, BorderLayout.EAST);
		// 北部面板
		jp0.add(jp00);
		jp0.add(jp01);
		c.add(jp0, BorderLayout.NORTH);

		// 中部面板
		c.add(whereText, BorderLayout.CENTER);

		// 南部面板
		JPanel jp1 = new JPanel();
		jp1.add(blnew);
		jp1.add(newTableNameText);
		jp1.add(ok);
		jp1.add(oldName);
		jp1.add(oldNameText);
		jp1.add(digtal);
		jp1.add(digtalText);
		jp1.add(type);
		jp1.add(typeText);
		jp1.add(jbn);
		
		c.add(jp1, BorderLayout.SOUTH);
		
		drawTable(model1, jt1, jbox1);
		drawTable(model2, jt2, jbox2);

		// 事件监听
		jbox1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				try{
				   drawTable(model1, jt1, jbox1);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, "数据读取失败,请检查数据库是否连接",
							"信息提示", JOptionPane.INFORMATION_MESSAGE);
					ex.printStackTrace();
				}
				jtxt1.setText("");
				listModel1.removeAllElements();
				list1.setModel(listModel1);
			}
		});

		jbox2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				try{
					drawTable(model2, jt2, jbox2);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, "数据读取失败,请检查数据库是否连接",
							"信息提示", JOptionPane.INFORMATION_MESSAGE);
					ex.printStackTrace();
				}
				jtxt2.setText("");
				listModel2.removeAllElements();
				list2.setModel(listModel2);
			}
		});

		// jtable1 click事件
		jt1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // 获得行位置
					int col = 0; // 获得列位置 String
					jtxt1.setText((String) (model1.getValueAt(row, col)));
				}
			}
		});
		// jtable2 click事件
		jt2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // 获得行位置
					int col = 0; // 获得列位置 String
					jtxt2.setText((String) (model2.getValueAt(row, col)));
				}
			}
		});

		// 添加按钮1 单击事件
		btnadd1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!jtxt1.getText().toString().equals("")) {
					// 从JList中获得这个集合,转换为默认项集合类型

					if (!listModel1.contains(jtxt1.getText())) {
						// 追加元素
						listModel1.add(listModel1.getSize(), jtxt1.getText());
						// 将这个集合添加到JList中
						list1.setModel(listModel1);
					} else {
						JOptionPane.showMessageDialog(null, "已在列表中，不能重复添加",
								"信息提示", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "不能为空", "信息提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// 添加按钮2 单击事件
		btnadd2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!jtxt2.getText().toString().equals("")) {
					// 从JList中获得这个集合,转换为默认项集合类型

					if (!listModel2.contains(jtxt2.getText())) {
						// 追加元素
						listModel2.add(listModel2.getSize(), jtxt2.getText());
						// 将这个集合添加到JList中
						list2.setModel(listModel2);
					} else {
						JOptionPane.showMessageDialog(null, "已在列表中,不能重复添加",
								"信息提示", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "不能为空,请选择要添加的列",
							"信息提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// 移除选项按钮1 事件监听
		btndelete1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!list1.getSelectedValue().equals("")) {
						listModel1.removeElement(list1.getSelectedValue());
						list1.setModel(listModel1);
					}
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(null, "请选择要删除的列", "信息提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// 移除选项按钮2 事件监听
		btndelete2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!list2.getSelectedValue().equals("")) {
						listModel2.removeElement(list2.getSelectedValue());
						list2.setModel(listModel2);
					}
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(null, "请选择要删除的列", "信息提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		//确定，生成新表
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//获取新表名称
				String tableName=newTableNameText.getText();
				if(tableName.equals("")){
					JOptionPane.showMessageDialog(null, "请先填写新表名称", "信息提示",
							JOptionPane.INFORMATION_MESSAGE);
				}else{
					//获取所有的字段信息
					List<Object[]> list=getList();
					//获取条件语句
					String where=whereText.getText();
					if(list.size()>0){
						if(DbData.saveTable(list,where,tableName)){
							JOptionPane.showMessageDialog(null, "新建表成功", "信息提示",
									JOptionPane.INFORMATION_MESSAGE);
						}else{
							JOptionPane.showMessageDialog(null, "新建表失败,请重新打开窗口再次尝试", "信息提示",
									JOptionPane.INFORMATION_MESSAGE);
						}					
					}else{	
						JOptionPane.showMessageDialog(null, "请先选择要添加的字段", "信息提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
			}
		});
		jbn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//获取字段名称
				String oldTable=oldNameText.getText();
				String digtalName=digtalText.getText();
				String typeName=typeText.getText();
				if(digtalName.equals("")){
					JOptionPane.showMessageDialog(null, "请先填写需要修改的表名，字段名称，数据类型", "信息提示",
							JOptionPane.INFORMATION_MESSAGE);
				}else{
					try {
						oldTable = new String(oldTable.getBytes("iso-8859-1"),"UTF-8");
						digtalName = new String(digtalName.getBytes("iso-8859-1"),"UTF-8");
						typeName = new String(typeName.getBytes("iso-8859-1"),"UTF-8");
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
					System.out.println(oldTable+"++"+digtalName+"++"+typeName);
					JDBCUtil.updateTable(oldTable, digtalName, typeName);
					JOptionPane.showMessageDialog(null, "修改成功", "信息提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});


		// 显示
		setSize(700, 400);
		setLocation(300, 200);
		setResizable(false);
		setVisible(true);
		setTitle("数据冲突");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * 获取选择的字段
	 * @return
	 */
	private List<Object[]> getList(){
		List<Object[]> list=new ArrayList<Object[]>();
		Object[][] rowData1 = getRowsInfo(jbox1.getSelectedItem().toString());
		Object[][] rowData2 = getRowsInfo(jbox2.getSelectedItem().toString());
		
		for (Object[] objects : rowData2) {
			if(listModel2.contains(objects[0])){
				if(objects[1].toString().toLowerCase().equals("varchar")||objects[1].toString().toLowerCase().equals("char")){
					objects[1]=objects[1]+"(200)";
				}
				list.add(objects);
			}
		}
		
		for (Object[] objects : rowData1) {
			if(listModel1.contains(objects[0])){
				if(objects[1].toString().toLowerCase().equals("varchar")||objects[1].toString().toLowerCase().equals("char")){
					objects[1]=objects[1]+"(200)";
				}
				list.add(objects);
			}
		}
		
		return list;
	}

	/**
	 * 画表格
	 * @param model
	 * @param jt
	 * @param combox
	 * @throws Exception
	 */
	private void drawTable(DefaultTableModel model, JTable jt,
			JComboBox<String> combox) throws Exception {
		validateWhereText();
		clearAllColumAndRow(model, jt);
		Object[][] rowData = getRowsInfo(combox.getSelectedItem().toString());
		String[] columnNames = { "列名", "数据类型" };
		model.setDataVector(rowData, columnNames);
	}
	
	/**
	 * 生成where条件
	 * @throws Exception
	 */
	private void validateWhereText() throws Exception{
		if(!jbox1.getSelectedItem().toString().equals(jbox2.getSelectedItem().toString())){

			List<String> ll1=DbData.getAllFiledInTableReturnList(jbox1.getSelectedItem().toString(),false);
			List<String> ll2=DbData.getAllFiledInTableReturnList(jbox2.getSelectedItem().toString(),false);
			
			for(int i=0;i<ll1.size();i++){
				String value=ll1.get(i);
				 
				if(ll2.contains(value)&&!value.toLowerCase().equals("id")){
					whereText.setText(jbox1.getSelectedItem()+"."+ll1.get(i)+"="+jbox2.getSelectedItem()+"."+ll1.get(i));
					break;
				}else{
					whereText.setText("");
				}
			}
		}else{
			whereText.setText("");
		}
	}

	/**
	 * 获取指定表的字段名称和字段类型
	 * @param tablename
	 * @return
	 */
	private Object[][] getRowsInfo(String tablename) {
		Object[][] objRows = null;
		try {
			objRows = DbData.getAllRowsNameAndType(tablename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRows;
	}

	/**
	 * 清除所有的行和列
	 * @param model
	 * @param jt
	 */
	private void clearAllColumAndRow(DefaultTableModel model, JTable jt) {

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

	/*
		public static void main(String[] args) throws Exception {
			new Db(0, "demo", "root", "111111");
			new DataIntegrationFrame();
		}
	 */
}
