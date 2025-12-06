package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import jakarta.servlet.Filter;

import java.io.IOException;

@WebFilter("/admin/*")
public class AdminAuthFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo filter nếu cần
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        // Lấy session
        HttpSession session = req.getSession(false);
        
        // Kiểm tra đã đăng nhập chưa
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        
        if (!isLoggedIn) {
            // Chưa đăng nhập -> redirect về trang login
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        // Kiểm tra role admin
        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            res.sendRedirect(req.getContextPath() + "/home"); 
            return;
        }
        
        // Nếu OK -> cho đi tiếp
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // Cleanup nếu cần
    }
}