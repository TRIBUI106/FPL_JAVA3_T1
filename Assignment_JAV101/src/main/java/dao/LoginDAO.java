// dao/LoginDAO.java
package dao;

import entity.User;
import utils.XJdbc;
import org.mindrot.jbcrypt.BCrypt;

public class LoginDAO {

    public User login(String id, String password) {
        String sql = "SELECT * FROM USERS WHERE Id = ?";
        User user = XJdbc.getSingleBean(User.class, sql, id);

        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }
}