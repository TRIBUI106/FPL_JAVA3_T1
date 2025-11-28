package dao;

import entity.Newsletter;
import utils.XJdbc;
import java.util.List;

public class NewsletterDAO {

    public List<Newsletter> getAll() {
        String sql = "SELECT Email, Enabled FROM NEWSLETTERS ORDER BY Email";
        return XJdbc.getBeanList(Newsletter.class, sql);
    }
    
    public boolean insert(String email) {
        String sql = "INSERT INTO NEWSLETTERS (Email, Enabled) VALUES (?, 1)";
        return XJdbc.executeUpdate(sql, email) > 0;
    }

    public boolean toggle(String email, int enabled) {
        String sql = "UPDATE NEWSLETTERS SET Enabled = ? WHERE Email = ?";
        return XJdbc.executeUpdate(sql, enabled, email) > 0;
    }

    public boolean delete(String email) {
        String sql = "DELETE FROM NEWSLETTERS WHERE Email = ?";
        return XJdbc.executeUpdate(sql, email) > 0;
    }

    public boolean exists(String email) {
        String sql = "SELECT COUNT(*) FROM NEWSLETTERS WHERE Email = ?";
        Long count = XJdbc.getValue(sql, email);
        return count != null && count > 0;
    }
}