<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${pageTitle}</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
	rel="stylesheet">
<style>
.news-card:hover {
	transform: translateY(-5px);
	transition: all 0.3s;
	box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.sidebar-box {
	background: #f8f9fa;
	border-radius: 12px;
	padding: 20px;
}
</style>
</head>
<body class="bg-light">
	<%@ include file="../layout/guest-header.jsp"%>

	<div class="container my-5">
		<div class="row g-4">
			<!-- Sidebar trái -->
			<div class="col-lg-4">
				<%@ include file="../layout/recent-viewed.jsp"%>

				<div class="sidebar-box mb-4">
					<h5 class="bg-danger text-white p-3 rounded-top mb-0">
						<i class="bi bi-fire"></i> Đã xem gần đây
					</h5>
					<ul class="list-group list-group-flush">
						<li class="list-group-item"><a
							href="${pageContext.request.contextPath}/detail?id=1"
							class="text-danger fw-bold"> TP.HCM mưa lớn, nhiều tuyến
								đường ngập sâu </a></li>
						<li class="list-group-item"><a href="#">Việt Nam vô địch
								SEA Games 33</a></li>
						<li class="list-group-item"><a href="#">Giá xăng giảm lần
								thứ 5 liên tiếp</a></li>
					</ul>
				</div>
			</div>

			<!-- Tin nổi bật -->
			<div class="col-lg-8">
				<h2 class="text-primary fw-bold mb-4 border-bottom pb-3">
					<i class="bi bi-star"></i> Tin nổi bật
				</h2>
				<div class="row g-4">
					<c:forEach var="n" items="${homeNews}">
						<div class="col-sm-6 col-md-4">
							<div class="card news-card border-0 shadow-sm h-100">
								<img src="${pageContext.request.contextPath}/${n.image}"
									class="card-img-top" alt="${n.title}">
								<div class="card-body">
									<h6>
										<a href="${pageContext.request.contextPath}/detail?id=${n.id}"
											class="text-decoration-none text-dark fw-bold">
											${n.title} </a>
									</h6>
									<p class="text-muted small">
										<i class="bi bi-person"></i> ${n.author} • ${n.postedDate}
									</p>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>

				<!-- Tin mới nhất -->
				<h2 class="text-success fw-bold mb-4 border-bottom pb-3 mt-5">
					<i class="bi bi-clock-history"></i> Tin mới nhất
				</h2>
				<div class="row g-4">
					<c:forEach var="n" items="${latestNews}">
						<div class="col-sm-6 col-md-4">
							<div class="card news-card border-0 shadow-sm h-100">
								<img src="${pageContext.request.contextPath}/${n.image}"
									class="card-img-top" alt="${n.title}">
								<div class="card-body">
									<h6>
										<a
											href="${pageContext.request.contextPath}/detail?id=${n.id}"
											class="text-decoration-none text-dark fw-bold">
											${n.title} </a>
									</h6>
									<p class="text-muted small">
										<i class="bi bi-person"></i> ${n.author} • ${n.postedDate}
									</p>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../layout/guest-footer.jsp"%>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
