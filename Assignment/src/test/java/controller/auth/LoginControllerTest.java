package controller.auth;

import dao.LoginDAO;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    private LoginDAO loginDAO;

    @InjectMocks
    private LoginController loginController;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;

    private LoginController controller;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new LoginController();

        // Inject mock DAO
        var field = LoginController.class.getDeclaredField("loginDAO");
        field.setAccessible(true);
        field.set(controller, loginDAO);

        // Mock session luôn có sẵn
        when(request.getSession()).thenReturn(session);
        when(request.getSession(anyBoolean())).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    @DisplayName("LoginSuccess - Admin")
    void testLoginSuccess_Admin() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("123456");
        User admin = new User();
        admin.setId("admin");
        admin.setRole(true);
        when(loginDAO.login("admin", "123456")).thenReturn(admin);
        controller.doPost(request, response);
        verify(session).setAttribute("user", admin);
        verify(response).sendRedirect("/admin/dashboard");
    }

    @Test
    @DisplayName("LoginSuccess - Phóng viên")
    void testLoginSuccess_Reporter() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("PV001");
        when(request.getParameter("password")).thenReturn("pvpass");
        User reporter = new User();
        reporter.setId("PV001");
        reporter.setRole(false);
        when(loginDAO.login("PV001", "pvpass")).thenReturn(reporter);
        controller.doPost(request, response);
        verify(response).sendRedirect("/guest/home.jsp");
    }

    @Test
    @DisplayName("LoginFail WrongCredentials")
    void testLoginFail_WrongCredentials() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("wronguser");
        when(request.getParameter("password")).thenReturn("wrongpass");
        when(loginDAO.login("wronguser", "wrongpass")).thenReturn(null);

        controller.doPost(request, response);

        verify(request).setAttribute("error", "Sai ID hoặc mật khẩu!");
        verify(dispatcher).forward(request, response);
        verify(response, never()).sendRedirect(anyString());
    }

    @Test
    @DisplayName("Login_EmptyFields_StillCallsDAO")
    void testLogin_EmptyFields_StillCallsDAO() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("   ");
        when(request.getParameter("password")).thenReturn("");
        when(loginDAO.login("", "")).thenReturn(null);

        controller.doPost(request, response);
        verify(loginDAO).login("", "");
        verify(request).setAttribute("error", "Sai ID hoặc mật khẩu!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("Login Username = Null")
    void testLogin_UsernameNull() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn(null);
        when(request.getParameter("password")).thenReturn("123");
        when(loginDAO.login(null, "123")).thenReturn(null);
        controller.doPost(request, response);
        verify(loginDAO).login(null, "123");
        verify(request).setAttribute("error", "Sai ID hoặc mật khẩu!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("AlreadyLoggedIn")
    void testAlreadyLoggedIn() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(new User());
        controller.doGet(request, response);
        verify(response).sendRedirect("/admin/dashboard");
    }

    @Test
    @DisplayName("ShowLoginPage")
    void testShowLoginPage() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(null);
        controller.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }

//    @Test
//    @DisplayName("Lỗi hệ thống → hiển thị thông báo")
//    void testLogin_SystemError() throws ServletException, IOException {
//        when(request.getParameter("username")).thenReturn("admin");
//        when(request.getParameter("password")).thenReturn("123");
//
//        doThrow(new RuntimeException("DB error"))
//            .when(loginDAO)
//            .login("admin", "123");
//
//        controller.doPost(request, response);
//
//        verify(request).setAttribute("error", "Hệ thống đang gặp sự cố, vui lòng thử lại sau!");
//        verify(dispatcher).forward(request, response);
//    }
    
}