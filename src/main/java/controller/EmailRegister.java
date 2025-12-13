package controller;

import java.io.IOException;
import dao.NewsletterDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/notifyRegister")
public class EmailRegister extends HttpServlet {

	private final NewsletterDAO dao = new NewsletterDAO();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		
		HttpSession session = req.getSession();
		
		try {
			int result = dao.regEmail(email);
			
			if (result > 0) {
				// Thành công
				session.setAttribute("toastType", "success");
				session.setAttribute("toastMessage", "Đăng ký thành công! Cảm ơn bạn đã theo dõi.");
			} else {
				// Thất bại
				session.setAttribute("toastType", "error");
				session.setAttribute("toastMessage", "Đăng ký thất bại. Vui lòng thử lại.");
			}
		} catch (Exception e) {
			// Lỗi (email trùng hoặc lỗi khác)
			e.printStackTrace();
			session.setAttribute("toastType", "error");
			session.setAttribute("toastMessage", "Email này đã được đăng ký rồi!");
		}
		
		// Redirect về trang trước đó
		String referer = req.getHeader("Referer");
		if (referer != null) {
			resp.sendRedirect(referer);
		} else {
			resp.sendRedirect(req.getContextPath() + "/home");
		}
	}

}