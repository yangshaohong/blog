package top.crazymad.dao;

import top.crazymad.entity.Article;

public interface ArticleDao {
	Article getArticleById(int blogid);
	boolean newArticle(Article article);
	boolean updateArticle(Article article);
}
