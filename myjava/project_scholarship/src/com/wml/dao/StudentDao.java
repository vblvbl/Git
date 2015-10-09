package com.wml.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wml.domain.StudentBean;

public class StudentDao {
	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;

	public void insertStudent(StudentBean studentBean) {
		String sql = "insert into student(num,passwd,name,inschool,academy,state)values(?,?,?,?,?,?)";
		try {
			connection = JdbcConnection.getJdbcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, studentBean.getNum());
			preparedStatement.setString(2, studentBean.getPasswd());
			preparedStatement.setString(3, studentBean.getName());
			Date sqlDate = new Date(studentBean.getInSchool().getTime());
			preparedStatement.setDate(4, sqlDate);
			preparedStatement.setString(5, studentBean.getAcademy());
			preparedStatement.setString(6, studentBean.getState());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConnection.freeConnection(connection, preparedStatement, null);
		}
	}

	public List<StudentBean> findAll() {
		String sql = "select * from student";
		List<StudentBean> list = new ArrayList<StudentBean>();
		try {
			connection = JdbcConnection.getJdbcConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				StudentBean studentBean = new StudentBean(
						resultSet.getString(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getDate(4),
						resultSet.getString(5), resultSet.getString(6));
				list.add(studentBean);
			}
			return list;
		} catch (Exception e) {

		} finally {
			JdbcConnection.freeConnection(connection, null, statement);
		}

		return null;

	}

	public List<StudentBean> findAll(String key, String value) {
		String sql = "select * from student where " + key + "=?";
		List<StudentBean> list = new ArrayList<StudentBean>();
		try {
			connection = JdbcConnection.getJdbcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, value);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				StudentBean studentBean = new StudentBean(
						resultSet.getString(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getDate(4),
						resultSet.getString(5), resultSet.getString(6));
				list.add(studentBean);
			}
			return list;
		} catch (Exception e) {

		} finally {
			JdbcConnection.freeConnection(connection, preparedStatement, null);
		}

		return null;

	}

	public String[] CanLogin(StudentBean sb) {
		String num = sb.getNum();
		String passwd = sb.getPasswd();
		String sql = "select name,state from student where num=? and passwd=?";
		try {
			connection = JdbcConnection.getJdbcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			preparedStatement.setString(2, passwd);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String[] cou = new String[2];
				cou[0] = resultSet.getString(1);
				cou[1] = resultSet.getString(2);
				return cou;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConnection.freeConnection(connection, preparedStatement, null);
		}

		return null;

	}

	public boolean deleteSudent(String numkey) {
		String sql = "delete from student where num=?";
		try {
			connection = JdbcConnection.getJdbcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, numkey);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConnection.freeConnection(connection, preparedStatement, null);
		}
		return false;
	}

	public void updateStudent(StudentBean studentBean) {
		String sql = "update student set passwd=?,name=?,inschool=?,academy=?,state=? where num=?";
		try {
			connection = JdbcConnection.getJdbcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, studentBean.getPasswd());
			preparedStatement.setString(2, studentBean.getName());
			Date date = new Date(studentBean.getInSchool().getTime());
			preparedStatement.setDate(3, date);
			preparedStatement.setString(4, studentBean.getAcademy());
			preparedStatement.setString(5, studentBean.getState());
			preparedStatement.setString(6, studentBean.getNum());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConnection.freeConnection(connection, preparedStatement, null);
		}
	}
}
