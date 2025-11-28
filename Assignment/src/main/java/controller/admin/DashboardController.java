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
        
        if ( (request.getContextPath() + "/" + request.getRequestURL()).equals("/admin") ) {
        	response.sendRedirect("/admin/dashboard");
        }
        
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}