package poly.com.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
public class EmployeesController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String uri = req.getRequestURI();
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String photo = req.getParameter("photo");
        String genderStr = req.getParameter("gender");
        String birthdayStr = req.getParameter("birthday");
        String salaryStr = req.getParameter("salary");
        String departmentId = req.getParameter("departmentId");

        if (uri.contains("add")) {
            try {
                boolean gender = "1".equals(genderStr) || "true".equalsIgnoreCase(genderStr);
                Date birthday = Date.valueOf(birthdayStr);
                double salary = Double.parseDouble(salaryStr);
                
                Employee emp = new Employee(id, password, fullname, photo, 
                                           gender, birthday, salary, departmentId);
                EmployeesDao.insert(emp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else if (uri.contains("update")) {
            try {
                boolean gender = "1".equals(genderStr) || "true".equalsIgnoreCase(genderStr);
                Date birthday = Date.valueOf(birthdayStr);
                double salary = Double.parseDouble(salaryStr);
                
                Employee emp = new Employee(id, password, fullname, photo, 
                                           gender, birthday, salary, departmentId);
                EmployeesDao.update(emp);
                System.out.println("Cập nhật nhân viên thành công!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else if (uri.contains("delete")) {
            EmployeesDao.delete(id);
            System.out.println("Xóa nhân viên thành công!");
            
        } else if (uri.contains("find")) {
            String keyword = req.getParameter("keyword"); // Sửa từ "txttim" thành "keyword"
            if (keyword != null && !keyword.trim().isEmpty()) {
                // Tìm theo tên để ra list nhiều kết quả
                List<Employee> searchResults = EmployeesDao.searchByName(keyword);
                req.setAttribute("employees", searchResults);
                
                // Nếu không tìm thấy kết quả nào
                if (searchResults == null || searchResults.isEmpty()) {
                    req.setAttribute("message", "Không tìm thấy nhân viên với từ khóa: " + keyword);
                }
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
                EmployeesDao.delete(idStr);
            }
        }
        
        // Load danh sách nhân viên và phòng ban
        loadDataAndForward(req, resp);
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