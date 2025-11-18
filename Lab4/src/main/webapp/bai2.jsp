<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">

	.container {
		margin-top: 3em;
		padding: .5em;
		border-radius: 8px;
		box-shadow: 2em;
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
		<input name="a"><br> <input name="b"><br>
		<button formaction="${cal}/add">+</button>
		<button formaction="${cal}/sub">-</button>
	</form>
	</div>

</body>
</html>