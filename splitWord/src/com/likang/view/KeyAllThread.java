package com.likang.view;

import java.io.File;

public class KeyAllThread implements Runnable {
	private MainFrame mainframe;
	private File myfile;
	private String charset;
	public KeyAllThread(MainFrame mf, File myfile, String charset){
		this.mainframe = mf;
		this.myfile = myfile;
		this.charset = charset;
	}
	@Override
	public void run() {
		try {
			MethUtil.writeAllKeyToFile(myfile, charset);
			mainframe.tellKeyAllOk();
		} catch (Exception e) {
			mainframe.tellKeyAllError();
		}
	}

}
