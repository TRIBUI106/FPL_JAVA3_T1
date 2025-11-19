<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        font-family: Arial, sans-serif;
        background: #f5f5f5;
    }

    .navbar {
        background: #ffffff;
        width: 100%;
        height: 60px;
        border-bottom: 1px solid #ddd;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0 25px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.06);
        position: sticky;
        top: 0;
        z-index: 10;
    }

    .navbar-title {
        font-size: 20px;
        font-weight: 600;
        color: #333;
    }

    .nav-links {
        display: flex;
        gap: 20px;
        font-size: 16px;
    }

    .nav-links a {
        text-decoration: none;
        color: #444;
        padding: 8px 10px;
        border-radius: 6px;
        transition: 0.25s;
    }

    .nav-links a:hover {
        background: #e6e6e6;
        color: #d9534f;
    }
</style>
<title>Lab 4 - Menu</title>
</head>
<body>


	<div class="navbar">
		<p class="navbar-title">Lab 4 - TV00042</p>

		<div class="nav-links">
			<a href="${pageContext.request.contextPath }/">Trang chủ</a> <a
				href="${pageContext.request.contextPath }/bai1">Bài 1</a> <a
				href="${pageContext.request.contextPath }/bai2">Bài 2</a> <a
				href="${pageContext.request.contextPath }/bai3">Bài 3</a> <a
				href="${pageContext.request.contextPath }/bai4">Bài 4</a>
		</div>
	</div>

</body>
</html>