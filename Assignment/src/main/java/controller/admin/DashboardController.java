package controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet({"/admin/dashboard", "/admin"})
public class DashboardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        
        if (uri.equals(contextPath + "/admin") || uri.equals(contextPath + "/admin/")) {
            response.sendRedirect(contextPath + "/admin/dashboard");
            return;
        }
        
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}