package controller.guest;

import dao.CategoryDAO;
import entity.Category;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet({"/category"}) 
@MultipartConfig
public class CategoryController extends HttpServlet {

    private static final CategoryDAO categoryDAO = new CategoryDAO();
    private static boolean initialized = false; 

    @Override
    public void init() throws ServletException {
        super.init();
        loadCategoriesToApplicationScope();
    }

   
    private synchronized void loadCategoriesToApplicationScope() {
        if (initialized) return; 

        ServletContext app = getServletContext();
        List<Category> categories = categoryDAO.getAll();
        app.setAttribute("categories", categories);
        initialized = true;

        System.out.println("ĐÃ LOAD " + categories.size() + " DANH MỤC KHI KHỞI ĐỘNG ỨNG DỤNG");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       
        if (!initialized) {
            loadCategoriesToApplicationScope();
        }

        String id = request.getParameter("id");

        
        if (request.getRequestURI().endsWith("/init-app")) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        
        Category cat = null;
        if (id != null && !id.trim().isEmpty()) {
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