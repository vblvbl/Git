package com.likang.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.likang.domain.FreshCard;

public class ExcelToDB {
	private Connection cn;
	private PreparedStatement ps;
	private Statement st;

	public void insert(FreshCard fd) {
		int count = fd.getFreshCount();
		try {
			cn = JdbcConnection.getJdbcConnection();
			String sql = "insert freshcount(time,freshcount,state) into values(?,?,?) ";
			ps = cn.prepareStatement(sql);
			ps.setString(1, fd.getFreshTime());
			ps.setInt(2, fd.getFreshCount());
			ps.setString(3, fd.getState());
			ps.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			JdbcConnection.freeConnection(cn, ps, null);

		}
	}

	public int find(FreshCard fd) {
		String num = fd.getNum();
		try {
			cn = JdbcConnection.getJdbcConnection();
			String sql = "select freshcount from fromexcel where num=?";
			ps = cn.prepareStatement(sql);
			ps.setString(1, num);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			JdbcConnection.freeConnection(cn, ps, null);
		}
		return 0;
	}

	public boolean creat(FreshCard fd) {
		try {
			cn = JdbcConnection.getJdbcConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "insert into fromexcel(time,cardid,name,duty,num,freshcount,state) values(?,?,?,?,?,?,?)";
		if (fd == null) {
			return false;
		}
		try {
			ps = cn.prepareStatement(sql);
			ps.setString(1, fd.getFreshTime());
			ps.setInt(2, fd.getCardId());
			ps.setString(3, fd.getName());
			ps.setString(4, fd.getDuty());
			ps.setString(5, fd.getNum());
			ps.setInt(6, fd.getFreshCount());
			ps.setString(7, fd.getState());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConnection.freeConnection(cn, ps, null);
		}
		return true;

	}
}
