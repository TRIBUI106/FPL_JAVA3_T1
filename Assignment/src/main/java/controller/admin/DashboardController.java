package controller.admin;
import jakarta.servlet.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import dao.NewsDAO;
import dao.CategoryDAO;
import dao.UserDAO;
import dao.NewsletterDAO;

@WebServlet({"/admin/dashboard", "/admin/logout", "/admin"})
public class DashboardController extends HttpServlet {
    
    private final NewsDAO newsDAO = new NewsDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final UserDAO userDAO = new UserDAO();
    private final NewsletterDAO newsletterDAO = new NewsletterDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        
        if (uri.equals(contextPath + "/admin") || uri.equals(contextPath + "/admin/")) {
            response.sendRedirect(contextPath + "/admin/dashboard");
            return;
        }
        
        if (uri.contains("logout")) {
            request.getSession().removeAttribute("user");
            response.sendRedirect(contextPath + "/home");
            return;
        }
        
        // Lấy thống kê
        try {
            int totalNews = newsDAO.countAll();
            int totalCategories = categoryDAO.countAll();
            int totalUsers = userDAO.countAll();
            int totalNewsletter = newsletterDAO.countAll();
            
            request.setAttribute("totalNews", totalNews);
            request.setAttribute("totalCategories", totalCategories);
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalNewsletter", totalNewsletter);
            
        } catch (SQLException ex) {
        	ex.printStackTrace();        
    	} catch (Exception e) {
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}