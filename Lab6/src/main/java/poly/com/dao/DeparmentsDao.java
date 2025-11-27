package poly.com.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import poly.com.model.Department;
public class DeparmentsDao extends Connectdao 
{

	 public void insertDepartment(String id, String name, String description)
	 	{
	        String sql = "INSERT INTO Departments (id, Name, Description) VALUES (?, ?, ?)";
	        try {
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, id);
	            ps.setString(2, name);
	            ps.setString(3, description);
	            ps.executeUpdate();
	            System.out.println("Thêm phòng ban thành công!");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	 /// cập nhật lại thành Entity thêm static
		public static int insert(Department entity) {
			String sql = "INSERT INTO Departments (id, Name, Description) VALUES (?, ?, ?)";
	        try {
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, entity.getId());
	            ps.setString(2, entity.getName());
	            ps.setString(3, entity.getDescription());
	            int rowInserted = ps.executeUpdate();
	            System.out.println("ThÃªm phÃ²ng ban thÃ nh cÃ´ng!");
	            return rowInserted;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return 0;
	        }
		}
	 //
	 
	 public static void deleteDepartment(String id)
	 {
	        String sql = "DELETE FROM Departments WHERE id = ?";
	        try {
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, id);
	            ps.executeUpdate();
	            System.out.println("Xóa phòng ban thành công!");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	 
	 // xóa giữ nguyên hoặc sửa boolean thêm chữ static
	 
	 
	 public void updateDepartment(String id, String name, String description) {
	        String sql = "UPDATE Departments SET Name = ?, Description = ? WHERE id = ?";
	        try {
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, name);
	            ps.setString(2, description);
	            ps.setString(3, id);
	            ps.executeUpdate();
	            System.out.println("Cập nhật phòng ban thành công!");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	 
	  
	 ///
			public static int update(Department entity) {
			String sql = "UPDATE Departments SET Name = ?, Description = ? WHERE id = ?";
	        try {
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, entity.getName());
	            ps.setString(2, entity.getDescription());
	            ps.setString(3, entity.getId());
	            int rowUpdated = ps.executeUpdate();
	            System.out.println("update success!");
	            return rowUpdated;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return 0;
	        }
		}
	 ///
	 
	 public void findDepartmentById(String id) {
	        String sql = "SELECT * FROM Departments WHERE id = ?";
	        try {
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, id);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                String name = rs.getString("Name");
	                String description = rs.getString("Description");
	                System.out.println("Phòng ban ID: " + id + ", Tên: " + name + ", Mô tả: " + description);
	            } else {
	                System.out.println("Không tìm thấy phòng ban!");
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	 
	 // hàm tìm trên giao diện
	 public static Department findDepartmentById1(String id) {
	        Department department = null;
	        String sql = "SELECT * FROM Departments WHERE id = ?";
	        try {
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, id);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                String name = rs.getString("Name");
	                String description = rs.getString("Description");
	                department = new Department(id, name, description);
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return department;
	    }
	 
	 
	 
	
	public void printAllDepartments() {
        String sql = "SELECT * FROM Departments";
        try 
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.isBeforeFirst()) {
                System.out.println("Không có phòng ban nào!");
                return;
            }
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                System.out.println("ID: " + id + ", Name: " + name + ", Description: " + description);
            }
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
	
	///(sử dụng trên giao diện jsp)
	public static List<Department> selectAll() {
		 List<Department> departments = new ArrayList<>();
		    String sql = "SELECT * FROM Departments";
		    try {
		        Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(sql);
		        while (rs.next()) {
		            String id = rs.getString("id");
		            String name = rs.getString("Name");
		            String description = rs.getString("Description");
		            departments.add(new Department(id, name, description));
		        }
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		    return departments;
	}
    
///	
	
	
	 public void addDepartmentStore(String id, String name, String description) {
	        try {
	            CallableStatement stmt = conn.prepareCall("{call sp_AddDepartment(?, ?, ?)}");
	            stmt.setString(1, id);
	            stmt.setString(2, name);
	            stmt.setString(3, description);
	            stmt.execute();
	            System.out.println("Thêm phòng ban thành công!");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	
	
}
