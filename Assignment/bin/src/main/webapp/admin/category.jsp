<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setBundle basename="locale" scope="application"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý loại tin - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
</head>
<body>
<div class="d-flex min-vh-100">
    <%@ include file="../layout/admin-sidebar.jsp" %>
    <div class="flex-grow-1 bg-light">
        <%@ include file="../layout/admin-header.jsp" %>
        <div class="container my-5">
            <h2 class="text-primary fw-bold mb-4">QUẢN LÝ LOẠI TIN</h2>
            <button class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#categoryModal" 
                    onclick="openAddModal()">
                <i class="bi bi-plus-circle"></i> Thêm loại tin
            </button>
            <c:if test="${not empty sessionScope.message}">
                <div class="alert alert-success alert-dismissible fade show">
                    ${sessionScope.message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <% session.removeAttribute("message"); %>
            </c:if>
            <table class="table table-striped table-hover align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>Mã loại</th>
                        <th>Tên loại</th>
                        <th width="150">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listCategories}" var="c">
                        <tr>
                            <td><strong>${c.id}</strong></td>
                            <td>${c.name}</td>
                            <td>
                                <button class="btn btn-warning btn-sm" 
                                        onclick="openEditModal('${c.id}', '${c.name}')">
                                    <i class="bi bi-pencil"></i> Sửa
                                </button>
                                <a href="${pageContext.request.contextPath}/admin/category?action=delete&id=${c.id}" 
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('Xóa loại tin [${c.name}] này?')">
                                    <i class="bi bi-trash"></i> Xóa
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="modal fade" id="categoryModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/admin/category" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title" id="modalTitle">Thêm loại tin mới</h5>
                    <button type="button" class="btn-close text-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="action" id="formAction" value="insert">
                    <input type="hidden" name="id" id="categoryId">

                    <div class="mb-3">
                        <label class="form-label fw-bold">Tên loại tin</label>
                        <input type="text" name="name" id="categoryName" class="form-control" 
                               placeholder="VD: Thể thao, Giải trí, Khoa học..." required>
                    </div>

                    <!-- Hiển thị mã tự sinh (chỉ đọc) -->
                    <div class="mb-3">
                        <label class="form-label fw-bold">Mã loại (tự sinh)</label>
                        <input type="text" id="displayId" class="form-control bg-light" readonly 
                               value="Sẽ tự động tạo khi lưu">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary">Lưu lại</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById("categoryName").addEventListener("input", function() {
        let name = this.value.trim();
        if (name === "") {
            document.getElementById("displayId").value = "Sẽ tự động tạo khi lưu";
            return;
        }

        // Ajax gọi servlet sinh thử ID (hoặc làm đơn giản bằng JS)
        let words = name.split(/\s+/);
        let id = words.map(w => w.charAt(0).toUpperCase()).join('');
        if (id === "") id = "CAT";

        document.getElementById("displayId").value = id + " (có thể thay đổi nếu trùng)";
    });

    function openAddModal() {
        document.getElementById("modalTitle").textContent = "Thêm loại tin mới";
        document.getElementById("formAction").value = "insert";
        document.getElementById("categoryName").value = "";
        document.getElementById("categoryId").value = "";
        document.getElementById("displayId").value = "Sẽ tự động tạo khi lưu";
    }

    function openEditModal(id, name) {
        document.getElementById("modalTitle").textContent = "Chỉnh sửa loại tin";
        document.getElementById("formAction").value = "update";
        document.getElementById("categoryId").value = id;
        document.getElementById("categoryName").value = name;
        document.getElementById("displayId").value = id + " (không đổi được)";
        new bootstrap.Modal(document.getElementById('categoryModal')).show();
    }
</script>
</body>
</html>