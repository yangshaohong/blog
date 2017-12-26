package top.crazymad.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class SaveFile {
	private FileOutputStream out;
	private InputStream in;
	private File file;
	private DiskFileItemFactory factory;
	private ServletFileUpload upload;
	private String savePath;
	
	public SaveFile() {
		factory = new DiskFileItemFactory();
		upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
	}
	
	public boolean saveToHeadImage(HttpServletRequest request, FileItem item) throws IOException {
		savePath = request.getSession().getServletContext().getRealPath("/images/head");
		try {
			Save(request, item);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	public boolean saveToBlogImage(HttpServletRequest request, FileItem item) throws IOException {
		savePath = request.getSession().getServletContext().getRealPath("/images/blog");
		try {
			Save(request, item);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public ServletFileUpload getUpload() {
		return upload;
	}
	
	private boolean Save(HttpServletRequest request, FileItem item) throws IOException {
		file = new File(savePath);
		// 判断目录是否存在，不存在的话创建一个出来
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(savePath + "目录不存在，需要创建");
			file.mkdir();
		}
		String filename = item.getName();
		System.out.println(filename);
		if (filename == null || filename.trim().equals("")) {
			return false;
		}
		filename = filename.substring(filename.lastIndexOf("\\")+1);			// 处理文件名中的路径
		System.out.println(savePath + "\\" + filename);
		in = item.getInputStream();
		out = new FileOutputStream(savePath + "\\" + filename);
		byte buffer[] = new byte[1024];
		int len = 0;
		while ((len=in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
		
		return true;
	}
}
