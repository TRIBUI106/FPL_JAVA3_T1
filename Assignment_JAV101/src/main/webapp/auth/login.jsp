<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đăng nhập - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg, #667eea, #764ba2); min-height: 100vh; display: flex; align-items: center; }
        .login-card { background: white; border-radius: 20px; padding: 40px; box-shadow: 0 20px 40px rgba(0,0,0,0.3); }
        .btn-login { background: #0d6efd; border: none; padding: 12px; font-size: 1.1rem; }
    </style>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="login-card text-center">
                <h2 class="mb-4 text-primary">ĐĂNG NHẬP HỆ THỐNG</h2>
                <form>
                    <div class="mb-3">
                        <input type="text" class="form-control form-control-lg" placeholder="Tên đăng nhập" required>
                    </div>
                    <div class="mb-4">
                        <input type="password" class="form-control form-control-lg" placeholder="Mật khẩu" required>
                    </div>
                    <button type="submit" class="btn btn-primary btn-login w-100">ĐĂNG NHẬP</button>
                </form>
                <div class="mt-4">
                    <a href="${getContext.request.contextPath}/" class="text-muted">← Quay về trang chủ</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>