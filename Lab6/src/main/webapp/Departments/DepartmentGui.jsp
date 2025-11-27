<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../menu.jsp"></jsp:include>
    <meta charset="UTF-8">
    <title>Danh sách phòng ban</title>
    <style>
        body { font-family: Arial, sans-serif; }
        h1 { text-align: center; margin-top: 30px; }
        form { width: 80%; margin: 20px auto; text-align: center; }
        input { margin: 5px; padding: 5px; }
        button { margin: 5px; padding: 6px 12px; cursor: pointer; }
        table { border-collapse: collapse; width: 80%; margin: 30px auto; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: center; }
        th { background-color: #f2f2f2; }
        tr:nth-child(even) { background-color: #f9f9f9; }
    </style>
</head>
<body>
    <h1>Danh sách Phòng Ban</h1>

    <!-- Form thêm / tìm kiếm / xóa -->
  <form  method="post">
    <label>Mã Phòng ban:
    <input type="text" name="id" value="${departmentEdit.id}" /><br/>
    <label>Tên phòng ban:</label>
    <input type="text" name="name" value="${departmentEdit.name}"  /><br>

    <label>Mô tả:</label>
    <input type="text" name="description" value="${departmentEdit.description}"  /><br>

    <button formaction="${pageContext.request.contextPath}/Departments/update" name="action" value="update">Cập nhật</button>
    <button formaction="${pageContext.request.contextPath}/Departments/delete" name="action" value="delete"> Xóa </button>
    <button formaction="${pageContext.request.contextPath}/Departments/add"  name="action" value="add"> Thêm Mới </button> <br/>
    <input type="text" name="txttim" value="tim"> 
    <button formaction="${pageContext.request.contextPath}/Departments/find" name="action" value="find"> Tìm </button>
</form>

    <!-- Bảng danh sách phòng ban -->
    <table>
        <tr>
            <th>Mã phòng ban</th>
            <th>Tên phòng ban</th>
            <th>Mô tả</th>
            <th>Hành động</th>
        </tr>

        <c:choose>
            <c:when test="${not empty departments}">
                <c:forEach var="d" items="${departments}">
                    <tr>
                        <td>${d.id}</td>
                        <td>${d.name}</td>
                        <td>${d.description}</td>
                        <td>
                           <a href="${pageContext.request.contextPath}/Departments/edit?id=${d.id}">Edit</a> |
                           <a href="${pageContext.request.contextPath}/Departments/delete?id=${d.id}" onclick="return confirm('Bạn có chắc muốn xóa?');">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="4">Không có phòng ban nào!</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
</body>
</html>
