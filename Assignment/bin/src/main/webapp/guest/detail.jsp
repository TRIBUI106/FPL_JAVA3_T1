<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết tin tức - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <%@ include file="../layout/guest-header.jsp" %>

    <div class="container my-5">
        <div class="row">
            <!-- Nội dung chính -->
            <div class="col-lg-8">
                <article class="bg-white p-5 rounded shadow-sm">
                    <h1 class="text-primary fw-bold mb-4">${news.title}</h1>
                    <p class="text-muted mb-4">
					    ABC News • ${news.postedDate} 
					    <span class="mx-2">•</span> 
					    <strong>${news.viewCount}</strong> lượt xem
					</p>
                    <img src="${pageContext.request.contextPath}/${news.image}" class="img-fluid rounded mb-4" alt="${news.title}">
                    <div class="lead fs-5 text-justify">
                        ${news.content}
                    </div>
                </article>
            </div>

            <!-- Sidebar: Đã xem gần đây -->
            <div class="col-lg-4">
			    <div class="sidebar-box mb-4">
			        <h5 class="bg-success text-white p-3 rounded-top mb-0 d-flex align-items-center">
			            <i class="bi bi-clock-history me-2"></i> 5 tin gần nhất bạn đã xem
			        </h5>
			        <ul class="list-group list-group-flush">
			
			            <c:set var="recent" value="${cookie.recent_news.value}" />
			            <c:if test="${not empty recent && recent != ''}">
			                <%
			                    String recent = (String) pageContext.getAttribute("recent");
			                    String[] items = recent.split("#");  // <<< ĐỔI THÀNH # Ở ĐÂY
			                    int count = 0;
			                    for (String item : items) {
			                        if (count >= 5) break;
			                        if (item == null || item.trim().isEmpty()) continue;
			
			                        String[] p = item.split("\\|", -1);
			                        String id = p[0];
			                        String title = java.net.URLDecoder.decode(p[1], "UTF-8");
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
			</div>

    <%@ include file="../layout/guest-footer.jsp" %>
</body>
</html>
