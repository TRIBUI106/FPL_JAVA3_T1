<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ page import="entity.User" %>
<%
    String currentPath = request.getServletPath();
    // Đảm bảo currentPath luôn có giá trị
    if (currentPath == null) currentPath = "";
    
    // Lấy user từ session
    User currentUser = (User) session.getAttribute("user");
    boolean isAdmin = (currentUser != null && currentUser.isAdmin());
%>

<div class="bg-primary text-white p-4" style="width: 280px; min-height: 100vh;">
    <h3 class="mb-5 fw-bold">ABC NEWS</h3>
    <nav class="nav flex-column gap-3 fs-5">
        <!-- Trang chủ - Chỉ admin mới thấy -->
        <% if (isAdmin) { %>
        <a href="${pageContext.request.contextPath}/admin/dashboard"
           class="nav-link text-white ${fn:contains(currentPath, 'dashboard') ? 'active bg-white text-primary fw-bold' : ''}">
            <i class="bi bi-house"></i> Trang chủ
        </a>
        <% } %>
        
        <!-- Quản lý tin tức - Cả admin và reporter đều thấy -->
        <a href="${pageContext.request.contextPath}/admin/news"
           class="nav-link text-white ${fn:contains(currentPath, 'news') ? 'active bg-white text-primary fw-bold' : ''}">
            <i class="bi bi-newspaper"></i> Quản lý tin tức
        </a>
        
        <!-- Các menu khác - Chỉ admin mới thấy -->
        <% if (isAdmin) { %>
        <a href="${pageContext.request.contextPath}/admin/category"
           class="nav-link text-white ${fn:contains(currentPath, 'category') ? 'active bg-white text-primary fw-bold' : ''}">
            <i class="bi bi-tags"></i> Quản lý loại tin
        </a>
        <a href="${pageContext.request.contextPath}/admin/user"
           class="nav-link text-white ${fn:contains(currentPath, 'user') ? 'active bg-white text-primary fw-bold' : ''}">
            <i class="bi bi-people"></i> Quản lý người dùng
        </a>
        <a href="${pageContext.request.contextPath}/admin/newsletter"
           class="nav-link text-white ${fn:contains(currentPath, 'newsletter') ? 'active bg-white text-primary fw-bold' : ''}">
            <i class="bi bi-envelope"></i> Email nhận thông báo
        </a>
        <% } %>

        <hr class="border-light opacity-50">

        <a href="${pageContext.request.contextPath}/home" class="nav-link text-warning">
            <i class="bi bi-box-arrow-up-right"></i> Xem trang người dùng
        </a>

        <a href="${pageContext.request.contextPath}/admin/logout" class="nav-link text-warning">
            <i class="bi bi-arrow-up-left-circle-fill"></i> Đăng xuất
        </a>

    </nav>
</div>