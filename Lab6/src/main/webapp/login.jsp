<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập hệ thống</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            height: 100vh;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            font-family: 'Roboto', sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-card {
            background: white;
            padding: 40px 50px;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.3);
            width: 380px;
            text-align: center;
            animation: float 6s ease-in-out infinite;
        }
        h2 { color: #764ba2; margin-bottom: 30px; }
        input {
            width: 100%;
            padding: 14px;
            margin:  margin: 10px 0;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
        }
        button {
            width: 100%;
            padding: 14px;
            background: #667eea;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 18px;
            font-weight: 600;
            cursor: pointer;
            margin-top: 10px;
            transition: 0.3s;
        }
        button:hover {
            background: #764ba2;
            transform: translateY(-3px);
        }
        .error { color: #e74c3c; margin-top: 15px; font-weight: 500; }
        @keyframes float {
            0%, 100% { transform: translateY(0); }
            50% { transform: translateY(-20px); }
        }
    </style>
</head>
<body>
<div class="login-card">
    <h2>ĐĂNG NHẬP</h2>
    <form action="login" method="post" class="gap-4">
        <input type="text" name="username" placeholder="Tên đăng nhập" class="margin-bottom: .3em;" required>
        <input type="password" name="password" placeholder="Mật khẩu" required>
        <button type="submit">Đăng Nhập</button>
    </form>
    <p class="error">${error}</p>
</div>
</body>
</html>