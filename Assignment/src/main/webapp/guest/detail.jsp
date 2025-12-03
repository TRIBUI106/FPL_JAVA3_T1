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
                    <h5 class="bg-danger text-white p-3 rounded-top mb-0">
                        <i class="bi bi-fire"></i> Đã xem gần đây
                    </h5>
                    <ul class="list-group list-group-flush">
                        <%
                            Cookie[] cookies = request.getCookies();
                            if (cookies != null) {
                                for (Cookie ck : cookies) {
                                    if ("recent_news".equals(ck.getName())) {
                                        String[] arr = ck.getValue().split(";;");
                                        for (String s : arr) {
                                            String[] parts = s.split("\\|");
                                            if (parts.length >= 3) {
                                                String nid = parts[0];
                                                String ntitle = java.net.URLDecoder.decode(parts[1], "UTF-8");
                                                String nimg = parts[2];
                        %>
                        <li class="list-group-item">
                            <a href="<%=request.getContextPath()%>/detail?id=<%=nid%>" class="fw-bold">
                                <%=ntitle%>
                            </a>
                        </li>
                        <%
                                            }
                                        }
                                    }
                                }
                            }
                        %>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../layout/guest-footer.jsp" %>
</body>
</html>
