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

        HttpSession session = request.getSession();
        if (session.getAttribute("auth") != null) {
            response.sendRedirect(request.getContextPath() + "/guest/home.jsp");
            return;
        }
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("username");
        String password = request.getParameter("password");

        if (id != null) {
            id = id.trim();
        }

        try {
            User user = loginDAO.login(id, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);   // dashboard.jsp đang dùng ${sessionScope.user.xxx}

                // ĐĂNG NHẬP THÀNH CÔNG → CHUYỂN LUÔN VÀO DASHBOARD ADMIN
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                return;
            } else {
                request.setAttribute("error", "Sai ID hoặc mật khẩu!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Hệ thống đang gặp sự cố, vui lòng thử lại sau!");
        }

        // Đăng nhập thất bại → quay lại trang login
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }
}
	
	
	
	
	
	
	
	
	
	
	
	
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//
//        // Giáº£ láº­p DB (sáº½ thay tháº­t á»Ÿ giai Ä‘oáº¡n 3)
//        if ("admin".equals(username) && "admin".equals(password)) {
//            HttpSession session = request.getSession();
//            session.setAttribute("user", new User("ADMIN", "Quản Trị Viên", true));
//            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
//        } else if ("pv001".equals(username) && "123".equals(password)) {
//            HttpSession session = request.getSession();
//            session.setAttribute("user", new User("PV001", "Nguyễn Văn A", false));
//            response.sendRedirect(request.getContextPath() + "/admin/news");
//        } else {
//            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
//            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
//        }
//    }


// Class táº¡m
//class User {
//    String id, fullname;
//    boolean isAdmin;
//    public User(String id, String fullname, boolean isAdmin) {
//        this.id = id; this.fullname = fullname; this.isAdmin = isAdmin;
//    }
//    public String getFullname() { return fullname; }
//    public boolean isAdmin() { return isAdmin; }
//}