package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Newsletter;
import utils.XJdbc;

public class NewsLetterDAO {

	private static final String SELECT_ALL = "SELECT Email FROM NEWSLETTERS";
	private final String registerNotifyEmailSQL = "INSERT INTO NEWSLETTERS(Email) VALUE (?)";
	private static final String COUNT_ALL_SQL = "SELECT COUNT(*) as count FROM NEWSLETTERS";
	private static final String DELETE_EMAIL_SQL = "DELETE FROM NEWSLETTERS WHERE Email = ?";
	private static final String GET_ALL_EMAIL_SQL = "SELECT Email FROM NEWSLETTRERS";
	
	public int countAll() throws SQLException {
		ResultSet rs = XJdbc.executeQuery(COUNT_ALL_SQL);
		if ( rs.next() ) {			
			return rs.getInt(1);
		}
		return 0;
	}
	
	public int deleteEmail(String email) {
		return XJdbc.executeUpdate(DELETE_EMAIL_SQL, email);
	}
	
	public List<String> getAllEmails() {
        List<String> emails = new ArrayList<>();
        
        try {
        	ResultSet rs = XJdbc.executeQuery(GET_ALL_EMAIL_SQL);
        	while ( rs.next() ) {
        		emails.add(rs.getString(1));
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return emails;
    }
	
	public int regEmail(String email) {
		return XJdbc.executeUpdate(registerNotifyEmailSQL, email);
	}
	
	public List<Newsletter> getAll() {
		List<Newsletter> list = XJdbc.getBeanList(Newsletter.class, SELECT_ALL);
		return list;
		
	}
	
}