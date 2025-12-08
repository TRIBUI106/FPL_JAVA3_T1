package dao;

import entity.User;
import utils.XJdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO {
	

	private static final String COUNT_ALL_SQL = "SELECT COUNT(*) as count FROM USERS";
	
	public int countAll() throws SQLException {
		ResultSet rs = XJdbc.executeQuery(COUNT_ALL_SQL);
		if ( rs.next() ) {			
			return rs.getInt(1);
		}
		return 0;
	}

    public boolean insert(User u) {
        String sql = "INSERT INTO USERS (Id, Password, Fullname, Birthday, Gender, Mobile, Email, Role) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int gender = (u.getGender() == null) ? 0 : (u.getGender() ? 1 : 0);
        int role = (u.getRole() != null && u.getRole()) ? 1 : 0;
        return XJdbc.executeUpdate(sql, u.getId(), u.getPassword(), u.getFullname(),
                u.getBirthday(), gender, u.getMobile(), u.getEmail(), role) > 0;
    }

    public boolean update(User u, boolean changePassword) {
        String sql = changePassword
                ? "UPDATE USERS SET Password = ?, Fullname = ?, Birthday = ?, Gender = ?, Mobile = ?, Email = ?, Role = ? WHERE Id = ?"
                : "UPDATE USERS SET Fullname = ?, Birthday = ?, Gender = ?, Mobile = ?, Email = ?, Role = ? WHERE Id = ?";
        
        int gender = (u.getGender() == null) ? 0 : (u.getGender() ? 1 : 0);
        int role = (u.getRole() != null && u.getRole()) ? 1 : 0;
        
        Object[] params = changePassword
                ? new Object[]{ u.getPassword(), u.getFullname(), u.getBirthday(), gender, u.getMobile(), u.getEmail(), role, u.getId() }
                : new Object[]{ u.getFullname(), u.getBirthday(), gender, u.getMobile(), u.getEmail(), role, u.getId() };
        
        return XJdbc.executeUpdate(sql, params) > 0;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM USERS WHERE Id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }

    public User findById(String id) {
        String sql = "SELECT * FROM USERS WHERE Id = ?";
        return XJdbc.getSingleBean(User.class, sql, id);
    }

    public List<User> selectAll(String keyword, int page, int pageSize) {
        String sql = """
            SELECT * FROM USERS 
            WHERE (? IS NULL OR Id LIKE ? OR Fullname LIKE ? OR Email LIKE ? OR Mobile LIKE ?)
            ORDER BY Id
            LIMIT ? OFFSET ?
            """;
        String search = keyword == null ? null : "%" + keyword + "%";
        return XJdbc.getBeanList(User.class, sql, keyword, search, search, search, search, pageSize, (page - 1) * pageSize);
    }

    public int count(String keyword) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE (? IS NULL OR Id LIKE ? OR Fullname LIKE ? OR Email LIKE ? OR Mobile LIKE ?)";
        String search = keyword == null ? null : "%" + keyword + "%";
        
        try (ResultSet rs = XJdbc.executeQuery(sql, keyword, search, search, search, search)) {
            if (rs.next()) {
                return rs.getInt(1); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String generateUserId() {
        String sql = "SELECT Id FROM USERS WHERE Id LIKE 'US%' ORDER BY Id DESC LIMIT 1";
        try {
            ResultSet rs = XJdbc.executeQuery(sql);
            if (rs.next()) {
                String lastId = rs.getString(1); 
                int number = Integer.parseInt(lastId.substring(2));
                return String.format("US%03d", number + 1); 
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "US001"; 
    }

    public boolean isIdExists(String id) {
        return findById(id) != null;
    }

    public boolean isEmailExists(String email, String excludeId) {
        String sql = "SELECT Id FROM USERS WHERE Email = ? AND Id <> ?";
        return XJdbc.getValue(sql, email, excludeId != null ? excludeId : "") != null;
    }
}