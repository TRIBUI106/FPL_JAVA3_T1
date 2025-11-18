<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style type="text/css">
	* {
		padding: 0;
		margin: 0;
	}
	
	body {
		text-align: center;
	}
	
	.container {
		background-color: #E0E0E0;
		width: 100%;
		height: 3em;
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		align-items: center;
		font-size: 2em;
	}
	
	.container > a {
		color: black;
		transition: .3s;
	}
	
	.container > a:hover {
		color: red;
		text-decoration: underline;
	}

</style>

<title>Lab 4 - Menu</title>
</head>
<body>

	<div class="container">
		<p>Lab 4 - TV00042</p>
		<a href="${pageContext.request.contextPath }/">Trang chủ</a> 
		<a href="${pageContext.request.contextPath }/bai1">Bài 1</a> 
		<a href="${pageContext.request.contextPath }/bai2">Bài 2</a> 
		<a href="${pageContext.request.contextPath }/bai3">Bài 3</a> 
		<a href="${pageContext.request.contextPath }/bai4">Bài 4</a>
	</div>

</body>
</html>