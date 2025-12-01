package controller;

import dao.CategoryDAO;
import entity.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.util.List;

@WebServlet(name = "InitServlet", urlPatterns = "/init-app", loadOnStartup = 1)
public class InitServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        try {
            CategoryDAO dao = new CategoryDAO();
            List<Category> categories = dao.getAll();

            // Kiểm tra null an toàn
            if (categories == null || categories.isEmpty()) {
                System.out.println("CẢNH BÁO: Không có danh mục nào trong DB!");
            }

            getServletContext().setAttribute("categories", categories);

            System.out.println("========================================");
            System.out.println("INIT SERVLET CHẠY THÀNH CÔNG 100%");
            System.out.println("ĐÃ LOAD " + (categories != null ? categories.size() : 0) + " DANH MỤC VÀO MENU");
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("LỖI KHỦNG KHIẾP KHI KHỞI ĐỘNG INIT SERVLET:");
            e.printStackTrace();
        }
    }
}