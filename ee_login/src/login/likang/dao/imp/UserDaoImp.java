package login.likang.dao.imp;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import login.likang.dao.JdbcConnection;
import login.likang.dao.UserDao;
import login.likang.domain.User;

public class UserDaoImp implements UserDao {
	private Connection cn;
	private java.sql.PreparedStatement ps;
	private Statement st;


	@Override
	public boolean creat(User user) {
		try {
			cn = JdbcConnection.getJdbcConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "insert into user(username,password,email,nickname,birthday) values(?,?,?,?,?)";
		if (user == null) {
			return false;
		}
		try {
			ps = cn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getNickname());
			Date date = user.getBirthday();
			ps.setDate(5, new java.sql.Date(date.getTime()));
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConnection.freeConnection(cn, ps, null);
		}
		return true;

	}

	@Override
	public boolean read(String username) {
		try {
			cn = JdbcConnection.getJdbcConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs = null;
		String sqlwords = "select username from user";
		boolean flag = false;
		try {
			st = cn.createStatement();
			rs = st.executeQuery(sqlwords);
			while (rs.next()) {
				if (username.equals(rs.getString(1))) {
					flag = true;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
//				JdbcConnection.freeConnection(cn, null, st);
			}
		}
		return flag;
	}

	@Override
	public boolean update(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(User user) {
		try {
			cn = JdbcConnection.getJdbcConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sqlwords = "delete from user where username=?";
		try {
			ps = cn.prepareStatement(sqlwords);
			ps.setString(1, user.getUsername());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConnection.freeConnection(cn, ps, null);
		}
		return true;
	}

	@Override
	public User loginAsk(String username, String password) {
		try {
			cn = JdbcConnection.getJdbcConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sqlwords = "select email,nickname,birthday from user where username=? and password=?";
		ResultSet rs = null;
		User user = null;
		try {
			ps = cn.prepareStatement(sqlwords);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			while (rs.next()) {
				user = new User(username, password, rs.getString(1),
						rs.getString(2),new Date(rs.getDate(3).getTime()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JdbcConnection.freeConnection(cn, ps, null);
			}
		}
		return user;
	}

}
