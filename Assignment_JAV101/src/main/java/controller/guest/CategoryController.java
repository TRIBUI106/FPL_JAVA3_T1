package controller.guest;

import dao.CategoryDAO;
import entity.Category;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryController extends HttpServlet {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // THÊM 4 DÒNG NÀY LÀ XONG LUÔN – LOAD MENU TỪ DB
        List<Category> categories = categoryDAO.getAll();
        request.getServletContext().setAttribute("categories", categories);

        String id = request.getParameter("id");

        Category cat = null;
        if (id != null) {
            cat = categoryDAO.findById(id);
        }

        if (cat == null) {
            cat = categoryDAO.findById("TT");
        }

        if (cat != null) {
            request.setAttribute("categoryName", cat.getName());
            request.setAttribute("categoryId", cat.getId());
        } else {
            request.setAttribute("categoryName", "Tin tức");
            request.setAttribute("categoryId", "TT");
        }

        request.getRequestDispatcher("/guest/category.jsp").forward(request, response);
    }
}