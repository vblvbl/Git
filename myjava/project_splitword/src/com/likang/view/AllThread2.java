package com.likang.view;

import java.io.File;

public class AllThread2 implements Runnable {
	private MainFrame mainframe;
	private File myfile;
	private String charset;

	public AllThread2(MainFrame mf, File myfile, String charset) {
		this.mainframe = mf;
		this.myfile = myfile;
		this.charset = charset;
	}

	@Override
	public void run() {
		try {
			MethUtil.writeAllToFile2(myfile, charset);
			mainframe.tellMethod2Over();
		} catch (Exception e) {
			mainframe.tellMethod2Error();
		}
	}

}
