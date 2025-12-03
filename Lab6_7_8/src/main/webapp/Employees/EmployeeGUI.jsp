<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý nhân viên</title>
    <jsp:include page="../menu.jsp"></jsp:include>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">

    <style>
        :root {
            --primary: #3498db;
            --primary-dark: #2980b9;
            --success: #27ae60;
            --warning: #f39c12;
            --danger: #e74c3c;
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
            max-width: 1400px;
            margin: 30px auto;
            padding: 20px;
        }
        
        h1 {
            text-align: center;
            color: white;
            font-weight: 700;
            margin-bottom: 30px;
            font-size: 2.5rem;
            text-shadow: 0 2px 10px rgba(0,0,0,0.3);
        }
        
        .card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            overflow: hidden;
            margin-bottom: 30px;
            animation: fadeInUp 0.8s ease;
        }
        
        .form-group {
            padding: 30px;
            background: #f8f9fa;
        }
        
        .form-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }
        
        .form-field {
            display: flex;
            flex-direction: column;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--text);
            font-size: 14px;
        }
        
        input[type=text],
        input[type=password],
        input[type=date],
        input[type=number],
        select {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 15px;
            transition: all 0.3s;
            box-sizing: border-box;
        }
        
        input:focus, select:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(52,152,219,0.2);
        }
        
        .gender-group {
            display: flex;
            gap: 20px;
            align-items: center;
            padding-top: 8px;
        }
        
        .gender-option {
            display: flex;
            align-items: center;
            gap: 8px;
        }
        
        .gender-option input[type=radio] {
            width: auto;
            margin: 0;
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
            cursor: pointer;
            transition: all 0.3s;
            font-size: 15px;
        }
        
        .btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.2);
        }
        
        .btn-add { background: var(--success); color: white; }
        .btn-update { background: var(--warning); color: white; }
        .btn-delete { background: var(--danger); color: white; }
        .btn-find { background: var(--primary); color: white; }
        .btn-reset { background: #95a5a6; color: white; }

        table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            border-radius: 10px;
            overflow: hidden;
        }
        
        th {
            background: var(--primary);
            color: white;
            padding: 15px 10px;
            text-transform: uppercase;
            font-size: 13px;
            letter-spacing: 0.5px;
            font-weight: 600;
        }
        
        td {
            padding: 12px 10px;
            background: white;
            transition: background 0.3s;
            font-size: 14px;
        }
        
        tr:hover td {
            background: #e3f2fd;
        }
        
        tr:nth-child(even) td {
            background: #f9f9f9;
        }
        
        .action {
            white-space: nowrap;
        }
        
        .action a {
            color: var(--primary);
            text-decoration: none;
            margin: 0 5px;
            font-weight: 500;
            transition: color 0.3s;
        }
        
        .action a:hover {
            color: var(--danger);
        }
        
        .badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 600;
        }
        
        .badge-male {
            background: #e3f2fd;
            color: #1976d2;
        }
        
        .badge-female {
            background: #fce4ec;
            color: #c2185b;
        }
        
        .salary {
            color: var(--success);
            font-weight: 600;
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
            .form-grid { 
                grid-template-columns: 1fr; 
            }
            .btn-group { 
                flex-direction: column; 
            }
            h1 { 
                font-size: 2rem; 
            }
            table {
                font-size: 12px;
            }
            th, td {
                padding: 8px 5px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h1><i class="fas fa-users"></i> Quản Lý Nhân Viên</h1>

    <div class="card">
        <div class="form-group">
            <form method="post" id="employeeForm">
                <div class="form-grid">
                    <div class="form-field">
                        <label><i class="fas fa-id-card"></i> Mã nhân viên *</label>
                        <input type="text" name="id" id="empId" 
                               value="${employeeEdit.id}" 
                               placeholder="VD: NV001" 
                               required
                               ${not empty employeeEdit ? 'readonly' : ''}>
                    </div>
                    
                    <div class="form-field">
                        <label><i class="fas fa-lock"></i> Mật khẩu *</label>
                        <input type="password" name="password" 
                               value="${employeeEdit.password}" 
                               placeholder="Nhập mật khẩu" 
                               required>
                    </div>
                    
                    <div class="form-field">
                        <label><i class="fas fa-user"></i> Họ và tên *</label>
                        <input type="text" name="fullname" 
                               value="${employeeEdit.fullname}" 
                               placeholder="Nguyễn Văn A" 
                               required>
                    </div>
                    
                    <div class="form-field">
                        <label><i class="fas fa-image"></i> Ảnh đại diện</label>
                        <input type="text" name="photo" 
                               value="${employeeEdit.photo}" 
                               placeholder="avatar.jpg">
                    </div>
                    
                    <div class="form-field">
                        <label><i class="fas fa-venus-mars"></i> Giới tính</label>
                        <div class="gender-group">
                            <div class="gender-option">
                                <input type="radio" name="gender" value="1" id="male" 
                                       ${employeeEdit.gender || empty employeeEdit ? 'checked' : ''}>
                                <label for="male" style="margin:0">Nam</label>
                            </div>
                            <div class="gender-option">
                                <input type="radio" name="gender" value="0" id="female"
                                       ${!employeeEdit.gender && not empty employeeEdit ? 'checked' : ''}>
                                <label for="female" style="margin:0">Nữ</label>
                            </div>
                        </div>
                    </div>
                    
                    <div class="form-field">
                        <label><i class="fas fa-birthday-cake"></i> Ngày sinh *</label>
                        <input type="date" name="birthday" 
                               value="${employeeEdit.birthday}" 
                               required>
                    </div>
                    
                    <div class="form-field">
                        <label><i class="fas fa-dollar-sign"></i> Lương *</label>
                        <input type="number" name="salary" 
                               value="${employeeEdit.salary}" 
                               placeholder="10000000" 
                               step="1" 
                               min="0" 
                               required>
                    </div>
                    
                    <div class="form-field">
                        <label><i class="fas fa-building"></i> Phòng ban *</label>
                        <select name="departmentId" required>
                            <option value="">-- Chọn phòng ban --</option>
                            <c:forEach var="dept" items="${departments}">
                                <option value="${dept.id}" 
                                        ${employeeEdit.departmentId == dept.id ? 'selected' : ''}>
                                    ${dept.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="btn-group">
                    <button type="submit" formaction="${pageContext.request.contextPath}/Employees/add" 
                            class="btn btn-add">
                        <i class="fas fa-plus"></i> Thêm mới
                    </button>
                    <button type="submit" formaction="${pageContext.request.contextPath}/Employees/update" 
                            class="btn btn-update">
                        <i class="fas fa-edit"></i> Cập nhật
                    </button>
                    <button type="submit" formaction="${pageContext.request.contextPath}/Employees/delete" 
                            class="btn btn-delete"
                            onclick="return confirm('Xác nhận xóa nhân viên này?')">
                        <i class="fas fa-trash"></i> Xóa
                    </button>
                    <button type="button" class="btn btn-reset" onclick="resetForm()">
                        <i class="fas fa-redo"></i> Làm mới
                    </button>
                </div>
            </form>
        </div>
    </div>

    <div class="card">
        <table>
            <thead>
                <tr>
                    <th>Mã NV</th>
                    <th>Họ tên</th>
                    <th>Giới tính</th>
                    <th>Ngày sinh</th>
                    <th>Lương</th>
                    <th>Phòng ban</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty employees}">
                        <c:forEach var="emp" items="${employees}">
                            <tr>
                                <td><strong>${emp.id}</strong></td>
                                <td>${emp.fullname}</td>
                                <td>
                                    <span class="badge ${emp.gender ? 'badge-male' : 'badge-female'}">
                                        ${emp.gender ? 'Nam' : 'Nữ'}
                                    </span>
                                </td>
                                <td>
                                    <fmt:formatDate value="${emp.birthday}" pattern="dd/MM/yyyy"/>
                                </td>
                                <td class="salary">
                                    <fmt:formatNumber value="${emp.salary}" type="number" groupingUsed="true"/> ₫
                                </td>
                                <td>${emp.departmentId}</td>
                                <td class="action">
                                    <a href="${pageContext.request.contextPath}/Employees/edit?id=${emp.id}">
                                        <i class="fas fa-edit"></i> Sửa
                                    </a> |
                                    <a href="${pageContext.request.contextPath}/Employees/delete?id=${emp.id}" 
                                       onclick="return confirm('Xóa nhân viên ${emp.fullname}?')">
                                        <i class="fas fa-trash"></i> Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="7" class="no-data">
                                <i class="fas fa-inbox"></i> Chưa có nhân viên nào! Hãy thêm mới nhé
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<script>
    function resetForm() {
        document.getElementById('employeeForm').reset();
        document.getElementById('empId').removeAttribute('readonly');
        window.location.href = '${pageContext.request.contextPath}/Employees';
    }
</script>
</body>
</html>