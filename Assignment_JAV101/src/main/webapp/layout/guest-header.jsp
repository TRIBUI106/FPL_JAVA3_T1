<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="dao.CategoryDAO" %>
<%@ page import="entity.Category" %>
<%@ page import="java.util.List" %>


<c:if test="${empty applicationScope.categories}">
    <c:redirect url="/init-app"/>
</c:if>

<header class="bg-primary text-white shadow-sm">
    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <a class="navbar-brand fs-4 fw-bold" href="${pageContext.request.contextPath}/home">ABC NEWS</a>
            
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto gap-3">
                    <li class="nav-item">
                        <a class="nav-link text-white fw-bold" href="${pageContext.request.contextPath}/home">Trang chủ</a>
                    </li>

                    <!-- HIỆN DANH MỤC TỪ APPLICATION SCOPE -->
                    <c:forEach items="${applicationScope.categories}" var="cat">
                        <li class="nav-item">
                            <a class="nav-link text-white" 
                               href="${pageContext.request.contextPath}/category?id=${cat.id}">
                                ${cat.name}
                            </a>
                        </li>
                    </c:forEach>

                    <li class="nav-item">
                        <a class="btn nav-link text-white" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>