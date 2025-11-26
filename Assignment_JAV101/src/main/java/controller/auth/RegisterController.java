package controller.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import dao.RegisterDAO;

@WebServlet({"/login", "/register"})
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/login")) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else if (uri.endsWith("/register")) {
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        } else if (uri.endsWith("/logout")) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/register")) {
            // Lấy dữ liệu từ form
            String id = req.getParameter("id");
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            String email = req.getParameter("email");

            // Gọi DAO/service để đăng ký
            RegisterDAO dao = new RegisterDAO();
            boolean success = dao.register(id, password, name, email);

            if (success) {
                // Đăng ký thành công → chuyển sang login
                req.setAttribute("message", "Đăng ký thành công, vui lòng đăng nhập!");
                req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
            } else {
                // Đăng ký thất bại (id trùng, lỗi DB, …)
                req.setAttribute("error", "ID đã tồn tại hoặc đăng ký thất bại!");
                req.getRequestDispatcher("/auth/register.jsp").forward(req, resp);            }
        }
    }
}

