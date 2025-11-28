<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="dao.CategoryDAO" %>
<%@ page import="entity.Category" %>
<%@ page import="java.util.List" %>


<c:if test="${empty applicationScope.categories}">
    <jsp:include page="${pageContext.request.contextPath}/" />
</c:if>

<header class="shadow-sm" 
        style="background: linear-gradient(90deg, #0d6efd, #003c9e);">
    <nav class="navbar navbar-expand-lg navbar-dark py-3">
        <div class="container">

            <!-- Logo -->
            <a class="navbar-brand fs-3 fw-bold" 
               href="${pageContext.request.contextPath}/home"
               style="letter-spacing:1px;">
                ABC NEWS
            </a>

            <!-- Toggle -->
            <button class="navbar-toggler" type="button" 
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- Menu -->
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto gap-3">

                    <li class="nav-item">
                        <a class="nav-link text-white fw-semibold" 
                           href="${pageContext.request.contextPath}/home">
                            Trang chủ
                        </a>
                    </li>

                    <!-- GIỮ NGUYÊN categories M CÓ -->
                    <c:forEach items="${applicationScope.categories}" var="cat">
                        <li class="nav-item">
                            <a class="nav-link text-white-50" 
                               href="${pageContext.request.contextPath}/category?id=${cat.id}">
                                ${cat.name}
                            </a>
                        </li>
                    </c:forEach>

                    <li class="nav-item">
                        <a class="btn btn-outline-light px-4 py-2 rounded-pill fw-semibold"
                           href="${pageContext.request.contextPath}/login">
                            Đăng nhập
                        </a>
                    </li>

                </ul>
            </div>

        </div>
    </nav>
</header>

<style>
    /* Chỉ thêm UI, không đụng logic */
    .nav-link {
        transition: 0.2s;
    }
    .nav-link:hover {
        color: #fff !important;
        transform: translateY(-1px);
    }
    .btn-outline-light:hover {
        background: #fff !important;
        color: #0d6efd !important;
        border-color: #fff !important;
    }
</style>
