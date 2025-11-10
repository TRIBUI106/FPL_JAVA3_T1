package poly.com.controller.lab2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/lab2/bai2")
public class bai2_controller extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		req.setAttribute("message", "Welcome to FPT Polytechnic");
		Map<String, Object> map = new HashMap<>();
		map.put("fullname", "Nguyễn Văn Tèo");
		map.put("gender", "Male");
		map.put("country", "Việt Nam");
		req.setAttribute("user", map);
		
		req.getRequestDispatcher("/lab2/page2.jsp").forward(req, resp);
	}
	
}
