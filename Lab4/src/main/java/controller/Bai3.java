package controller;

import java.io.IOException;
import java.util.Arrays;

import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/bai3", "/user/reg"})
public class Bai3 extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("status", "new");
		req.getRequestDispatcher("/form.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = new User();
	    user.setUsername(req.getParameter("username"));
	    user.setPassword(req.getParameter("password"));
	    user.setGender(req.getParameter("gender"));
	    user.setHaveFamily(req.getParameter("isHaveFamily") != null);
	    user.setCountry(req.getParameter("country"));
	    user.setHobby(Arrays.asList(req.getParameterValues("hobby")));
	    user.setNote(req.getParameter("note"));

	    req.setAttribute("user", user);
	    req.setAttribute("status","user");
	    req.getRequestDispatcher("/form.jsp").forward(req, resp);
	}
	
}
