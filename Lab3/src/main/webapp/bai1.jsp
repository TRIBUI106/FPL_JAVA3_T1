<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lab 3 - Bài 1</title>
</head>
<body>

	<h1>Bài 1</h1>

	<select name="country">
		<c:forEach var="cts" items="${countries}">
		<option value="${cts.id}">${cts.name}</option>
		</c:forEach>
	</select>	

</body>
</html>