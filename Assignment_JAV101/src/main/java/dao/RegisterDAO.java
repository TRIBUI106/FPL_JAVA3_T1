package dao;

import java.util.regex.Pattern;

import entity.User;
import utils.XJdbc;


public class RegisterDAO {
	// Bắt đặt đúng định dạng email sai thì ko đăng ký đc
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

	private boolean isValidEmail(String email) {
		return email != null && EMAIL_PATTERN.matcher(email).matches();
	}

	public boolean register(String id, String password, String name, String email) {

		if (!isValidEmail(email)) {
			return false;
		}

		// Kiểm tra id đã tồn tại chưa
		String checkUser = "SELECT * FROM USERS WHERE Id = ?";
		User existing = XJdbc.getSingleBean(User.class, checkUser, id);
		if (existing != null) {
			// Nếu đã có user thì không cho đăng ký
			return false;
		}
		
		// Kiểm tra xem email tồn tại chưa trùng thì cho nhập khác
		String checkUserByEmail = "SELECT * FROM USERS WHERE Email = ?";
        User existingByEmail = XJdbc.getSingleBean(User.class, checkUserByEmail, email);
        if (existingByEmail != null) {
            return false;
        }
		
		// Role mặc định khi đăng ký là reporter
        String insertSql = "INSERT INTO USERS (Id, Password, Name, Email, Role) VALUES (?, ?, ?, ?, ?)";
        int rows = XJdbc.executeUpdate(insertSql, id, password, name, email, 0);
        
		// 3. Trả về true nếu insert thành công
		return rows > 0;
	}

}
