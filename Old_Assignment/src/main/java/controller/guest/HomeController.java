package controller.guest;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet({"/", "/home"})
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Dữ liệu giả (sẽ thay bằng DAO sau)
        request.setAttribute("pageTitle", "ABC News - Tin tức mới nhất");
        request.getRequestDispatcher("/guest/home.jsp").forward(request, response);
    }
}