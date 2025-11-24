<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Chi tiết tin tức - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <%@ include file="../layout/guest-header.jsp" %>

    <div class="container my-5">
        <div class="row">
            <div class="col-lg-8">
                <article class="bg-white p-5 rounded shadow-sm">
                    <h1 class="text-primary fw-bold mb-4">TP.HCM mưa lớn, nhiều tuyến đường ngập sâu</h1>
                    <p class="text-muted mb-4"><i class="bi bi-person"></i> Nguyễn Văn A • 24/11/2025 • Lượt xem: 12,456</p>
                    <img src="https://via.placeholder.com/800x450" class="img-fluid rounded mb-4" alt="news">
                    <div class="lead fs-5 text-justify">
                        Nội dung bản tin chi tiết dài 800-1000 từ ở đây...<br><br>
                        (Cậu có thể copy 1 bài báo thật để dán vào)
                    </div>
                </article>
            </div>
            <div class="col-lg-4">
                <!-- Sidebar giống home.jsp -->
            </div>
        </div>
    </div>

    <%@ include file="../layout/guest-footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>