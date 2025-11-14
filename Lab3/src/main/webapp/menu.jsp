<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lab 3</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background: #fafafa;
        margin: 0;
        padding: 0;
    }

    h1 {
        text-align: center;
        margin-top: 40px;
        font-weight: 600;
        color: #333;
    }

    .menu-container {
        width: 320px;
        margin: 40px auto 0;
        padding: 20px;
        background: #fff;
        border-radius: 12px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.08);
    }

    .menu-item {
        display: block;
        text-decoration: none;
        padding: 12px;
        border: 1px solid #e0e0e0;
        border-radius: 8px;
        margin-bottom: 10px;
        color: #333;
        font-size: 15px;
        transition: all 0.2s ease-in-out;
    }

    .menu-item:hover {
        background: #f2f2f2;
        border-color: #c8c8c8;
    }
</style>

</head>
<body>

    <h1>Lab 3 - Main Menu</h1>

    <div class="menu-container">
        <a href="bai1" class="menu-item">Bài 1</a>
        <a href="bai2" class="menu-item">Bài 2</a>
        <a href="bai3" class="menu-item">Bài 3</a>
        <a href="bai4" class="menu-item">Bài 4</a>
        <a href="bai5" class="menu-item">Bài 5</a>
    </div>

</body>
</html>
