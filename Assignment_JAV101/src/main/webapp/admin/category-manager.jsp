<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý loại tin - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../assets/css/admin-style.css">
</head>
<body class="admin-body">
<div class="row g-0">
    <!-- Sidebar admin -->
    <div class="col-md-3 admin-sidebar">
        <div class="text-center py-4">
            <h4 class="text-white">ABC NEWS</h4>
            <p class="text-light">Welcome, <strong>Quản trị viên</strong></p>
        </div>
        <nav class="nav flex-column">
            <a href="dashboard.jsp" class="nav-link">Trang chủ</a>
            <a href="news.jsp" class="nav-link">Quản lý tin tức</a>
            <a href="category-manager.jsp" class="nav-link active">Quản lý loại tin</a>
            <a href="user-manager.jsp" class="nav-link">Quản lý người dùng</a>
            <a href="newsletter.jsp" class="nav-link">Quản lý Newsletter</a>
            <a href="../guest/home.jsp" class="nav-link text-warning">Xem trang người dùng</a>
        </nav>
    </div>

    <div class="col-md-9">
        <div class="admin-header text-white text-center py-4">
            <h2>QUẢN LÝ LOẠI TIN</h2>
        </div>
        <div class="container my-5">
            <div class="admin-content">
                <button class="btn btn-success btn-lg mb-4">THÊM LOẠI TIN MỚI</button>
                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr><th>Mã loại tin</th><th>Tên loại tin</th><th>Thao tác</th></tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>TT</td><td>Thời sự</td>
                            <td>
                                <button class="btn btn-warning btn-sm">Sửa</button>
                                <button class="btn btn-danger btn-sm">Xóa</button>
                            </td>
                        </tr>
                        <tr><td>KT</td><td>Kinh tế</td><td><button class="btn btn-warning btn-sm">Sửa</button> <button class="btn btn-danger btn-sm">Xóa</button></td></tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>