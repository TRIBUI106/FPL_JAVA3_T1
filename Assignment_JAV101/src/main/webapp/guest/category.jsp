<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Thời sự - ABC News</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="guest-body">
    <%@ include file="../layout/header.jsp" %>

    <div class="container my-5">
        <div class="row">
            <div class="col-lg-3">
                <!-- Sidebar giống home -->
                <%@ include file="sidebar.jsp" %>
            </div>
            <div class="col-lg-9">
                <div class="content-area">
                    <h2 class="text-primary mb-4 border-bottom pb-3">THỜI SỰ</h2>
                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="card news-card border-0">
                                <img src="../assets/images/news2.jpg" class="card-img-top" alt="news">
                                <div class="card-body">
                                    <h5><a href="detail.jsp" class="text-decoration-none">Bão số 7 sắp đổ bộ miền Trung</a></h5>
                                    <small class="text-muted">Trần Văn B • 24/11/2025</small>
                                </div>
                            </div>
                        </div>
                        <!-- Lặp thêm 5-6 tin -->
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../layout/footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>