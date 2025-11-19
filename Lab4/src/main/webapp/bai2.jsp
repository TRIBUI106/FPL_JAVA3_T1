<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>

<style>
    body {
        font-family: Arial, sans-serif;
        background: #f5f5f5;
        margin: 0;
        padding: 0;
    }

    .container {
        width: 350px;
        margin: 4em auto;
        background: #fff;
        padding: 25px;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.08);
        text-align: center;
    }

    h1 {
        font-size: 20px;
        color: #333;
        margin-bottom: 25px;
        font-weight: 600;
    }

    input {
        width: 100%;
        padding: 10px;
        margin-bottom: 12px;
        border-radius: 8px;
        border: 1px solid #d0d0d0;
        font-size: 15px;
        transition: 0.2s;
        box-sizing: border-box;
    }

    input:focus {
        border-color: #888;
        outline: none;
        box-shadow: 0 0 0 2px #e2e2e2;
    }

    button {
        width: 48%;
        padding: 10px;
        margin-top: 10px;
        font-size: 17px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: 0.2s;
        background: #eee;
    }

    button:hover {
        background: #dcdcdc;
    }

    .btn-add {
        background: #4caf50;
        color: white;
    }

    .btn-add:hover {
        background: #449d47;
    }

    .btn-sub {
        background: #f44336;
        color: white;
    }

    .btn-sub:hover {
        background: #d8372c;
    }

</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


	<jsp:include page="index.jsp"></jsp:include>

    <div class="container">
        <h1>${message}</h1>

        <c:url value="/calculate" var="cal" />

        <form method="post">
            <input name="a" placeholder="Nhập số thứ nhất">
            <input name="b" placeholder="Nhập số thứ hai">

            <button class="btn-add" formaction="${cal}/add">+</button>
            <button class="btn-sub" formaction="${cal}/sub">−</button>
        </form>
    </div>

</body>
</html>