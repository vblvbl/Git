package com.shiliangyu.datebase;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shiliangyu.domain.DayEvent;

public class DayEventDao {
	private DayEventSQL ds;

	public DayEventDao(Context context) {
		ds = new DayEventSQL(context);
	}

	public void addEvent(int month, int postion, String beginTime,
			String endTime, String actName, String actAddress, String actContent) {
		SQLiteDatabase sd = ds.getWritableDatabase();
		sd.execSQL(
				"insert into day_event(month,postion,beginTime,endTime,actName,actAddress,actContent) values (?,?,?,?,?,?,?)",
				new Object[] { month, postion, beginTime, endTime, actName,
						actAddress, actContent });
		sd.close();
	}

	public void delEvent(int month, int postion) {
		SQLiteDatabase sd = ds.getWritableDatabase();
		sd.execSQL("delete from day_event where month=? and postion=?",
				new Object[] { month, postion });
		sd.close();
	}

	public List<DayEvent> readEvent(int month, int postion) {
		SQLiteDatabase sd = ds.getReadableDatabase();
		Cursor cs;
		List<DayEvent> ls = new ArrayList<DayEvent>();
		try {
			cs = sd.rawQuery(
					"select * from day_event where month=? and postion=?",
					new String[] { month + "", postion + "" });
		} catch (Exception e) {
			return null;
		}
		while (cs.moveToNext()) {
			DayEvent de = new DayEvent();
			de.setBeginTime(cs.getString(cs.getColumnIndex("beginTime")));
			de.setEndTime(cs.getString(cs.getColumnIndex("endTime")));
			de.setActName(cs.getString(cs.getColumnIndex("actName")));
			de.setActAddress(cs.getString(cs.getColumnIndex("actAddress")));
			de.setActContent(cs.getString(cs.getColumnIndex("actContent")));
			ls.add(de);
		}
		cs.close();
		sd.close();
		return ls;
	}
}
