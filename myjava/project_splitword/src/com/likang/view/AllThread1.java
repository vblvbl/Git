package com.likang.view;

import java.io.File;

public class AllThread1 implements Runnable {
	private MainFrame mainframe;
	private File myfile;
	private String charset;

	public AllThread1(MainFrame mf, File myfile, String charset) {
		this.mainframe = mf;
		this.myfile = myfile;
		this.charset = charset;
	}

	@Override
	public void run() {
		try {
			MethUtil.writeAllToFile1(myfile, charset);
			mainframe.tellMethod1Over();
		} catch (Exception e) {
			mainframe.tellMethod1Error();
		}
	}

}
