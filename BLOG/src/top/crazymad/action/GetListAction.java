package top.crazymad.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.*;

import top.crazymad.dao.jdbc.ArticleListDaoImpl;
import top.crazymad.entity.Article;

/**
 * Servlet implementation class GetList	
 */
@WebServlet("/GetList")
public class GetListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ArticleListDaoImpl articleListDaoImpl = new ArticleListDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Article> articleList = articleListDaoImpl.getArticleList();
		JSONArray json = new JSONArray();
		int len = articleList.size();
		for (int i = 0; i < len; i++) {
			JSONObject item = new JSONObject();
			item.put("blogid", articleList.get(i).getId());
			item.put("title", articleList.get(i).getTitle());
			item.put("abstract_", articleList.get(i).getAbstract_());
			item.put("pdate", articleList.get(i).getPDate());
			item.put("mdate", articleList.get(i).getMDate());
			json.add(item);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
