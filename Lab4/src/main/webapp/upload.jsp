<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upload File</title>
</head>
<body>

	<jsp:include page="index.jsp"></jsp:include>

	<h2>Upload file ảnh</h2>

	<c:url value="upload" var="url" />

	<form action="${url}" method="post" enctype="multipart/form-data">
		<input name="photo" type="file"><br>
		<br>
		<button>Upload</button>
	</form>

	<h3 style="color: green;">${message}</h3>

	<c:if test="${not empty filename}">
		<c:url value="/static/file/${filename}" var="imgUrl" />
		<img src="${imgUrl}" width="300">
	</c:if>

</body>
</html>