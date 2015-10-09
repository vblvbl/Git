package com.likang;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.likang.daoimpl.ExcelToDB;
import com.likang.domain.FreshCard;
import com.likang.util.BeanFromExcel;

public class Start {

	public static void main(String[] args) {
		MyTimer mt = new MyTimer();
		new Timer().schedule(mt, 1000 * 60 * 2, 1000 * 60);
		// System.out.println(bf.getNewestFile());

	}
}

class MyTimer extends TimerTask {

	@Override
	public void run() {
		BeanFromExcel bf = new BeanFromExcel();
		List<FreshCard> list = bf.BeangetFromExcel();
		ExcelToDB db = new ExcelToDB();
		for (FreshCard ls : list) {
			int count = db.find(ls);
			if (count<1) {
				// System.out.println(ls.toString());
				db.creat(ls);
			}else{
				db.insert(ls);
			}
		}
	}

}