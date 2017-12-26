package top.crazymad.action;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import top.crazymad.utils.SaveFile;

/**
 * Servlet implementation class UploadImageAction
 */
@WebServlet("/UploadImageAction")
public class UploadImageAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SaveFile saveFile = new SaveFile();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadImageAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session.isNew() == true && session.getAttribute("islogin").equals("true") == false) {
			return;
		}
		if (!ServletFileUpload.isMultipartContent(request)) {	// 判断提交的是不是form表单
			return;
		}
		try {
			List<FileItem> list = saveFile.getUpload().parseRequest(request);
			for (FileItem item : list) {
				System.out.println("读取form表单中...");
				if (item.isFormField()) {						// 如果数据只是普通输入项
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					System.out.println(name+"="+value);
				} else { 										// 如果数据是文件数据
					boolean res = saveFile.saveToBlogImage(request, item);	// 将图片上传至图片文件夹
					if (res == true) {
						response.getWriter().write("success");
					} else {
						response.getWriter().write("fail");
					}
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
