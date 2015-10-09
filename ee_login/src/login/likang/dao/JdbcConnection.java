package login.likang.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class JdbcConnection {

	private static String user = "root";
	private static String password = "123456";
	private static String url = "jdbc:mysql://localhost:3306/Login";

	static {

		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getJdbcConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
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
