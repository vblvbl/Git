package com.wml.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcConnection {
	static {

		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getJdbcConnection() throws SQLException {
		return DriverManager.getConnection(Constant.URL, Constant.USERNAME,
				Constant.PASSWORD);
	}

	public static void freeConnection(Connection cn, PreparedStatement ps,
			Statement st) {
		if (ps != null && st == null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (cn != null) {
					try {
						cn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else if (st != null && ps == null) {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (cn != null) {
					try {
						cn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}
}

