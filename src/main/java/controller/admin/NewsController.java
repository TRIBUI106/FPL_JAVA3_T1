package controller.admin;

import entity.News;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.czEmail;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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
    private czEmail emailService = new czEmail();
    private static final String UPLOAD_DIR = "D:/uploads/";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String id = req.getParameter("id");
        String format = req.getParameter("format");

     // ========== Load ảnh và lưu từ ổ D trong thư mục uploads ==========
        if ("image".equals(action)) {
            String file = req.getParameter("file");
            if (file == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            File imgFile = new File(UPLOAD_DIR, file);
            if (!imgFile.exists()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            resp.setContentType(getServletContext().getMimeType(imgFile.getName()));
            try (FileInputStream in = new FileInputStream(imgFile);
                 OutputStream out = resp.getOutputStream()) {
                in.transferTo(out);
            }
            return;
        }
        
        // ========== LẤY LATEST ID ==========
        if ("getLatestId".equals(action)) {
            String categoryId = req.getParameter("categoryId");
            int num = dao.getLatestIdWithCategory(categoryId);
            String newId = num < 100 ? "0" + num : String.valueOf(num);
            String latestId = categoryId + newId;
            
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"latestId\": \"" + latestId + "\"}");
            return;
        }

        // ========== LẤY DỮ LIỆU EDIT THEO FORMAT JSON ==========
        if ("edit".equals(action) && id != null && "json".equals(format)) {
            News news = dao.findById(id);
            
            // Tạo JSON thủ công
            String json = String.format(
                "{\"id\": \"%s\", \"title\": \"%s\", \"content\": \"%s\", \"categoryId\": \"%s\", \"home\": %s}",
                escapeJson(news.getId()),
                escapeJson(news.getTitle()),
                escapeJson(news.getContent()),
                escapeJson(news.getCategoryId()),
                news.isHome()
            );
            
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(json);
            return;
        }

        // ========== XÓA TIN ==========
        if ("delete".equals(action) && id != null) {
            dao.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/news");
            return;
        }

        // ========== LẤY DANH SÁCH TIN VÀ CATEGORIES ==========
        String searchBy = req.getParameter("searchBy");
        String keyword = req.getParameter("keyword");
        List<News> list;

        if (searchBy != null && !searchBy.equals("all") && keyword != null && !keyword.isBlank()) {
            list = dao.searchByCategoryAndKeyword(searchBy, keyword);
        } else if (searchBy != null && !searchBy.equals("all")) {
            list = dao.searchByCategory(searchBy);
        } else if (keyword != null && !keyword.isBlank()) {
            list = dao.searchNews("all", keyword);
        } else {
            list = dao.getAll();
        }
        
        req.setAttribute("listNews", list);

        List<Category> categories = dao.getAllCate();
        req.setAttribute("categories", categories);
        
        // Auto Gen nè 
        String catId = categories.getFirst().getId();
        int num = dao.getLatestIdWithCategory(catId);
        String latestId = num < 100 ? (num < 10 ? "00" + num : "0" + num)  : String.valueOf(num);
        latestId = catId + latestId;
        
        req.setAttribute("latestId", latestId);
        req.setAttribute("searchBy", searchBy);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/admin/news.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String action = req.getParameter("action");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String categoryId = req.getParameter("categoryId");
        String id = req.getParameter("id");
        boolean home = req.getParameter("home") != null;
        
     // Lấy file upload
        Part imagePart = req.getPart("image");
        String fileName = imagePart.getSubmittedFileName();

        String imagePath = null;
        if (fileName != null && !fileName.isEmpty()) {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            imagePart.write(UPLOAD_DIR + fileName);
            imagePath = fileName; // chỉ lưu tên file
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
            n.setViewCount(0);
            
            boolean result = dao.insert(n);
            
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
    
    // ========== HÀM ESCAPE JSON ==========
    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}