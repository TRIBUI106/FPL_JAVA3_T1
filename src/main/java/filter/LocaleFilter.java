package filter;

import java.io.IOException;
import java.util.Locale;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.jstl.core.Config;

@WebFilter("/*")
public class LocaleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        String language = req.getParameter("lang");
        Locale locale = null;

        if (language != null) {
            // User vừa chọn ngôn ngữ mới
            locale = new Locale(language);
            
            // Lưu vào cookie (30 ngày)
            Cookie cookie = new Cookie("userLocale", language);
            cookie.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
            cookie.setPath("/"); // áp dụng cho toàn bộ app
            resp.addCookie(cookie);
            
        } else {
            // Thứ tự ưu tiên: Session -> Cookie -> Browser
            locale = (Locale) session.getAttribute("locale");
            
            if (locale == null) {
                // Đọc từ cookie
                Cookie[] cookies = req.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("userLocale".equals(cookie.getName())) {
                            locale = new Locale(cookie.getValue());
                            break;
                        }
                    }
                }
                
                // Nếu vẫn null thì lấy từ browser
                if (locale == null) {
                    locale = req.getLocale();
                    String lang = locale.getLanguage();
                    
                    // Validate: chỉ cho phép en hoặc vi
                    if (!"en".equals(lang) && !"vi".equals(lang)) {
                        locale = new Locale("vi"); // mặc định tiếng Việt
                    }
                }
            }
        }

        // Set vào session và JSTL config
        session.setAttribute("locale", locale);
        Config.set(session, Config.FMT_LOCALE, locale);

        chain.doFilter(request, response);
    }
}