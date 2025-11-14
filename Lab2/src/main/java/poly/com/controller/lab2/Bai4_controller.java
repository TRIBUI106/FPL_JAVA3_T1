package poly.com.controller.lab2;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.com.entity.User;

@WebServlet("/lab2/bai4")
public class Bai4_controller extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User bean = new User();
		bean.setFullname("Hậu Đang Khóc");
		bean.setGender(true);
		bean.setCountry("US");
		req.setAttribute("user", bean);
		req.setAttribute("editabled", true);
		req.getRequestDispatcher("/lab2/form.jsp").forward(req, resp);
	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fullname = req.getParameter("fullname");
		System.out.println(fullname);
		req.getRequestDispatcher("/lab2/form.jsp").forward(req, resp);
	
	}
	
}
