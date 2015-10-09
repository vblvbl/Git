package com.likang.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

public class MainFrame extends JFrame implements CallBack {
	private JScrollPane jsc_framePanel2TextArea;
	private JScrollPane jsc_framePanel2TextArea2;
	private JScrollPane framePanel2JScrollPane;
	private JButton framePanel2KeyButton;
	private JButton framePanel2BuildButton;
	private JButton framePanel2OutButton;
	private JButton framePanel2OutAllButton;
	private JTextArea framePanel2TextArea;
	private JPopupMenu popupMenu;
	private Style[] styles;
	private Document doc;
	private JMenuItem copyItem;
	private JMenuItem pasteItem;
	private ArrayList<File> files;
	private JLabel jpic;
	private int count = 0;
	private int count2 = 0;
	private String max;
	private JComboBox<String> jFrame1Combo;
	private JComboBox<String> jFrame2Combo;
	private JPanel frame1ButtonJpanelvert;
	private int height;
	private int softheight;
	private int width;
	private int softwidth;
	private JPanel cardPanel;
	private JPanel framePanel1;
	private JPanel framePanel2;
	private CardLayout cardLayout;
	private JButton jb1;
	private JButton jb2;
	private int cardPanelwidth;
	private int cardPanelheight;
	private Container container;
	private JButton jbload;
	private JButton jbprevious;
	private JButton jbnext;
	private JButton jbspliteword;
	private JButton jbsorteword;
	private JButton jbout1;
	private JButton jbout2;
	private File myfile;
	protected File myfile2;
	private URL picurl;
	private String fenci;
	private JLabel jFrame1Lable;
	private JLabel jFrame1Lable1;
	private JLabel jFrame1Lable11;
	private JLabel jFrame1LablePhoto;
	private String charset = "GB2312";
	private JTextArea jtFrame1Are;
	private JTextPane jtFrame1Are2;
	private JScrollPane jFrame1ScrollPane;
	private JScrollPane jFrame1ScrollPane2;
	private JButton jballout1;
	private JButton jballout2;
	private JPanel frame1ButtonJpanel;
	private JPanel frame2ButtonJpanel;
	private JButton jbnext2;
	private JButton jbprevious2;
	private JButton jbload2;
	protected JTextArea framePanel2TextArea2;
	protected ArrayList<File> file2s;
	protected String orign;
	protected String need2;

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

	public MainFrame() {
		Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
		height = (int) ds.getHeight();
		width = (int) ds.getWidth();
		this.setTitle("分词系统   李康出品");
		softwidth = 3 * width / 5;
		softheight = 5 * height / 7;
		this.setLayout(null);
		this.setBounds(width / 5, height / 10, softwidth, softheight);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		init();
	}

	private void init() {
		picurl = this.getClass().getClassLoader().getResource("pics/word.png");
		container = this.getContentPane();

		container.setLayout(null);
		jb1 = new JButton("分词");
		jb1.setBounds(0, 0, softwidth / 2, 30);
		jb1.setBackground(new Color(176, 226, 255));
		jb1.setEnabled(false);

		jb2 = new JButton("关键词");
		jb2.setBounds(softwidth / 2, 0, softwidth / 2, 30);
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		cardPanelwidth = softwidth - 4;
		cardPanelheight = height - 60;
		cardPanel.setBounds(0, 30, cardPanelwidth, cardPanelheight);
		initFrame1();
		initFrame2();
		cardPanel.add(framePanel1, "frame1");
		cardPanel.add(framePanel2, "frame2");
		container.add(jb1);
		container.add(jb2);
		container.add(cardPanel);
		setEvent();
	}

	private void setEvent() {
		jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "frame1");
				jb1.setEnabled(false);
				jb1.setBackground(new Color(176, 226, 255));
				// jb2.setBackground(new Color(100, 149, 237));
				jb2.setEnabled(true);
				jb2.setBackground(null);
				count=0;
			}
		});
		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "frame2");
				// jb2.setBackground(new Color(205, 92, 92));
				jb1.setBackground(null);
				jb2.setEnabled(false);
				jb2.setBackground(new Color(255 ,228 ,225));
				jb1.setEnabled(true);
				count=0;
			}
		});

	}

	private void initFrame1() {
		/* 最外层的叫frame1的一个Panel */
		framePanel1 = new JPanel();
		framePanel1.setBackground(new Color(176, 226, 255));
		framePanel1.setLayout(null);
		framePanel1.setBounds(0, 0, cardPanelwidth, cardPanelheight);
		/* 流水布局的按钮的集合Panel */
		frame1ButtonJpanel = new JPanel();
		frame1ButtonJpanel.setOpaque(false);
		frame1ButtonJpanel.setBounds(2, 2, cardPanelwidth - 4, 35);
		// 按钮的集合Panel中的各个控件
		jFrame1Combo = new JComboBox<String>();
		jFrame1Combo.addItem("gbk2312");
		jFrame1Combo.addItem("utf-8");
		jFrame1Combo.setEnabled(false);
		jbnext = new JButton("下一个");
		// jbnext.setEnabled(false);
		jbprevious = new JButton("上一个");
		// jbprevious.setEnabled(false);
		jbload = new JButton("选择加载文件");
		// 把控件加入到按钮的集合Panel
		frame1ButtonJpanel.add(jFrame1Combo);
		frame1ButtonJpanel.add(jbload);
		frame1ButtonJpanel.add(jbprevious);
		frame1ButtonJpanel.add(jbnext);
		/* 字符标签控件 */
		jFrame1Lable = new JLabel("加载原文:");
		jFrame1Lable.setFont(new Font("宋体", Font.BOLD, 20));
		jFrame1Lable.setForeground(new Color(139, 131, 134));
		jFrame1Lable.setBounds(4, 45, 200, 25);
		/* 滑动文本区 */
		initPopmenu();
		jtFrame1Are = new JTextArea();
		jtFrame1Are.setLineWrap(true);
		jtFrame1Are.setEditable(false);
		jtFrame1Are.setOpaque(false);
		jtFrame1Are.setFont(new Font("Dialog", Font.BOLD, 17));
		jFrame1ScrollPane = new JScrollPane(jtFrame1Are);
		jFrame1ScrollPane.setBounds(10, 75, cardPanelwidth / 9 * 4, cardPanelheight /18*10);
		jFrame1ScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jFrame1ScrollPane.setOpaque(false);
		jFrame1ScrollPane.getViewport().setOpaque(false);
		
		jtFrame1Are2 = new JTextPane();
		jtFrame1Are2.setOpaque(false);
		jtFrame1Are2.setFont(new Font("Dialog", Font.BOLD, 15));
		jtFrame1Are2.setEditable(false);

		doc = jtFrame1Are2.getDocument();

		Style skyblue_personword = jtFrame1Are2.addStyle("skyblue", null);
		StyleConstants.setForeground(skyblue_personword, new Color(135, 206, 250));

		Style deepzise_areaword = jtFrame1Are2.addStyle("deepzise", null);
		StyleConstants.setForeground(deepzise_areaword, new Color(0, 238, 0));

		Style zise_nword = jtFrame1Are2.addStyle("zise", null);
		StyleConstants.setForeground(zise_nword, new Color(123, 104, 238));

		Style huise_numword = jtFrame1Are2.addStyle("huise", null);
		StyleConstants.setForeground(huise_numword, new Color(255, 193, 37));

		Style red_timeword = jtFrame1Are2.addStyle("red", null);
		StyleConstants.setForeground(red_timeword, Color.red);

		Style black_otherword = jtFrame1Are2.addStyle("black", null);
		StyleConstants.setForeground(black_otherword, new Color(0, 134, 139));

		styles = new Style[] { skyblue_personword, deepzise_areaword, zise_nword, huise_numword, red_timeword,
				black_otherword };

		jFrame1ScrollPane2 = new JScrollPane(jtFrame1Are2);
		jFrame1ScrollPane2.setBounds(10 + cardPanelwidth / 9 * 4 + 100, 75, cardPanelwidth / 20 * 8,
				cardPanelheight / 25 * 11 );
		jFrame1ScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jFrame1ScrollPane2.setOpaque(false);
		jFrame1ScrollPane2.getViewport().setOpaque(false);
		/* 字符标签控件 */
		jFrame1Lable1 = new JLabel("分词结果:");
		jFrame1Lable1.setFont(new Font("宋体", Font.BOLD, 20));
		jFrame1Lable1.setForeground(new Color(139, 131, 134));
		jFrame1Lable1.setBounds(10 + cardPanelwidth / 9 * 4 + 100, 45, 130, 25);
		/* 计算单词个数的标签 */
		jFrame1Lable11 = new JLabel("");
		jFrame1Lable11.setFont(new Font("宋体", Font.BOLD, 16));
		jFrame1Lable11.setForeground(new Color(189, 183, 107));
		jFrame1Lable11.setBounds(10 + cardPanelwidth / 9 * 4 + 250, 48, 200, 20);
		/* 按钮的集合Panel1 */
		frame1ButtonJpanelvert = new JPanel();
		frame1ButtonJpanelvert.setOpaque(false);
		frame1ButtonJpanelvert.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
		frame1ButtonJpanelvert.setBounds(10 + cardPanelwidth / 9 * 4 + 5, 75, 90, cardPanelheight / 7 * 2 + 40);

		// 按钮的集合Panel1中的各个控件
		jbspliteword = new JButton("1>分词");
		jbsorteword = new JButton("2>排列");
		// jbspliteword.setBounds(5, 2, 80, 30);
		jbout1 = new JButton("导出>1");
		jbout1.setEnabled(false);
		jbout2 = new JButton("导出>2");
		jbout2.setEnabled(false);
		// jbout.setBounds(5, 35, 80, 30);

		// 把控件加入到按钮的集合Panel
		frame1ButtonJpanelvert.add(jbspliteword);
		frame1ButtonJpanelvert.add(jbsorteword);
		frame1ButtonJpanelvert.add(jbout1);
		frame1ButtonJpanelvert.add(jbout2);

		/* 图片的jlable */
		jpic = new JLabel();
		jpic.setIcon(new ImageIcon(picurl));
		jpic.setBounds(cardPanelwidth / 2 - 20,cardPanelheight / 25 * 11+80, cardPanelwidth / 2, 40);

		jballout1 = new JButton("方式1批量导出");
		jballout1.setFont(new Font("Dialog", Font.BOLD, 14));
		jballout1.setEnabled(false);
		jballout1.setBounds(cardPanelwidth / 2, cardPanelheight / 25 * 11+130, cardPanelwidth /16*3, 30);

		jballout2 = new JButton("方式2批量导出");
		jballout2.setFont(new Font("Dialog", Font.BOLD, 14));
		jballout2.setEnabled(false);
		jballout2.setBounds(cardPanelwidth / 2 + cardPanelwidth /16*4, cardPanelheight / 25 * 11+130, cardPanelwidth /16*3,
				30);

		/* 把外层空间加入到frame */
		framePanel1.add(jFrame1Lable);
		framePanel1.add(jballout1);
		framePanel1.add(jballout2);
		framePanel1.add(jFrame1Lable11);
		framePanel1.add(jFrame1Lable1);
		framePanel1.add(frame1ButtonJpanelvert);
		framePanel1.add(jFrame1ScrollPane);
		framePanel1.add(jFrame1ScrollPane2);
		framePanel1.add(frame1ButtonJpanel);
		framePanel1.add(jpic);
		setFrame1Event();
	}

	private void initPopmenu() {
		popupMenu = new JPopupMenu();
		copyItem = new JMenuItem("复制");
		pasteItem = new JMenuItem("粘贴");
	}

	private void setFrame1Event() {
		/* popMenu的实现 */
		final MouseInputListener mouseInputListener = MainFrame.this.getMouseInputListener(jtFrame1Are);
		jtFrame1Are.addMouseListener(mouseInputListener);
		jtFrame1Are.addMouseMotionListener(mouseInputListener);

		jbout1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jtFrame1Are2.getText().equals("")) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "请先执行分词操作!", "先操作", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				try {
					MethUtil.writeToFile1(myfile, fenci, count);
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "文件已经导出,请查看载入目录下result1文件夹!", "导出成功",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "写出文件失败!", "写入文件", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		jbout2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jtFrame1Are2.getText().equals("")) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "请先执行分词操作!", "先操作", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				try {
					MethUtil.writeToFile2(myfile, jtFrame1Are2.getText(), count);
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "文件已经导出,请查看载入目录下result2文件夹!", "导出成功",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "写出文件失败!", "写入文件", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		jbspliteword.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String word = jtFrame1Are.getText();
				if (word.equals("")) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "请先加载文件!", "加载信息", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				try {
					fenci = MethUtil.splitMethod(max);
					jtFrame1Are2.setText(fenci);
					int num = MethUtil.wordCount(fenci);
					jFrame1Lable11.setText("共有 :" + num + " 个单词");
					jbout1.setEnabled(true);
					jFrame1Lable1.setForeground(new Color(255, 106, 106));
					jtFrame1Are2.setOpaque(true);
				} catch (Exception e1) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "分词异常!", "异常", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		jbsorteword.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jtFrame1Are2.getText().equals("")) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "请先执行分词操作!", "先操作", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				// 写单词区分 并标记颜色的方法
				String paixu = MethUtil.sortFenci(fenci);
				String[] miwords = new String[] { "人名", "地点", "名词", "数词", "时间词", "其他" };
				ArrayList<Integer> ls = MethUtil.locationWord(paixu, miwords);
				jtFrame1Are2.setText(paixu);
				for (int i = 0; i < ls.size(); i++) {
					try {
						doc.remove(ls.get(i), miwords[i].length());
						doc.insertString(ls.get(i), miwords[i], styles[i]);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				jbout2.setEnabled(true);
			}
		});
		jFrame1Combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jtFrame1Are.setText("");
				String select = (String) jFrame1Combo.getSelectedItem();
				if (select.equals("gbk2312")) {
					charset = "GB2312";
					if (myfile != null) {
						max = MethUtil.fileToString(files.get(count), charset);
						jtFrame1Are.setText(max);
					} else {
						return;
					}
				} else if (select.equals("utf-8")) {
					charset = "UTF-8";
					if (myfile != null) {
						max = MethUtil.fileToString(files.get(count), charset);
						jtFrame1Are.setText(max);
					} else {
						return;
					}
				}

			}
		});

		jbprevious.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jtFrame1Are.getText().equals("")) {
					// JOptionPane jOptionPane = new JOptionPane();
					// JOptionPane.showMessageDialog(MainFrame.this, "您没有选择文件",
					// "选择文件空", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (count == 0) {
					return;
				}
				count--;
				max = MethUtil.fileToString(files.get(count), charset);
				jtFrame1Are.setText(max);
				jtFrame1Are2.setText("");
				jFrame1Lable11.setText("");
				jbout1.setEnabled(false);
				jbout2.setEnabled(false);
				jFrame1Lable.setForeground(new Color(139, 131, 134));
				jFrame1Lable1.setForeground(new Color(139, 131, 134));
			}
		});

		jbnext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jtFrame1Are.getText().equals("")) {
					// JOptionPane jOptionPane = new JOptionPane();
					// JOptionPane.showMessageDialog(MainFrame.this, "您没有选择文件",
					// "选择文件空", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (count >= files.size() - 1) {
					return;
				}
				count++;
				max = MethUtil.fileToString(files.get(count), charset);
				jtFrame1Are.setText(max);
				jtFrame1Are2.setText("");
				jFrame1Lable11.setText("");
				jbout1.setEnabled(false);
				jFrame1Lable.setForeground(new Color(139, 131, 134));
				jFrame1Lable1.setForeground(new Color(139, 131, 134));
				jbout2.setEnabled(false);
			}
		});
		jbload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (charset == null || charset.equals("")) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "请先选择字符编码", "字符编码error",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(MainFrame.this);
				myfile = chooser.getSelectedFile();
				if (myfile != null) {
					files = MethUtil.fileList(myfile);
					max = MethUtil.fileToString(files.get(count), charset);
					// System.out.println(need);
					// String tmp = MethUtil.dataMethod(need,
					// jtArea.getText());
					jFrame1Lable.setForeground(new Color(255, 106, 106));
					jbprevious.setEnabled(true);
					jbnext.setEnabled(true);
					jFrame1Combo.setEnabled(true);
					jtFrame1Are.setText(max);
					jballout1.setEnabled(true);
					jballout2.setEnabled(true);
					jtFrame1Are.setOpaque(true);

				} else {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "您没有选择文件", "选择文件空", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		jballout1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new AllThread1(MainFrame.this, myfile, charset)).start();
				JOptionPane jOptionPane = new JOptionPane();
				JOptionPane.showMessageDialog(MainFrame.this, "后台正在批量处理,请耐心等待,完成后会提示", "方法1写入中...",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		jballout2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new AllThread1(MainFrame.this, myfile, charset)).start();
				JOptionPane.showMessageDialog(MainFrame.this, "后台正在批量处理,请耐心等待,完成后会提示", "方法2写入中...",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

	}

	private MouseInputListener getMouseInputListener(final JTextArea jtFrame1Are) {
		return new MouseInputListener() {
			public void mouseClicked(MouseEvent e) {
				processEvent(e);
			}

			public void mousePressed(MouseEvent e) {
				processEvent(e);
			}

			public void mouseReleased(MouseEvent e) {
				processEvent(e);
				if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0 && !e.isControlDown() && !e.isShiftDown()) {
					if (!jtFrame1Are.getText().equals("")) {
						popupMenu.add(copyItem);
						popupMenu.add(pasteItem);
						// popupMenu.show(e.getComponent(), e.getX(), e.getY());
						popupMenu.show(e.getComponent(), e.getX(), e.getY());
					}
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
					MouseEvent ne = new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), modifiers, e.getX(),
							e.getY(), e.getClickCount(), false);
					jtFrame1Are.dispatchEvent(ne);
				}
			}
		};
	}

	private void initFrame2() {
		framePanel2 = new JPanel();
		framePanel2.setLayout(null);
		framePanel2.setBounds(0, 0, cardPanelwidth, cardPanelheight);
		framePanel2.setBackground(new Color(255 ,228 ,225));
		frame2ButtonJpanel = new JPanel();
		frame2ButtonJpanel.setOpaque(false);
		frame2ButtonJpanel.setBounds(2, 2, cardPanelwidth - 4, 35);
		// 按钮的集合Panel中的各个控件
		jFrame2Combo = new JComboBox<String>();
		jFrame2Combo.addItem("gbk2312");
		jFrame2Combo.addItem("utf-8");
		jFrame2Combo.setEnabled(false);
		jbnext2 = new JButton("下一个");
		// jbnext.setEnabled(false);
		jbprevious2 = new JButton("上一个");
		// jbprevious.setEnabled(false);
		jbload2 = new JButton("选择加载文件");
		// 把控件加入到按钮的集合Panel
		frame2ButtonJpanel.add(jFrame2Combo);
		frame2ButtonJpanel.add(jbload2);
		frame2ButtonJpanel.add(jbprevious2);
		frame2ButtonJpanel.add(jbnext2);
		/* 左边的文本框 */
		framePanel2TextArea = new JTextArea();
		framePanel2TextArea.setLineWrap(true);
		framePanel2TextArea.setEditable(false);
		framePanel2TextArea.setOpaque(false);
		framePanel2TextArea.setFont(new Font("Dialog", Font.BOLD, 14));
		jsc_framePanel2TextArea = new JScrollPane(framePanel2TextArea);
		jsc_framePanel2TextArea.setBounds(0, 45, cardPanelwidth / 25 * 12, cardPanelheight / 4);
		jsc_framePanel2TextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsc_framePanel2TextArea.setOpaque(false);
		jsc_framePanel2TextArea.getViewport().setOpaque(false);
		/* 箭头按钮图标 */
		framePanel2KeyButton = new JButton();
		framePanel2KeyButton.setContentAreaFilled(false);
		framePanel2KeyButton.setBounds(cardPanelwidth / 25 * 12, cardPanelheight / 8 + 30, cardPanelwidth / 25, 30);
		framePanel2KeyButton
				.setIcon(new ImageIcon(MainFrame.this.getClass().getClassLoader().getResource("pics/key.png")));
		/* 右边的文本框 */
		framePanel2TextArea2 = new JTextArea();
		framePanel2TextArea2.setLineWrap(true);
		framePanel2TextArea2.setEditable(false);
		framePanel2TextArea2.setOpaque(false);
		framePanel2TextArea2.setFont(new Font("Dialog", Font.BOLD, 14));
		jsc_framePanel2TextArea2 = new JScrollPane(framePanel2TextArea2);
		jsc_framePanel2TextArea2.setBounds(cardPanelwidth / 25 * 13, 45, cardPanelwidth / 25 * 12, cardPanelheight / 4);
		jsc_framePanel2TextArea2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsc_framePanel2TextArea2.setOpaque(false);
		jsc_framePanel2TextArea2.getViewport().setOpaque(false);
		/* 表格 */
		framePanel2JScrollPane = new JScrollPane();
		framePanel2JScrollPane.setBounds(cardPanelwidth / 11, cardPanelheight / 4 + 50, cardPanelwidth / 11 * 9,
				cardPanelheight / 7 * 2);
		framePanel2JScrollPane.setOpaque(false);
		framePanel2JScrollPane.getViewport().setOpaque(false);
		/* 制表按钮 */
		framePanel2BuildButton = new JButton("制做表格");
		framePanel2BuildButton.setBounds(cardPanelwidth / 7, cardPanelheight / 4 + 60 + cardPanelheight / 7 * 2,
				cardPanelwidth / 7, 30);
		framePanel2BuildButton.setEnabled(false);
		/* 导出按钮 */
		framePanel2OutButton = new JButton("单个导出");
		framePanel2OutButton.setBounds(cardPanelwidth / 7 * 3, cardPanelheight / 4 + 60 + cardPanelheight / 7 * 2,
				cardPanelwidth / 7, 30);
		framePanel2OutButton.setEnabled(false);
		/* 批量导出按钮 */
		framePanel2OutAllButton = new JButton("批量导出");
		framePanel2OutAllButton.setBounds(cardPanelwidth / 7 * 5, cardPanelheight / 4 + 60 + cardPanelheight / 7 * 2,
				cardPanelwidth / 7, 30);
		framePanel2OutAllButton.setEnabled(false);
		/* 全部加入frame2的panel中 */
		framePanel2.add(frame2ButtonJpanel);
		framePanel2.add(jsc_framePanel2TextArea);
		framePanel2.add(jsc_framePanel2TextArea2);
		framePanel2.add(framePanel2JScrollPane);
		framePanel2.add(framePanel2KeyButton);
		framePanel2.add(framePanel2BuildButton);
		framePanel2.add(framePanel2OutButton);
		framePanel2.add(framePanel2OutAllButton);

		setFrame2Event();

	}

	private void setFrame2Event() {
		jFrame2Combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				framePanel2TextArea.setText("");
				String select = (String) jFrame2Combo.getSelectedItem();
				if (select.equals("gbk2312")) {
					charset = "GB2312";
					if (myfile2 != null) {
						max = MethUtil.fileToString(file2s.get(count), charset);
						framePanel2TextArea.setText(max);
					} else {
						return;
					}
				} else if (select.equals("utf-8")) {
					charset = "UTF-8";
					if (myfile != null) {
						max = MethUtil.fileToString(file2s.get(count), charset);
						framePanel2TextArea.setText(max);
					} else {
						return;
					}
				}

			}
		});

		jbprevious2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (framePanel2TextArea.getText().equals("")) {
					// JOptionPane jOptionPane = new JOptionPane();
					// JOptionPane.showMessageDialog(MainFrame.this, "您没有选择文件",
					// "选择文件空", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (count2 == 0) {
					return;
				}
				count2--;
				max = MethUtil.fileToString(file2s.get(count2), charset);
				framePanel2TextArea.setText(max);
				framePanel2TextArea2.setText("");
				framePanel2JScrollPane.setViewportView(null);
				framePanel2BuildButton.setEnabled(false);
				framePanel2OutButton.setEnabled(false);
				framePanel2OutAllButton.setEnabled(false);
			}
		});

		jbnext2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (framePanel2TextArea.getText().equals("")) {
					// JOptionPane jOptionPane = new JOptionPane();
					// JOptionPane.showMessageDialog(MainFrame.this, "您没有选择文件",
					// "选择文件空", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (count2 >= file2s.size() - 1) {
					return;
				}
				count2++;
				max = MethUtil.fileToString(file2s.get(count2), charset);
				framePanel2TextArea.setText(max);
				framePanel2TextArea2.setText("");
				framePanel2JScrollPane.setViewportView(null);
				framePanel2BuildButton.setEnabled(false);
				framePanel2OutButton.setEnabled(false);
				framePanel2OutAllButton.setEnabled(false);
			}
		});
		jbload2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (charset == null || charset.equals("")) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "请先选择字符编码", "字符编码error",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(MainFrame.this);
				myfile2 = chooser.getSelectedFile();
				if (myfile2 != null) {
					file2s = MethUtil.fileList(myfile2);
					max = MethUtil.fileToString(file2s.get(count), charset);
					// System.out.println(need);
					// String tmp = MethUtil.dataMethod(need,
					// jtArea.getText());
					jbprevious2.setEnabled(true);
					jbnext2.setEnabled(true);
					jFrame2Combo.setEnabled(true);
					framePanel2TextArea.setOpaque(true);
					framePanel2TextArea.setText(max);

					// count++;

				} else {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "您没有选择文件", "选择文件空", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		framePanel2KeyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String word = framePanel2TextArea.getText();
				if (word.equals("")) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "请先加载文件!", "加载信息", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				try {
					String need1 = MethUtil.keywordMethod(word);
				 need2=MethUtil.newwordMethod(word);
					orign=new StringBuilder().append(need1).append(need2).toString();
					StringBuilder sb=new StringBuilder();
					sb.append("--->关键词:"+"\r\n");
					sb.append(need1+"\r\n"+"\r\n");
					sb.append("--->新词:"+"\r\n");
					sb.append(need2);
					framePanel2TextArea2.setOpaque(true);
					framePanel2TextArea2.setText(sb.toString());
					framePanel2BuildButton.setEnabled(true);
					framePanel2OutButton.setEnabled(true);
					framePanel2OutAllButton.setEnabled(true);
				} catch (Exception e1) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "关键词异常!", "异常", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		framePanel2BuildButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (orign.equals("")||orign==null) {
					return;
				}
				String[] columnNames = new String[] { "关键词", "词性", "权重" };
//				String[] spliteneed2=need2.split("#");
//				StringBuilder sb=new StringBuilder();
//				for(String sn:spliteneed2){
//					sb.append(sn);
//				}
				Object[][] rowData = MethUtil.getForm(orign,framePanel2TextArea.getText(),need2);
				DefaultTableModel model = new DefaultTableModel(rowData, columnNames){
					 public boolean isCellEditable(int row,int column)
					  {
					   return false;
					  }
				};
				JTable jtb = new JTable(model);
				jtb.setRowHeight(30);
				jtb.setRowMargin(6);
				jtb.setGridColor(Color.RED);
				jtb.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
				jtb.setBorder(BorderFactory.createEtchedBorder());
				framePanel2JScrollPane.setViewportView(jtb);
			}
		});
		framePanel2OutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String key=framePanel2TextArea2.getText();
				if (key.equals("")) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "请先执行分词操作!", "先操作", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				try {
					MethUtil.writeToFileKey(myfile2, key, count2);
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "文件已经导出,请查看载入目录下keyresult文件夹!", "导出成功",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(MainFrame.this, "写出文件失败!", "写入文件", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

	
		framePanel2OutAllButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new KeyAllThread(MainFrame.this, myfile2, charset)).start();
				JOptionPane jOptionPane = new JOptionPane();
				JOptionPane.showMessageDialog(MainFrame.this, "后台正在批量处理,请耐心等待,完成后会提示", "批量关键词写入",
						JOptionPane.INFORMATION_MESSAGE);

			}
		});

	}

	@Override
	public void tellMethod1Over() {
		JOptionPane jOptionPane = new JOptionPane();
		JOptionPane.showMessageDialog(MainFrame.this, "批量执行方法1写入完成", "写入成功", JOptionPane.INFORMATION_MESSAGE);

	}

	@Override
	public void tellMethod1Error() {
		JOptionPane jOptionPane = new JOptionPane();
		JOptionPane.showMessageDialog(MainFrame.this, "批量写入异常", "方法1写入", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void tellMethod2Over() {
		JOptionPane jOptionPane = new JOptionPane();
		JOptionPane.showMessageDialog(MainFrame.this, "批量执行方法2写入完成", "写入成功", JOptionPane.INFORMATION_MESSAGE);

	}

	@Override
	public void tellMethod2Error() {
		JOptionPane jOptionPane = new JOptionPane();
		JOptionPane.showMessageDialog(MainFrame.this, "批量写入异常", "方法2写入", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void tellKeyAllOk() {
		JOptionPane jOptionPane = new JOptionPane();
		JOptionPane.showMessageDialog(MainFrame.this, "批量执行Key写入完成", "写入成功", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void tellKeyAllError() {
		JOptionPane jOptionPane = new JOptionPane();
		JOptionPane.showMessageDialog(MainFrame.this, "批量写入Key异常", "key写入", JOptionPane.INFORMATION_MESSAGE);
	}

}
