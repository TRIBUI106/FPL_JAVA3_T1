<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LAB 2: Menu Điều Hướng</title>
    <style>
    .container {
        display: flex; /* Bật chế độ Flexbox */
        flex-wrap: wrap; /* Cho phép các mục nhảy xuống hàng mới nếu không đủ chỗ */
        justify-content: space-around; /* Căn giữa các item và tạo khoảng trống đồng đều giữa chúng */
        margin-top: 20px;
    }
        body { font-family: Arial, sans-serif; line-height: 1.6; padding: 20px; }
        .menu-link { 
            display: block; 
            margin: 10px 0; 
            padding: 12px 15px; 
            border: 1px solid #007bff; 
            border-radius: 5px;
            text-decoration: none; 
            color: #007bff; 
            background-color: #f4f8ff;
            font-weight: bold;
        }
        .menu-link:hover { 
            background-color: #007bff; 
            color: white; 
        }
    </style>
</head>
<body>
    <h1>Bài 5 / JSP Include này kia</h1>
    
<div class ="container">
    <div class="item">
        <jsp:include page="item.jsp">
            <jsp:param name="name" value="Trưởng Buồng"/>
            <jsp:param name="img" value="anh2.png"/>
        </jsp:include>
    </div>

    <div class="item">
        <jsp:include page="item.jsp">
            <jsp:param name="name" value="Phó Buồng"/>
            <jsp:param name="img" value="hau.jxl"/>
        </jsp:include >
    </div>

    <div class="item">
        <jsp:include page="item.jsp">
            <jsp:param name="name" value="Quản Giáo"/>
            <jsp:param name="img" value="hainam.png"/> 
        </jsp:include>
    </div>
    <div class="item">
        <jsp:include page="item.jsp">
            <jsp:param name="name" value="Tù Nhân 1"/>
            <jsp:param name="img" value="quocanh.jpg"/> 
        </jsp:include>
    </div>
     <div class="item">
        <jsp:include page="item.jsp">
            <jsp:param name="name" value="Tù Nhân 2"/>
            <jsp:param name="img" value="quynhanh.jpg"/> 
        </jsp:include>
    </div>
    <div class="item">
        <jsp:include page="item.jsp">
            <jsp:param name="name" value="Tù Nhân 3"/>
            <jsp:param name="img" value="tinh.jpg"/> 
        </jsp:include>
    </div>
   
    
    
   </div>
</body>
</html>