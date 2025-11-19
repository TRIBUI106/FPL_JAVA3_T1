package controller;

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
        req.getRequestDispatcher("/upload.jsp").forward(req, resp);
	}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy file từ input name="photo"
        Part photo = req.getPart("photo");

        // Lấy tên file upload lên
        String filename = photo.getSubmittedFileName();

        // Tạo đường dẫn lưu file
        String path = "/static/files/" + filename;

        // Lấy đường dẫn tuyệt đối (real path)
        String realPath = req.getServletContext().getRealPath(path);

        // Lưu file vào project
        photo.write(realPath);

        req.setAttribute("message", "Upload thành công: " + filename);
        req.getRequestDispatcher("/upload.jsp").forward(req, resp);
    }
	
}
