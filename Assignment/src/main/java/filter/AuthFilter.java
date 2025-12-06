package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import jakarta.servlet.Filter;

import java.io.IOException;

import entity.User;

@WebFilter({"/admin/*", "/reporter/*"})
public class AuthFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        
        // 1. Kiểm tra đã login chưa
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return; 
        }
        
        // 2. Lấy user và check quyền
        User u = (User) session.getAttribute("user");
        boolean isAdmin = u.getRole();
        String requestURI = req.getRequestURI();
        
        // 3. Kiểm tra quyền theo đường dẫn
        if (requestURI.contains("/admin/")) {
            if (!isAdmin) {
                res.sendRedirect(req.getContextPath() + "/reporter"); 
                return;
            }
        } else if (requestURI.contains("/reporter/")) {
             if (isAdmin) {
                 res.sendRedirect(req.getContextPath() + "/admin/dashboard");
                 return;
             }
        }
        
        // 4. Cho đi tiếp
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // Cleanup 
    }
}