<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý tin tức - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
</head>
<body>
<div class="d-flex min-vh-100">
    <%@ include file="../layout/admin-sidebar.jsp" %>
    <div class="flex-grow-1 bg-light">
        <%@ include file="../layout/admin-header.jsp" %>
        <div class="container my-5">
            <h2 class="text-primary fw-bold mb-4">QUẢN LÝ TIN TỨC</h2>
            <button class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#addModal">Thêm tin mới</button>
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr><th>ID</th><th>Tiêu đề</th><th>Ảnh</th><th>Hành động</th></tr>
                </thead>
                <tbody>
                    <c:forEach items="${listNews}" var="n">
                    <tr>
                        <td>${n.id}</td>
                        <td>${n.title}</td>
                        <td><img src="${pageContext.request.contextPath}/${n.image}" width="80" class="rounded"></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/news?action=edit&id=${n.id}" class="btn btn-warning btn-sm">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/news?action=delete&id=${n.id}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa tin này?')">Xóa</a>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal thêm tin -->
<div class="modal fade" id="addModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/admin/news" method="post" enctype="multipart/form-data">
                <div class="modal-header">
                    <h5 class="modal-title">Thêm tin tức mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input name="id" class="form-control mb-3" placeholder="Mã tin" required>
                    <input name="title" class="form-control mb-3" placeholder="Tiêu đề" required>
                    <textarea name="content" class="form-control mb-3" rows="4" placeholder="Nội dung"></textarea>
                    <input type="file" name="image" class="form-control mb-3" accept="image/*" required>
                    <select name="categoryId" class="form-select mb-3">
                        <option value="TT">Thời sự</option>
                        <option value="KT">Kinh tế</option>
                        <!-- Thêm các loại khác -->
                    </select>
                    <div class="form-check"><input type="checkbox" name="home" class="form-check-input"> <label>Hiện trên trang chủ</label></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary">Lưu tin</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>