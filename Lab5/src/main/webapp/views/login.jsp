<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        .login-card {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 20px;
            box-shadow: 0 20px 50px rgba(0,0,0,0.2);
            backdrop-filter: blur(10px);
        }
        .btn-login {
            background: linear-gradient(45deg, #4facfe, #00f2fe);
            border: none;
            border-radius: 50px;
            padding: 12px 40px;
            font-weight: 600;
            text-transform: uppercase;
        }
        .btn-login:hover {
            transform: translateY(-3px);
            box-shadow: 0 15px 30px rgba(79, 209, 197, 0.4);
        }
        h1 {
            background: linear-gradient(45deg, #4facfe, #00f2fe);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            font-weight: 800;
        }
    </style>
</head>
<body class="d-flex align-items-center">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6 col-xl-5">
                <div class="login-card p-5">
                    <div class="text-center mb-5">
                        <i class="fas fa-user-lock fa-4x text-primary mb-4"></i>
                        <h1 class="display-5">Welcome Back</h1>
                        <p class="text-muted">Sign in to continue</p>
                    </div>

                    <c:if test="${not empty message}">
                        <div class="alert alert-danger text-center">${message}</div>
                    </c:if>

                    <form action="<%=request.getContextPath()%>/login" method="post">
                        <div class="mb-4">
                            <label class="form-label fw-bold">Username</label>
                            <input name="username" value="${username}" type="text" class="form-control form-control-lg rounded-pill" required>
                        </div>
                        <div class="mb-4">
                            <label class="form-label fw-bold">Password</label>
                            <input name="password" type="password" class="form-control form-control-lg rounded-pill" required>
                        </div>
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <div class="form-check">
                                <input name="remember" class="form-check-input" type="checkbox" id="remember">
                                <label class="form-check-label" for="remember">Remember me</label>
                            </div>
                            <a href="#" class="text-decoration-none">Forgot password?</a>
                        </div>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary btn-login text-white">
                                <i class="fas fa-sign-in-alt me-2"></i> Login
                            </button>
                        </div>
                    </form>

                    <div class="text-center mt-4">
                        <a href="<%=request.getContextPath()%>/">Back to Home</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>