<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đăng nhập - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-primary min-vh-100 d-flex align-items-center">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-5">
                <div class="card shadow-lg border-0 rounded-4">
                    <div class="card-body p-5 text-center">
                        <h2 class="text-primary fw-bold mb-4">ĐĂNG NHẬP HỆ THỐNG</h2>
                        <form action="${pageContext.request.contextPath}/login" method="post">
                            <input type="text" name="username" class="form-control form-control-lg mb-3" placeholder="Tên đăng nhập" required>
                            <input type="password" name="password" class="form-control form-control-lg mb-4" placeholder="Mật khẩu" required>
                            <button class="btn btn-primary btn-lg w-100">ĐĂNG NHẬP</button>
                        </form>
                        <div class="mt-4">
                            <a href="${pageContext.request.contextPath}/home" class="text-muted">← Quay về trang chủ</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>