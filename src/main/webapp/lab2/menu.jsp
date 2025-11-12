<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lab 2 Menu</title>
</head>
<body>

	<h1>
	Lab 2
	</h1>

	<ul>
	    <li><a href="${pageContext.request.contextPath}/lab2/bai1">Bài 1</a></li>
	    <li><a href="${pageContext.request.contextPath}/lab2/bai2">Bài 2</a></li>
	    <li><a href="${pageContext.request.contextPath}/lab2/bai3">Bài 3</a></li>
	    <li><a href="${pageContext.request.contextPath}/lab2/bai4">Bài 4</a></li>
	    <li><a href="${pageContext.request.contextPath}/lab2/bai5">Bài 5</a></li>
	    <li><a href="${pageContext.request.contextPath}/">Về trang chủ</a></li>
	</ul>


</body>
</html>

<style>

body {
	font-size: 2em;
}

body > h1 {
	margin-top: 5em;
	text-align: center;
}

ul {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
}

ul > li > a{
	color: black;
	text-decoration: none;	
}

ul > li > a:hover {
	color: red;
	text-decoration: underline;
}
</style>