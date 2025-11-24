<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý người dùng - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../assets/css/admin-style.css">
</head>
<body class="admin-body">
<div class="row g-0">
    <%@ include file="sidebar.jsp" %> <!-- nếu cậu muốn dùng chung -->
    <div class="col-md-9">
        <div class="admin-header text-white text-center py-4">
            <h2>QUẢN LÝ NGƯỜI DÙNG</h2>
        </div>
        <div class="container my-5">
            <div class="admin-content">
                <button class="btn btn-success btn-lg mb-4">THÊM PHÓNG VIÊN / QUẢN TRỊ</button>
                <table class="table table-striped">
                    <thead class="table-dark">
                        <tr><th>ID</th><th>Họ tên</th><th>Email</th><th>Vai trò</th><th>Thao tác</th></tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>ADMIN</td><td>Quản trị viên</td><td>admin@abc.com</td>
                            <td><span class="badge bg-danger">Quản trị</span></td>
                            <td><button class="btn btn-warning btn-sm">Sửa</button></td>
                        </tr>
                        <tr>
                            <td>PV001</td><td>Nguyễn Văn A</td><td>pv1@abc.com</td>
                            <td><span class="badge bg-primary">Phóng viên</span></td>
                            <td><button class="btn btn-warning btn-sm">Sửa</button> <button class="btn btn-danger btn-sm">Xóa</button></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>