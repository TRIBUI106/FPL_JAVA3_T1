<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ABC News - Tin tức mới nhất</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">
    <%@ include file="../layout/header.jsp" %>

    <div class="container my-5">
        <div class="row g-4">
            <!-- Sidebar -->
            <div class="col-lg-4">
                <div class="card border-0 shadow-sm mb-4">
                    <div class="card-header bg-danger text-white"><i class="bi bi-fire"></i> Xem nhiều nhất</div>
                    <div class="list-group list-group-flush">
                        <a href="detail.jsp" class="list-group-item list-group-item-action">TP.HCM mưa lớn, nhiều tuyến đường ngập sâu</a>
                        <a href="detail.jsp" class="list-group-item list-group-item-action">Việt Nam vô địch SEA Games 33</a>
                        <a href="detail.jsp" class="list-group-item list-group-item-action">Giá xăng giảm lần thứ 5</a>
                    </div>
                </div>

                <div class="card border-0 shadow-sm mb-4">
                    <div class="card-header bg-success text-white"><i class="bi bi-clock"></i> Tin mới nhất</div>
                    <div class="list-group list-group-flush">
                        <a href="detail.jsp" class="list-group-item list-group-item-action">Bão số 7 sắp đổ bộ miền Trung</a>
                    </div>
                </div>

                <div class="card border-0 shadow-sm">
                    <div class="card-header bg-purple text-white"><i class="bi bi-eye"></i> Đã xem gần đây</div>
                    <div class="list-group list-group-flush">
                        <a href="detail.jsp" class="list-group-item list-group-item-action">U23 Việt Nam thắng lớn</a>
                    </div>
                </div>
            </div>

            <!-- Main content -->
            <div class="col-lg-8">
                <h2 class="mb-4 text-primary fw-bold">Tin nổi bật</h2>
                <div class="row g-4">
                    <div class="col-md-6">
                        <div class="card border-0 shadow-sm h-100">
                            <img src="../assets/images/news1.jpg" class="card-img-top" alt="news" style="height:200px; object-fit:cover;">
                            <div class="card-body">
                                <h5 class="card-title"><a href="detail.jsp" class="text-decoration-none text-dark">TP.HCM mưa lớn, nhiều tuyến đường ngập sâu</a></h5>
                                <p class="text-muted small">Nguyễn Văn A • 24/11/2025</p>
                            </div>
                        </div>
                    </div>
                    <!-- Thêm 5-6 tin nữa giống vậy -->
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../layout/footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .bg-purple { background: linear-gradient(135deg, #6f42c1, #8b5cf6); }
    </style>
</body>
</html>