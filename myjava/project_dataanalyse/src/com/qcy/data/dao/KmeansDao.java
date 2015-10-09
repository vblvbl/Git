package com.qcy.data.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.likang.data.domain.Student;

public class KmeansDao {
	private Connection cn;
	private java.sql.PreparedStatement ps;
	private Statement st;

	public List<Student> findAll() {
		List<Student> studentList = new ArrayList<Student>();
		try {
			cn = JdbcConnection.getJdbcConnection();
			String sql = "select * from t_student";
			st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Student student = new Student(rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getInt(4), rs.getInt(5));
				studentList.add(student);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			JdbcConnection.freeConnection(cn, ps, null);
		}
		return studentList;
	}
}
