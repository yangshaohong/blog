package top.crazymad.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import top.crazymad.dao.jdbc.JDBCBase;
import top.crazymad.dao.jdbc.JDBCUtil;
import top.crazymad.entity.Article;
import top.crazymad.dao.ArticleDao;

public class ArticleDaoImpl extends JDBCBase implements ArticleDao {
	public Article getArticleById(int blogid) {
		String sql = "select * from article where id=" + blogid;
		System.out.println(sql);
		return queryArticle(sql, null);
	}

	private Article queryArticle(String sql, Object[] param) {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Article article = new Article();
		try {
			ps = con.prepareStatement(sql);
			rs = query(ps, param);
			/*
			 * while(rs.next()){ City city = Packager.packCity(rs); Integer pid
			 * = rs.getInt("parent_id"); if(pid != 0)
			 * city.setParentCity(getCityById(pid));
			 * citys.add(Packager.packCity(rs)); }
			 */
			if (rs.next()) {
				System.out.println(rs.getInt("id"));
				article.setId(rs.getInt("id"));
				article.setTitle(rs.getString("title"));
				article.setMd(rs.getString("md"));
				article.setPDate(rs.getString("publish_date"));
				article.setMDate(rs.getString("modify_date"));
				article.setAbstract_(rs.getString("abstract"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, ps, con);
		}
		return article;
	}
	public boolean newArticle(Article article) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date time = new java.util.Date();		
		String sql = "insert into article (title, md, abstract, publish_date, modify_date) "
				+ "values('" + article.getTitle() + "', "
						+ "'" + article.getMd() + "', "
								+ "'" + article.getAbstract_() + "', '"
										+ sdf.format(time) +"', '"
												+ sdf.format(time) + "')";
		
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
	public boolean updateArticle(Article article) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date time = new java.util.Date();		
		String sql = "update article set title='" + article.getTitle() 
				+ "', md='" + article.getMd()
					+ "', abstract='" + article.getAbstract_() 
						+ "', modify_date='" + sdf.format(time)
							+ "' where id=" + article.getId();
		
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
