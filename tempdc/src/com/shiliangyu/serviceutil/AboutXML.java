package com.shiliangyu.serviceutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.os.Environment;
import android.util.Xml;

import com.shiliangyu.domain.WhtherForecast;
import com.shiliangyu.domain.WhtherToday;

public class AboutXML {

	/**
	 * 天气情况序列化成whthertoday.xml文件在手机sd卡上
	 * 
	 * @param wd
	 *            接受一个今天天气的javabean实例
	 * @throws IOException
	 */
	public static void todayXmlMemory(WhtherToday wd, Context ct)
			throws IOException {
		XmlSerializer ser = Xml.newSerializer();
		File memory = new File(ct.getFilesDir(), "wtoy.xml");
		if (memory.exists()) {
			memory.delete();
			memory.createNewFile();
		}
		FileOutputStream fl = new FileOutputStream(memory);
		ser.setOutput(fl, "utf-8");
		ser.startDocument("utf-8", true);
		ser.startTag(null, "todaywhther");
		ser.startTag(null, "updatetime");
		ser.text(wd.getUpdateTime());
		ser.endTag(null, "updatetime");
		ser.startTag(null, "temp");
		ser.text(wd.getNowTemp());
		ser.endTag(null, "temp");
		ser.startTag(null, "fengli");
		ser.text(wd.getNowFengLi());
		ser.endTag(null, "fengli");
		ser.startTag(null, "shidu");
		ser.text(wd.getNowShiDu());
		ser.endTag(null, "shidu");
		ser.startTag(null, "fengxiang");
		ser.text(wd.getNowFengXiang());
		ser.endTag(null, "fengxiang");
		ser.startTag(null, "icon");
		ser.text(wd.getNowIcon());
		ser.endTag(null, "icon");
		Map<String, String> zhiShuMap = wd.getZhiShuMap();
		Set<String> set = zhiShuMap.keySet();
		ser.startTag(null, "map");
		for (String s : set) {

			String v = zhiShuMap.get(s);
			ser.startTag(null, "zhishu");
			ser.text(s);
			ser.endTag(null, "zhishu");
			ser.startTag(null, "word");
			ser.text(v);
			ser.endTag(null, "word");

		}
		ser.endTag(null, "map");
		ser.endTag(null, "todaywhther");
		ser.endDocument();
		fl.close();
	}

	/**
	 * 天气情况序列化成whtherforecast.xml在手机sd卡上
	 * 
	 * @param wd
	 *            接受一个今后天气的javabean实例集合
	 * @throws IOException
	 */
	public static void forecastXmlMemory(List<WhtherForecast> ls, Context ct)
			throws IOException {
		XmlSerializer ser = Xml.newSerializer();
		File memory = new File(ct.getFilesDir(), "wfast.xml");
		if (memory.exists()) {
			memory.delete();
			memory.createNewFile();
		}
		FileOutputStream fl = new FileOutputStream(memory);

		ser.setOutput(fl, "utf-8");
		ser.startDocument("utf-8", true);
		ser.startTag(null, "forecast");
		for (WhtherForecast ins : ls) {
			ser.startTag(null, "forecastwhther");
			ser.startTag(null, "week");
			ser.text(ins.getWeek());
			ser.endTag(null, "week");
			ser.startTag(null, "maxtemp");
			ser.text(ins.getMaxTemp());
			ser.endTag(null, "maxtemp");
			ser.startTag(null, "mintemp");
			ser.text(ins.getMinTemp());
			ser.endTag(null, "mintemp");
			ser.startTag(null, "icon");
			ser.text(ins.getIcon());
			ser.endTag(null, "icon");
			ser.endTag(null, "forecastwhther");
		}
		ser.endTag(null, "forecast");
		ser.endDocument();
		fl.close();
	}

	/**
	 * 对本地whthertoday.xml文件的pull解析方法
	 * 
	 * @return 返回一个WhtherToday的实例
	 * @throws Exception
	 */
	public static WhtherToday pullTodayXml(Context ct) throws Exception {
		FileInputStream fi = new FileInputStream(new File(ct.getFilesDir(),
				"wtoy.xml"));
		XmlPullParser xp = Xml.newPullParser();
		int type = 0;
		xp.setInput(fi, "utf-8");
		type = xp.getEventType();
		WhtherToday today = null;
		Map<String, String> map = null;
		int i = 0;
		int j = 0;
		String[] keys = null;
		String[] values = null;
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_TAG:
				if ("todaywhther".equals(xp.getName()))
					today = new WhtherToday();
				else if ("updatetime".equals(xp.getName()))
					today.setUpdateTime(xp.nextText());
				else if ("temp".equals(xp.getName()))
					today.setNowTemp(xp.nextText());
				else if ("fengli".equals(xp.getName()))
					today.setNowFengLi(xp.nextText());
				else if ("shidu".equals(xp.getName()))
					today.setNowShiDu(xp.nextText());
				else if ("fengxiang".equals(xp.getName()))
					today.setNowFengXiang(xp.nextText());
				else if ("icon".equals(xp.getName()))
					today.setNowIcon(xp.nextText());
				else if ("map".equals(xp.getName())) {
					map = new HashMap<String, String>();
					keys = new String[10];
					values = new String[10];
				} else if ("zhishu".equals(xp.getName())) {

					keys[i] = xp.nextText();

				} else if ("word".equals(xp.getName())) {
					values[j] = xp.nextText();
					if (i < 9 && j < 9) {
						map.put(keys[i], values[i]);
						i++;
						j++;
					} else if (i == 9 && j == 9) {
						map.put(keys[i], values[i]);
						today.setZhiShuMap(map);
					}
				}
				break;
			case XmlPullParser.END_TAG:

				break;
			default:
				break;
			}

			type = xp.next();
		}

		return today;
	}

	/**
	 * 对本地whtherforecast.xml文件的pull解析方法
	 * 
	 * @return 返回一个WhtherForecast实例的集合
	 * @throws Exception
	 */
	public static List<WhtherForecast> pullForecastXml(Context ct)
			throws Exception {
		XmlPullParser xp = Xml.newPullParser();
		FileInputStream fi = new FileInputStream(new File(ct.getFilesDir(),
				"wfast.xml"));
		xp.setInput(fi, "utf-8");
		int type = xp.getEventType();
		List<WhtherForecast> ls = null;
		WhtherForecast wh = null;
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_TAG:
				if ("forecast".equals(xp.getName())) {
					ls = new ArrayList<WhtherForecast>();
				} else if ("forecastwhther".equals(xp.getName())) {
					wh = new WhtherForecast();
				} else if ("week".equals(xp.getName())) {
					wh.setWeek(xp.nextText());
				} else if ("maxtemp".equals(xp.getName())) {
					wh.setMaxTemp(xp.nextText());
				} else if ("mintemp".equals(xp.getName())) {
					wh.setMinTemp(xp.nextText());
				} else if ("icon".equals(xp.getName())) {
					wh.setIcon(xp.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				if ("forecastwhther".equals(xp.getName())) {
					ls.add(wh);
				}
				break;

			default:
				break;
			}

			type = xp.next();
		}

		return ls;
	}

	/**
	 * 对网络xml文件的特定pull解析方法
	 * 
	 * @return 返回一个WhtherToday的实例
	 * @throws Exception
	 */
	public static WhtherToday pullIOTodayXml(InputStream in) throws Exception {
		XmlPullParser xp = Xml.newPullParser();
		int type = 0;
		xp.setInput(in, "utf-8");
		type = xp.getEventType();
		WhtherToday today = null;
		int dayTemp = 0;
		int fengxiangTemp = 0;
		int i = 0;
		int j = 0;
		Map<String, String> map = null;
		String[] key = null;
		String[] value = null;
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_TAG:
				if ("resp".equals(xp.getName())) {
					today = new WhtherToday();
				} else if ("updatetime".equals(xp.getName())) {
					today.setUpdateTime(xp.nextText());
				} else if ("wendu".equals(xp.getName())) {
					String temp = xp.nextText() + "°";
					today.setNowTemp(temp);
				} else if ("fengli".equals(xp.getName())) {
					String fengli = "风力:" + xp.nextText();
					today.setNowFengLi(fengli);
				} else if ("shidu".equals(xp.getName())) {
					String shidu = "湿度:" + xp.nextText();
					today.setNowShiDu(shidu);
				} else if ("fengxiang".equals(xp.getName())) {
					if (fengxiangTemp == 0) {
						today.setNowFengXiang(xp.nextText());
						fengxiangTemp++;
					} else {
						break;
					}
				} else if ("type".equals(xp.getName())) {
					if (dayTemp == 0) {
						today.setNowIcon(xp.nextText());
					} else {
						break;
					}
				} else if ("zhishus".equals(xp.getName())) {
					map = new HashMap<String, String>();
					key = new String[10];
					value = new String[10];
				} else if ("name".equals(xp.getName())) {
					key[i] = xp.nextText();

				} else if ("detail".equals(xp.getName())) {
					value[j] = xp.nextText();
				}
				break;
			case XmlPullParser.END_TAG:
				if ("zhishu".equals(xp.getName())) {
					map.put(key[i], value[j]);
					if (i < 9 && j < 9) {
						i++;
						j++;
					} else if (i == 9 && j == 9) {
						map.put(key[9], value[9]);
						today.setZhiShuMap(map);
					}
				} else if ("day".equals(xp.getName())) {
					dayTemp++;
				}
				break;
			default:
				break;
			}
			type = xp.next();
		}

		return today;

	}

	/**
	 * 对网络xml文件的特定pull解析方法
	 * 
	 * @return 返回一个WhtherForecast实例的集合
	 * @throws Exception
	 */
	public static List<WhtherForecast> pullIOForecastXml(InputStream in)
			throws Exception {
		XmlPullParser xp = Xml.newPullParser();
		int type = 0;
		xp.setInput(in, "utf-8");
		int count = 1;
		int temp = 0;
		type = xp.getEventType();
		WhtherForecast forecast = null;
		List<WhtherForecast> ls = null;
		loop: while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_TAG:
				if ("forecast".equals(xp.getName())) {
					ls = new ArrayList<WhtherForecast>();
				} else if ("weather".equals(xp.getName())) {
					if (count < 5) {
						forecast = new WhtherForecast();
					} else {
						break;
					}
				} else if ("date".equals(xp.getName())) {
					if (count < 5) {
						String data = xp.nextText();
						String sb = new StringBuilder(data).reverse()
								.toString();
						String week = new StringBuilder(sb.substring(0, 3))
								.reverse().toString();
						forecast.setWeek(week);
					} else {
						break;
					}
				} else if ("high".equals(xp.getName())) {
					if (count < 5) {
						String highAll = xp.nextText();
						String[] s = highAll.split(" ");
						forecast.setMaxTemp(s[1]);
					} else {
						break;
					}
				} else if ("low".equals(xp.getName())) {
					if (count < 5) {
						String lowAll = xp.nextText();
						String[] s = lowAll.split(" ");
						forecast.setMinTemp(s[1]);
					} else {
						break;
					}
				} else if ("day".equals(xp.getName())) {
					temp = 1;
				} else if ("night".equals(xp.getName())) {
					temp = -1;
				} else if ("type".equals(xp.getName())) {
					if (temp > 0) {
						forecast.setIcon(xp.nextText());
					}
				}
				break;
			case XmlPullParser.END_TAG:
				if ("weather".equals(xp.getName())) {
					if (count < 5) {

						ls.add(forecast);
					}
					count++;
				}

				else if ("forecast".equals(xp.getName())) {
					break loop;
				}
				break;
			default:
				break;
			}
			type = xp.next();
		}
		return ls;
	}
}