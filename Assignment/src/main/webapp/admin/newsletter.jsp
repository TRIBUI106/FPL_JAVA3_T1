<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý Newsletter - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
</head>
<body>
<div class="d-flex min-vh-100">
    <%@ include file="../layout/admin-sidebar.jsp" %>
    <div class="flex-grow-1 bg-light">
        <%@ include file="../layout/admin-header.jsp" %>
        <div class="container my-5">
            <h2 class="text-primary fw-bold mb-4">QUẢN LÝ ĐĂNG KÝ NHẬN TIN</h2>
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr><th>STT</th><th>Email</th><th>Trạng thái</th><th>Hành động</th></tr>
                </thead>
                <tbody>
                    <c:forEach items="${listNewsletters}" var="n" varStatus="loop">
                    <tr>
                        <td>${loop.count}</td>
                        <td>${n.email}</td>
                        <td><span class="badge bg-success">Đang hoạt động</span></td>
                        <td><a href="${pageContext.request.contextPath}/admin/newsletter?action=delete&id=${n.id}" class="btn btn-danger btn-sm" onclick="return confirm('Hủy đăng ký này?')">Hủy</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>