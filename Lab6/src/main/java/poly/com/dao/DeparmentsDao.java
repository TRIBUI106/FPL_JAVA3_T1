package poly.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import poly.com.model.Department;

public class DeparmentsDao extends Connectdao {

    // Thêm phòng ban (có trả về số dòng ảnh hưởng)
    public static int insert(Department entity) {
        String sql = "INSERT INTO Departments (id, Name, Description) VALUES (?, ?, ?)";
        try (Connection conn = Connectdao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getId());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getDescription());
            int row = ps.executeUpdate();
            System.out.println("Thêm phòng ban thành công!");
            return row;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // Cập nhật phòng ban
    public static int update(Department entity) {
        String sql = "UPDATE Departments SET Name = ?, Description = ? WHERE id = ?";
        try (Connection conn = Connectdao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setString(3, entity.getId());
            int row = ps.executeUpdate();
            System.out.println("Cập nhật thành công!");
            return row;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // Xóa phòng ban
    public static int delete(String id) {
        String sql = "DELETE FROM Departments WHERE id = ?";
        try (Connection conn = Connectdao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // Tìm theo ID
    public static Department findById(String id) {
        String sql = "SELECT * FROM Departments WHERE id = ?";
        try (Connection conn = Connectdao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Department(
                    rs.getString("id"),
                    rs.getString("Name"),
                    rs.getString("Description")
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả phòng ban (dùng cho JSP)
    public static List<Department> selectAll() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM Departments";
        try (Connection conn = Connectdao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Department d = new Department(
                    rs.getString("id"),
                    rs.getString("Name"),
                    rs.getString("Description")
                );
                list.add(d);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}