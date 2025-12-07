package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.XJdbc;

public class NewsLetterDAO {

	private final String registerNotifyEmailSQL = "INSERT INTO NEWSLETTERS(Email) VALUE (?)";
	private static final String COUNT_ALL_SQL = "SELECT COUNT(*) as count FROM NEWSLETTERS";
	
	public int countAll() throws SQLException {
		ResultSet rs = XJdbc.executeQuery(COUNT_ALL_SQL);
		if ( rs.next() ) {			
			return rs.getInt(1);
		}
		return 0;
	}
	
	public int regEmail(String email) {
		return XJdbc.executeUpdate(registerNotifyEmailSQL, email);
	}
	
}
