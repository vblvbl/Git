package com.likang.data.domain;

public class Student {
	private int id;
	private String number;
	private String name;
	private int age;
	private int sex;

	public Student() {

	}

	public Student(int id, String number, String name, int age, int sex) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

}
