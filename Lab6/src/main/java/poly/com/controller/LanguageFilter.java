package poly.com.controller;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter("/*")
public class LanguageFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        String lang = req.getParameter("lang");
        if (lang != null) {
            Locale locale = new Locale(lang);
            session.setAttribute("Lang", locale);
        } else if (session.getAttribute("Lang") == null) {
            session.setAttribute("Lang", Locale.forLanguageTag("vi"));
        }

        chain.doFilter(request, response);
    }
}
