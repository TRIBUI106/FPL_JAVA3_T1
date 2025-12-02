package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import entity.User;
import utils.XJdbc;

/**
 * Unit Test cho RegisterDAO
 * Test Cases: TC01, TC02, TC03, TC04
 */
public class RegisterDAOTest {
    
    private RegisterDAO registerDAO;
    private static final String TEST_ID = "TESTUSER001";
    private static final String TEST_EMAIL = "testuser@example.com";
    
    @Before
    public void setUp() {
        registerDAO = new RegisterDAO();
        // Xóa dữ liệu test nếu tồn tại
        cleanUpTestData();
    }
    
    @After
    public void tearDown() {
        // Dọn dẹp sau khi test
        cleanUpTestData();
    }
    
    private void cleanUpTestData() {
        try {
            XJdbc.executeUpdate("DELETE FROM USERS WHERE Id = ?", TEST_ID);
            XJdbc.executeUpdate("DELETE FROM USERS WHERE Email = ?", TEST_EMAIL);
        } catch (Exception e) {
            // Ignore if not exists
        }
    }
    
    /**
     * TC01: Kiểm tra validate email hợp lệ
     * Expected: Return true, user được thêm vào database
     */
    @Test
    public void testRegisterWithValidEmail() {
        // Arrange
        String id = TEST_ID;
        String password = "password123";
        String fullname = "Test User";
        String email = TEST_EMAIL;
        
        // Act
        boolean result = registerDAO.register(id, password, fullname, email);
        
        // Assert
        assertTrue("Should register successfully with valid email", result);
        
        // Verify user exists in database
        User user = XJdbc.getSingleBean(User.class, 
            "SELECT * FROM USERS WHERE Id = ?", id);
        assertNotNull("User should exist in database", user);
        assertEquals("Email should match", email, user.getEmail());
    }
    
    /**
     * TC02: Kiểm tra validate email không hợp lệ
     * Expected: Return false, không thêm user vào database
     */
    @Test
    public void testRegisterWithInvalidEmail() {
        // Arrange
        String id = TEST_ID;
        String password = "password123";
        String fullname = "Test User";
        String invalidEmail = "invalid-email";
        
        // Act
        boolean result = registerDAO.register(id, password, fullname, invalidEmail);
        
        // Assert
        assertFalse("Should not register with invalid email", result);
        
        // Verify user does not exist
        User user = XJdbc.getSingleBean(User.class, 
            "SELECT * FROM USERS WHERE Id = ?", id);
        assertNull("User should not exist in database", user);
    }
    
    /**
     * TC03: Kiểm tra đăng ký với ID đã tồn tại
     * Expected: Return false, không thêm user mới
     */
    @Test
    public void testRegisterWithDuplicateId() {
        // Arrange - First registration
        String id = TEST_ID;
        String password1 = "password123";
        String fullname1 = "First User";
        String email1 = "first@example.com";
        
        registerDAO.register(id, password1, fullname1, email1);
        
        // Act - Try to register again with same ID
        String password2 = "password456";
        String fullname2 = "Second User";
        String email2 = "second@example.com";
        
        boolean result = registerDAO.register(id, password2, fullname2, email2);
        
        // Assert
        assertFalse("Should not register with duplicate ID", result);
        
        // Verify original user still exists
        User user = XJdbc.getSingleBean(User.class, 
            "SELECT * FROM USERS WHERE Id = ?", id);
        assertEquals("Should keep original email", email1, user.getEmail());
        
        // Clean up second email
        XJdbc.executeUpdate("DELETE FROM USERS WHERE Email = ?", email1);
    }
    
    /**
     * TC04: Kiểm tra đăng ký với Email đã tồn tại
     * Expected: Return false, không thêm user mới
     */
    @Test
    public void testRegisterWithDuplicateEmail() {
        // Arrange - First registration
        String id1 = TEST_ID;
        String password1 = "password123";
        String fullname1 = "First User";
        String email = TEST_EMAIL;
        
        registerDAO.register(id1, password1, fullname1, email);
        
        // Act - Try to register with different ID but same email
        String id2 = "TESTUSER002";
        String password2 = "password456";
        String fullname2 = "Second User";
        
        boolean result = registerDAO.register(id2, password2, fullname2, email);
        
        // Assert
        assertFalse("Should not register with duplicate email", result);
        
        // Verify second user does not exist
        User user2 = XJdbc.getSingleBean(User.class, 
            "SELECT * FROM USERS WHERE Id = ?", id2);
        assertNull("Second user should not exist", user2);
    }
    
    /**
     * TC - Additional: Test with null email
     */
    @Test
    public void testRegisterWithNullEmail() {
        boolean result = registerDAO.register(TEST_ID, "pass", "Name", null);
        assertFalse("Should not register with null email", result);
    }
    
    /**
     * TC - Additional: Test with empty email
     */
    @Test
    public void testRegisterWithEmptyEmail() {
        boolean result = registerDAO.register(TEST_ID, "pass", "Name", "");
        assertFalse("Should not register with empty email", result);
    }
}