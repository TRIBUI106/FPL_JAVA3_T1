package poly.com.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import poly.com.dao.DeparmentsDao;
import poly.com.dao.EmployeeDAO;
import poly.com.model.Employee;
import poly.com.model.Department;

@WebServlet({"/Employees","/Employees/add","/Employees/update","/Employees/delete","/Employees/edit","/Employees/find"})
public class EmployeeController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String photo = req.getParameter("photo");
        boolean gender = Boolean.parseBoolean(req.getParameter("gender"));
        String birthdayStr = req.getParameter("birthday");
        Date birthday = new Date(2025, 12, 30);
        if (birthdayStr != null && !birthdayStr.isEmpty()) {
            birthday = Date.valueOf(birthdayStr);
        }
        double salary = Double.parseDouble(req.getParameter("salary"));
        String dept = req.getParameter("departmentId");

        Employee e = new Employee(id, password, fullname, photo, gender, birthday, salary, dept);

        if (uri.contains("add")) {
        	EmployeeDAO.insert(e);
        } else if (uri.contains("update")) {
        	EmployeeDAO.update(e);
        } else if (uri.contains("delete")) {
        	EmployeeDAO.delete(id);
        } else if (uri.contains("find")) {
            Employee emp = EmployeeDAO.findById(req.getParameter("txttim"));
            req.setAttribute("employeeEdit", emp);
        }

        loadData(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getRequestURI().contains("edit")) {
            String id = req.getParameter("id");
            Employee e = EmployeeDAO.findById(id);
            req.setAttribute("employeeEdit", e);
        }
        loadData(req, resp);
    }

    private void loadData(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Employee> empList = EmployeeDAO.selectAll();
        List<Department> deptList = DeparmentsDao.selectAll();

        req.setAttribute("employees", empList);
        req.setAttribute("departments", deptList);

        req.getRequestDispatcher("/Employees/EmployeeGUI.jsp").forward(req, resp);
    }
}
