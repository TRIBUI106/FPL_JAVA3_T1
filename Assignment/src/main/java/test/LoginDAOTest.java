package test;

import entity.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test cho LoginDAO - 5 test cases
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginDAOTest {
    
    private static LoginDAO loginDAO;
    
    @BeforeAll
    public static void setupClass() {
        System.out.println("=== BẮT ĐẦU TEST LoginDAO ===");
        loginDAO = new LoginDAO();
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("=== KẾT THÚC TEST LoginDAO ===");
    }
    
    // ========== TC_LOGIN_001-003: VALID LOGIN TESTS ==========
    
    @Test
    @Order(1)
    @DisplayName("TC_LOGIN_001: Login với credentials hợp lệ - Thành công")
    public void testLogin_ValidCredentials_Success() {
        // Giả sử trong DB có user: admin/admin123
        User user = loginDAO.login("admin", "admin123");
        
        assertNotNull(user, "Login thành công nên trả về User object");
        assertEquals("admin", user.getId());
        assertNotNull(user.getFullname());
    }
    
    @Test
    @Order(2)
    @DisplayName("TC_LOGIN_002: Login với password sai - Thất bại")
    public void testLogin_WrongPassword_Fail() {
        User user = loginDAO.login("admin", "wrongpassword");
        
        assertNull(user, "Login với password sai nên return null");
    }
    
    @Test
    @Order(3)
    @DisplayName("TC_LOGIN_003: Login với username không tồn tại - Thất bại")
    public void testLogin_NonExistentUsername_Fail() {
        User user = loginDAO.login("nonexistentuser", "anypassword");
        
        assertNull(user, "Login với username không tồn tại nên return null");
    }
    
    // ========== TC_LOGIN_004-005: EDGE CASE TESTS ==========
    
    @Test
    @Order(4)
    @DisplayName("TC_LOGIN_004: Login với username null - Thất bại")
    public void testLogin_NullUsername_Fail() {
        User user = loginDAO.login(null, "admin123");
        
        assertNull(user, "Login với username null nên return null");
    }
    
    @Test
    @Order(5)
    @DisplayName("TC_LOGIN_005: Login với password null - Thất bại")
    public void testLogin_NullPassword_Fail() {
        User user = loginDAO.login("admin", null);
        
        assertNull(user, "Login với password null nên return null");
    }
}