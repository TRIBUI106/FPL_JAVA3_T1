package controller.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Giáº£ láº­p DB (sáº½ thay tháº­t á»Ÿ giai Ä‘oáº¡n 3)
        if ("admin".equals(username) && "admin".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", new User("ADMIN", "Quản Trị Viên", true));
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else if ("pv001".equals(username) && "123".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", new User("PV001", "Nguyễn Văn A", false));
            response.sendRedirect(request.getContextPath() + "/admin/news");
        } else {
            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
        }
    }
}

// Class táº¡m
class User {
    String id, fullname;
    boolean isAdmin;
    public User(String id, String fullname, boolean isAdmin) {
        this.id = id; this.fullname = fullname; this.isAdmin = isAdmin;
    }
    public String getFullname() { return fullname; }
    public boolean isAdmin() { return isAdmin; }
}