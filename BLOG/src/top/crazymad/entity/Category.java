package top.crazymad.entity;

/**
 * 
 * @author 21169
 * 该类用于储存文章类别信息
 *
 */

public class Category {	
	private int classId;				// 类别编号
	private String name;				// 类别名字		
	private int count;					// 本类文章数量
	
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCount() {
		return this.count;
	}
	
}
