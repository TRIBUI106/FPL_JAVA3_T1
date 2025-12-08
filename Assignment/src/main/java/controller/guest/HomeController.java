package controller.guest;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import dao.NewsDAO;
import entity.News;

@WebServlet({"/", "/home"})
public class HomeController extends HttpServlet {
	private NewsDAO dao = new NewsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy danh sách tin có home = true
        List<News> homeNews = dao.getHomeNews();
        // Lấy 5 tin mới nhất
        List<News> latestNews = dao.getLatestNews();
        // Đặt tiêu đề trang
        request.setAttribute("pageTitle", "ABC News - Tin tức mới nhất");
        
        // Gửi danh sách tin ra JSP
        request.setAttribute("homeNews", homeNews);
        request.setAttribute("latestNews", latestNews);
        // Forward sang giao diện trang chủ
        request.getRequestDispatcher("/guest/home.jsp").forward(request, response);
    }
}