package com.likang.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.likang.daoimpl.Constant;
import com.likang.domain.FreshCard;

public class BeanFromExcel {
	File excFilePath = new File(Constant.EXCBUILS);
	String needFileName = null;

	public BeanFromExcel() {
		needFileName = getNewestFile();
	}

	public String getNewestFile() {
		FilenameFilter fbf = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(".xls");
			}
		};
		File[] excFile = excFilePath.listFiles(fbf);
		Date[] excDate = new Date[excFile.length];
		SimpleDateFormat sp = new SimpleDateFormat("yyyyMMdd");
		Date temp = null;
		int i = 0;
		for (File file : excFile) {
			String fileName = file.getName();
			String Name = fileName.substring(0, 8);
			System.out.println(Name);
			try {
				excDate[i] = sp.parse(Name);
				i++;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Date date : excDate) {
			if (temp == null) {
				temp = date;
			} else {
				if (temp.before(date)) {
					temp = date;
				}
			}
		}
		String NeedName = sp.format(temp);
		String lastFile = NeedName + ".xls";
		return lastFile;

	}

	public List<FreshCard> BeangetFromExcel() {
		File operFile = new File(Constant.EXCBUILS, needFileName);
		List<FreshCard> list = new ArrayList<FreshCard>();
		try {
			Workbook wb = Workbook.getWorkbook(operFile);
			int sheets = wb.getSheets().length;
			for (int i = 0; i < sheets; i++) {
				Sheet sheet = wb.getSheet(i);
				int k;
				if (sheet != null) {
					int col = sheet.getColumns();
					int row = sheet.getRows();
					for (int j = 0; j < row; j++) {
						if (j > 0) {
							FreshCard fd = new FreshCard();
							for (k = 0; k < col; k++) {
								String data = sheet.getCell(k, j).getContents();
								if (k == 0) {
									fd.setFreshTime(data);
								} else if (k == 1) {
									fd.setCardId(Integer.parseInt(data));
								} else if (k == 2) {
									fd.setName(data);
								} else if (k == 3) {
									fd.setDuty(data);
								} else if (k == 4) {
									fd.setNum(data);
								} else if (k == 5) {
									fd.setFreshCount(Integer.parseInt(data));
								} else if (k == 6) {
									fd.setState(data);
								} else {
									break;
								}
							}
							list.add(fd);
						}
					}
				}
			}
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}
}
