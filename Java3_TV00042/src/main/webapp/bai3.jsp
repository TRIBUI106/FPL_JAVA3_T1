<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="Menu.jsp"></jsp:include>
	<h1>Đây là bài 3</h1>
	
	<form method="post">
		<button formaction="/MyVeryFirstServletProject/nv/insert">Insert</button>
		<button formaction="/MyVeryFirstServletProject/nv/update">Update</button>
		<button formaction="/MyVeryFirstServletProject/nv/delete">Delete</button>
	</form>

</body>
</html>