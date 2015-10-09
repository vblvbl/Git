package login.likang.jtest;

import java.sql.Connection;
import java.sql.SQLException;

import login.likang.dao.JdbcConnection;

import org.junit.Assert;
import org.junit.Test;

public class JTest {
	@Test
	public void test_a() {
		Connection cn=null;
		try {
			cn = JdbcConnection.getJdbcConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(cn);

	}

}
