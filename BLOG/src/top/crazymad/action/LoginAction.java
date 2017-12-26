package top.crazymad.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import top.crazymad.dao.jdbc.UserDaoImpl;
import top.crazymad.entity.User;
import top.crazymad.dao.jdbc.ArticleListDaoImpl;

import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * Servlet implementation class LoginAction
 */
@WebServlet("/LoginAction")
public class LoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDaoImpl userDaoImpl = new UserDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginAction() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String data = getData(request);
		System.out.println(data);
		JSONObject json = null;
		try {
			json = (JSONObject)(new JSONParser().parse(data));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String account = (String) json.get(new String("account"));
		String password = (String) json.get(new String("password"));
		User user = userDaoImpl.getUserByAccount(account);
		if (user == null) {									// 该账号不存在
			response.getWriter().write("nouser");
		} else if (user.getPassword().equals(password)) {	// 密码正确	
			HttpSession session = request.getSession();
			session.setAttribute("islogin", "true");
			System.out.println(session.isNew());
			response.getWriter().write("success");
		} else {											// 密码错误
			response.getWriter().write("errpassword");
		}
	}

	private String getData(HttpServletRequest request) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer("");
		String temp;
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		return sb.toString();
	}
}
