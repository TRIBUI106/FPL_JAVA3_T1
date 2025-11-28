package controller.guest;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/detail")
public class DetailController extends HttpServlet {

    // Dữ liệu mẫu 10 tin (mày thêm thoải mái)
    private static final String[][] SAMPLE_NEWS = {
        {"1", "TP.HCM mưa lớn, nhiều tuyến đường ngập sâu", 
         "Nội dung chi tiết về mưa lũ ở Sài Gòn hôm nay...<br><br>Đường Nguyễn Hữu Cảnh ngập sâu 0.5m, người dân khổ sở...", 
         "https://via.placeholder.com/800x450/0066cc/ffffff?text=Mua+Lon+TP.HCM", "24/11/2025"},
         
        {"2", "Việt Nam vô địch SEA Games 33", 
         "Tuyển Việt Nam đã xuất sắc đánh bại Thái Lan 3-1 trong trận chung kết...", 
         "https://via.placeholder.com/800x450/ffcc00/000000?text=Vang+Bong+SEA+Games", "23/11/2025"},
         
        {"3", "Giá xăng giảm lần thứ 5 liên tiếp", 
         "Từ 15h hôm nay, xăng RON 95 giảm 500 đồng/lít...", 
         "https://via.placeholder.com/800x450/009933/ffffff?text=Xang+Giam+Gia", "22/11/2025"},
         
        {"4", "Bão số 8 sắp vào Biển Đông", 
         "Dự báo bão sẽ mạnh cấp 12, giật cấp 15...", 
         "https://via.placeholder.com/800x450/cc0000/ffffff?text=Bao+So+8", "21/11/2025"},
         
        {"5", "iPhone 18 ra mắt với camera 200MP", 
         "Apple chính thức công bố iPhone mới nhất...", 
         "https://via.placeholder.com/800x450/333333/ffffff?text=iPhone+18", "20/11/2025"}
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Tìm tin theo id
        String[] news = null;
        for (String[] n : SAMPLE_NEWS) {
            if (n[0].equals(id)) {
                news = n;
                break;
            }
        }

        if (news == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Đưa dữ liệu vào request
        request.setAttribute("newsId", news[0]);
        request.setAttribute("title", news[1]);
        request.setAttribute("content", news[2]);
        request.setAttribute("image", news[3]);
        request.setAttribute("date", news[4]);

        // LƯU VÀO COOKIE 5 TIN GẦN NHẤT
        saveToRecentCookie(request, response, news);

        request.getRequestDispatcher("/guest/detail.jsp").forward(request, response);
    }

    private void saveToRecentCookie(HttpServletRequest request, HttpServletResponse response, String[] news) {
        String data = news[0] + "|" +
                      URLEncoder.encode(news[1], StandardCharsets.UTF_8) + "|" +
                      news[3];

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
        c.setMaxAge(30 * 24 * 60 * 60);
        c.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
        response.addCookie(c);
    }
}