package controller.auth;

import jakarta.servlet.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import dao.LoginDAO;
import entity.User;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private final LoginDAO loginDAO = new LoginDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // không tạo mới
        if (session != null && session.getAttribute("user") != null) {
            // đã login → redirect dashboard
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        // chưa login → show login page
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("username");
        String password = request.getParameter("password");

        if (id != null) id = id.trim();

        try {
            User user = loginDAO.login(id, password);

            if (user != null) {
            	
                HttpSession session = request.getSession(); // tạo session nếu chưa có
                session.setAttribute("user", user);

                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                
                return;
            } else {
                request.setAttribute("error", "Sai ID hoặc mật khẩu!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Hệ thống đang gặp sự cố, vui lòng thử lại sau!");
        }

        // login thất bại → show lại trang login
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }
}
