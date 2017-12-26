package top.crazymad.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import top.crazymad.dao.jdbc.*;
import top.crazymad.entity.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class EditAction
 */
@WebServlet("/EditAction")
public class EditAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ArticleDaoImpl aritcleDaoImpl = new ArticleDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String strId = request.getParameter("blogid");
		Article article = new Article();

		if (strId != null) {
			int blogid = Integer.parseInt(strId); //strId
			article = aritcleDaoImpl.getArticleById(blogid);
			System.out.print(article.getTitle());
		}
		request.setAttribute("article", article);
		response.setCharacterEncoding("UTF-8");
		request.getRequestDispatcher("/views/edit.jsp").forward(request,response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session.isNew() == true || session.getAttribute("islogin").equals("true") == false) {
			return;
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer("");
		String temp;
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		String jsonStr = sb.toString();
		
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject json = (JSONObject)obj;
		int id = Integer.parseInt((String) json.get("blogid"));
		Article article = new Article();
		article.setTitle((String) json.get("title"));
		article.setMd((String) json.get("text"));
		article.setId(id);
		article.setAbstract_((String) json.get("abstract_"));
		boolean res = false;
		if (id < 0) {
			res = aritcleDaoImpl.newArticle(article);		
		} else {
			res = aritcleDaoImpl.updateArticle(article);
		}
		System.out.println(res);
	}
	
}	
