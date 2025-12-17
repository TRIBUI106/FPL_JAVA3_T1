<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setBundle basename="locale" scope="application"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
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

            <c:if test="${not empty msg}"><div class="alert alert-success">${msg}</div></c:if>
            <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>

            <div class="card">
                <div class="card-body">
                    <div class="d-flex justify-content-between mb-3">
                        <form class="d-flex" method="get">
                            <input name="keyword" class="form-control me-2" placeholder="Tìm kiếm..." value="${keyword}">
                            <button class="btn btn-outline-primary">Tìm</button>
                        </form>
                        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addModal">Thêm người dùng</button>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th><th>Họ tên</th><th>Email</th><th>SĐT</th><th>Giới tính</th><th>Vai trò</th><th>Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listUsers}" var="u">
                                <tr>
                                    <td>${u.id}</td>
                                    <td>${u.fullname}</td>
                                    <td>${u.email}</td>
                                    <td>${u.mobile}</td>
                                    <td>${u.genderText}</td>
                                    <td><span class="badge ${u.role ? 'bg-danger' : 'bg-primary'}">${u.role ? 'Quản trị' : 'Phóng viên'}</span></td>
                                    <td>
                                        <button onclick="editUser('${u.id}','${u.fullname}','${u.email}','${u.birthday}','${u.gender}','${u.mobile}',${u.role})"
                                                class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#editModal">Sửa</button>
                                        <a href="?action=delete&id=${u.id}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa?')">Xóa</a>
                                    </td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <!-- Phân trang -->
                    <nav>
                        <ul class="pagination justify-content-center">
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" href="?page=${currentPage-1}&keyword=${keyword}">Trước</a>
                            </li>
                            <c:forEach begin="1" end="${totalPage}" var="i">
                                <liavatar class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="?page=${i}&keyword=${keyword}">${i}</a>
                                </li>
                            </c:forEach>
                            <li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
                                <a class="page-link" href="?page=${currentPage+1}&keyword=${keyword}">Sau</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal Thêm -->
<div class="modal fade" id="addModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/admin/user" method="post">
                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title">Thêm người dùng mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="text-muted small mb-3">ID sẽ tự động sinh (US001, US002...)</div>
                    <div class="row">
                        <div class="col-md-6">
                            <input name="fullname" class="form-control mb-3" placeholder="Họ và tên" required>
                            <input name="email" class="form-control mb-3" placeholder="Email" required>
                            <input type="password" name="password" class="form-control mb-3" placeholder="Mật khẩu" required>
                        </div>
                        <div class="col-md-6">
                            <input type="date" name="birthday" class="form-control mb-3">
                            <select name="gender" class="form-select mb-3">
                                <option value="">-- Giới tính --</option>
                                <option value="male">Nam</option>
                                <option value="female">Nữ</option>
                            </select>
                            <input name="mobile" class="form-control mb-3" placeholder="Số điện thoại (10 số)">
                            <select name="role" class="form-select mb-3">
                                <option value="false">Phóng viên</option>
                                <option value="true">Quản trị viên</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-success">Thêm mới</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Modal Sửa -->
<div class="modal fade" id="editModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/admin/user" method="post">
                <div class="modal-header bg-warning">
                    <h5 class="modal-title">Sửa người dùng</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="id" id="editId">
                    <div class="row">
                        <div class="col-md-6">
                            <input name="fullname" id="editFullname" class="form-control mb-3" required>
                            <input name="email" id="editEmail" class="form-control mb-3" required>
                            <input type="password" name="password" class="form-control mb-3" placeholder="Mật khẩu mới (để trống nếu không đổi)">
                        </div>
                        <div class="col-md-6">
                            <input type="date" name="birthday" id="editBirthday" class="form-control mb-3">
                            <select name="gender" id="editGender" class="form-select mb-3">
                                <option value="">-- Giới tính --</option>
                                <option value="male">Nam</option>
                                <option value="female">Nữ</option>
                            </select>
                            <input name="mobile" id="editMobile" class="form-control mb-3">
                            <select name="role" id="editRole" class="form-select mb-3">
                                <option value="false">Phóng viên</option>
                                <option value="true">Quản trị viên</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-warning">Cập nhật</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
function editUser(id, fullname, email, birthday, gender, mobile, role) {
    document.getElementById('editId').value = id;
    document.getElementById('editFullname').value = fullname;
    document.getElementById('editEmail').value = email;
    document.getElementById('editBirthday').value = birthday;
    document.getElementById('editMobile').value = mobile || '';
    document.getElementById('editRole').value = role;
    const g = document.getElementById('editGender');
    g.value = gender === true ? 'male' : gender === false ? 'female' : '';
}
function copyText(text) {
    navigator.clipboard.writeText(text).then(() => alert('Đã copy: ' + text));
}
</script>
</body>
</html>