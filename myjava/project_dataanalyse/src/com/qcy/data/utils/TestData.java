package com.qcy.data.utils;

import com.qcy.data.dao.Db;

public class TestData {

	public static void main(String[] args) throws Exception {
		new Db(0,"demo","root","111111");
		String sql="";
		/*int[] age=new int[40];
		for (int i = 0; i < age.length; i++) {
			 age[i]=10+i;
		}
		Random rand=new Random();
		
		for (int i=11;i<=10000;i++) {
			int z=rand.nextInt(40);
			sql="insert into t_student(age,name,number,sex) values("+age[z]+",'name"+i+"','00"+i+"',"+rand.nextInt(2)+")";
			Db.executeMethod(sql);
		}*/
		sql="create table ex2("+
			"ID INT,"+ 
			"nc VARCHAR(200),"+ 
			"Z  VARCHAR(200))";
	
	
		Db.createTable(sql,"ex2");
	}
}
