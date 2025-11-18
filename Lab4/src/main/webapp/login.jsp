<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
	
	form {
		width: 40%;
		height: 8em;
		align-items: center;
		justify-content: center;
		display: flex;
		flex-direction: column;
		margin: 5rem auto;
	}
	
	form button {
		width: 50%;
	}

</style>
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="index.jsp"></jsp:include>

	<c:url value="/account/login" var="url" />
	<form action="${url}" method="post">
		${message}
		<hr>
		<label for="username">Username</label>
		<input name="username"><br> 
		<label for="password">Password</label>
		<input name="password" type="password"><br>
		<button>Login</button>
	</form>

</body>
</html>