<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng nhập</title>
    <style>
        body { font-family: Arial; background: #f2f2f2; }
        .login-box {
            width: 300px; margin: 80px auto; padding: 20px;
            background: white; border-radius: 8px; box-shadow: 0 0 10px #ccc;
        }
        input[type=text], input[type=password] {
            width: 100%; padding: 8px; margin: 5px 0; box-sizing: border-box;
        }
        button {
            width: 100%; padding: 10px; background: #007bff;
            color: white; border: none; border-radius: 4px;
        }
        .error { color: red; }
    </style>
</head>
<body>
<div class="login-box">
    <h2>Đăng nhập</h2>
    <form action="login" method="post">
        <label>Tên đăng nhập</label>
        <input type="text" name="username" required>
        <label>Mật khẩu</label>
        <input type="password" name="password" required>
        <button type="submit">Đăng nhập</button>
    </form>
    <p class="error">${error}</p>
</div>
</body>
</html>
