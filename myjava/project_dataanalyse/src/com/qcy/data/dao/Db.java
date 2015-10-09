package com.qcy.data.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {

	private static String dbname;
	private static String username;
	private static String password;
	private static String drivername;
	private static Connection conn;
	private static PreparedStatement pstm;
	private static Statement stmt = null;
	private static ResultSet rs = null;// 执行查询

	public Db() {
	}

	public Db(int type, String dbname, String username, String password)
			throws Exception {

		String drivername = "";
		if (type == 0) {
			drivername = "com.mysql.jdbc.Driver";
			dbname = "jdbc:mysql://localhost:3306/"
					+ dbname
					+ "?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
		} else if (type == 1) {
			drivername = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			dbname = "jdbc:sqlserver://localhost:1433;databaseName=" + dbname;
		} else if (type == 2) {
			drivername = "sun.jdbc.odbc.JdbcOdbcDriver";
			dbname = "jdbc:odbc:" + dbname;
		}
		Db.dbname = dbname;
		Db.username = username;
		Db.drivername = drivername;
		Db.password = password;

		Class.forName(drivername);
		conn = DriverManager.getConnection(dbname, username, password);
	}

	public static void getConnection() throws Exception {
		Class.forName(Db.drivername);
		if (conn == null)
			conn = DriverManager.getConnection(Db.dbname, Db.username,
					Db.password);
	}

	public static void close()// ////////关闭数据库的方法
	{
		try {
			if (conn != null)
				conn.close();
			if (pstm != null)
				pstm.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static ResultSet getRs(String sql) throws Exception {
		if (conn == null || conn.isClosed()) {
			getConnection();
		}
		try {
			System.out.println(sql);
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 执行增加 删除 修改
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static int executeMethod(String sql) throws Exception {
		int i = 0;
		if (conn == null || conn.isClosed()) {
			getConnection();
		}
		try {
			pstm = conn.prepareStatement(sql);
			i = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 创建数据库
	 * 
	 * @param createSql
	 * @param tableName
	 */
	public static int createTable(String createSql, String tableName) {
		int c=0;
		try {
			stmt = conn.createStatement();
			stmt.execute("drop table if exists " + tableName + " ;");
			c=stmt.executeUpdate(createSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public static void executeProc(String procname, String params) {
		try {
			if (conn == null || conn.isClosed()) {
				getConnection();
			}

			CallableStatement stmt = null;

			stmt = conn.prepareCall("{call " + procname + "(" + params + ")}");
			System.out.println("zhixingcunchuguocheng1-----------------"
					+ stmt.execute());

		} catch (Exception e) {
			System.out.println("hahad = " + e.toString());
		}
	}

	public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		Db.conn = conn;
	}

	public static PreparedStatement getPstm() {
		return pstm;
	}

	public static void setPstm(PreparedStatement pstm) {
		Db.pstm = pstm;
	}

	public static ResultSet getRs() {
		return rs;
	}

	public static void setRs(ResultSet rs) {
		Db.rs = rs;
	}

	public static String getDbname() {
		return dbname;
	}

	public static void setDbname(String dbname) {
		Db.dbname = dbname;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		Db.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Db.password = password;
	}

	public static String getDrivername() {
		return drivername;
	}

	public static void setDrivername(String drivername) {
		Db.drivername = drivername;
	}

}
