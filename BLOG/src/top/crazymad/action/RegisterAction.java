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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import top.crazymad.dao.jdbc.RegisterImpl;
import top.crazymad.dao.jdbc.UserDaoImpl;
import top.crazymad.entity.User;

/**
 * Servlet implementation class RegisterAction
 */
@WebServlet("/RegisterAction")
public class RegisterAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static RegisterImpl registerImpl = new RegisterImpl();
	private static UserDaoImpl userDaoImpl = new UserDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterAction() {
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
		String code = (String) json.get(new String("code"));
		System.out.print(account + password + code);
		HttpSession session = request.getSession();
		String serverCode = (String) session.getAttribute("code");
		System.out.println(serverCode);
		if (serverCode.equals(code) == false) {
			response.getWriter().write("code error");
			System.out.println("code error " + code + " " + serverCode);
			return;
		}
		User user = userDaoImpl.getUserByAccount(account);
		if (user == null) {
			if (registerImpl.registerQuery(account, password) == true) {
				response.getWriter().write("success");
			}
		} else {
			response.getWriter().write("exist");
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
