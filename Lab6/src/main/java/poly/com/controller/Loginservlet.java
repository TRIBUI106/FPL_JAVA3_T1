package poly.com.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import poly.com.dao.UserDao;
import poly.com.model.User;
@WebServlet({"/login","/admin"})
public class Loginservlet extends HttpServlet 
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher("/login.jsp").forward(req, resp);
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Lấy dữ liệu từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Gọi DAO để kiểm tra
        UserDao dao = new UserDao();
        User user = dao.login(username, password);

        if (user != null) 
        {
            // Lưu vào session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Chuyển sang trang home.jsp
            response.sendRedirect("admin.jsp");
        }
        else {
            // Gửi thông báo lỗi
            request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
		
		
		
		
	}
	

