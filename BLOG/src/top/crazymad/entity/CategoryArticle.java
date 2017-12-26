package top.crazymad.entity;

public class CategoryArticle {
	private int articleId;						// 文章编号
	private int categorys;						// 类别集合（通过位数来判断）
	private String abstract_;					// 文章摘要
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public int getCategorys() {
		return categorys;
	}
	public void setCategorys(int categorys) {
		this.categorys = categorys;
	}
	public String getAbstract_() {
		return abstract_;
	}
	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}
}
