<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register | Focus</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
<style>
body {
    background: linear-gradient(135deg, #74ebd5 0%, #9face6 100%);
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
}

.register-card {
    background-color: #fff;
    border-radius: 1rem;
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
    max-width: 500px;
    width: 100%;
    padding: 2rem;
}

.register-card h3 {
    font-weight: bold;
    color: #0d6efd;
}

.social-icons i {
    font-size: 1.5rem;
    margin: 0 0.5rem;
    cursor: pointer;
    color: #6c757d;
    transition: color 0.3s;
}

.social-icons i:hover {
    color: #0d6efd;
}
</style>
</head>
<body>
    <div class="register-card">
        <div class="text-center mb-4">
            <i class="bi bi-person-circle" style="font-size:3rem; color:#0d6efd;"></i>
            <h3 class="mt-2">Welcome to Focus</h3>
            <small class="text-muted">Register your account</small>
        </div>

        <!-- Form đăng ký -->
        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="mb-3">
                <label class="form-label">Id</label>
                <input type="text" class="form-control" name="id" placeholder="Your id" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Name</label>
                <input type="text" class="form-control" name="fullname" placeholder="Your full name" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" class="form-control" name="email" placeholder="you@example.com" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Password</label>
                <input type="password" class="form-control" name="password" placeholder="8+ characters" required>
            </div>

            <button type="submit" class="btn btn-primary w-100 mb-3">Register</button>

            <!-- Đăng ký bằng mạng xã hội -->
            <div class="text-center mb-3">
                <span class="text-muted">Or sign up with</span>
                <div class="social-icons mt-2">
                    <i class="bi bi-facebook"></i>
                    <i class="bi bi-linkedin"></i>
                    <i class="bi bi-google"></i>
                </div>
            </div>

            <!-- Link chuyển sang đăng nhập -->
            <p class="text-center">
                Already have an account?
                <a href="${pageContext.request.contextPath}/auth/login.jsp">Login here</a>
            </p>
        </form>
    </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
