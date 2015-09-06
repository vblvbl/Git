package com.shiliangyu.serviceutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MySharedPreferences {

	public static void loadCityInfo(Context ct, String city) {
		SharedPreferences sp = ct.getSharedPreferences("cityconfig",
				Context.MODE_PRIVATE);
		Editor et = sp.edit();
		et.putString("city", city);
		et.commit();
	}

	public static String readCityInfo(Context ct) {
		SharedPreferences sp = ct.getSharedPreferences("cityconfig",
				Context.MODE_PRIVATE);
		 return sp.getString("city", "±±¾©");
	}
}
