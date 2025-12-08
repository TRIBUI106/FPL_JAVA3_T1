package poly.com.controller;
import poly.com.controller.*;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/admin.jsp","/Departments/*"})  // Có thể thêm nhiều trang hoặc thư mục cần bảo vệ
public class Aufilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (isLoggedIn) 
        {
            // Đã đăng nhập => cho phép vào trang vào controler
            chain.doFilter(request, response);
        }
        else {
            // Chưa đăng nhập => chuyển về login.jsp
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
