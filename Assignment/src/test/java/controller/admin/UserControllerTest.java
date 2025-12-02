package controller.admin;

import dao.UserDAO;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.io.IOException;
import java.sql.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private RequestDispatcher dispatcher;
    @Mock private UserDAO userDAO;

    private UserController controller;
    private ArgumentCaptor<String> captor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new UserController();
        
        // Inject mock DAO vào controller (vì nó dùng new UserDAO())
        try {
            var field = UserController.class.getDeclaredField("dao");
            field.setAccessible(true);
            field.set(controller, userDAO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getCharacterEncoding()).thenReturn("UTF-8");
        captor = ArgumentCaptor.forClass(String.class);
    }

    @Test
    @DisplayName("AddNewReporterSuccess")
    void testAddNewReporterSuccess() throws ServletException, IOException {
        // Given
        when(request.getParameter("fullname")).thenReturn("Nguyễn Văn A");
        when(request.getParameter("email")).thenReturn("nguyenvana@gmail.com");
        when(request.getParameter("mobile")).thenReturn("0905123456");
        when(request.getParameter("birthday")).thenReturn("1995-05-20");
        when(request.getParameter("gender")).thenReturn("male");
        when(request.getParameter("role")).thenReturn("false");
        when(request.getParameter("password")).thenReturn("123456");

        when(userDAO.isEmailExists(anyString(), isNull())).thenReturn(false);
        when(userDAO.generateUserId()).thenReturn("US007");
        when(userDAO.insert(any(User.class))).thenReturn(true);
        controller.doPost(request, response);
        verify(userDAO).insert(argThat(u -> 
            u.getId().equals("US007") &&
            u.getFullname().equals("Nguyễn Văn A") &&
            u.getEmail().equals("nguyenvana@gmail.com") &&
            u.getRole() == false &&
            u.getPassword().equals("123456")
        ));
        verify(request).setAttribute(eq("msg"), contains("US007"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("AddNewAdminSuccess")
    void testAddNewAdminSuccess() throws ServletException, IOException {
        when(request.getParameter("fullname")).thenReturn("Admin Mới");
        when(request.getParameter("email")).thenReturn("adminmoi@gmail.com");
        when(request.getParameter("role")).thenReturn("true");
        when(request.getParameter("password")).thenReturn("admin123");

        when(userDAO.isEmailExists(anyString(), isNull())).thenReturn(false);
        when(userDAO.generateUserId()).thenReturn("US008");
        when(userDAO.insert(any(User.class))).thenReturn(true);

        controller.doPost(request, response);

        verify(userDAO).insert(argThat(u -> u.getRole() == true && u.getId().equals("US008")));
        verify(request).setAttribute(eq("msg"), contains("Thêm người dùng thành công"));
    }

    @Test
    @DisplayName("AddUserEmailExists")
    void testAddUserEmailExists() throws ServletException, IOException {
        when(request.getParameter("fullname")).thenReturn("Test User");
        when(request.getParameter("email")).thenReturn("exists@gmail.com");
        when(request.getParameter("password")).thenReturn("123");

        when(userDAO.isEmailExists("exists@gmail.com", null)).thenReturn(true);

        controller.doPost(request, response);

        verify(userDAO, never()).insert(any());
        verify(request).setAttribute(eq("error"), contains("Email này đã được sử dụng"));
    }

    @Test
    @DisplayName("EditUserSuccess")
    void testEditUserSuccess() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("US005");
        when(request.getParameter("fullname")).thenReturn("Nguyễn Văn B Updated");
        when(request.getParameter("email")).thenReturn("updated@gmail.com");
        when(request.getParameter("password")).thenReturn(""); // không đổi pass

        when(userDAO.isIdExists("US005")).thenReturn(true);
        when(userDAO.isEmailExists("updated@gmail.com", "US005")).thenReturn(false);
        when(userDAO.update(any(User.class), eq(false))).thenReturn(true);

        controller.doPost(request, response);

        verify(userDAO).update(argThat(u -> 
            u.getId().equals("US005") && 
            u.getFullname().equals("Nguyễn Văn B Updated")
        ), eq(false));
        verify(request).setAttribute(eq("msg"), contains("Cập nhật thành công"));
    }

    @Test
    @DisplayName("Delete User Success")
    void testDeleteUserSuccess() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("US009");
        when(session.getAttribute("userId")).thenReturn("US001");

        when(userDAO.delete("US009")).thenReturn(true);

        controller.doGet(request, response);

        verify(userDAO).delete("US009");
        verify(request).setAttribute("msg", "Xóa thành công!");
    }

    @Test
    @DisplayName("Cannot Delete Self")
    void testCannotDeleteSelf() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("US001");
        when(session.getAttribute("userId")).thenReturn("US001");

        controller.doGet(request, response);

        verify(userDAO, never()).delete(anyString());
        verify(request).setAttribute("error", "Không thể tự xóa chính mình!");
    }

    

    @Test
    @DisplayName("TC023 - Thêm user thiếu mật khẩu → lỗi")
    void testAddUserMissingPassword() throws ServletException, IOException {
        when(request.getParameter("fullname")).thenReturn("Test");
        when(request.getParameter("email")).thenReturn("test@gmail.com");
        when(request.getParameter("password")).thenReturn("");

        when(userDAO.isEmailExists(anyString(), isNull())).thenReturn(false);

        controller.doPost(request, response);

        verify(userDAO, never()).insert(any());
        verify(request).setAttribute(eq("error"), contains("Mật khẩu không được để trống"));
    }

    @Test
    @DisplayName("TC026 - Sửa user với email đã tồn tại (của người khác)")
    void testEditUserEmailExists() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("US010");
        when(request.getParameter("email")).thenReturn("used@gmail.com");

        when(userDAO.isIdExists("US010")).thenReturn(true);
        when(userDAO.isEmailExists("used@gmail.com", "US010")).thenReturn(true);

        controller.doPost(request, response);

        verify(userDAO, never()).update(any(), anyBoolean());
        verify(request).setAttribute(eq("error"), contains("Email này đã được sử dụng"));
    }
}