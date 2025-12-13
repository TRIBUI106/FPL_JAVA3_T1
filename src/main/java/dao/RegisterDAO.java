package dao;

import java.util.regex.Pattern;

import entity.User;
import utils.XJdbc;

public class RegisterDAO {
	// Bắt định dạng nhập email phải đúng
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
	private static final String SQL_CHECK_ID = "SELECT * FROM USERS WHERE Id = ?";
	private static final String SQL_CHECK_EMAIL = "SELECT * FROM USERS WHERE Email = ?";
	private static final String SQL_INSERT = "INSERT INTO USERS (Id, Password, Fullname, Email, Role) VALUES (?, ?, ?, ?, ?)";
	
	// Kiểm tra định dạng email và xem có trống ko
	private boolean isValidEmail(String email) {
		return email != null && EMAIL_PATTERN.matcher(email).matches();
	}
	
	
	public boolean register(String id, String password, String fullname, String email) {
		if (!isValidEmail(email)) {
			return false;
		}

		// Kiểm tra xem id có bị trùng không nếu trùng thì cho nhập lại
		User existing = XJdbc.getSingleBean(User.class, SQL_CHECK_ID, id);
		if (existing != null) {
			return false;
		}

		// Kiểm tra email đã tồn tại chưa nếu có phải nhập khác đi
		User existingByEmail = XJdbc.getSingleBean(User.class, SQL_CHECK_EMAIL, email);
		if (existingByEmail != null) {
			return false;
		}

		// Role mặc định khi đăng ký là là phóng viên (0)
		int rows = XJdbc.executeUpdate(SQL_INSERT, id, password, fullname, email, 0);

		return rows > 0;
	}
}
