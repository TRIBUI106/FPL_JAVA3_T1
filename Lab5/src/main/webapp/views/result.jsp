<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Staff Information - Result</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            font-family: 'Segoe UI', sans-serif;
        }
        .card {
            background: rgba(255, 255, 255, 0.97);
            border: none;
            border-radius: 24px;
            overflow: hidden;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
        }
        .profile-img {
            width: 180px;
            height: 180px;
            object-fit: cover;
            border: 6px solid white;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        }
        .info-label {
            font-weight: 600;
            color: #5a6c7d;
            font-size: 1.1rem;
        }
        .info-value {
            font-size: 1.2rem;
            color: #2c3e50;
            font-weight: 500;
        }
        .badge-hobby {
            background: linear-gradient(45deg, #a8edea, #fed6e3);
            color: #333;
            font-weight: 600;
            padding: 0.5em 1em;
            border-radius: 50px;
        }
        h1 {
            background: linear-gradient(45deg, #4facfe, #00f2fe);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            font-weight: 800;
        }
    </style>
</head>
<body class="d-flex align-items-center py-5">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-10 col-xl-8">
                <div class="card p-5">
                    <div class="text-center mb-5">
                        <h1 class="display-5">Staff Information</h1>
                        <p class="text-muted fs-5">Successfully added!</p>
                    </div>

                    <div class="row g-5">
                        <div class="col-md-4 text-center">
                            <img src="<%=request.getContextPath()%>/files/${bean.photo_file}" 
                                 class="rounded-circle profile-img" alt="Staff Photo">
                        </div>
                        <div class="col-md-8">
                            <div class="row g-4">
                                <div class="col-12">
                                    <span class="info-label"><i class="fas fa-user me-2"></i> Full Name</span>
                                    <p class="info-value fs-4">${bean.fullname}</p>
                                </div>
                                <div class="col-sm-6">
                                    <span class="info-label"><i class="fas fa-calendar-alt me-2"></i> Birthday</span>
                                    <p class="info-value">${bean.birthday}</p>
                                </div>
                                <div class="col-sm-6">
                                    <span class="info-label"><i class="fas fa-venus-mars me-2"></i> Gender</span>
                                    <p class="info-value">
                                        <c:choose>
                                            <c:when test="${bean.gender}">Male</c:when>
                                            <c:otherwise>Female</c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                                <div class="col-sm-6">
                                    <span class="info-label"><i class="fas fa-ring me-2"></i> Marital Status</span>
                                    <p class="info-value">
                                        <c:choose>
                                            <c:when test="${bean.married}">Married</c:when>
                                            <c:otherwise>Single</c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                                <div class="col-sm-6">
                                    <span class="info-label"><i class="fas fa-flag me-2"></i> Country</span>
                                    <p class="info-value">${bean.country}</p>
                                </div>
                                <div class="col-12">
                                    <span class="info-label"><i class="fas fa-gamepad me-2"></i> Hobbies</span>
                                    <div class="mt-3">
                                        <c:forEach items="${bean.hobbies}" var="hobby">
                                            <span class="badge badge-hobby me-2 mb-2">${hobby}</span>
                                        </c:forEach>
                                        <c:if test="${empty bean.hobbies}">
                                            <span class="text-muted">No hobbies selected</span>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <span class="info-label"><i class="fas fa-sticky-note me-2"></i> Note</span>
                                    <p class="info-value mt-2">${bean.note != null ? bean.note : '—'}</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="text-center mt-5">
                        <a href="<%=request.getContextPath()%>/form.jsp" class="btn btn-outline-primary px-5 py-3 rounded-pill">
                            <i class="fas fa-plus me-2"></i> Add Another Staff
                        </a>
                        <a href="<%=request.getContextPath()%>/">Back to Home</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>