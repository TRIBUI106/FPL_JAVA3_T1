package poly.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
}