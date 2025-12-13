<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${categoryName} - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <%@ include file="../layout/guest-header.jsp" %>

    <div class="container my-5">
        <h2 class="text-primary fw-bold mb-4">${categoryName}</h2>
        
        <!-- Kiểm tra nếu có tin tức -->
        <c:if test="${empty list}">
            <div class="alert alert-info">
                <i class="bi bi-info-circle"></i> Chưa có tin tức nào trong danh mục này.
            </div>
        </c:if>
        
        <div class="row g-4">
            <c:forEach var="news" items="${list}">
                <div class="col-md-6">
                    <div class="card border-0 shadow-sm h-100">
                        <!-- Image -->
                        <c:choose>
                            <c:when test="${not empty news.image}">
                                <img src="${pageContext.request.contextPath}/uploads/${news.image}" 
                                     class="card-img-top" 
                                     alt="${news.title}"
                                     style="height: 250px; object-fit: cover;">
                            </c:when>
                            <c:otherwise>
                                <img src="https://via.placeholder.com/600x350?text=No+Image" 
                                     class="card-img-top" 
                                     alt="No image"
                                     style="height: 250px; object-fit: cover;">
                            </c:otherwise>
                        </c:choose>
                        
                        <!-- Card Body -->
                        <div class="card-body d-flex flex-column">
                            <!-- Title -->
                            <h5 class="card-title">
                                <a href="${pageContext.request.contextPath}/detail?id=${news.id}" 
                                   class="text-decoration-none text-dark">
                                    ${news.title}
                                </a>
                            </h5>
                            
                            <!-- Content (rút gọn) -->
                            <p class="card-text text-muted flex-grow-1">
                                <!-- Hiển thị 150 ký tự đầu của content -->
                                <c:choose>
                                    <c:when test="${fn:length(news.content) > 150}">
                                        ${fn:substring(news.content, 0, 150)}...
                                    </c:when>
                                    <c:otherwise>
                                        ${news.content}
                                    </c:otherwise>
                                </c:choose>
                            </p>
                            
                            <!-- Footer: Date + Read More -->
                            <div class="d-flex justify-content-between align-items-center mt-auto">
                                <small class="text-muted">
                                    <i class="bi bi-calendar"></i> ${news.postedDate}
                                </small>
                                <a href="${pageContext.request.contextPath}/detail?id=${news.id}" 
                                   class="btn btn-sm btn-outline-primary">
                                    Đọc thêm <i class="bi bi-arrow-right"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <%@ include file="../layout/guest-footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>