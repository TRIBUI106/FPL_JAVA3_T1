package poly.com.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.com.dao.DeparmentsDao;
import poly.com.model.Department;



@WebServlet({"/Departments","/Departments/loadall","/Departments/add","/Departments/delete","/Departments/edit","/Departments/find","/Departments/update"})
public class DeparmentsController extends HttpServlet {
	protected void deleteDepartments(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
   	{
   	  String idToDelete = request.getParameter("id");
         DeparmentsDao.delete(idToDelete);
  
   	} 
	   
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = req.getRequestURI();
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        if (uri.contains("add")) 
        {
            Department dept = new Department(id, name, description);
            DeparmentsDao.insert(dept);
          
        } else if (uri.contains("update")) {
            Department dept = new Department(id, name, description);
            DeparmentsDao.update(dept);
            System.out.println("Cập nhật thành công!");

        } else if (uri.contains("delete"))
        {
        	DeparmentsDao.delete(id);
            System.out.println("Xóa thành công!");

        } else if (uri.contains("find")) 
        {
            Department dept = DeparmentsDao.findById(req.getParameter("txttim"));
            if (dept != null) {
                req.setAttribute("departmentEdit", dept);
            }
        }

        List<Department> list = DeparmentsDao.selectAll();
        req.setAttribute("departments", list);
        req.getRequestDispatcher("/Departments/DepartmentGui.jsp").forward(req, resp);
        // Sau khi xử lý xong ➝ load danh sách
     
    
	}
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        DeparmentsDao dao = new DeparmentsDao();

        if (uri.contains("edit")) 
        {
            // Lấy id từ URL
            String idStr = req.getParameter("id");
            if (idStr != null) 
            {
                           // Tìm phòng ban theo id
                Department dept = dao.findById(idStr);
                // Gửi thông tin phòng ban lên JSP để hiển thị trong form
                req.setAttribute("departmentEdit", dept);
                           
            }
            
            
        }
        else
        	if(uri.contains("delete"))
        	{
        		deleteDepartments(req,resp);
        		
        	}
               
        // Load toàn bộ danh sách để hiển thị bảng
        List<Department> list = dao.selectAll();
        req.setAttribute("departments", list);
        req.getRequestDispatcher("/Departments/DepartmentGui.jsp").forward(req, resp);
    }
    
    
    
    
}
