<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.Lang}" />
<fmt:setBundle basename="i18n/global" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Lý Người Dùng</title>
    <jsp:include page="../menu.jsp"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <style>
        /* Dùng lại style đẹp như trang phòng ban */
        :root { --primary: #e74c3c; --text: #2c3e50; }
        body { font-family: 'Roboto', sans-serif; background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%); margin:0; padding:20px; }
        .container { max-width: 1300px; margin: 0 auto; }
        h1 { text-align:center; color:var(--text); font-size:2.8rem; margin:30px 0; }
        .card { background:white; border-radius:16px; box-shadow:0 15px 35px rgba(0,0,0,0.12); padding:30px; margin-bottom:20px 0; }
        input, select { width:100%; padding:12px; margin:8px 0; border:2px solid #ddd; border-radius:8px; }
        .btn { padding:12px 24px; border:none; border-radius:8px; color:white; cursor:pointer; margin:5px; }
        .btn-add { background:#27ae60; }
        .btn-update { background:#f39c12; }
        .btn-delete { background:var(--primary); }
        table { width:100%; border-collapse:collapse; margin-top:30px; }
        th, td { padding:15px; text-align:center; border-bottom:1px solid #eee; }
        th { background:var(--primary); color:white; }
        .role-admin { background:#e74c3c; color:white; padding:5px 12px; border-radius:20px; }
        .role-user { background:#3498db; color:white; padding:5px 12px; border-radius:20px; }
    </style>
</head>
<body>
<div class="container">
    <h1>QUẢN LÝ NGƯỜI DÙNG</h1>

    <div class="card">
        <h2>${userEdit == null ? 'Thêm Người Dùng Mới' : 'Chỉnh Sửa Người Dùng'}</h2>
        <form method="post" action="${pageContext.request.contextPath}/User">
            <input type="hidden" name="action" value="${userEdit == null ? 'add' : 'update'}">

            <label>Tên đăng nhập</label>
            <input type="text" name="username" value="${userEdit.username}" ${userEdit != null ? 'readonly' : ''} required>

            <label>Mật khẩu</label>
            <input type="password" name="password" value="${userEdit.password}" required>

            <label>Họ tên</label>
            <input type="text" name="fullname" value="${userEdit.fullname}" required>

            <label>Vai trò</label>
            <select name="role">
                <option value="1" ${userEdit.role == 1 ? 'selected' : ''}>Quản trị (Admin)</option>
                <option value="0" ${userEdit.role == 0 ? 'selected' : ''}>Người dùng thường</option>
            </select>

            <label>Email</label>
            <input type="email" name="email" value="${userEdit.email}">

            <label>Địa chỉ</label>
            <input type="text" name="address" value="${userEdit.address}">

            <label>Số điện thoại</label>
            <input type="text" name="sdt" value="${userEdit.sdt}">

            <button type="submit" class="btn btn-${userEdit == null ? 'add' : 'update'}">
                ${userEdit == null ? 'Thêm Mới' : 'Cập Nhật'}
            </button>
        </form>
    </div>

    <div class="card">
        <h2>Danh Sách Người Dùng</h2>
        <table>
            <tr>
                <th>STT</th>
                <th>Username</th>
                <th>Họ tên</th>
                <th>Email</th>
                <th>Vai trò</th>
                <th>Hành động</th>
            </tr>
            <c:forEach var="u" items="${users}" varStatus="stt">
                <tr>
                    <td>${stt.index + 1}</td>
                    <td>${u.username}</td>
                    <td>${u.fullname}</td>
                    <td>${u.email}</td>
                    <td>
                        <span class="role-${u.role == 1 ? 'admin' : 'user'}">
                            ${u.role == 1 ? 'ADMIN' : 'USER'}
                        </span>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/User/edit?id=${u.username}">Sửa</a> |
                        <a href="${pageContext.request.contextPath}/User?action=delete&username=${u.username}"
                           onclick="return confirm('Xóa ${u.fullname} ?')">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>