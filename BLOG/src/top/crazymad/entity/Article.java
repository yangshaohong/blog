package top.crazymad.entity;
/**
 * 
 * @author crazy_mad
 * 该类用于存储博客文章信息
 *
 */
public class Article {
	private String title = "";
	private int id = -1;
	private String md = "";				// MarkDown格式的正文
	private String PDate = "";			// 文章发布时间
	private String MDate = "";			// 文章最后修改时间
	private String abstract_ = "";		// 文章摘要
	private int category = -1;			// 文章分类
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMd() {
		return md;
	}
	public void setMd(String md) {
		this.md = md;
	}
	public String getPDate() {
		return PDate;
	}
	public void setPDate(String pDate) {
		PDate = pDate;
	}
	public String getMDate() {
		return MDate;
	}
	public void setMDate(String mDate) {
		MDate = mDate;
	}
	public String getAbstract_() {
		return abstract_;
	}
	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
}
