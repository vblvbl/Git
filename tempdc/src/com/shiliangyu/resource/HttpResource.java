package com.shiliangyu.resource;

public class HttpResource {
	public final static String beautifulGirl ="http://www.bz55.com/uploads1/allimg/120206/1_120206115858_1.jpg";
	public final static String p2p ="http://gdown.baidu.com/data/wisegame/77cea56db3195019/p2psearcher_7.apk";

	public static String getCityHttpUrl(String city) {
		if (city == "����") {
			return "http://wthrcdn.etouch.cn/WeatherApi?city=%E5%8C%97%E4%BA%AC";
		} else if (city == "���") {
			return "http://wthrcdn.etouch.cn/WeatherApi?city=%E5%A4%A9%E6%B4%A5";
		} else if (city == "�Ϻ�") {
			return "http://wthrcdn.etouch.cn/WeatherApi?city=%E4%B8%8A%E6%B5%B7";
		} else if (city == "�Ͼ�") {
			return "http://wthrcdn.etouch.cn/WeatherApi?city=%E5%8D%97%E4%BA%AC";
		} else if (city == "�ൺ") {
			return "http://wthrcdn.etouch.cn/WeatherApi?city=%E9%9D%92%E5%B2%9B";
		} else if (city == "����") {
			return "http://wthrcdn.etouch.cn/WeatherApi?city=%E5%B9%BF%E5%B7%9E";
		} else if (city == "ͭ��") {
			return "http://wthrcdn.etouch.cn/WeatherApi?city=%E9%93%9C%E9%99%B5";
		} else if (city == "����") {
			return "http://wthrcdn.etouch.cn/WeatherApi?city=%E5%8E%A6%E9%97%A8";
		} else if (city == "����") {
			return "http://wthrcdn.etouch.cn/WeatherApi?city=%E6%B2%88%E9%98%B3";
		} else if (city == "����") {
			return "http://wthrcdn.etouch.cn/WeatherApi?city=%E6%B7%B1%E5%9C%B3";
		} else if (city == "�Ϸ�") {
			return "http://wthrcdn.etouch.cn/WeatherApi?city=%E5%90%88%E8%82%A5";
		}

		return null;
	}
}
