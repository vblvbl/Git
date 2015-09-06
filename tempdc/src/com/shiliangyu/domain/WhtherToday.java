package com.shiliangyu.domain;

import java.util.Map;

public class WhtherToday {
	String updateTime;
	String nowTemp;
	String nowFengLi;
	String nowShiDu;
	String nowFengXiang;
	String nowIcon;
	Map<String, String> zhiShuMap;

	public String getNowIcon() {
		return nowIcon;
	}

	public void setNowIcon(String nowIcon) {
		this.nowIcon = nowIcon;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getNowTemp() {
		return nowTemp;
	}

	public void setNowTemp(String nowTemp) {
		this.nowTemp = nowTemp;
	}

	public String getNowFengLi() {
		return nowFengLi;
	}

	public void setNowFengLi(String nowFengLi) {
		this.nowFengLi = nowFengLi;
	}

	public String getNowShiDu() {
		return nowShiDu;
	}

	public void setNowShiDu(String nowShiDu) {
		this.nowShiDu = nowShiDu;
	}

	public String getNowFengXiang() {
		return nowFengXiang;
	}

	public void setNowFengXiang(String nowFengXiang) {
		this.nowFengXiang = nowFengXiang;
	}

	public Map<String, String> getZhiShuMap() {
		return zhiShuMap;
	}

	public void setZhiShuMap(Map<String, String> zhiShuMap) {
		this.zhiShuMap = zhiShuMap;
	}

}
