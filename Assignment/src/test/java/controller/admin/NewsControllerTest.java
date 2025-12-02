package controller.admin;

import dao.NewsDAO;
import entity.Category;
import entity.News;
import entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class NewsControllerTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private RequestDispatcher dispatcher;
    @Mock private Part imagePart;
    @Mock private ServletContext servletContext;  
    @Mock private NewsDAO newsDAO;
    private NewsController controller;
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new NewsController();
        var field = NewsController.class.getDeclaredField("dao");
        field.setAccessible(true);
        field.set(controller, newsDAO);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getServletContext()).thenReturn(servletContext);  
        when(servletContext.getRealPath("/img")).thenReturn("/tmp/test/img"); 

       
        when(request.getPart("image")).thenReturn(imagePart);
        when(imagePart.getHeader("content-disposition"))
            .thenReturn("form-data; name=\"image\"; filename=\"test.jpg\"");
        when(imagePart.getSize()).thenReturn(1024L);
    }

    @Test
    @DisplayName("ViewAllNews")
    void testViewAllNews() throws ServletException, IOException {
        when(session.getAttribute("user")).thenReturn(new User());
        when(newsDAO.getAll()).thenReturn(Arrays.asList(new News()));
        when(newsDAO.getAllCate()).thenReturn(Arrays.asList(new Category("TT", "Thể thao")));

        controller.doGet(request, response);

        verify(request).setAttribute(eq("listNews"), anyList());
        verify(request).setAttribute(eq("categories"), anyList());
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("AddNewsSuccess_WithImage")
    void testAddNewsSuccess_WithImage() throws ServletException, IOException {
        setupLoggedInUser();
        when(request.getParameter("title")).thenReturn("Tin nóng");
        when(request.getParameter("content")).thenReturn("Nội dung chi tiết...");
        when(request.getParameter("categoryId")).thenReturn("TT");
        when(request.getParameter("home")).thenReturn("on");
        when(imagePart.getSize()).thenReturn(50000L);
        controller.doPost(request, response);
        verify(newsDAO).insert(argThat(n ->
            n.getTitle().equals("Tin nóng") &&
            n.getImage() != null &&
            n.getImage().endsWith(".jpg") &&
            n.getPostedDate().equals(Date.valueOf(LocalDate.now())) &&
            n.getAuthor().equals("US001")
        ));
        verify(session).setAttribute(eq("message"), contains("thành công"));
        verify(response).sendRedirect("/admin/news");
    }

    @Test
    @DisplayName("AddNews_NoImage")
    void testAddNews_NoImage() throws ServletException, IOException {
        setupLoggedInUser();
        when(request.getParameter("title")).thenReturn("Tin không ảnh");
        when(request.getParameter("content")).thenReturn("Nội dung...");
        when(request.getParameter("categoryId")).thenReturn("GT");
        when(imagePart.getSize()).thenReturn(0L);
        controller.doPost(request, response);
        verify(newsDAO).insert(argThat(n ->
            n.getImage() == null || n.getImage().contains("default-news.jpg")
        ));
    }

    @Test
    @DisplayName("AddNews_MissingTitle")
    void testAddNews_MissingTitle() throws ServletException, IOException {
        setupLoggedInUser();
        when(request.getParameter("title")).thenReturn("   ");
        controller.doPost(request, response);
        verify(newsDAO, never()).insert(any());
        verify(session).setAttribute("message", "Tiêu đề không được để trống!");
        verify(response).sendRedirect("/admin/news");
    }

    @Test
    @DisplayName("UpdateNews_KeepOldImage")
    void testUpdateNews_KeepOldImage() throws ServletException, IOException {
        setupLoggedInUser();
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("id")).thenReturn("N001");
        when(request.getParameter("title")).thenReturn("Tin đã sửa");
        when(request.getParameter("content")).thenReturn("Nội dung mới");
        when(request.getParameter("categoryId")).thenReturn("TT");
        when(imagePart.getSize()).thenReturn(0L); 
        News old = new News();
        old.setImage("img/old.jpg");
        when(newsDAO.findById("N001")).thenReturn(old);
        controller.doPost(request, response);
        verify(newsDAO).update(argThat(n ->
            n.getId().equals("N001") &&
            n.getImage().equals("img/old.jpg")
        ));
        verify(session).setAttribute(eq("message"), contains("Cập nhật tin thành công"));
    }

    @Test
    @DisplayName("DeleteNews")
    void testDeleteNews() throws ServletException, IOException {
        when(session.getAttribute("user")).thenReturn(new User());
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("N999");
        controller.doGet(request, response);
        verify(newsDAO).delete("N999");
        verify(response).sendRedirect("/admin/news");
    }

    @Test
    @DisplayName("NotLoggedIn_RedirectLogin")
    void testNotLoggedIn_RedirectLogin() throws ServletException, IOException {
        when(session.getAttribute("user")).thenReturn(null);
        controller.doGet(request, response);
        verify(response).sendRedirect("/login");
    }
    private void setupLoggedInUser() {
        User user = new User();
        user.setId("US001");
        when(session.getAttribute("user")).thenReturn(user);
    }
}