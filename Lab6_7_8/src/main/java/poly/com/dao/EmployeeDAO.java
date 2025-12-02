package poly.com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import poly.com.model.Employee;

public class EmployeeDAO extends Connectdao {

    public static int insert(Employee e) {
        String sql = "INSERT INTO Employees VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getId());
            ps.setString(2, e.getPassword());
            ps.setString(3, e.getFullname());
            ps.setString(4, e.getPhoto());
            ps.setBoolean(5, e.isGender());
            ps.setDate(6, e.getBirthday());
            ps.setDouble(7, e.getSalary());
            ps.setString(8, e.getDepartmentId());

            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public static int update(Employee e) {
        String sql = "UPDATE Employees SET Password=?, Fullname=?, Photo=?, Gender=?, Birthday=?, Salary=?, DepartmentId=? WHERE Id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getPassword());
            ps.setString(2, e.getFullname());
            ps.setString(3, e.getPhoto());
            ps.setBoolean(4, e.isGender());
            ps.setDate(5, e.getBirthday());
            ps.setDouble(6, e.getSalary());
            ps.setString(7, e.getDepartmentId());
            ps.setString(8, e.getId());

            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public static int delete(String id) {
        String sql = "DELETE FROM Employees WHERE Id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public static Employee findById(String id) {
        String sql = "SELECT * FROM Employees WHERE Id=?";
        try (Connection conn = getConnection();
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

    public static List<Employee> selectAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM Employees";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Employee e = new Employee(
                    rs.getString("Id"),
                    rs.getString("Password"),
                    rs.getString("Fullname"),
                    rs.getString("Photo"),
                    rs.getBoolean("Gender"),
                    rs.getDate("Birthday"),
                    rs.getDouble("Salary"),
                    rs.getString("DepartmentId")
                );
                list.add(e);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
