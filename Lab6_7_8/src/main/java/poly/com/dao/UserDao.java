package poly.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import poly.com.model.User;

public class UserDao extends Connectdao {

	public User login(String username, String password) {
	    String sql = "SELECT * FROM usernd WHERE username = ? AND password = ?";
	    try (Connection conn = Connectdao.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, username);
	        ps.setString(2, password);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            User user = new User();
	            user.setUsername(rs.getString("username"));
	            user.setFullname(rs.getString("fullname"));
	            user.setRole(rs.getInt("role"));
	            return user;
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	
	// Lấy tất cả người dùng
    public static List<User> selectAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM usernd";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User u = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getInt("role"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("sdt")
                );
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm người dùng
    public static int insert(User u) {
        String sql = "INSERT INTO usernd (username, password, fullname, role, email, address, sdt) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullname());
            ps.setInt(4, u.getRole());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getAddress());
            ps.setString(7, u.getSdt());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Cập nhật người dùng
    public static int update(User u) {
        String sql = "UPDATE usernd SET password=?, fullname=?, role=?, email=?, address=?, sdt=? WHERE username=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getPassword());
            ps.setString(2, u.getFullname());
            ps.setInt(3, u.getRole());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getAddress());
            ps.setString(6, u.getSdt());
            ps.setString(7, u.getUsername());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Xóa người dùng
    public static int delete(String username) {
        String sql = "DELETE FROM usernd WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Tìm theo username
    public static User findById(String username) {
        String sql = "SELECT * FROM usernd WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getInt("role"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("sdt")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
}