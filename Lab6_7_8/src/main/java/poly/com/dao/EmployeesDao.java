package poly.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import poly.com.model.Employee;

public class EmployeesDao extends Connectdao {

    // Thêm nhân viên mới
    public static int insert(Employee entity) {
        String sql = "INSERT INTO Employees (Id, Password, Fullname, Photo, Gender, Birthday, Salary, DepartmentId) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Connectdao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getId());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getFullname());
            ps.setString(4, entity.getPhoto());
            ps.setBoolean(5, entity.isGender());
            ps.setDate(6, entity.getBirthday());
            ps.setDouble(7, entity.getSalary());
            ps.setString(8, entity.getDepartmentId());
            
            int row = ps.executeUpdate();
            System.out.println("Thêm nhân viên thành công!");
            return row;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // Cập nhật nhân viên
    public static int update(Employee entity) {
        String sql = "UPDATE Employees SET Password=?, Fullname=?, Photo=?, Gender=?, " +
                     "Birthday=?, Salary=?, DepartmentId=? WHERE Id=?";
        try (Connection conn = Connectdao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getPassword());
            ps.setString(2, entity.getFullname());
            ps.setString(3, entity.getPhoto());
            ps.setBoolean(4, entity.isGender());
            ps.setDate(5, entity.getBirthday());
            ps.setDouble(6, entity.getSalary());
            ps.setString(7, entity.getDepartmentId());
            ps.setString(8, entity.getId());
            
            int row = ps.executeUpdate();
            System.out.println("Cập nhật nhân viên thành công!");
            return row;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // Xóa nhân viên
    public static int delete(String id) {
        String sql = "DELETE FROM Employees WHERE Id=?";
        try (Connection conn = Connectdao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // Tìm nhân viên theo ID
    public static Employee findById(String id) {
        String sql = "SELECT * FROM Employees WHERE Id=?";
        try (Connection conn = Connectdao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Employee(
                    rs.getString("Id"),
                    rs.getString("Password"),
                    rs.getString("Fullname"),
                    rs.getString("Photo"),
                    rs.getBoolean("Gender"),
                    rs.getDate("Birthday"),
                    rs.getDouble("Salary"),
                    rs.getString("DepartmentId")
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả nhân viên
    public static List<Employee> selectAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM Employees ORDER BY Id";
        try (Connection conn = Connectdao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee emp = new Employee(
                    rs.getString("Id"),
                    rs.getString("Password"),
                    rs.getString("Fullname"),
                    rs.getString("Photo"),
                    rs.getBoolean("Gender"),
                    rs.getDate("Birthday"),
                    rs.getDouble("Salary"),
                    rs.getString("DepartmentId")
                );
                list.add(emp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    // Tìm kiếm nhân viên theo tên (cho chức năng tìm kiếm)
    public static List<Employee> searchByName(String keyword) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM Employees WHERE fullname LIKE ?";
        
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getString("id"));
                emp.setFullname(rs.getString("fullname"));
                emp.setPassword(rs.getString("password"));
                emp.setPhoto(rs.getString("photo"));
                emp.setGender(rs.getBoolean("gender"));
                emp.setBirthday(rs.getDate("birthday"));
                emp.setSalary(rs.getDouble("salary"));
                emp.setDepartmentId(rs.getString("departmentId"));
                list.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
}