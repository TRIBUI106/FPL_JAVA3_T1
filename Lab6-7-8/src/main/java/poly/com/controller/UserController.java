package poly.com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import poly.com.dao.UserDao;
import poly.com.model.User;
import java.io.IOException;
import java.util.List;


@WebServlet({"/User", "/User/*"})
public class UserController extends HttpServlet {

    private UserDao dao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        if (action.contains("/edit")) {
            String id = req.getParameter("id");
            User u = UserDao.findById(id);
            req.setAttribute("userEdit", u);
        }

        List<User> list = UserDao.selectAll();
        req.setAttribute("users", list);
        req.getRequestDispatcher("/User/nguoidung.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        int role = Integer.parseInt(req.getParameter("role"));
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String sdt = req.getParameter("sdt");

        User u = new User(username, password, fullname, role, email, address, sdt);

        if ("add".equals(action)) {
            UserDao.insert(u);
        } else if ("update".equals(action)) {
            UserDao.update(u);
        } else if ("delete".equals(action)) {
            UserDao.delete(username);
        }

        resp.sendRedirect(req.getContextPath() + "/User");
    }
}