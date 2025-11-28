<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!-- Không cần check nữa vì InitServlet đã load sẵn từ lúc khởi động -->
<!-- Nếu vẫn muốn an toàn 200% thì để lại đoạn này cũng được (không sao) -->
<c:if test="${empty applicationScope.categories}">
    <jsp:include page="/init-app" />
</c:if>

<header class="shadow-sm" style="background: linear-gradient(90deg, #0d6efd, #003c9e);">
    <nav class="navbar navbar-expand-lg navbar-dark py-3">
        <div class="container">
            <a class="navbar-brand fs-3 fw-bold" href="${pageContext.request.contextPath}/home">
                ABC NEWS
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto gap-3">
                    <li class="nav-item">
                        <a class="nav-link text-white fw-semibold" href="${pageContext.request.contextPath}/home">Trang chủ</a>
                    </li>
                    <c:forEach items="${applicationScope.categories}" var="cat">
                        <li class="nav-item">
                            <a class="nav-link text-white-50 hover-white" 
                               href="${pageContext.request.contextPath}/category?id=${cat.id}">
                                ${cat.name}
                            </a>
                        </li>
                    </c:forEach>
                    <li class="nav-item">
                        <a class="btn btn-outline-light px-4 py-2 rounded-pill fw-semibold"
                           href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<style>
    .hover-white:hover { color: #fff !important; transform: translateY(-2px); transition: 0.2s; }
</style>