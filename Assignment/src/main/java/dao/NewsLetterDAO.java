package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import entity.Newsletter;
import utils.XJdbc;

public class NewsLetterDAO {

	private static final String SELECT_ALL = "SELECT Email FROM NEWSLETTERS";
	private final String registerNotifyEmailSQL = "INSERT INTO NEWSLETTERS(Email) VALUE (?)";
	private static final String COUNT_ALL_SQL = "SELECT COUNT(*) as count FROM NEWSLETTERS";
	private static final String deleteEmailFromNewsletterSQL = "DELETE FROM NEWSLETTERS WHERE Email = ?";
	
	public int countAll() throws SQLException {
		ResultSet rs = XJdbc.executeQuery(COUNT_ALL_SQL);
		if ( rs.next() ) {			
			return rs.getInt(1);
		}
		return 0;
	}
	
	public int deleteEmail(String email) {
		return XJdbc.executeUpdate(deleteEmailFromNewsletterSQL, email);
	}
	
	public int regEmail(String email) {
		return XJdbc.executeUpdate(registerNotifyEmailSQL, email);
	}
	
	public List<Newsletter> getAll() {
		List<Newsletter> list = XJdbc.getBeanList(Newsletter.class, SELECT_ALL);
		return list;
		
	}
	
}
