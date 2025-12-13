package controller.guest;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import dao.NewsDAO;
import entity.News;

@WebServlet("/detail")
public class DetailController extends HttpServlet {
    private NewsDAO dao = new NewsDAO();
    
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        News n = dao.findById(id);
        if (n == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        request.setAttribute("news", n);
        
        //tăng vieecount ở đây
        dao.incrementViewCount(id);
        request.setAttribute("news", n);

        // Lưu cookie "recent_news" (dùng '#' làm delimiter thay vì ';')
        String data = n.getId() + "|" +
                      URLEncoder.encode(n.getTitle(), StandardCharsets.UTF_8) + "|" +
                      n.getImage();

        String old = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie ck : cookies) {
                if ("recent_news".equals(ck.getName())) {
                    old = ck.getValue();
                    break;
                }
            }
        }

        String[] arr = old.isEmpty() ? new String[0] : old.split("#");
        StringBuilder sb = new StringBuilder(data);

        for (String s : arr) {
        	if (!s.startsWith(n.getId() + "|")) {
                if ((sb.length() > 0 ? sb.toString().split("#").length : 0) < 4) { 
                    sb.append("#").append(s);
                }
            }
        }

        Cookie c = new Cookie("recent_news", sb.toString());
        c.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
        c.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
        response.addCookie(c);

        request.getRequestDispatcher("/guest/detail.jsp").forward(request, response);
    }

	private void saveToRecentCookie(HttpServletRequest request, HttpServletResponse response, String[] news) {
		String data = news[0] + "|" + URLEncoder.encode(news[1], StandardCharsets.UTF_8) + "|" + news[3];

		String old = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if ("recent_news".equals(c.getName())) {
					old = c.getValue();
					break;
				}
			}
		}

		String[] arr = old.isEmpty() ? new String[0] : old.split(";;");
		StringBuilder sb = new StringBuilder(data);

		for (String s : arr) {
			if (!s.startsWith(news[0] + "|") && sb.toString().split(";;").length < 5) {
				sb.append(";;").append(s);
			}
		}

		Cookie c = new Cookie("recent_news", sb.toString());
		c.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
		c.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
		response.addCookie(c);
	}
}
