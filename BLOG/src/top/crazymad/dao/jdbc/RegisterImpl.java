package top.crazymad.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterImpl extends JDBCBase {
	public boolean registerQuery(String account, String password) {
		String sql = "insert into user (account, password, head) values('" + account + "', '" + password + "', 'head.png')";

		System.out.println(sql);
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			int res = save(sql, null);
			System.out.println(res);
			if (res >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, ps, con);
		}
		return false;
	}
}
