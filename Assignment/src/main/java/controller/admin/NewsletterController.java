package controller.admin;

import java.io.IOException;
import java.util.List;

import dao.NewsLetterDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import entity.Newsletter;

@WebServlet("/admin/newsletter")
public class NewsletterController extends HttpServlet { 
	
	private final NewsLetterDAO dao = new NewsLetterDAO();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getParameter("action");
		
		if ("delete".equals(action)) {
			String email = req.getParameter("id");
			int rows = dao.deleteEmail(email);
		}

		List<Newsletter> list = dao.getAll();
		req.setAttribute("listNewsletters", list);
		req.getRequestDispatcher("/admin/newsletter.jsp").forward(req, resp);
	}

}
