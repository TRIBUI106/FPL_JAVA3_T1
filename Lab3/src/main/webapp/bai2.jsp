<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table>
		<thead>
			<tr>
				<th>No.</th>
				<th>Id</th>
				<th>Name</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="cts" items="${countries}" varStatus="vs">
				<tr>
					<td>${vs.count}</td> 
					<td>${cts.id}</td>
					<td>${cts.name}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>