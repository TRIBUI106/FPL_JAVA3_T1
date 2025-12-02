package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import dao.CategoryDAO;
import dao.NewsDAO;
import entity.News;
import entity.Category;
import utils.XJdbc;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Unit Test cho NewsDAO
 * Test Cases: TC17, TC18, TC19, TC20
 */
public class NewsDAOTest {
    
    private NewsDAO newsDAO;
    private CategoryDAO categoryDAO;
    private static final String TEST_NEWS_ID = "N001";
    private static final String TEST_CATEGORY_ID = "TC";
    
    @Before
    public void setUp() {
        newsDAO = new NewsDAO();
        categoryDAO = new CategoryDAO();
        cleanUpTestData();
        setupTestCategory();
    }
    
    @After
    public void tearDown() {
        cleanUpTestData();
    }
    
    private void setupTestCategory() {
        try {
            Category testCategory = new Category(TEST_CATEGORY_ID, "Test Category");
            categoryDAO.insert(testCategory);
        } catch (Exception e) {
            // Category might already exist
        }
    }
    
    private void cleanUpTestData() {
        try {
            XJdbc.executeUpdate("DELETE FROM NEWS WHERE Id LIKE 'N%'");
            XJdbc.executeUpdate("DELETE FROM CATEGORIES WHERE Id = ?", TEST_CATEGORY_ID);
        } catch (Exception e) {
            // Ignore
        }
    }
    
    /**
     * TC17: Kiểm tra insert News thành công
     * Expected: Return true, news được lưu vào database
     */
    @Test
    public void testInsertNewsSuccessfully() {
        // Arrange
        News news = new News();
        news.setId(TEST_NEWS_ID);
        news.setTitle("Test News Title");
        news.setContent("This is test news content");
        news.setImage("img/test.jpg");
        news.setPostedDate(Date.valueOf(LocalDate.now()));
        news.setAuthor("TESTAUTHOR");
        news.setViewCount(0);
        news.setCategoryId(TEST_CATEGORY_ID);
        news.setHome(true);
        
        // Act
        boolean result = newsDAO.insert(news);
        
        // Assert
        assertTrue("Should insert news successfully", result);
        
        // Verify in database
        News inserted = newsDAO.findById(TEST_NEWS_ID);
        assertNotNull("News should exist in database", inserted);
        assertEquals("Title should match", "Test News Title", inserted.getTitle());
        assertEquals("Category ID should match", TEST_CATEGORY_ID, inserted.getCategoryId());
    }
    
    /**
     * TC18: Kiểm tra insert News với ID trùng
     * Expected: Return false, không thêm được
     */
    @Test
    public void testInsertNewsWithDuplicateId() {
        // Arrange - Insert first news
        News news1 = new News();
        news1.setId(TEST_NEWS_ID);
        news1.setTitle("First News");
        news1.setContent("First content");
        news1.setImage("img/first.jpg");
        news1.setPostedDate(Date.valueOf(LocalDate.now()));
        news1.setAuthor("AUTHOR1");
        news1.setViewCount(0);
        news1.setCategoryId(TEST_CATEGORY_ID);
        news1.setHome(false);
        
        newsDAO.insert(news1);
        
        // Act - Try to insert with same ID
        News news2 = new News();
        news2.setId(TEST_NEWS_ID); // Same ID
        news2.setTitle("Second News");
        news2.setContent("Second content");
        news2.setImage("img/second.jpg");
        news2.setPostedDate(Date.valueOf(LocalDate.now()));
        news2.setAuthor("AUTHOR2");
        news2.setViewCount(0);
        news2.setCategoryId(TEST_CATEGORY_ID);
        news2.setHome(false);
        
        boolean result = newsDAO.insert(news2);
        
        // Assert
        assertFalse("Should not insert news with duplicate ID", result);
        
        // Verify original news still exists with original data
        News existing = newsDAO.findById(TEST_NEWS_ID);
        assertEquals("Should keep original title", "First News", existing.getTitle());
    }
    
    /**
     * TC19: Kiểm tra getHomeNews
     * Expected: Return List không null, chỉ chứa news có Home=true
     */
    @Test
    public void testGetHomeNews() {
        // Arrange - Insert multiple news, some with Home=true
        News homeNews1 = createTestNews("NH001", "Home News 1", true);
        News homeNews2 = createTestNews("NH002", "Home News 2", true);
        News regularNews = createTestNews("NR001", "Regular News", false);
        
        newsDAO.insert(homeNews1);
        newsDAO.insert(homeNews2);
        newsDAO.insert(regularNews);
        
        // Act
        List<News> homeNewsList = newsDAO.getHomeNews();
        
        // Assert
        assertNotNull("Home news list should not be null", homeNewsList);
        assertTrue("Should have at least 2 home news", homeNewsList.size() >= 2);
        
        // Verify all returned news have Home=true
        for (News news : homeNewsList) {
            assertTrue("All news should have Home=true", news.isHome());
        }
        
        // Verify regular news is not in the list
        boolean containsRegular = homeNewsList.stream()
            .anyMatch(n -> "NR001".equals(n.getId()));
        assertFalse("Should not contain regular news", containsRegular);
        
        // Cleanup
        newsDAO.delete("NH001");
        newsDAO.delete("NH002");
        newsDAO.delete("NR001");
    }
    
    /**
     * TC20: Kiểm tra getLatestNews
     * Expected: Return List có tối đa 5 news, sắp xếp theo ngày giảm dần
     */
    @Test
    public void testGetLatestNews() {
        // Arrange - Insert 7 news with different dates
        LocalDate baseDate = LocalDate.now();
        
        for (int i = 1; i <= 7; i++) {
            News news = new News();
            news.setId("NL00" + i);
            news.setTitle("Latest News " + i);
            news.setContent("Content " + i);
            news.setImage("img/news" + i + ".jpg");
            news.setPostedDate(Date.valueOf(baseDate.minusDays(i)));
            news.setAuthor("AUTHOR");
            news.setViewCount(0);
            news.setCategoryId(TEST_CATEGORY_ID);
            news.setHome(false);
            
            newsDAO.insert(news);
        }
        
        // Act
        List<News> latestNews = newsDAO.getLatestNews();
        
        // Assert
        assertNotNull("Latest news list should not be null", latestNews);
        assertTrue("Should return at most 5 news", latestNews.size() <= 5);
        assertEquals("Should return exactly 5 news", 5, latestNews.size());
        
        // Verify sorted by date descending (newest first)
        for (int i = 0; i < latestNews.size() - 1; i++) {
            Date currentDate = latestNews.get(i).getPostedDate();
            Date nextDate = latestNews.get(i + 1).getPostedDate();
            assertTrue("Should be sorted by date descending", 
                currentDate.compareTo(nextDate) >= 0);
        }
        
        // Verify the newest news is first
        assertEquals("First news should be NL001", "NL001", latestNews.get(0).getId());
        
        // Cleanup
        for (int i = 1; i <= 7; i++) {
            newsDAO.delete("NL00" + i);
        }
    }
    
    /**
     * TC - Additional: Test update news
     */
    @Test
    public void testUpdateNewsSuccessfully() {
        // Arrange - Insert news first
        News news = createTestNews(TEST_NEWS_ID, "Original Title", false);
        newsDAO.insert(news);
        
        // Act - Update news
        news.setTitle("Updated Title");
        news.setContent("Updated content");
        news.setViewCount(100);
        newsDAO.update(news);
        
        // Assert
        News updated = newsDAO.findById(TEST_NEWS_ID);
        assertNotNull("News should still exist", updated);
        assertEquals("Title should be updated", "Updated Title", updated.getTitle());
        assertEquals("Content should be updated", "Updated content", updated.getContent());
        assertEquals("View count should be updated", 100, updated.getViewCount());
    }
    
    /**
     * TC - Additional: Test delete news
     */
    @Test
    public void testDeleteNewsSuccessfully() {
        // Arrange - Insert news first
        News news = createTestNews(TEST_NEWS_ID, "To Be Deleted", false);
        newsDAO.insert(news);
        
        // Act
        newsDAO.delete(TEST_NEWS_ID);
        
        // Assert
        News deleted = newsDAO.findById(TEST_NEWS_ID);
        assertNull("News should not exist after deletion", deleted);
    }
    
    /**
     * TC - Additional: Test getAll news
     */
    @Test
    public void testGetAllNews() {
        // Arrange - Insert multiple news
        News news1 = createTestNews("NA001", "News A", false);
        News news2 = createTestNews("NA002", "News B", false);
        newsDAO.insert(news1);
        newsDAO.insert(news2);
        
        // Act
        List<News> allNews = newsDAO.getAll();
        
        // Assert
        assertNotNull("News list should not be null", allNews);
        assertTrue("Should have at least 2 news", allNews.size() >= 2);
        
        // Verify sorted by PostedDate DESC
        if (allNews.size() > 1) {
            Date firstDate = allNews.get(0).getPostedDate();
            Date secondDate = allNews.get(1).getPostedDate();
            assertTrue("Should be sorted by date descending", 
                firstDate.compareTo(secondDate) >= 0);
        }
        
        // Cleanup
        newsDAO.delete("NA001");
        newsDAO.delete("NA002");
    }
    
    /**
     * TC - Additional: Test findById with non-existent ID
     */
    @Test
    public void testFindByIdNonExistent() {
        News news = newsDAO.findById("NOTEXIST999");
        assertNull("Should return null for non-existent ID", news);
    }
    
    // Helper method to create test news
    private News createTestNews(String id, String title, boolean home) {
        News news = new News();
        news.setId(id);
        news.setTitle(title);
        news.setContent("Test content for " + title);
        news.setImage("img/test.jpg");
        news.setPostedDate(Date.valueOf(LocalDate.now()));
        news.setAuthor("TESTAUTHOR");
        news.setViewCount(0);
        news.setCategoryId(TEST_CATEGORY_ID);
        news.setHome(home);
        return news;
    }
}