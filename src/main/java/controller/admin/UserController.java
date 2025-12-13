package controller.admin;

import dao.UserDAO;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/user")
public class UserController extends HttpServlet {

    private final UserDAO dao = new UserDAO();
    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            String id = req.getParameter("id");
            User u = dao.findById(id);
            if (u != null) {
                req.setAttribute("userEdit", u);
            }
        } else if ("delete".equals(action)) {
            String id = req.getParameter("id");
            HttpSession session = req.getSession();
            String currentUserId = (String) session.getAttribute("userId"); // giả sử đã login
            if (!id.equals(currentUserId)) {
                dao.delete(id);
                req.setAttribute("msg", "Xóa thành công!");
            } else {
                req.setAttribute("error", "Không thể tự xóa chính mình!");
            }
        }

        loadList(req);
        req.getRequestDispatcher("/admin/user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        UserDAO dao = new UserDAO();

        User u = new User();
        String rawPassword = null; 

        // === 1. XÁC ĐỊNH LÀ THÊM MỚI HAY SỬA ===
        String idParam = req.getParameter("id");
        boolean isEdit = (idParam != null && !idParam.trim().isEmpty() && dao.isIdExists(idParam));

        if (isEdit) {
            u.setId(idParam.trim());
        } else {
            u.setId(dao.generateUserId());
        }

        // === 2. LẤY DỮ LIỆU TỪ FORM ===
        u.setFullname(req.getParameter("fullname"));
        u.setEmail(req.getParameter("email"));
        u.setMobile(req.getParameter("mobile"));

        // Ngày sinh
        String birthdayStr = req.getParameter("birthday");
        java.sql.Date birthday = null;
        if (birthdayStr != null && !birthdayStr.trim().isEmpty()) {
            try {
                birthday = java.sql.Date.valueOf(birthdayStr);
            } catch (Exception e) {
            }
        }
        u.setBirthday(birthday);

        // Giới tính
        String genderStr = req.getParameter("gender");
        Boolean gender = null;
        if ("male".equals(genderStr)) gender = true;
        else if ("female".equals(genderStr)) gender = false;
        u.setGender(gender);

        // Vai trò
        String roleStr = req.getParameter("role");
        u.setRole("true".equals(roleStr) || "1".equals(roleStr));

        // === 3. VALIDATE ===
        StringBuilder errorMsg = new StringBuilder();

        if (u.getFullname() == null || u.getFullname().trim().isEmpty()) {
            errorMsg.append("• Họ và tên không được để trống!<br>");
        }
        if (u.getEmail() == null || !u.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMsg.append("• Email không hợp lệ!<br>");
        }
        if (dao.isEmailExists(u.getEmail(), isEdit ? u.getId() : null)) {
            errorMsg.append("• Email này đã được sử dụng!<br>");
        }
        if (u.getMobile() != null && !u.getMobile().trim().isEmpty()) {
            if (!u.getMobile().matches("0[35789]\\d{8}")) {
                errorMsg.append("• Số điện thoại phải có 10 số, bắt đầu bằng 03,05,07,08,09!<br>");
            }
        }

        if (!isEdit) {
            rawPassword = req.getParameter("password");
            if (rawPassword == null || rawPassword.trim().isEmpty()) {
                errorMsg.append("• Mật khẩu không được để trống!<br>");
            } else if (rawPassword.length() < 3) {
                errorMsg.append("• Mật khẩu phải ít nhất 3 ký tự!<br>");
            } else {
                u.setPassword(rawPassword); 
            }
        } else {
          
            String newPass = req.getParameter("password");
            if (newPass != null && !newPass.trim().isEmpty()) {
                if (newPass.length() < 3) {
                    errorMsg.append("• Mật khẩu mới phải ít nhất 3 ký tự!<br>");
                } else {
                    u.setPassword(newPass);
                }
            }
        }

        // === 4. XỬ LÝ LƯU & THÔNG BÁO ===
        if (errorMsg.length() > 0) {
            req.setAttribute("error", "<div class='alert alert-danger'>" + errorMsg.toString() + "</div>");
        } else {
            boolean success;
            if (isEdit) {
                boolean changePassword = req.getParameter("password") != null && !req.getParameter("password").trim().isEmpty();
                success = dao.update(u, changePassword);
                req.setAttribute("msg", "<div class='alert alert-success'><strong>Cập nhật thành công!</strong></div>");
            } else {
                success = dao.insert(u);
                if (success) {
                    req.setAttribute("msg",
                        "<div class='alert alert-success text-center py-4'>" +
                        "<h5>Thêm người dùng thành công!</h5>" +
                        "<div class='mt-3'>" +
                        "<p class='mb-2'><strong>Tài khoản:</strong> <span class='text-primary fs-5'>" + u.getId() + "</span></p>" +
                        "<p class='mb-3'><strong>Mật khẩu:</strong> <span class='text-danger fs-5'>" + rawPassword + "</span></p>" +
                        "<button class='btn btn-outline-primary btn-sm me-2' onclick=\"copyText('" + u.getId() + "')\">Copy tài khoản</button>" +
                        "<button class='btn btn-outline-danger btn-sm' onclick=\"copyText('" + rawPassword + "')\">Copy mật khẩu</button>" +
                        "</div></div>"
                    );
                } else {
                    req.setAttribute("error", "<div class='alert alert-danger'>Thêm thất bại! Vui lòng thử lại.</div>");
                }
            }
        }

        // === 5. TẢI LẠI DANH SÁCH ===
        loadList(req);
        req.getRequestDispatcher("/admin/user.jsp").forward(req, resp);
    }

    private void loadList(HttpServletRequest req) {
        String keyword = req.getParameter("keyword");
        String pageStr = req.getParameter("page");
        int page = pageStr == null ? 1 : Integer.parseInt(pageStr);

        List<User> list = dao.selectAll(keyword, page, PAGE_SIZE);
        int total = dao.count(keyword);
        int totalPage = (total + PAGE_SIZE - 1) / PAGE_SIZE;

        req.setAttribute("listUsers", list);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("currentPage", page);
        req.setAttribute("keyword", keyword);
    }
}