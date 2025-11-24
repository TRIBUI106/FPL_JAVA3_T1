<%@ page contentType="text/html;charset=UTF-8" %>
<header class="bg-primary text-white shadow-sm">
    <div class="container py-3">
        <div class="d-flex flex-wrap align-items-center justify-content-between">
            <h1 class="h3 mb-0 fw-bold">ABC NEWS</h1>
            <nav class="nav nav-pills gap-3">
                <a href="<%=request.getContextPath()%>/home" class="nav-link text-white active">Trang chủ</a>
                <a href="<%=request.getContextPath()%>/guest/category.jsp?id=TT" class="nav-link text-white">Thời sự</a>
                <a href="<%=request.getContextPath()%>/guest/category.jsp?id=KT" class="nav-link text-white">Kinh tế</a>
                <a href="<%=request.getContextPath()%>/guest/category.jsp?id=PL" class="nav-link text-white">Pháp luật</a>
                <a href="<%=request.getContextPath()%>/guest/category.jsp?id=VH" class="nav-link text-white">Văn hóa</a>
                <a href="<%=request.getContextPath()%>/guest/category.jsp?id=SK" class="nav-link text-white">Thể thao</a>
            </nav>
        </div>
    </div>
</header>