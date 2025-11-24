package poly.com.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import poly.com.utils.Cookies;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = Cookies.get("username1", request);
		String password = Cookies.get("password1", request);
		request.setAttribute("username", username);
		request.setAttribute("password", password);
		
		request.getRequestDispatcher("/views/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		
		if(!username.equals("abc")) {
			request.setAttribute("message", "Username invalid!");
		}
		else if(!password.equals("123")){
			request.setAttribute("message", "Password invalid!");
			
		}
		else {
			request.setAttribute("message", "Login success!");
			int hours = (remember==null)?0:1;
			Cookies.add("username1", username, hours, response);
			Cookies.add("password1", password, hours, response);
			
		}
		////// 
		request.setAttribute("username", username);
		request.setAttribute("password", password);
		/////
		
		request.getRequestDispatcher("/views/login.jsp").forward(request, response);
	}

}
