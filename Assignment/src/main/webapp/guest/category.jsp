<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${categoryName} - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <%@ include file="../layout/guest-header.jsp" %>

    <div class="container my-5">
        <h2 class="text-primary fw-bold mb-4">${categoryName}</h2>
        <div class="row g-4">
            <div class="col-md-6">
                <div class="card border-0 shadow-sm">
                    <img src="https://via.placeholder.com/600x350" class="card-img-top" alt="news">
                    <div class="card-body">
                        <h5><a href="${pageContext.request.contextPath}/detail?id=1" class="text-decoration-none">Bão số 7 sắp đổ bộ miền Trung</a></h5>
                        <small class="text-muted">24/11/2025</small>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../layout/guest-footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>