package controller.guest;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/category")
public class CategoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = switch (id != null ? id : "TT") {
            case "TT" -> "Thời sự";
            case "KT" -> "Kinh tế";
            case "PL" -> "Pháp luật";
            case "VH" -> "Văn hóa";
            default -> "Thể thao";
        };
        request.setAttribute("categoryName", name);
        request.setAttribute("categoryId", id);
        request.getRequestDispatcher("/guest/category.jsp").forward(request, response);
    }
}