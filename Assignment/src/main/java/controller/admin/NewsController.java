package controller.admin;

import entity.News;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.czEmail;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import entity.Category;
import dao.NewsDAO;

@WebServlet("/admin/news")
@MultipartConfig
public class NewsController extends HttpServlet {
    private NewsDAO dao = new NewsDAO();
    private czEmail emailService = new czEmail(); // ← Thêm EmailService

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String id = req.getParameter("id");

        if ("delete".equals(action) && id != null) {
            dao.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/news");
            return;
        }

        if ("edit".equals(action) && id != null) {
            News cat = dao.findById(id);
            req.setAttribute("cat", cat);
        }
        String searchBy = req.getParameter("searchBy");
        String keyword = req.getParameter("keyword");
        List<News> list;

        if (searchBy != null && !searchBy.equals("all") && keyword != null && !keyword.isBlank()) {
            // lọc kết hợp loại tin + id hoặc content
            list = dao.searchByCategoryAndKeyword(searchBy, keyword);
        } else if (searchBy != null && !searchBy.equals("all")) {
            // lọc theo thể loại tin
            list = dao.searchByCategory(searchBy);
        } else if (keyword != null && !keyword.isBlank()) {
            // tìm theo keyword trên nhiều cột
            list = dao.searchNews("all", keyword);
        } else {
            // hiển thị tất cả khi keyword rỗng
            list = dao.getAll();
        }


        req.setAttribute("listNews", list);

        List<Category> categories = dao.getAllCate();
        req.setAttribute("categories", categories);
        req.setAttribute("searchBy", searchBy);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/admin/news.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String action = req.getParameter("action");
        String id = req.getParameter("id") != null ? req.getParameter("id") : 0;
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String categoryId = req.getParameter("categoryId");
        boolean home = req.getParameter("home") != null;
        
        
        // Lấy file upload
        Part imagePart = req.getPart("image");
        String fileName = imagePart.getSubmittedFileName();

        String uploadPath = req.getServletContext().getRealPath("/img");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String imagePath = null;
        if (fileName != null && !fileName.isEmpty()) {
            imagePart.write(uploadPath + File.separator + fileName);
            imagePath = "img/" + fileName;
        }

        News n = new News();
        n.setId(id);
        n.setTitle(title);
        n.setContent(content);

        if ("update".equals(action)) {
            if (imagePath != null) {
                n.setImage(imagePath);
            } else {
                News old = dao.findById(id);
                n.setImage(old.getImage());
            }
        } else {
            n.setImage(imagePath);
        }

        n.setPostedDate(Date.valueOf(LocalDate.now()));

        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser != null) {
            n.setAuthor(currentUser.getId());
        }
        n.setCategoryId(categoryId);
        n.setHome(home);

        String message;
        if ("update".equals(action)) {
            dao.update(n);
            message = "Cập nhật tin thành công!";
        } else {
            // ========== THÊM MỚI TIN TỨC ==========
            n.setViewCount(0);
            int num = dao.getLatestIdWithCategory(categoryId);
            String newId = num < 100 ? "0" + num : String.valueOf(num);
            n.setId(newId);
            
            boolean result = dao.insert(n);
            
            // ========== GỬI EMAIL ASYNC (KHÔNG CHẶN RESPONSE) ==========
            if (result && n.getId() != null) {
                String newsUrl = req.getScheme() + "://" + 
                                req.getServerName() + ":" + 
                                req.getServerPort() + 
                                req.getContextPath() + 
                                "/detail?id=" + n.getId();
                
                String contextPath = req.getScheme() + "://" + 
                                    req.getServerName() + ":" + 
                                    req.getServerPort() + 
                                    req.getContextPath();
                
                // Gọi async - return ngay lập tức
                emailService.sendNewsNotificationAsync(
                    n.getId(), 
                    title, 
                    newsUrl, 
                    contextPath
                );
                
                System.out.println("✅ [NewsController] Tin đã lưu, email đang gửi ở background");
            }
            
            message = "Thêm mới tin \"" + title + "\" thành công! Đang gửi thông báo đến subscribers...";
        }

        req.getSession().setAttribute("toastType", "success");
        req.getSession().setAttribute("toastMessage", message);
        resp.sendRedirect(req.getContextPath() + "/admin/news");
    }
}