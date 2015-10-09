package com.wml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wml.domain.AdmBean;
import com.wml.domain.StudentBean;

public class AdmDao {
	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;
	public boolean CanLogin(AdmBean sb) {
		String num = sb.getNum();
		String passwd = sb.getPass();
		String sql = "select * from admin where num=? and passwd=?";
		try {
			connection = JdbcConnection.getJdbcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			preparedStatement.setString(2, passwd);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConnection.freeConnection(connection, preparedStatement, null);
		}

		return false;

	}

}
