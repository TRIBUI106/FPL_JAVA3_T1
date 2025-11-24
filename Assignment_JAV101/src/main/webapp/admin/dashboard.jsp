<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="d-flex">
    <!-- Sidebar -->
    <div class="bg-primary text-white vh-100 px-4 py-5" style="width: 280px;">
        <h3 class="mb-5">ABC NEWS</h3>
        <nav class="nav flex-column gap-2">
            <a href="dashboard.jsp" class="nav-link text-white active"><i class="bi bi-house"></i> Trang chủ</a>
            <a href="news.jsp" class="nav-link text-white"><i class="bi bi-newspaper"></i> Quản lý tin tức</a>
            <a href="category.jsp" class="nav-link text-white"><i class="bi bi-tags"></i> Loại tin</a>
            <a href="user.jsp" class="nav-link text-white"><i class="bi bi-people"></i> Người dùng</a>
            <a href="newsletter.jsp" class="nav-link text-white"><i class="bi bi-envelope"></i> Newsletter</a>
            <hr>
            <a href="${pageContext.request.contextPath}/" class="nav-link text-warning"><i class="bi bi-box-arrow-in-right"></i> Xem trang chủ</a>
        </nav>
    </div>

    <!-- Main -->
    <div class="flex-grow-1 p-5">
        <h2 class="mb-4">CÔNG CỤ QUẢN TRỊ</h2>
        <div class="alert alert-success">
            <strong>Chào mừng ${sessionScope.user != null ? sessionScope.user.fullname : 'Quản trị viên'}!</strong>
        </div>
        <div class="row g-4">
            <div class="col-md-4">
                <div class="card text-center p-4 shadow-sm">
                    <i class="bi bi-newspaper display-4 text-primary"></i>
                    <h3>156</h3>
                    <p>Tin tức</p>
                </div>
            </div>
            <!-- Thêm card khác -->
        </div>
    </div>
</div>
</body>
</html>