<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý Newsletter - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../assets/css/admin-style.css">
</head>
<body class="admin-body">
<div class="row g-0">
    <div class="col-md-3 admin-sidebar">
        <!-- Copy sidebar giống dashboard.jsp -->
        <div class="text-center py-4"><h4 class="text-white">ABC NEWS</h4></div>
        <nav class="nav flex-column">
            <a href="dashboard.jsp" class="nav-link">Trang chủ</a>
            <a href="news.jsp" class="nav-link">Quản lý tin tức</a>
            <a href="category-manager.jsp" class="nav-link">Quản lý loại tin</a>
            <a href="user-manager.jsp" class="nav-link">Quản lý người dùng</a>
            <a href="newsletter.jsp" class="nav-link active">Quản lý Newsletter</a>
        </nav>
    </div>
    <div class="col-md-9">
        <div class="admin-header text-white text-center py-4">
            <h2>QUẢN LÝ ĐĂNG KÝ NHẬN TIN</h2>
        </div>
        <div class="container my-5">
            <div class="admin-content">
                <table class="table table-striped">
                    <thead class="table-dark">
                        <tr><th>STT</th><th>Email</th><th>Trạng thái</th><th>Thao tác</th></tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>nguyenvana@gmail.com</td>
                            <td><span class="badge bg-success">Đang hoạt động</span></td>
                            <td><button class="btn btn-danger btn-sm">Hủy</button></td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>abc123@gmail.com</td>
                            <td><span class="badge bg-success">Đang hoạt động</span></td>
                            <td><button class="btn btn-danger btn-sm">Hủy</button></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>