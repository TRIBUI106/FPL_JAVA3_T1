package poly.com.controller.lab2;

import java.io.IOException;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/lab2/bai1")
public class Bai1_controller extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("message", "Welcome To FPT Polytechnic");
		req.setAttribute("now", new Date());
		req.getRequestDispatcher("/lab2/page.jsp").forward(req, resp);
	}
	
}
