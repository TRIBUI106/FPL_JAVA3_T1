<%@ page contentType="text/html; charset=UTF-8" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<header class="bg-primary text-white py-3 mb-3">
    <div class="container">
        <h2 class="fw-bold">News Portal</h2>
        <nav class="mt-2">
            <a href="${pageContext.request.contextPath}/home" class="text-white me-3">Trang chủ</a>
			<a href="${pageContext.request.contextPath}/category?id=1" class="text-white me-3">Văn hóa</a>
			<a href="${pageContext.request.contextPath}/category?id=2" class="text-white me-3">Pháp luật</a>
			<a href="${pageContext.request.contextPath}/category?id=3" class="text-white me-3">Thể thao</a>
		</nav>
    </div>
</header>
