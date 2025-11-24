<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Lấy tên file hiện tại để highlight menu (không dùng contains nữa)
    String currentPage = request.getRequestURI();
    String contextPath = request.getContextPath();
    currentPage = currentPage.substring(contextPath.length());
    if (currentPage.startsWith("/admin/")) {
        currentPage = currentPage.substring("/admin/".length());
    }
%>

<div class="col-md-3 admin-sidebar bg-dark text-light">
    <div class="text-center py-4 border-bottom border-secondary">
        <h3 class="mb-0 text-white">ABC NEWS</h3>
        <p class="mb-0 mt-2 text-info">
            Welcome, <strong>${sessionScope.user != null ? sessionScope.user.fullname : 'Quản trị viên'}</strong>
        </p>
    </div>

    <nav class="nav flex-column mt-4">
        <!-- Trang chủ quản trị -->
        <a href="<%=request.getContextPath()%>/admin/dashboard.jsp" 
           class="nav-link px-4 py-3 text-light <%= currentPage.contains("dashboard") ? "active bg-primary" : "" %>">
            Trang chủ quản trị
        </a>

        <!-- Quản lý tin tức -->
        <a href="<%=request.getContextPath()%>/admin/news.jsp" 
           class="nav-link px-4 py-3 text-light <%= currentPage.contains("news.jsp") ? "active bg-primary" : "" %>">
            Quản lý tin tức
        </a>

        <!-- Quản lý loại tin -->
        <a href="<%=request.getContextPath()%>/admin/category-manager.jsp" 
           class="nav-link px-4 py-3 text-light <%= currentPage.contains("category-manager") ? "active bg-primary" : "" %>">
            Quản lý loại tin
        </a>

        <!-- Quản lý người dùng -->
        <a href="<%=request.getContextPath()%>/admin/user-manager.jsp" 
           class="nav-link px-4 py-3 text-light <%= currentPage.contains("user-manager") ? "active bg-primary" : "" %>">
            Quản lý người dùng
        </a>

        <!-- Quản lý Newsletter -->
        <a href="<%=request.getContextPath()%>/admin/newsletter.jsp" 
           class="nav-link px-4 py-3 text-light <%= currentPage.contains("newsletter.jsp") ? "active bg-primary" : "" %>">
            Quản lý Newsletter
        </a>

        <hr class="border-secondary mx-4">

        <!-- Link ra trang người dùng -->
        <a href="<%=request.getContextPath()%>/guest/home.jsp" 
           class="nav-link px-4 py-3 text-warning fw-bold">
            Xem trang người dùng
        </a>

        <!-- Đăng xuất (sẽ làm thật ở giai đoạn 2) -->
        <a href="<%=request.getContextPath()%>/auth/logout.jsp" 
           class="nav-link px-4 py-3 text-danger fw-bold">
            Đăng xuất
        </a>
    </nav>
</div>