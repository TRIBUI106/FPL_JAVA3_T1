package controller.admin;

import dao.CategoryDAO;
import entity.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet({"/admin/category"})
public class CategoryAdminController extends HttpServlet {
    private final CategoryDAO dao = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        String id = req.getParameter("id");

        // Xóa
        if ("delete".equals(action) && id != null) {
            dao.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/category");
            return;
        }

        // Lấy dữ liệu để sửa
        if ("edit".equals(action) && id != null) {
            Category cat = dao.findById(id);
            req.setAttribute("cat", cat);
        }

        // Load danh sách
        List<Category> list = dao.getAll();
        req.setAttribute("listCategories", list);

        req.getRequestDispatcher("/admin/category.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name").trim();
        String action = req.getParameter("action");

        if (name == null || name.isEmpty()) {
            resp.sendRedirect("category");
            return;
        }

        Category c;
        String message;

        if ("update".equals(action)) {
            
            String oldId = req.getParameter("id"); 
            String newId = dao.generateId(name);
            if (newId.equals(oldId)) {
                c = new Category(oldId, name);
                dao.update(c);
                message = "Cập nhật thành công (mã không đổi)!";
            } else {
              
                dao.delete(oldId);
                c = new Category(newId, name);
                dao.insert(c);
                message = "Cập nhật thành công! Mã loại đã đổi thành: " + newId;
            }
        } else {
            String generatedId = dao.generateId(name);
            c = new Category(generatedId, name);
            dao.insert(c);
            message = "Thêm mới loại tin \"" + name + "\" thành công! Mã: " + generatedId;
        }

        req.getSession().setAttribute("message", message);
        resp.sendRedirect(req.getContextPath() + "/admin/category");
    }
}