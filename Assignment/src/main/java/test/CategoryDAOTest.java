package test;

import entity.Category;
import entity.News;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test cho CategoryDAO - 10 test cases
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryDAOTest {
    
    private static CategoryDAO categoryDAO;
    private static String testCategoryId = "TEST_CAT";
    
    @BeforeAll
    public static void setupClass() {
        System.out.println("=== BẮT ĐẦU TEST CategoryDAO ===");
        categoryDAO = new CategoryDAO();
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("=== KẾT THÚC TEST CategoryDAO ===");
        try {
            categoryDAO.delete(testCategoryId);
            categoryDAO.delete("TEST_UPDATE");
        } catch (Exception e) {}
    }
    
    // ========== TC_CATEGORY_001-003: INSERT TESTS ==========
    
    @Test
    @Order(1)
    @DisplayName("TC_CATEGORY_001: Insert category với dữ liệu hợp lệ - Thành công")
    public void testInsert_ValidData_Success() {
        Category cat = new Category(testCategoryId, "Test Category");
        
        assertDoesNotThrow(() -> categoryDAO.insert(cat),
            "Insert với dữ liệu hợp lệ không nên throw exception");
        
        Category saved = categoryDAO.findById(testCategoryId);
        assertNotNull(saved, "Category đã lưu nên tồn tại");
        assertEquals("Test Category", saved.getName());
    }
    
    @Test
    @Order(2)
    @DisplayName("TC_CATEGORY_002: Insert category với Name rỗng - Exception")
    public void testInsert_EmptyName_Exception() {
        Category cat = new Category("TEST_EMPTY", "");
        
        assertThrows(Exception.class, () -> categoryDAO.insert(cat),
            "Insert với name rỗng nên throw exception");
    }
    
    @Test
    @Order(3)
    @DisplayName("TC_CATEGORY_003: GenerateId từ tên 1 từ")
    public void testGenerateId_SingleWord_Success() {
        String id = categoryDAO.generateId("Thể thao");
        
        assertNotNull(id, "GenerateId nên trả về string không null");
        assertFalse(id.isEmpty(), "ID không nên rỗng");
        assertTrue(id.matches("[A-Z]+"), "ID nên là chữ hoa");
    }
    
    // ========== TC_CATEGORY_004-006: UPDATE TESTS ==========
    
    @Test
    @Order(4)
    @DisplayName("TC_CATEGORY_004: Update category với dữ liệu hợp lệ - Thành công")
    public void testUpdate_ValidData_Success() {
        Category cat = categoryDAO.findById(testCategoryId);
        assertNotNull(cat, "Category phải tồn tại");
        
        cat.setName("Updated Category Name");
        assertDoesNotThrow(() -> categoryDAO.update(cat),
            "Update không nên throw exception");
        
        Category updated = categoryDAO.findById(testCategoryId);
        assertEquals("Updated Category Name", updated.getName());
    }
    
    @Test
    @Order(5)
    @DisplayName("TC_CATEGORY_005: Update category với ID không tồn tại")
    public void testUpdate_NonExistentId_NoException() {
        Category cat = new Category("NON_EXISTENT", "Some Name");
        
        assertDoesNotThrow(() -> categoryDAO.update(cat),
            "Update với ID không tồn tại không nên throw exception");
    }
    
    // ========== TC_CATEGORY_006-007: DELETE TESTS ==========
    
    @Test
    @Order(6)
    @DisplayName("TC_CATEGORY_006: Delete category với ID hợp lệ - Thành công")
    public void testDelete_ValidId_Success() {
        String deleteId = "TEST_DELETE";
        Category cat = new Category(deleteId, "To Be Deleted");
        categoryDAO.insert(cat);
        
        assertDoesNotThrow(() -> categoryDAO.delete(deleteId),
            "Delete không nên throw exception");
        
        Category deleted = categoryDAO.findById(deleteId);
        assertNull(deleted, "Category đã xóa không nên tồn tại");
    }
    
    @Test
    @Order(7)
    @DisplayName("TC_CATEGORY_007: Delete category với ID không tồn tại")
    public void testDelete_NonExistentId_NoException() {
        assertDoesNotThrow(() -> categoryDAO.delete("NON_EXISTENT_ID"),
            "Delete với ID không tồn tại không nên throw exception");
    }
    
    // ========== TC_CATEGORY_008-010: QUERY TESTS ==========
    
    @Test
    @Order(8)
    @DisplayName("TC_CATEGORY_008: FindById với ID hợp lệ - Trả về Category")
    public void testFindById_ValidId_ReturnCategory() {
        Category cat = categoryDAO.findById(testCategoryId);
        
        assertNotNull(cat, "FindById nên trả về Category object");
        assertEquals(testCategoryId, cat.getId());
    }
    
    @Test
    @Order(9)
    @DisplayName("TC_CATEGORY_009: GetAll - Trả về List không rỗng")
    public void testGetAll_ReturnNonEmptyList() {
        List<Category> list = categoryDAO.getAll();
        
        assertNotNull(list, "GetAll nên trả về List không null");
        assertTrue(list.size() > 0, "List nên có ít nhất 1 category");
    }
    
    @Test
    @Order(10)
    @DisplayName("TC_CATEGORY_010: FindNewsByCategoryId - Trả về List News")
    public void testFindNewsByCategoryId_ReturnList() {
        // Giả sử "TT" là category có tin
        List<News> news = categoryDAO.findNewsByCategoryId("TT");
        
        assertNotNull(news, "FindNewsByCategoryId nên trả về List");
        // Note: List có thể rỗng nếu không có tin nào thuộc category này
    }
}