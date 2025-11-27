<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách phòng ban</title>
    <jsp:include page="../menu.jsp"></jsp:include>

    <!-- Font Awesome + Google Font -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">

    <style>
        :root {
            --primary: #e74c3c;
            --primary-dark: #c0392b;
            --gray: #f8f9fa;
            --text: #2c3e50;
        }
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            margin: 0;
            padding: 0;
            min-height: 100vh;
        }
        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 20px;
        }
        h1 {
            text-align: center;
            color: var(--text);
            font-weight: 700;
            margin-bottom: 30px;
            font-size: 2.5rem;
            text-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.15);
            overflow: hidden;
            margin-bottom: 30px;
            animation: fadeInUp 0.8s ease;
        }
        .form-group {
            padding: 30px;
            background: #f1f2f6;
        }
        .form-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--text);
        }
        input[type=text], input[type=submit] {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s;
        }
        input[type=text]:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(231,76,60,0.2);
        }
        .btn-group {
            display: flex;
            flex-wrap: wrap;
            gap: 12px;
            justify-content: center;
            margin-top: 20px;
        }
        .btn {
            padding: 12px 25px;
            border: none;
            border-radius: 8px;
            font-weight: 600;
            cursor:hover {
                transform: translateY(-3px);
                box-shadow: 0 8px 20px rgba(0,0,0,0.2);
            }
        }
        .btn-add { background: #27ae60; color: white; }
        .btn-update { background: #f39c12; color: white; }
        .btn-delete { background: var(--primary); color: white; }
        .btn-find { background: #3498db; color: white; }

        table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            margin-top: 20px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            border-radius: 10px;
            overflow: hidden;
            animation: fadeInUp 1s ease;
        }
        th {
            background: var(--primary);
            color: white;
            padding: 18px;
            text-transform: uppercase;
            font-size: 14px;
            letter-spacing: 1px;
        }
        td {
            padding: 15px;
            background: white;
            transition: background 0.3s;
        }
        tr:hover td {
            background: #fdf2f2;
        }
        tr:nth-child(even) td {
            background: #f9f9f9;
        }
        .action a {
            color: #3498db;
            text-decoration: none;
            margin: 0 8px;
            font-weight: 500;
        }
        .action a:hover {
            color: var(--primary);
            text-decoration: underline;
        }
        .no-data {
            text-align: center;
            padding: 40px;
            color: #7f8c8d;
            font-size: 1.2rem;
        }
        @keyframes fadeInUp {
            from { opacity: 0; transform: translateY(30px); }
            to { opacity: 1; transform: translateY(0); }
        }
        @media (max-width: 768px) {
            .form-grid { grid-template-columns: 1fr; }
            .btn-group { flex-direction: column; }
            h1 { font-size: 2rem; }
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Quản Lý Phòng Ban</h1>

    <div class="card">
        <div class="form-group">
            <form method="post">
                <div class="form-grid">
                    <div>
                        <label>Mã Phòng ban</label>
                        <input type="text" name="id" value="${departmentEdit.id}" placeholder="VD: D01" required>
                    </div>
                    <div>
                        <label>Tên phòng ban</label>
                        <input type="text" name="name" value="${departmentEdit.name}" placeholder="Nhập tên phòng ban" required>
                    </div>
                    <div>
                        <label>Mô tả</label>
                        <input type="text" name="description" value="${departmentEdit.description}" placeholder="Mô tả công việc phòng ban">
                    </div>
                </div>

                <div class="btn-group">
                    <button type="submit" formaction="${pageContext.request.contextPath}/Departments/add"    class="btn btn-add">Thêm Mới</button>
                    <button type="submit" formaction="${pageContext.request.contextPath}/Departments/update" class="btn btn-update">Cập Nhật</button>
                    <button type="submit" formaction="${pageContext.request.contextPath}/Departments/delete" class="btn btn-delete">Xóa</button>
                    <button type="submit" formaction="${pageContext.request.contextPath}/Departments/find"   class="btn btn-find">Tìm Kiếm</button>
                </div>
            </form>
        </div>
    </div>

    <div class="card">
        <table>
            <thead>
                <tr>
                    <th>Mã phòng ban</th>
                    <th>Tên phòng ban</th>
                    <th>Mô tả</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty departments}">
                        <c:forEach var="d" items="${departments}">
                            <tr>
                                <td><strong>${d.id}</strong></td>
                                <td>${d.name}</td>
                                <td>${d.description}</td>
                                <td class="action">
                                    <a href="${pageContext.request.contextPath}/Departments/edit?id=${d.id}">Sửa</a> |
                                    <a href="${pageContext.request.contextPath}/Departments/delete?id=${d.id}" 
                                       onclick="return confirm('Xóa phòng ban ${d.name}?')">
                                       Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="4" class="no-data">
                                Chưa có phòng ban nào! Hãy thêm mới nhé
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>