package top.crazymad.dao;

import java.util.List;

import top.crazymad.entity.Article;

public interface AritcleListDao {
	List<Article> getArticleList();
	List<Article> getArticleListByPage(int page, int pageCount);
}
