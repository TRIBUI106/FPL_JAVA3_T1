package test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test cho RegisterDAO - 5 test cases
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterDAOTest {
    
    private static RegisterDAO registerDAO;
    private static UserDAO userDAO;
    private static String testUserId = "TEST_USER" + System.currentTimeMillis();
    
    @BeforeAll
    public static void setupClass() {
        System.out.println("=== BẮT ĐẦU TEST RegisterDAO ===");
        registerDAO = new RegisterDAO();
        userDAO = new UserDAO();
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("=== KẾT THÚC TEST RegisterDAO ===");
        try {
            userDAO.delete(testUserId);
        } catch (Exception e) {}
    }
    
    // ========== TC_REGISTER_001-005: REGISTRATION TESTS ==========
    
    @Test
    @Order(1)
    @DisplayName("TC_REGISTER_001: Register với dữ liệu hợp lệ - Thành công")
    public void testRegister_ValidData_Success() {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        boolean result = registerDAO.register(testUserId, "password123", "Test User", email);
        
        assertTrue(result, "Register với dữ liệu hợp lệ nên return true");
    }
    
    @Test
    @Order(2)
    @DisplayName("TC_REGISTER_002: Register với ID trùng - Thất bại")
    public void testRegister_DuplicateId_Fail() {
        String email = "another" + System.currentTimeMillis() + "@example.com";
        boolean result = registerDAO.register(testUserId, "password", "Another User", email);
        
        assertFalse(result, "Register với ID trùng nên return false");
    }
    
    @Test
    @Order(3)
    @DisplayName("TC_REGISTER_003: Register với email không hợp lệ - Thất bại")
    public void testRegister_InvalidEmail_Fail() {
        boolean result = registerDAO.register("TEST_USER2", "password", "User", "invalid-email");
        
        assertFalse(result, "Register với email không hợp lệ nên return false");
    }
    
    @Test
    @Order(4)
    @DisplayName("TC_REGISTER_004: Register với email rỗng - Thất bại")
    public void testRegister_EmptyEmail_Fail() {
        boolean result = registerDAO.register("TEST_USER3", "password", "User", "");
        
        assertFalse(result, "Register với email rỗng nên return false");
    }
    
    @Test
    @Order(5)
    @DisplayName("TC_REGISTER_005: Register với email null - Thất bại")
    public void testRegister_NullEmail_Fail() {
        boolean result = registerDAO.register("TEST_USER4", "password", "User", null);
        
        assertFalse(result, "Register với email null nên return false");
    }
}