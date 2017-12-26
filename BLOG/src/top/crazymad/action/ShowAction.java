package top.crazymad.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.crazymad.dao.jdbc.*;
import top.crazymad.entity.*;

/**
 * Servlet implementation class ShowAction
 */
@WebServlet("/ShowAction")
public class ShowAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ArticleDaoImpl aritcleDaoImpl = new ArticleDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String strId = request.getParameter("blogid");
		int blogid = Integer.parseInt(strId); //strId
		
		Article article = aritcleDaoImpl.getArticleById(blogid);
		System.out.print(article.getTitle());
		request.setAttribute("article", article);
		response.setCharacterEncoding("UTF-8");
		request.getRequestDispatcher("/views/show.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
