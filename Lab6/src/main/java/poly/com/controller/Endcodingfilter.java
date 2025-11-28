package poly.com.controller;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

/**
 * Lớp EncodingFilter dùng để thiết lập UTF-8 cho toàn bộ ứng dụng web
 * Giúp xử lý tiếng Việt đúng, không bị lỗi font khi gửi và nhận dữ liệu
 */
@WebFilter("/*") // Áp dụng cho tất cả request
public class Endcodingfilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Thiết lập mã hóa tiếng Việt cho tất cả request/response
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        // Cho phép request đi tiếp
        chain.doFilter(request, response);
    }
}
