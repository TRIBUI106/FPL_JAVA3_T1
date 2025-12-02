package controller.admin;

import dao.CategoryDAO;
import entity.Category;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CategoryAdminControllerTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private RequestDispatcher dispatcher;
    @Mock private CategoryDAO categoryDAO;

    private CategoryAdminController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        controller = new CategoryAdminController();

        // Inject mock DAO vào controller (vì nó dùng new CategoryDAO())
        try {
            var field = CategoryAdminController.class.getDeclaredField("dao");
            field.setAccessible(true);
            field.set(controller, categoryDAO);
        } catch (Exception e) {
            throw new RuntimeException("Cannot inject mock DAO", e);
        }

        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    @DisplayName("TC019 - Quản trị thêm loại tin mới thành công")
    void testAddNewCategorySuccess() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Thể Thao");
        when(request.getParameter("action")).thenReturn(null); // thêm mới

        when(categoryDAO.generateId("Thể Thao")).thenReturn("TT");
        when(categoryDAO.exists("TT")).thenReturn(false);

        controller.doPost(request, response);

        verify(categoryDAO).insert(argThat(c -> c.getId().equals("TT") && c.getName().equals("Thể Thao")));
        verify(session).setAttribute(eq("message"), contains("Thêm mới loại tin \"Thể Thao\" thành công! Mã: TT"));
        verify(response).sendRedirect("/admin/category");
    }

    @Test
    @DisplayName("TC020 - Thêm loại tin bị trùng tên → sinh ID khác (vẫn thành công)")
    void testAddCategoryWithDuplicateName_GenerateNewId() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Tin Tức");
        when(categoryDAO.generateId("Tin Tức")).thenReturn("TTX1"); 
        controller.doPost(request, response);
        verify(categoryDAO).insert(argThat(c -> c.getId().equals("TTX1")));
        verify(session).setAttribute(eq("message"), contains("Mã: TTX1"));
    }

    @Test
    @DisplayName("TC019 - Thêm loại tin với tên rỗng → không làm gì")
    void testAddCategoryEmptyName() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("   ");
        controller.doPost(request, response);
        verify(categoryDAO, never()).insert(any());
        verify(response).sendRedirect("category");
    }

    @Test
    @DisplayName("TC021 - Quản trị sửa tên loại tin → mã không đổi")
    void testUpdateCategory_NameOnly_IdUnchanged() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("id")).thenReturn("TT");
        when(request.getParameter("name")).thenReturn("Thể Thao Mới");
        when(categoryDAO.generateId("Thể Thao Mới")).thenReturn("TT"); 
        controller.doPost(request, response);
        verify(categoryDAO, never()).delete(anyString());
        verify(categoryDAO).update(argThat(c -> c.getId().equals("TT") && c.getName().equals("Thể Thao Mới")));
        verify(session).setAttribute(eq("message"), contains("mã không đổi"));
    }

    @Test
    @DisplayName("TC021 - Sửa tên loại tin → mã bị thay đổi (xóa cũ, thêm mới)")
    void testUpdateCategory_IdChanged() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("id")).thenReturn("TT");
        when(request.getParameter("name")).thenReturn("Giải Trí");
        when(categoryDAO.generateId("Giải Trí")).thenReturn("GT");
        controller.doPost(request, response);
        verify(categoryDAO).delete("TT");
        verify(categoryDAO).insert(argThat(c -> c.getId().equals("GT") && c.getName().equals("Giải Trí")));
        verify(session).setAttribute(eq("message"), contains("Mã loại đã đổi thành: GT"));
    }

    @Test
    @DisplayName("TC022 - Quản trị xóa loại tin thành công")
    void testDeleteCategorySuccess() throws ServletException, IOException {
        when(session.getAttribute("user")).thenReturn(new Object()); 
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("XYZ");
        controller.doGet(request, response);
        verify(categoryDAO).delete("XYZ");
        verify(response).sendRedirect("/admin/category");
    }

    @Test
    @DisplayName("TC022 - Xóa loại tin khi chưa đăng nhập → chuyển về login")
    void testDeleteCategoryNotLoggedIn() throws ServletException, IOException {
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("ABC");
        controller.doGet(request, response);
        verify(categoryDAO, never()).delete(anyString());
        verify(response).sendRedirect("/login");
    }
 
}