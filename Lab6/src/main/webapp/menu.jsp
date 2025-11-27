<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.Lang}" />
<fmt:setBundle basename="i18n/global" />

<style>
    nav {
        background: linear-gradient(90deg, #e74c3c, #c0392b);
        padding: 15px 50px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.2);
        position: sticky;
        top: 0;
        z-index: 1000;
    }
    nav a {
        color: white;
        text-decoration: none;
        font-weight: 500;
        padding: 10px 20px;
        border-radius: 30px;
        transition: all 0.3s;
        margin: 0 5px;
    }
    nav a:hover {
        background: rgba(255,255,255,0.2);
        transform: translateY(-2px);
    }
    .lang-switch {
        float: right;
        margin-top: 8px;
    }
    .lang-switch a {
        background: rgba(255,255,255,0.2);
        padding: 8px 15px !important;
        border-radius: 20px;
        font-size: 14px;
    }
    .lang-switch a:hover {
        background: white;
        color: #e74c3c !important;
    }
    hr { border: 1px solid #eee; margin: 0; }
</style>

<nav>
    <a href="${pageContext.request.contextPath}/admin.jsp">Trang Chủ</a> |
    <a href="${pageContext.request.contextPath}/lienhe.jsp">Liên Hệ</a> |
    <a href="${pageContext.request.contextPath}/Departments">Phòng Ban</a> |
    <a href="${pageContext.request.contextPath}/User">Người Dùng</a> |
    <a href="${pageContext.request.contextPath}/logout">Đăng Xuất</a>

    <div class="lang-switch">
        <a href="?lang=vi">VIỆT NAM</a> |
        <a href="?lang=en">ENGLISH</a>
    </div>
</nav>