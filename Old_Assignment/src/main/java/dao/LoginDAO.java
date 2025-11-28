package dao;

import entity.User;
import utils.XJdbc;
import java.sql.ResultSet;

public class LoginDAO {

    private static final String LOGIN_SQL = 
        "SELECT * FROM USERS WHERE Id = ? AND Password = ?";

    public User login(String id, String password) {
        try {
            ResultSet rs = XJdbc.executeQuery(LOGIN_SQL, id, password);
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getString("Id"));
                u.setPassword(rs.getString("Password")); 
                u.setFullname(rs.getString("Fullname"));  
                u.setBirthday(rs.getDate("Birthday")); 
                u.setGender(rs.getBoolean("Gender"));
                u.setMobile(rs.getString("Mobile"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getBoolean("Role"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}