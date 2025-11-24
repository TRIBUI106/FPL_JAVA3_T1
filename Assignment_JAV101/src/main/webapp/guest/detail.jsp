<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Chi tiết tin - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../assets/css/guest-style.css">
</head>
<body class="guest-body">
    <%@ include file="../layout/header.jsp" %>

    <div class="container my-5">
        <div class="row">
            <div class="col-lg-3">
                <%@ include file="sidebar.jsp" %>
            </div>
            <div class="col-lg-9">
                <div class="content-area">
                    <h1 class="text-primary mb-4">TP.HCM mưa lớn, nhiều tuyến đường ngập sâu</h1>
                    <div class="text-muted mb-4">
                        <small>Tác giả: Nguyễn Văn A • Ngày đăng: 24/11/2025 • Lượt xem: 12,456</small>
                    </div>
                    <img src="../assets/images/news1.jpg" class="img-fluid rounded mb-4" alt="news">
                    <p class="lead fs-5">Nội dung bản tin... (đoạn văn dài 500-1000 từ ở đây)</p>
                    
                    <hr class="my-5">
                    <h4 class="text-success">Tin cùng chuyên mục</h4>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <a href="detail.jsp" class="text-decoration-none">Cơn mưa lịch sử khiến Sài Gòn tê liệt</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../layout/footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>