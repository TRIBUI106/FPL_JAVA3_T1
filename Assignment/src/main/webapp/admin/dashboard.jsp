<%@ page import="entity.User"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setBundle basename="locale" scope="application"/>
<%
    User currentUser = (User) session.getAttribute("user");
    boolean isAdmin = (currentUser != null && currentUser.isAdmin());
    %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản trị - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <style>
        .stat-card {
            border-left: 4px solid;
            transition: transform 0.2s;
        }
        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
<div class="d-flex min-vh-100">

    <!-- Sidebar -->
    <jsp:include page="../layout/admin-sidebar.jsp"></jsp:include>

    <!-- Main -->
    <div class="flex-grow-1 p-5 bg-light">
        <!-- Header -->
        <div class="mb-4">
            <h2 class="text-primary fw-bold"><fmt:message key="dashboard.title"/></h2>
            <p class="text-muted"><fmt:message key="dashboard.welcomeback"/> <strong>${sessionScope.user.fullname}</strong> !</p>
        </div>

        <!-- Stats Cards -->
        <div class="row g-4 mb-5">
            <!-- Tổng số tin tức -->
            <div class="col-md-3">
                <div class="card stat-card border-primary">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <p class="text-muted mb-1"><fmt:message key="dashboard.newsCount"/></p>
                                <h3 class="fw-bold text-primary mb-0">${totalNews != null ? totalNews : 0}</h3>
                            </div>
                            <div class="bg-primary bg-opacity-10 p-3 rounded">
                                <i class="bi bi-newspaper fs-1 text-primary"></i>
                            </div>
                        </div>
                        <small class="text-success"><i class="bi bi-arrow-up"></i> <fmt:message key="dashboard.newsCount2"/></small>
                    </div>
                </div>
            </div>

            <!-- Tổng số loại tin -->
            <div class="col-md-3">
                <div class="card stat-card border-success">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <p class="text-muted mb-1"><fmt:message key="dashboard.catCount"/></p>
                                <h3 class="fw-bold text-success mb-0">${totalCategories != null ? totalCategories : 0}</h3>
                            </div>
                            <div class="bg-success bg-opacity-10 p-3 rounded">
                                <i class="bi bi-tags fs-1 text-success"></i>
                            </div>
                        </div>
                        <small class="text-muted"><fmt:message key="dashboard.catCount2"/></small>
                    </div>
                </div>
            </div>

            <!-- Tổng số người dùng -->
            <div class="col-md-3">
                <div class="card stat-card border-info">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <p class="text-muted mb-1"><fmt:message key="dashboard.userCount"/></p>
                                <h3 class="fw-bold text-info mb-0">${totalUsers != null ? totalUsers : 0}</h3>
                            </div>
                            <div class="bg-info bg-opacity-10 p-3 rounded">
                                <i class="bi bi-people fs-1 text-info"></i>
                            </div>
                        </div>
                        <small class="text-muted"><fmt:message key="dashboard.userCount2"/></small>
                    </div>
                </div>
            </div>

            <!-- Newsletter subscribers -->
            <div class="col-md-3">
                <div class="card stat-card border-warning">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <p class="text-muted mb-1"><fmt:message key="dashboard.emailCount"/></p>
                                <h3 class="fw-bold text-warning mb-0">${totalNewsletter != null ? totalNewsletter : 0}</h3>
                            </div>
                            <div class="bg-warning bg-opacity-10 p-3 rounded">
                                <i class="bi bi-envelope fs-1 text-warning"></i>
                            </div>
                        </div>
                        <small class="text-muted"><fmt:message key="dashboard.emailCount2"/></small>
                    </div>
                </div>
            </div>
        </div>

        <!-- Quick Actions -->
        <div class="row g-4 mb-5">
            <div class="col-12">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title fw-bold mb-4">
                            <i class="bi bi-lightning-charge-fill text-warning"></i> <fmt:message key="dashboard.quickActions"></fmt:message>
                        </h5>
                        <div class="row g-3">
                            <div class="col-md-3">
                                <a href="${pageContext.request.contextPath}/admin/news?action=create" 
                                   class="btn btn-primary w-100 py-3">
                                    <i class="bi bi-plus-circle me-2"></i><fmt:message key="dashboard.management.news"></fmt:message>
                                </a>
                            </div>
                            <% if (isAdmin) {%>
                            <div class="col-md-3">
                                <a href="${pageContext.request.contextPath}/admin/category" 
                                   class="btn btn-success w-100 py-3">
                                    <i class="bi bi-tags me-2"></i><fmt:message key="dashboard.management.category"></fmt:message>
                                </a>
                            </div>
                            <div class="col-md-3">
                                <a href="${pageContext.request.contextPath}/admin/user" 
                                   class="btn btn-info w-100 py-3">
                                    <i class="bi bi-person-plus me-2"></i><fmt:message key="dashboard.management.user"></fmt:message>
                                </a>
                            </div>
                            <div class="col-md-3">
                                <a href="${pageContext.request.contextPath}/home" 
                                   class="btn btn-outline-secondary w-100 py-3">
                                    <i class="bi bi-box-arrow-up-right me-2"></i><fmt:message key="dashboard.management.homepage"></fmt:message>
                                </a>
                            </div>
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>

        <!-- Recent Activity -->
        <div class="row g-4">
            <!-- Tin mới nhất -->
            <div class="col-md-6">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title fw-bold mb-4">
                            <i class="bi bi-clock-history text-primary"></i> <fmt:message key="dashboard.p3.latest"/>
                        </h5>
                        <div class="list-group list-group-flush">
                            <!-- Sample data - thay bằng dữ liệu thật từ database -->
                            <div class="list-group-item d-flex justify-content-between align-items-start">
                                <div class="ms-2 me-auto">
                                    <div class="fw-bold">Tiêu đề tin tức mẫu 1</div>
                                    <small class="text-muted">2 giờ trước</small>
                                </div>
                                <span class="badge bg-primary rounded-pill">Công nghệ</span>
                            </div>
                            <div class="list-group-item d-flex justify-content-between align-items-start">
                                <div class="ms-2 me-auto">
                                    <div class="fw-bold">Tiêu đề tin tức mẫu 2</div>
                                    <small class="text-muted">5 giờ trước</small>
                                </div>
                                <span class="badge bg-success rounded-pill">Thể thao</span>
                            </div>
                            <div class="list-group-item d-flex justify-content-between align-items-start">
                                <div class="ms-2 me-auto">
                                    <div class="fw-bold">Tiêu đề tin tức mẫu 3</div>
                                    <small class="text-muted">1 ngày trước</small>
                                </div>
                                <span class="badge bg-info rounded-pill">Giải trí</span>
                            </div>
                        </div>
                        <div class="text-center mt-3">
                            <a href="${pageContext.request.contextPath}/admin/news" class="btn btn-sm btn-outline-primary">
                                <fmt:message key="dashboard.p3.watchAll"/> <i class="bi bi-arrow-right"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Thông tin hệ thống -->
            <div class="col-md-6">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title fw-bold mb-4">
                            <i class="bi bi-info-circle text-success"></i> <fmt:message key="dashboard.p3.information"/>
                        </h5>
                        <div class="table-responsive">
                            <table class="table table-sm">
                                <tbody>
                                    <tr>
                                        <td><i class="bi bi-person-badge text-primary"></i> <fmt:message key="dashboard.p3.role"/></td>
                                        <td class="text-end">
                                            <span class="badge ${sessionScope.user.role ? 'bg-danger' : 'bg-info'}">
                                                <% if ( isAdmin ) {%>
                                                	<fmt:message key="role.admin"/> 
                                                <% } else { %>
                                                	<fmt:message key="role.reporter"/>
                                                <% } %>
                                            </span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><i class="bi bi-envelope text-primary"></i> Email</td>
                                        <td class="text-end">${sessionScope.user.email}</td>
                                    </tr>
                                    <tr>
                                        <td><i class="bi bi-phone text-primary"></i> <fmt:message key="dashboard.p3.phone"/></td>
                                        <td class="text-end">${sessionScope.user.mobile}</td>
                                    </tr>
                                    <tr>
                                        <td><i class="bi bi-calendar-check text-primary"></i> <fmt:message key="dashboard.p3.dob"/></td>
                                        <td class="text-end">${sessionScope.user.birthdayFormatted}</td>
                                    </tr>
                                    <tr>
                                        <td><i class="bi bi-clock text-primary"></i> <fmt:message key="dashboard.p3.status"/></td>
                                        <td class="text-end">
                                            <span class="badge bg-success"><fmt:message key="dashboard.p3.state"/></span>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="alert alert-light mt-3 mb-0">
                            <small>
                                <i class="bi bi-lightbulb text-warning"></i>
                                <strong><fmt:message key="dashboard.p3.tips"/></strong> <fmt:message key="dashboard.p3.tips2"/>
                            </small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>