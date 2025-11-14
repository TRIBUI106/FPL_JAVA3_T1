package controller;

import java.io.IOException;
import java.util.List;

import entity.Country;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bai2")
public class Bai2 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Country> cList = List.of(
				new Country("VN", "Việt Nam"),
				new Country("CN", "China"),
				new Country("US", "United State")
				);
		req.setAttribute("countries", cList);
		req.getRequestDispatcher("bai2.jsp").forward(req, resp);
	}
	
}
