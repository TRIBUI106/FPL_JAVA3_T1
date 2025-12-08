package test;

import entity.Newsletter;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test cho NewsLetterDAO - 5 test cases
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewsLetterDAOTest {
    
    private static NewsLetterDAO newsletterDAO;
    private static String testEmail = "test" + System.currentTimeMillis() + "@example.com";
    
    @BeforeAll
    public static void setupClass() {
        System.out.println("=== BẮT ĐẦU TEST NewsLetterDAO ===");
        newsletterDAO = new NewsLetterDAO();
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("=== KẾT THÚC TEST NewsLetterDAO ===");
        try {
            newsletterDAO.deleteEmail(testEmail);
        } catch (Exception e) {}
    }
    
    // ========== TC_NEWSLETTER_001-003: REGISTER EMAIL TESTS ==========
    
    @Test
    @Order(1)
    @DisplayName("TC_NEWSLETTER_001: Register email hợp lệ - Thành công")
    public void testRegEmail_ValidEmail_Success() {
        int result = newsletterDAO.regEmail(testEmail);
        
        assertTrue(result > 0, "Register email thành công nên return > 0");
    }
    
    @Test
    @Order(2)
    @DisplayName("TC_NEWSLETTER_002: Register email trùng - Thất bại")
    public void testRegEmail_DuplicateEmail_Fail() {
        // Email đã được đăng ký ở test trước
        assertThrows(Exception.class, () -> newsletterDAO.regEmail(testEmail),
            "Register email trùng nên throw exception");
    }
    
    @Test
    @Order(3)
    @DisplayName("TC_NEWSLETTER_003: Register email null - Exception")
    public void testRegEmail_NullEmail_Exception() {
        assertThrows(Exception.class, () -> newsletterDAO.regEmail(null),
            "Register email null nên throw exception");
    }
    
    // ========== TC_NEWSLETTER_004-005: QUERY TESTS ==========
    
    @Test
    @Order(4)
    @DisplayName("TC_NEWSLETTER_004: GetAllEmails - Trả về List không null")
    public void testGetAllEmails_ReturnNonNullList() {
        List<String> emails = newsletterDAO.getAllEmails();
        
        assertNotNull(emails, "GetAllEmails nên trả về List không null");
        assertTrue(emails.size() > 0, "List nên có ít nhất 1 email");
        assertTrue(emails.contains(testEmail), "List nên chứa email vừa đăng ký");
    }
    
    @Test
    @Order(5)
    @DisplayName("TC_NEWSLETTER_005: DeleteEmail - Xóa thành công")
    public void testDeleteEmail_Success() {
        int result = newsletterDAO.deleteEmail(testEmail);
        
        assertTrue(result > 0, "Delete email nên return > 0");
        
        List<String> emails = newsletterDAO.getAllEmails();
        assertFalse(emails.contains(testEmail), "Email đã xóa không nên còn trong list");
    }
}