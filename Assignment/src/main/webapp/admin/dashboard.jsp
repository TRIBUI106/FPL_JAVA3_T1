<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản trị - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
</head>
<body>
<div class="d-flex min-vh-100">
    <!-- Sidebar -->
    <jsp:include page="../layout/admin-sidebar.jsp"></jsp:include>

    <!-- Main -->
    <div class="flex-grow-1 p-5 bg-light">
        <h2 class="text-primary fw-bold mb-5">CÔNG CỤ QUẢN TRỊ TIN TỨC</h2>
        <div class="alert alert-success">
            <strong>Chào mừng ${sessionScope.user.fullname}!</strong> Vai trò: ${sessionScope.user.role ? 'Quản trị viên' : 'Phóng viên'}
        </div>
        <p>Vui lòng chọn chức năng bên trái.</p>
    </div>
</div>
</body>
</html>