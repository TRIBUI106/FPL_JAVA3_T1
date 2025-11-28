package controller;

import dao.CategoryDAO;
import entity.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.util.List;

/**
 * Servlet này chỉ có 1 nhiệm vụ duy nhất:
 * Load danh mục vào applicationScope khi Tomcat khởi động
 * → Menu header lúc nào cũng có, không cần check lung tung nữa!
 */
@WebServlet(urlPatterns = "/init-app", loadOnStartup = 1)
public class InitServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        CategoryDAO dao = new CategoryDAO();
        List<Category> categories = dao.getAll();

        // Đưa danh sách danh mục vào applicationScope
        getServletContext().setAttribute("categories", categories);

        System.out.println("========================================");
        System.out.println("KHỞI ĐỘNG ỨNG DỤNG THÀNH CÔNG!");
        System.out.println("ĐÃ LOAD " + categories.size() + " DANH MỤC VÀO MENU");
        System.out.println("========================================");
    }
}