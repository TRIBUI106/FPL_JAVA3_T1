package controller.guest;

import dao.CategoryDAO;
import entity.Category;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/category")
@MultipartConfig
public class CategoryController extends HttpServlet {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        Category cat = null;
        if (id != null && !id.trim().isEmpty()) {
            cat = categoryDAO.findById(id);
        }
        if (cat == null) {
            cat = categoryDAO.findById("TT"); // mặc định Tin tức tổng hợp
        }

        request.setAttribute("categoryName", cat.getName());
        request.setAttribute("categoryId", cat.getId());

        request.getRequestDispatcher("/guest/category.jsp").forward(request, response);
    }
}