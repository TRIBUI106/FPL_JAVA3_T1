// dao/LoginDAO.java
package dao;

import entity.User;
import utils.XJdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

public class LoginDAO {
	
	String checkUserExist = "SELECT 1 FROM USERS where Id = ?";
	String getUserSQL = "SELECT id, password FROM USERS WHERE id = ?";

    public User login(String id, String password) {
    	
    	try {
    		ResultSet rs = XJdbc.executeQuery(checkUserExist, id);
        	
            if ( rs.next() ) {
            	ResultSet userRs = XJdbc.executeQuery(getUserSQL, id);
            	User u = new User(userRs.getString("Id"),userRs.getString("password"));
            	return u;
            }
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
		return null;
    	
    	
    }
}