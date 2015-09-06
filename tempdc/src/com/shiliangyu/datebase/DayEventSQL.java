package com.shiliangyu.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DayEventSQL extends SQLiteOpenHelper {
	public DayEventSQL(Context context) {
		super(context, "dayevent.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table day_event(id integer primary key autoincrement,month varchar(20),postion varchar(20),beginTime varchar(20), endTime varchar(20), actName varchar(20), actAddress varchar(20), actContent varchar(20))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
