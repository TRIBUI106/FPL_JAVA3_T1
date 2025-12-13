package controller.guest;

import dao.CategoryDAO;
import entity.Category;
import entity.News;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/category")
@MultipartConfig
public class CategoryController extends HttpServlet {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        Category cat = null;
        List<News> nList = null;
        if (id != null && !id.trim().isEmpty()) {
            cat = categoryDAO.findById(id);
            nList = categoryDAO.findNewsByCategoryId(id);
        }
        if (cat == null) {
            cat = categoryDAO.findById("TT"); // mặc định Tin tức tổng hợp
        }

        request.setAttribute("categoryName", cat.getName());
        request.setAttribute("categoryId", cat.getId());
        request.setAttribute("list", nList);

        request.getRequestDispatcher("/guest/category.jsp").forward(request, response);
    }
}