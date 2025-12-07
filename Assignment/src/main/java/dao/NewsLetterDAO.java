package dao;

import utils.XJdbc;

public class NewsLetterDAO {

	private final String registerNotifyEmailSQL = "INSERT INTO NEWSLETTERS(Email) VALUE (?)";
	
	public int regEmail(String email) {
		return XJdbc.executeUpdate(registerNotifyEmailSQL, email);
	}
	
}
