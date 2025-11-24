<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý người dùng - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
</head>
<body>
<div class="d-flex min-vh-100">
    <%@ include file="../layout/admin-sidebar.jsp" %>
    <div class="flex-grow-1 bg-light">
        <%@ include file="../layout/admin-header.jsp" %>
        <div class="container my-5">
            <h2 class="text-primary fw-bold mb-4">QUẢN LÝ NGƯỜI DÙNG</h2>
            <button class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#addModal">Thêm người dùng</button>
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr><th>ID</th><th>Họ tên</th><th>Email</th><th>Vai trò</th><th>Hành động</th></tr>
                </thead>
                <tbody>
                    <c:forEach items="${listUsers}" var="u">
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.fullname}</td>
                        <td>${u.email}</td>
                        <td><span class="badge ${u.role ? 'bg-danger' : 'bg-primary'}">${u.role ? 'Quản trị' : 'Phóng viên'}</span></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/user?action=edit&id=${u.id}" class="btn btn-warning btn-sm">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/user?action=delete&id=${u.id}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa người dùng này?')">Xóa</a>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal thêm người dùng -->
<div class="modal fade" id="addModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/admin/user" method="post">
                <div class="modal-header">
                    <h5 class="modal-title">Thêm người dùng</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input name="id" class="form-control mb-3" placeholder="Mã người dùng" required>
                    <input name="fullname" class="form-control mb-3" placeholder="Họ tên" required>
                    <input name="email" class="form-control mb-3" placeholder="Email" required>
                    <input type="password" name="password" class="form-control mb-3" placeholder="Mật khẩu" required>
                    <select name="role" class="form-select mb-3">
                        <option value="false">Phóng viên</option>
                        <option value="true">Quản trị viên</option>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>