package test;

import entity.News;
import org.junit.jupiter.api.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test cho NewsDAO - 15 test cases
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewsDAOTest {
    
    private static NewsDAO newsDAO;
    private static String testNewsId = "TEST_N001";
    
    @BeforeAll
    public static void setupClass() {
        System.out.println("=== BẮT ĐẦU TEST NewsDAO ===");
        newsDAO = new NewsDAO();
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("=== KẾT THÚC TEST NewsDAO ===");
        // Cleanup
        try {
            newsDAO.delete(testNewsId);
            newsDAO.delete("TEST_N002");
            newsDAO.delete("TEST_BOUNDARY");
        } catch (Exception e) {}
    }
    
    // ========== TC_NEWS_001-005: INSERT TESTS ==========
    
    @Test
    @Order(1)
    @DisplayName("TC_NEWS_001: Insert tin với dữ liệu hợp lệ - Thành công")
    public void testInsert_ValidData_Success() {
        News news = createValidNews(testNewsId);
        boolean result = newsDAO.insert(news);
        
        assertTrue(result, "Insert nên trả về true");
        
        News saved = newsDAO.findById(testNewsId);
        assertNotNull(saved, "Tin đã lưu nên tồn tại");
        assertEquals("Test News Title", saved.getTitle());
    }
    
    @Test
    @Order(2)
    @DisplayName("TC_NEWS_002: Insert tin với ID trùng - Thất bại")
    public void testInsert_DuplicateId_Fail() {
        News news = createValidNews(testNewsId);
        boolean result = newsDAO.insert(news);
        
        assertFalse(result, "Insert với ID trùng nên return false");
    }
    
    @Test
    @Order(3)
    @DisplayName("TC_NEWS_003: Insert tin với Title rỗng - Thất bại")
    public void testInsert_EmptyTitle_Fail() {
        News news = createValidNews("TEST_N002");
        news.setTitle("");
        
        assertThrows(Exception.class, () -> newsDAO.insert(news),
            "Insert với title rỗng nên throw exception");
    }
    
    @Test
    @Order(4)
    @DisplayName("TC_NEWS_004: Insert tin với CategoryId không tồn tại - Thất bại")
    public void testInsert_InvalidCategory_Fail() {
        News news = createValidNews("TEST_N003");
        news.setCategoryId("INVALID_CAT");
        
        assertThrows(Exception.class, () -> newsDAO.insert(news),
            "Insert với category không hợp lệ nên throw exception");
    }
    
    @Test
    @Order(5)
    @DisplayName("TC_NEWS_005: Insert tin với Title 255 ký tự (boundary) - Thành công")
    public void testInsert_TitleMaxLength_Success() {
        String maxTitle = "A".repeat(255);
        News news = createValidNews("TEST_BOUNDARY");
        news.setTitle(maxTitle);
        
        boolean result = newsDAO.insert(news);
        assertTrue(result, "Insert với title max length nên thành công");
        
        newsDAO.delete("TEST_BOUNDARY");
    }
    
    // ========== TC_NEWS_006-010: UPDATE TESTS ==========
    
    @Test
    @Order(6)
    @DisplayName("TC_NEWS_006: Update tin với dữ liệu hợp lệ - Thành công")
    public void testUpdate_ValidData_Success() {
        News news = newsDAO.findById(testNewsId);
        assertNotNull(news, "Tin phải tồn tại trước khi update");
        
        String newTitle = "Updated Title " + System.currentTimeMillis();
        news.setTitle(newTitle);
        news.setContent("Updated content");
        
        newsDAO.update(news);
        
        News updated = newsDAO.findById(testNewsId);
        assertEquals(newTitle, updated.getTitle());
    }
    
    @Test
    @Order(7)
    @DisplayName("TC_NEWS_007: Update tin với ID không tồn tại - Không có exception")
    public void testUpdate_NonExistentId_NoException() {
        News news = createValidNews("NON_EXISTENT_ID");
        
        assertDoesNotThrow(() -> newsDAO.update(news),
            "Update với ID không tồn tại không nên throw exception");
    }
    
    @Test
    @Order(8)
    @DisplayName("TC_NEWS_008: Update chỉ Title, giữ nguyên các field khác")
    public void testUpdate_OnlyTitle_OtherFieldsUnchanged() {
        News original = newsDAO.findById(testNewsId);
        String originalContent = original.getContent();
        
        original.setTitle("Title Only Update");
        newsDAO.update(original);
        
        News updated = newsDAO.findById(testNewsId);
        assertEquals("Title Only Update", updated.getTitle());
        assertEquals(originalContent, updated.getContent());
    }
    
    // ========== TC_NEWS_009-011: DELETE TESTS ==========
    
    @Test
    @Order(9)
    @DisplayName("TC_NEWS_009: Delete tin với ID hợp lệ - Thành công")
    public void testDelete_ValidId_Success() {
        String deleteId = "TEST_DELETE_001";
        News news = createValidNews(deleteId);
        newsDAO.insert(news);
        
        newsDAO.delete(deleteId);
        
        News deleted = newsDAO.findById(deleteId);
        assertNull(deleted, "Tin đã xóa không nên tồn tại");
    }
    
    @Test
    @Order(10)
    @DisplayName("TC_NEWS_010: Delete tin với ID không tồn tại - Không có exception")
    public void testDelete_NonExistentId_NoException() {
        assertDoesNotThrow(() -> newsDAO.delete("NON_EXISTENT_ID"),
            "Delete với ID không tồn tại không nên throw exception");
    }
    
    // ========== TC_NEWS_011-013: FIND BY ID TESTS ==========
    
    @Test
    @Order(11)
    @DisplayName("TC_NEWS_011: FindById với ID hợp lệ - Trả về News")
    public void testFindById_ValidId_ReturnNews() {
        News news = newsDAO.findById(testNewsId);
        
        assertNotNull(news, "FindById nên trả về News object");
        assertEquals(testNewsId, news.getId());
    }
    
    @Test
    @Order(12)
    @DisplayName("TC_NEWS_012: FindById với ID không tồn tại - Trả về null")
    public void testFindById_NonExistentId_ReturnNull() {
        News news = newsDAO.findById("NON_EXISTENT_ID");
        assertNull(news, "FindById với ID không tồn tại nên return null");
    }
    
    @Test
    @Order(13)
    @DisplayName("TC_NEWS_013: FindById với ID null - Trả về null")
    public void testFindById_NullId_ReturnNull() {
        News news = newsDAO.findById(null);
        assertNull(news, "FindById với ID null nên return null");
    }
    
    // ========== TC_NEWS_014-015: GET ALL TESTS ==========
    
    @Test
    @Order(14)
    @DisplayName("TC_NEWS_014: GetAll - Trả về List không null")
    public void testGetAll_ReturnNonNullList() {
        List<News> list = newsDAO.getAll();
        
        assertNotNull(list, "GetAll nên trả về List không null");
        assertTrue(list.size() > 0, "List nên có ít nhất 1 tin");
    }
    
    @Test
    @Order(15)
    @DisplayName("TC_NEWS_015: GetHomeNews - Chỉ trả về tin có Home=true")
    public void testGetHomeNews_OnlyHomeNews() {
        List<News> homeNews = newsDAO.getHomeNews();
        
        assertNotNull(homeNews, "GetHomeNews nên trả về List");
        
        // Verify tất cả tin đều có home = true
        for (News n : homeNews) {
            assertTrue(n.isHome(), "Tất cả tin trong homeNews nên có Home=true");
        }
    }
    
    // ========== HELPER METHOD ==========
    
    private News createValidNews(String id) {
        News news = new News();
        news.setId(id);
        news.setTitle("Test News Title");
        news.setContent("Test content for news");
        news.setImage("img/test.jpg");
        news.setPostedDate(Date.valueOf(LocalDate.now()));
        news.setAuthor("admin");
        news.setCategoryId("TT");
        news.setHome(true);
        news.setViewCount(0);
        return news;
    }
}