package com.qcy.data.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	private static ResultSet rs;
	private static Statement stmt;
	private static Connection coon;
	private static String url = "jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	private static String username = "root";
	private static String password = "123456";
	private static String driverName = "com.mysql.jdbc.Driver";

	public JDBCUtil() {
		super();
	}

	static {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public static void close(Statement st, Connection conn) {
		if(st!=null){
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 修改表的类型
	 * @param procname
	 * @param params
	 */
	public static void updateTable(String tableName,String digtalName,String typeName){
		System.out.println(tableName+"--"+digtalName+"--"+typeName);
		String sql="alter table"+" "+tableName+" "+"modify "+digtalName+" "+typeName;
		try {
			coon=JDBCUtil.getConnection();
			stmt=coon.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("ok!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 测试
	 */
//	public static void updateTable(){
////		String sql=" alter table  "+table+"  modify "+digtal+" "+datatype;
//		String s=" alter table cc modify Z int";
//		try {
//			coon=JDBCUtil.getConnection();
//			stmt=coon.createStatement();
//			stmt.executeUpdate(s);
//			System.out.println("ok!");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	public static void main(String[] args ){
//		JDBCUtil.updateTable();
//	}
}

