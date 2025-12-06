package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import jakarta.servlet.Filter;

import java.io.IOException;

import entity.User;

@WebFilter("/admin/*")
public class AdminAuthFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        HttpSession session = req.getSession(false);
        
        User u = (User) session.getAttribute("user");        
        boolean isAdmin = u.getRole();

        boolean isLoggedIn = (session != null && u != null);
        
        if (!isAdmin) {
            res.sendRedirect(req.getContextPath() + "/home"); 
            return;
        }
        
        // nếu k phải admin thì redirect vô news-management
        
        if (!isLoggedIn) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
    	// Cleanup 
    }
}