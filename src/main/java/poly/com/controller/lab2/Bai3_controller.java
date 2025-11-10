package poly.com.controller.lab2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/lab2/bai3")
public class Bai3_controller extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("fullname", "Trí Vui Vẻ");
		map.put("gender", true);
		map.put("country", "CN");
		req.setAttribute("user", map);
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

