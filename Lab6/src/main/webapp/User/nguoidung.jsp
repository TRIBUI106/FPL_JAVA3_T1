<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.Lang}" />
<fmt:setBundle basename="i18n/global" />

<!-- Gọi header chung -->
<jsp:include page="../menu.jsp"/>

<h2><fmt:message key="about.title"/></h2>
<p><fmt:message key="about.description"/></p>
<h1> quản lý người dùng </h1>

<hr/>
<footer>
    <p>&copy; 2025 Demo đa ngôn ngữ JSP</p>
</footer>
