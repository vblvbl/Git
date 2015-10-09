package com.qcy.data.utils;

import java.util.List;

public class ListParseString {

	public static String[] LToStr(List<String> strList){
		
		String[] strArray=new String[strList.size()];
		int i=0;
		for (;i<strArray.length;i++) {
			strArray[i]=strList.get(i);
		}
		
		return strArray;
	}
}
