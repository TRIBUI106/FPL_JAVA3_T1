package poly.com.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import poly.com.dao.EmployeesDao;
import poly.com.dao.DeparmentsDao;
import poly.com.model.Employee;
import poly.com.model.Department;

@WebServlet({
    "/Employees",
    "/Employees/add",
    "/Employees/delete",
    "/Employees/edit",
    "/Employees/find",
    "/Employees/update"
})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class EmployeesController extends HttpServlet {
    
    private final String UPLOAD_DIR = "uploads";
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String uri = req.getRequestURI();
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String genderStr = req.getParameter("gender");
        String birthdayStr = req.getParameter("birthday");
        String salaryStr = req.getParameter("salary");
        String departmentId = req.getParameter("departmentId");

        if (uri.contains("add")) {
            try {
                // Upload file và lấy đường dẫn
                String photo = uploadFile(req);
                
                boolean gender = "1".equals(genderStr) || "true".equalsIgnoreCase(genderStr);
                Date birthday = Date.valueOf(birthdayStr);
                double salary = Double.parseDouble(salaryStr);
                
                Employee emp = new Employee(id, password, fullname, photo, 
                                           gender, birthday, salary, departmentId);
                int result = EmployeesDao.insert(emp);
                
                if (result > 0) {
                    req.setAttribute("message", "Thêm nhân viên thành công!");
                } else {
                    req.setAttribute("message", "Thêm nhân viên thất bại!");
                }
            } catch (Exception e) {
                req.setAttribute("message", "Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
            
        } else if (uri.contains("update")) {
            try {
                // Lấy thông tin nhân viên cũ
                Employee oldEmp = EmployeesDao.findById(id);
                String photo;
                
                // Kiểm tra có upload file mới không
                Part filePart = req.getPart("photo");
                if (filePart != null && filePart.getSize() > 0) {
                    photo = uploadFile(req);  // Upload file mới
                } else {
                    photo = oldEmp != null ? oldEmp.getPhoto() : "uploads/default.jpg";  // Giữ ảnh cũ
                }
                
                boolean gender = "1".equals(genderStr) || "true".equalsIgnoreCase(genderStr);
                Date birthday = Date.valueOf(birthdayStr);
                double salary = Double.parseDouble(salaryStr);
                
                Employee emp = new Employee(id, password, fullname, photo, 
                                           gender, birthday, salary, departmentId);
                int result = EmployeesDao.update(emp);
                
                if (result > 0) {
                    req.setAttribute("message", "Cập nhật nhân viên thành công!");
                } else {
                    req.setAttribute("message", "Cập nhật nhân viên thất bại!");
                }
            } catch (Exception e) {
                req.setAttribute("message", "Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
            
        } else if (uri.contains("delete")) {
            int result = EmployeesDao.delete(id);
            if (result > 0) {
                req.setAttribute("message", "Xóa nhân viên thành công!");
            } else {
                req.setAttribute("message", "Xóa nhân viên thất bại!");
            }
            
        } else if (uri.contains("find")) {
            String keyword = req.getParameter("txttim");
            if (keyword != null && !keyword.trim().isEmpty()) {
                // Tìm theo ID trước
                Employee emp = EmployeesDao.findById(keyword);
                if (emp != null) {
                    req.setAttribute("employeeEdit", emp);
                    req.setAttribute("message", "Tìm thấy nhân viên: " + emp.getFullname());
                } else {
                    // Nếu không tìm thấy theo ID thì tìm theo tên
                    List<Employee> searchResults = EmployeesDao.searchByName(keyword);
                    if (!searchResults.isEmpty()) {
                        req.setAttribute("employees", searchResults);
                        req.setAttribute("message", "Tìm thấy " + searchResults.size() + " nhân viên");
                    } else {
                        req.setAttribute("message", "Không tìm thấy nhân viên với từ khóa: " + keyword);
                    }
                }
            } else {
                req.setAttribute("message", "Vui lòng nhập từ khóa tìm kiếm!");
            }
        }

        // Load danh sách nhân viên và phòng ban
        loadDataAndForward(req, resp);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String uri = req.getRequestURI();

        if (uri.contains("edit")) {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                Employee emp = EmployeesDao.findById(idStr);
                req.setAttribute("employeeEdit", emp);
            }
            
        } else if (uri.contains("delete")) {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                int result = EmployeesDao.delete(idStr);
                if (result > 0) {
                    req.setAttribute("message", "Xóa nhân viên thành công!");
                } else {
                    req.setAttribute("message", "Xóa nhân viên thất bại!");
                }
            }
        }
        
        // Load danh sách nhân viên và phòng ban
        loadDataAndForward(req, resp);
    }
    
    // Method upload file
    private String uploadFile(HttpServletRequest req) throws IOException, ServletException {
        Part filePart = req.getPart("photo");
        
        // Nếu không có file, trả về ảnh mặc định
        if (filePart == null || filePart.getSize() == 0) {
            return "uploads/default.jpg";
        }

        // Lấy tên file
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        
        // Tạo tên file unique để tránh trùng
        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String uniqueFileName = timestamp + "_" + fileName.replace(" ", "_");
        
        // Đường dẫn thư mục upload
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        
        // Tạo thư mục nếu chưa tồn tại
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Ghi file vào thư mục
        String filePath = uploadPath + File.separator + uniqueFileName;
        filePart.write(filePath);

        // Trả về đường dẫn để lưu vào database
        return UPLOAD_DIR + "/" + uniqueFileName;
    }
    
    // Helper method để tránh code lặp lại
    private void loadDataAndForward(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Load danh sách nhân viên
        if (req.getAttribute("employees") == null) {
            List<Employee> employees = EmployeesDao.selectAll();
            req.setAttribute("employees", employees);
        }
        
        // Load danh sách phòng ban cho dropdown
        List<Department> departments = DeparmentsDao.selectAll();
        req.setAttribute("departments", departments);
        
        // Forward đến JSP
        req.getRequestDispatcher("/EmployeeGui.jsp").forward(req, resp);
    }
}