package top.crazymad.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import top.crazymad.dao.AritcleListDao;
import top.crazymad.entity.Article;

public class ArticleListDaoImpl extends JDBCBase implements AritcleListDao {
	public List<Article> getArticleList() {
		String sql = "select id, title, publish_date, modify_date, abstract from article";
		return queryArticle(sql, null);
	}
	public List<Article> getArticleListByPage(int page, int pageCount) {
		int begin = page * pageCount;
		String sql = "select id, title, publish_date, modify_date, abstract from article limit "
				+ begin + ", " + pageCount;
		return queryArticle(sql, null);
	}
	private List<Article> queryArticle(String sql, Object[] param) {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Article> articleList = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			rs = query(ps, param);
			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setTitle(rs.getString("title"));
				article.setPDate(rs.getString("publish_date"));
				article.setMDate(rs.getString("modify_date"));
				article.setAbstract_(rs.getString("abstract"));
				articleList.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, ps, con);
		}
		return articleList;
	}
}
