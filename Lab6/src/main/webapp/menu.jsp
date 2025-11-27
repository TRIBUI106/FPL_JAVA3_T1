<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.Lang}" />
<fmt:setBundle basename="i18n/global" />


<c:url var="homeUrl" value="/admin.jsp"/>
<c:url var="aboutUrl" value="/lienhe.jsp"/>
<c:url var="deptUrl" value="/Departments"/>
<c:url var="userUrl" value="/User/nguoidung.jsp"/>
<c:url var="logoutUrl" value="/logout"/>

<nav>
    <a href="${homeUrl}"><fmt:message key="menu.home"/></a>||
    <a href="${aboutUrl}"><fmt:message key="menu.about"/></a>||
    <a href="${deptUrl}"><fmt:message key="menu.department"/></a>||
    <a href="${userUrl}"><fmt:message key="menu.user"/></a>||
    <a href="${logoutUrl}"><fmt:message key="menu.logout"/></a>

    <div class="lang-switch" style="margin-left:auto;">
        <a href="?lang=vi"> TIẾNG VIỆT </a> | <a href="?lang=en"> ENLISH</a>
    </div>
</nav>
<hr/>
