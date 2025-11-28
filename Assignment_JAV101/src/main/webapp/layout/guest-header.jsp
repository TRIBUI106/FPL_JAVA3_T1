<header class="shadow-sm border-bottom" style="background: linear-gradient(90deg, #0d6efd 0%, #003c9e 100%);">
    <nav class="navbar navbar-expand-lg navbar-dark py-3">
        <div class="container">

            <!-- Logo -->
            <a class="navbar-brand fs-3 fw-bold tracking-wide" 
               href="${pageContext.request.contextPath}/home"
               style="letter-spacing: 1px;">
                ABC NEWS
            </a>

            <!-- Toggle -->
            <button class="navbar-toggler" type="button" 
                    data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- Menu -->
            <div class="collapse navbar-collapse" id="navbarNav">

                <ul class="navbar-nav ms-auto align-items-lg-center gap-lg-4 mt-3 mt-lg-0">

                    <li class="nav-item">
                        <a class="nav-link text-white fs-6 fw-semibold" href="${pageContext.request.contextPath}/home">
                            Trang chủ
                        </a>
                    </li>

                    <!-- Categories -->
                    <c:forEach items="${applicationScope.categories}" var="cat">
                        <li class="nav-item">
                            <a class="nav-link text-white-50 fs-6"
                               href="${pageContext.request.contextPath}/category?id=${cat.id}"
                               style="transition: 0.2s;">
                                ${cat.name}
                            </a>
                        </li>
                    </c:forEach>

                    <!-- Login -->
                    <li class="nav-item">
                        <a class="btn btn-outline-light fw-semibold px-4 py-2 rounded-pill"
                           href="${pageContext.request.contextPath}/login"
                           style="transition: 0.25s;">
                            Đăng nhập
                        </a>
                    </li>

                </ul>
            </div>

        </div>
    </nav>
</header>

<style>
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
