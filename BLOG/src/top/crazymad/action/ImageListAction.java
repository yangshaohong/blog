package top.crazymad.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class ImageListAction
 * 该类用于获取imgae文件夹下的文件列表或者确认该目录下是否有某个文件的存在
 */
@WebServlet("/ImageListAction")
public class ImageListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageListAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			response.setCharacterEncoding("UTF-8");
			JSONArray json = new JSONArray();
			String confirm = request.getParameter("confirm");
			String dirPath = request.getSession().getServletContext().getRealPath("/images/blog");
			System.out.println("目录:" + dirPath);
			File dir = new File(dirPath);
			File[] fileList = dir.listFiles();
			int len = fileList.length;
			if (confirm == null) {						// 获取文件（image）列表
				System.out.println("获取文件列表");
				for (int i = 0; i < len; i++) {
					JSONObject item = new JSONObject();
					String filename = fileList[i].toString();
					filename = filename.substring(filename.lastIndexOf("\\")+1);
					item.put("filename", filename);
					System.out.println(filename);
					json.add(item);
				}
				
				response.getWriter().write(json.toString());
			} else {									// 确认某文件（image）是否存在
				System.out.println("文件数量：" + dir.length());
				for (int i = 0; i < len; i++) {
					if (confirm.equals(fileList[i])) {
						response.getWriter().write("yes");
					}
				}
				response.getWriter().write("no");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}
