<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Lý Nhân Viên</title>
    <jsp:include page="../menu.jsp"></jsp:include>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: #f5f5f5;
            font-family: 'Roboto', sans-serif;
        }
        h1 {
            text-align: center;
            margin: 20px 0;
        }
        .card {
            margin-bottom: 30px;
            padding: 20px;
        }
        .btn-group button {
            margin-right: 10px;
        }
        table th, table td {
            vertical-align: middle !important;
        }
        .photo-img {
            width: 60px;
            height: 60px;
            object-fit: cover;
            border-radius: 50%;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Quản Lý Nhân Viên</h1>

    <!-- Form CRUD -->
    <div class="card">
        <form method="post" enctype="multipart/form-data">
            <div class="row g-3">
                <div class="col-md-3">
                    <label class="form-label">Mã NV</label>
                    <input type="text" name="id" class="form-control" value="${employeeEdit.id}" placeholder="VD: E001" required>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Họ và tên</label>
                    <input type="text" name="fullname" class="form-control" value="${employeeEdit.fullname}" placeholder="Nhập tên nhân viên" required>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Mật khẩu</label>
                    <input type="text" name="password" class="form-control" value="${employeeEdit.password}" placeholder="Nhập mật khẩu" required>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Ảnh</label>
                    <input type="text" name="photo" class="form-control" value="${employeeEdit.photo}" placeholder="Tên file ảnh">
                </div>
                <div class="col-md-2">
                    <label class="form-label">Giới tính</label>
                    <select name="gender" class="form-select">
                        <option value="1" ${employeeEdit.gender == 1 ? 'selected' : ''}>Nam</option>
                        <option value="0" ${employeeEdit.gender == 0 ? 'selected' : ''}>Nữ</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Ngày sinh</label>
                    <input type="date" name="birthday" class="form-control" value="${employeeEdit.birthday}">
                </div>
                <div class="col-md-2">
                    <label class="form-label">Lương</label>
                    <input type="number" step="0.01" name="salary" class="form-control" value="${employeeEdit.salary}">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Phòng ban</label>
                    <select name="departmentId" class="form-select">
                        <c:forEach var="d" items="${departments}">
                            <option value="${d.id}" ${employeeEdit.departmentId == d.id ? 'selected' : ''}>${d.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="mt-3 btn-group">
                <button type="submit" formaction="${pageContext.request.contextPath}/Employees/add" class="btn btn-success">Thêm Mới</button>
                <button type="submit" formaction="${pageContext.request.contextPath}/Employees/update" class="btn btn-warning">Cập Nhật</button>
                <button type="submit" formaction="${pageContext.request.contextPath}/Employees/delete" class="btn btn-danger">Xóa</button>
                <button type="submit" formaction="${pageContext.request.contextPath}/Employees/find" class="btn btn-primary">Tìm Kiếm</button>
            </div>
        </form>
    </div>

    <!-- Bảng danh sách -->
    <div class="card">
        <table class="table table-bordered table-striped align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Mã NV</th>
                    <th>Họ và tên</th>
                    <th>Mật khẩu</th>
                    <th>Ảnh</th>
                    <th>Giới tính</th>
                    <th>Ngày sinh</th>
                    <th>Lương</th>
                    <th>Phòng ban</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty employees}">
                        <c:forEach var="e" items="${employees}">
                            <tr>
                                <td><strong>${e.id}</strong></td>
                                <td>${e.fullname}</td>
                                <td>${e.password}</td>
                                <td><img src="${pageContext.request.contextPath}/images/${e.photo}" class="photo-img" alt="photo"></td>
                                <td>${e.gender == 1 ? 'Nam' : 'Nữ'}</td>
                                <td>${e.birthday}</td>
                                <td>${e.salary}</td>
                                <td>${e.departmentId}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/Employees/edit?id=${e.id}" class="btn btn-sm btn-info">Sửa</a>
                                    <a href="${pageContext.request.contextPath}/Employees/delete?id=${e.id}" class="btn btn-sm btn-danger"
                                       onclick="return confirm('Xóa nhân viên ${e.fullname}?')">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="9" class="text-center">Chưa có nhân viên nào!</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
