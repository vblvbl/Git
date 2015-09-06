package com.shiliangyu.datebase;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ChooseDao {
	ChoosePostionSQL sp;

	public ChooseDao(Context ct) {
		sp = new ChoosePostionSQL(ct);
	}

	public void addPostion(int month, int postion) {
		SQLiteDatabase sd = sp.getWritableDatabase();
		sd.execSQL("insert into choose(month,postion) values (?,?)",
				new Object[] { month, postion });
		sd.close();
	}

	public void delPostion(int month, int postion) {
		SQLiteDatabase sd = sp.getWritableDatabase();
		sd.execSQL("delete from choose where month=? and postion=?",
				new Object[] { month, postion });
		sd.close();
	}

	public List findAll(int month) {
		SQLiteDatabase sd = sp.getReadableDatabase();
		Cursor cs = null;
		try {
			cs = sd.rawQuery("select * from choose where month=?",
					new String[] { month + "" });
		} catch (Exception e) {
			return null;
		}
		List flag = new ArrayList();
		while (cs.moveToNext()) {
			flag.add(Integer.parseInt(cs.getString(cs.getColumnIndex("postion"))));
		}
		cs.close();
		sd.close();
		return flag;
	}
}
