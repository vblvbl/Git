package com.wml.domain;

import java.util.Date;

//姓名、学号、入学时间、所在学院等信息。
public class StudentBean {
	private String num;
	private String passwd;
	private String name;
	private Date inSchool;
	private String academy;
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public StudentBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentBean(String num, String passwd, String name, Date inSchool,
			String academy, String state) {
		super();
		this.name = name;
		this.num = num;
		this.passwd = passwd;
		this.inSchool = inSchool;
		this.academy = academy;
		this.state = state;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Date getInSchool() {
		return inSchool;
	}

	public void setInSchool(Date inSchool) {
		this.inSchool = inSchool;
	}

	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}

}
