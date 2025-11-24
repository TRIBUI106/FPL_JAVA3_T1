<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html><head><title>Quản lý tin tức</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="../assets/css/admin-style.css">
</head>
<body class="admin-body">
<div class="row g-0">
    <%@ include file="sidebar.jsp" %>
    <div class="col-md-9">
        <%@ include file="header.jsp" %>
        <div class="container my-5">
            <div class="admin-content">
                <h3>Quản lý tin tức</h3>
                <button class="btn btn-success mb-3">Thêm tin mới</button>
                <table class="table table-hover">
                    <thead class="table-dark"><tr><th>ID</th><th>Tiêu đề</th><th>Tác giả</th><th>Ngày đăng</th><th>Thao tác</th></tr></thead>
                    <tbody>
                        <tr><td>N001</td><td>TP.HCM mưa lớn...</td><td>PV001</td><td>24/11/2025</td>
                            <td><button class="btn btn-warning btn-sm">Sửa</button> <button class="btn btn-danger btn-sm">Xóa</button></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body></html>