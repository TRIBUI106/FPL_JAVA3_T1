<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="sidebar-box mb-4">
    <h5 class="bg-success text-white p-3 rounded-top mb-0 d-flex align-items-center">
        <i class="bi bi-clock-history me-2"></i> 5 tin gần nhất bạn đã xem
    </h5>
    <ul class="list-group list-group-flush">

        <!-- Lấy cookie -->
        <c:set var="recent" value="${cookie.recent_news.value}" />
        <c:if test="${not empty recent && recent != ''}">
            <%
                String recent = (String) pageContext.getAttribute("recent");
                String[] items = recent.split(";;");
                int count = 0;
                for (String item : items) {
                    if (count >= 5) break;
                    if (item == null || item.trim().isEmpty()) continue;

                    String[] p = item.split("\\|", -1); // split giữ nguyên phần rỗng
                    String id = p[0];
                    String title = java.net.URLDecoder.decode(p[1].replace("+", " "), "UTF-8");
                    String image = p.length > 2 ? p[2] : "";
            %>
            <li class="list-group-item border-0 px-0 py-3">
                <div class="d-flex gap-3 align-items-start">
                    <% if (image != null && !image.isEmpty() && !"null".equals(image)) { %>
                    <img src="<%=image%>" class="rounded flex-shrink-0" width="80" height="60" style="object-fit:cover" alt="thumb">
                    <% } %>
                    <div>
                        <a href="${pageContext.request.contextPath}/detail?id=<%=id%>"
                           class="text-success text-decoration-none fw-600 hover-underline line-clamp-2 d-block mb-1">
                            <%=title%>
                        </a>
                        <small class="text-muted">Vừa xem</small>
                    </div>
                </div>
            </li>
            <%
                    count++;
                }
            %>
        </c:if>

        <c:if test="${empty recent || recent == ''}">
            <li class="list-group-item text-center text-muted py-4">
                Chưa có tin nào được xem
            </li>
        </c:if>
    </ul>
</div>

<style>
    .line-clamp-2{overflow:hidden;display:-webkit-box;-webkit-line-clamp:2;-webkit-box-orient:vertical}
    .hover-underline:hover{text-decoration:underline!important}
    .fw-600{font-weight:600}
</style>