package controller.admin;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import dao.NewsletterDAO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Newsletter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

@WebServlet("/admin/newsletter")
public class NewsletterController extends HttpServlet {

    private final NewsletterDAO dao = new NewsletterDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            String email = req.getParameter("email");
            if (email != null && !email.trim().isEmpty()) {
                dao.delete(email);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/newsletter");
            return;
        }

        List<Newsletter> list = dao.getAll();
        req.setAttribute("listNewsletters", list);
        req.getRequestDispatcher("/admin/newsletter.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        String email = req.getParameter("email");
        String enabledStr = req.getParameter("enabled");

        boolean success = false;
        String message = "";

        if ("add".equals(action) && email != null && !email.trim().isEmpty()) {
            if (dao.exists(email)) {
                message = "Email này đã tồn tại!";
            } else {
                success = dao.insert(email.trim());
                message = success ? "Thêm email thành công!" : "Thêm thất bại!";
            }
        }

        else if ("toggle".equals(action) && email != null && enabledStr != null) {
            int enabled = "1".equals(enabledStr) ? 1 : 0;
            success = dao.toggle(email, enabled);
            message = success ? "Cập nhật trạng thái thành công!" : "Lỗi cập nhật!";
        }

        resp.getWriter().write(gson.toJson(new AjaxResponse(success, message)));
    }

    private static class AjaxResponse {
        boolean success;
        String message;
        AjaxResponse(boolean s, String m) {
            this.success = s;
            this.message = m;
        }
    }
}