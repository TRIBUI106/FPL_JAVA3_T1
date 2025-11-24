<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản trị - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
</head>
<body>
<div class="d-flex min-vh-100">
    <!-- Sidebar -->
    <div class="bg-primary text-white p-4" style="width: 280px;">
        <h3 class="mb-5">ABC NEWS</h3>
        <nav class="nav flex-column gap-3">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link text-white active"><i class="bi bi-house"></i> Trang chủ</a>
            <a href="${pageContext.request.contextPath}/admin/news" class="nav-link text-white"><i class="bi bi-newspaper"></i> Quản lý tin tức</a>
            <a href="${pageContext.request.contextPath}/admin/category" class="nav-link text-white"><i class="bi bi-tags"></i> Quản lý loại tin</a>
            <a href="${pageContext.request.contextPath}/admin/user" class="nav-link text-white"><i class="bi bi-people"></i> Quản lý người dùng</a>
            <a href="${pageContext.request.contextPath}/admin/newsletter" class="nav-link text-white"><i class="bi bi-envelope"></i> Newsletter</a>
            <hr>
            <a href="${pageContext.request.contextPath}/home" class="nav-link text-warning"><i class="bi bi-box-arrow-up-right"></i> Xem trang người dùng</a>
        </nav>
    </div>

    <!-- Main -->
    <div class="flex-grow-1 p-5 bg-light">
        <h2 class="text-primary fw-bold mb-5">CÔNG CỤ QUẢN TRỊ TIN TỨC</h2>
        <div class="alert alert-success">
            <strong>Chào mừng ${sessionScope.user.fullname}!</strong> Vai trò: ${sessionScope.user.role ? 'Quản trị viên' : 'Phóng viên'}
        </div>
        <p>Vui lòng chọn chức năng bên trái.</p>
    </div>
</div>
</body>
</html>