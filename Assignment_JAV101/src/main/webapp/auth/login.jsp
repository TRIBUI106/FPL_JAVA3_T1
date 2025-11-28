<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đăng nhập - ABC News</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: #f4f6f9;
        }
        .login-wrapper {
            min-height: 100vh;
        }
        .brand-title {
            font-size: 28px;
            font-weight: 700;
            color: #0d6efd;
        }
        .side-image {
            background: url('https://images.unsplash.com/photo-1525182008055-f88b95ff7980?auto=format&fit=crop&w=900&q=60')
                        center center/cover no-repeat;
            border-radius: 0 16px 16px 0;
        }
        @media (max-width: 768px) {
            .side-image {
                display: none;
            }
        }
        .card-custom {
            border-radius: 16px;
            overflow: hidden;
        }
    </style>
</head>

<body>
<div class="container login-wrapper d-flex align-items-center justify-content-center">
    <div class="row w-100 justify-content-center">
        <div class="col-md-10 col-lg-8">
            <div class="card shadow-lg border-0 card-custom">
                <div class="row g-0">
                    
                    <!-- Form -->
                    <div class="col-md-6 p-5 d-flex flex-column justify-content-center bg-white">
                        <div class="text-center mb-4">
                            <div class="brand-title">ABC NEWS</div>
                            <p class="text-muted mt-2">Đăng nhập hệ thống quản trị</p>
                        </div>

                        <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger mb-4">
                                <%= request.getAttribute("error") %>
                            </div>
                        <% } %>

                        <form action="${pageContext.request.contextPath}/login" method="post">

                            <div class="mb-3">
                                <input type="text"
                                       name="username"
                                       class="form-control form-control-lg"
                                       placeholder="Tên đăng nhập"
                                       required autofocus>
                            </div>

                            <div class="mb-4">
                                <input type="password"
                                       name="password"
                                       class="form-control form-control-lg"
                                       placeholder="Mật khẩu"
                                       required>
                            </div>

                            <button type="submit" class="btn btn-primary btn-lg w-100">
                                Đăng nhập
                            </button>
                        </form>

                        <div class="mt-4 text-center">
                            <a href="${pageContext.request.contextPath}/" class="text-muted">
                                ← Quay về trang chủ
                            </a>
                        </div>
                    </div>

                    <!-- Image -->
                    <div class="col-md-6 side-image"></div>

                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
