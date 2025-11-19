package controller;

import java.io.File;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
@WebServlet({"/bai4", "/upload"})
public class Bai4 extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher("/upload.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    Part photo = req.getPart("photo");
	    String filename = photo.getSubmittedFileName();
	    
	    if ( filename.isBlank() || filename.isEmpty() ) {
		    req.setAttribute("message", "Bạn chưa chọn ảnh !");
		    req.getRequestDispatcher("/upload.jsp").forward(req, resp);
	    }
	    
	    // Lấy đường dẫn deploy hiện tại (Tomcat temp)
	    String appPath = req.getServletContext().getRealPath("/");  
	    // Thư mục upload trong webapp: static/files
	    String uploadDir = appPath + "static" + File.separator + "files";
	    File dir = new File(uploadDir);

	    File file = new File(dir, filename);
	    photo.write(file.getAbsolutePath());

	    req.setAttribute("message", "Upload thành công!");
	    req.setAttribute("filename", filename);
	    req.getRequestDispatcher("/upload.jsp").forward(req, resp);

	}

}
