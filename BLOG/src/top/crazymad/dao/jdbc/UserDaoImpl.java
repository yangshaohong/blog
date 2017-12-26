package top.crazymad.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import top.crazymad.dao.ArticleDao;
import top.crazymad.dao.UserDao;
import top.crazymad.entity.Article;
import top.crazymad.entity.User;

public class UserDaoImpl extends JDBCBase implements UserDao {
	public User getUserByAccount(String account) {
		String sql = "select * from user where account='" + account + "'";
		return queryUser(sql, null);
	}
	private User queryUser(String sql, Object[] param) {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = new User();
		try {
			ps = con.prepareStatement(sql);
			rs = query(ps, param);
			if (rs.next()) {
				user.setAccount(rs.getString("account"));
				System.out.println("account:" + user.getAccount());
				user.setPassword(rs.getString("password"));
				System.out.println("password:" + user.getPassword());
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, ps, con);
		}
		return user;
	}
}
