package controller.admin;

import entity.News;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import entity.Category;

import dao.NewsDAO;

@WebServlet("/admin/news")
@MultipartConfig // thêm annotation này để Tomcat parse multipart form
public class NewsController extends HttpServlet {
	private NewsDAO dao = new NewsDAO();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
		String action = req.getParameter("action");
		String id = req.getParameter("id");

		if ("delete".equals(action) && id != null) {
			dao.delete(id);
			resp.sendRedirect(req.getContextPath() + "/admin/news");
			return;
		}

		if ("edit".equals(action) && id != null) {
			News cat = dao.findById(id);
			req.setAttribute("cat", cat);
		}

		List<News> list = dao.getAll();
		req.setAttribute("listNews", list);

		List<Category> categories = dao.getAllCate();
		req.setAttribute("categories", categories);

		req.getRequestDispatcher("/admin/news.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		String id = req.getParameter("id");

		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String categoryId = req.getParameter("categoryId");
		boolean home = req.getParameter("home") != null;

		// Lấy file upload
		Part imagePart = req.getPart("image");
		String fileName = imagePart.getSubmittedFileName();
		// đường dẫn lưu file (ví dụ trong thư mục "uploads" của ứng dụng)
		String uploadPath = req.getServletContext().getRealPath("/") + "uploads";
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdir();
		imagePart.write(uploadPath + File.separator + fileName);

		News n = new News();
		n.setId(id);
		n.setTitle(title);
		n.setContent(content);
		n.setImage("uploads/" + fileName); // lưu đường dẫn file
		n.setPostedDate(Date.valueOf(LocalDate.now()));
		// Lấy user từ session
		User currentUser = (User) req.getSession().getAttribute("user");
		if (currentUser != null) {
			n.setAuthor(currentUser.getId());
		}
		n.setCategoryId(categoryId);
		n.setHome(home);

		String message;
		if ("update".equals(action)) {
			dao.update(n);
			message = "Cập nhật tin thành công!";
		} else {
			n.setViewCount(0);
			dao.insert(n);
			message = "Thêm mới tin \"" + title + "\" thành công!";
		}

		req.getSession().setAttribute("message", message);
		resp.sendRedirect(req.getContextPath() + "/admin/news");
	}
}
