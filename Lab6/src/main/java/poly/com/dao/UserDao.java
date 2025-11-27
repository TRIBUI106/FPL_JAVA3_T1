package poly.com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import poly.com.model.User;

public class UserDao extends Connectdao
{

	public User login(String username, String password) {
        String sql = "SELECT * FROM Usernd WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullname(rs.getString("fullname"));
                return user; // trả về User nếu đăng nhập đúng
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null; // đăng nhập thất bại
    }
	
	
}
