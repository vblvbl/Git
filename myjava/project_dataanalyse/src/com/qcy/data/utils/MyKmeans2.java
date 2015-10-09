package com.qcy.data.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.likang.data.domain.Student;

public class MyKmeans2 {
	List<Student> studentlist = null;
	int clazz;
	List<Student>[] lists = null;
	int x = 0;
	int y = 0;

	public MyKmeans2(List<Student> ls, int clazz) {
		this.studentlist = ls;
		this.clazz = clazz;
		lists = new ArrayList[clazz];
	}

	public void chooseTwoPoint() {
		int i=0;
		while(i<clazz){
		Random random = new Random();
		Student student = studentlist.get(random.nextInt(studentlist.size()));
		x = student.getSex();
		y = student.getAge();
		}
	}

	public void firstKmeans() {

	}
}
