<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<fmt:setBundle basename="locale" scope="application" />
<%
String toastType = (String) session.getAttribute("toastType");
String toastMessage = (String) session.getAttribute("toastMessage");

// Xóa message khỏi session sau khi đọc
if (toastType != null && toastMessage != null) {
	session.removeAttribute("toastType");
	session.removeAttribute("toastMessage");
}
%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Quản lý tin tức - ABC News</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="d-flex min-vh-100">
		<%@ include file="../layout/admin-sidebar.jsp"%>
		<div class="flex-grow-1 bg-light">
			<%@ include file="../layout/admin-header.jsp"%>
			<div class="container my-5">
				<h2 class="text-primary fw-bold mb-4"> <fmt:message key="news.title" /> </h2> 

				<!-- Toast Container -->
				<div class="toast-container position-fixed bottom-0 end-0 p-3"
					style="z-index: 9999;">
					<div id="mainToast" class="toast align-items-center border-0"
						role="alert" aria-live="assertive" aria-atomic="true">
						<div class="d-flex">
							<div class="toast-body d-flex align-items-center">
								<i id="toastIcon" class="me-2"></i> <span id="toastText"></span>
							</div>
							<button type="button"
								class="btn-close btn-close-white me-2 m-auto"
								data-bs-dismiss="toast" aria-label="Close"></button>
						</div>
					</div>
				</div>

				<div class="row mb-4 g-3">
					<div class="col-md-4">
						<button class="btn btn-success w-40" data-bs-toggle="modal"
							data-bs-target="#addModal">
							<i class="bi bi-plus-circle"></i> <fmt:message key="news.field.add" />
						</button>
					</div>
					<div class="col-md-8">
						<form action="${pageContext.request.contextPath}/admin/news"
							method="get" id="filterForm">
							<div class="row g-2">
								<div class="col-sm-4">
									<select name="searchBy" class="form-select"
										onchange="document.getElementById('filterForm').submit();">
										<option value="all" ${searchBy == 'all' ? 'selected' : ''}>--
											<fmt:message key="news.field.all" /> --</option>
										<c:forEach items="${categories}" var="c">
											<option value="${c.id}" ${searchBy == c.id ? 'selected' : ''}>${c.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-5">
									<input type="text" name="keyword" class="form-control"
										placeholder="<fmt:message key="news.field.input" />" value="${keyword}">
								</div>
								<div class="col-sm-3">
									<button type="submit" class="btn btn-primary w-100">
										<i class="bi bi-search"></i> <fmt:message key="news.field.search" />
									</button>
								</div>
							</div>
						</form>

					</div>
				</div>

				<table class="table table-striped table-hover">
					<thead class="table-dark">
						<tr>
							<th>ID</th>
							<th><fmt:message key="news.table.title" /></th>
							<th><fmt:message key="news.table.image" /></th>
							<th><fmt:message key="news.table.upload" /></th>
							<th><fmt:message key="news.table.author" /></th>
							<th><fmt:message key="news.table.actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listNews}" var="n">
							<tr>
								<td>${n.id}</td>
								<td>${n.title}</td>
								<td><img
									src="${pageContext.request.contextPath}/${n.image}" width="80"
									class="rounded"></td>
								<td>${n.postedDate}</td>
								<td>${n.author}</td>
								<td><a
									href="${pageContext.request.contextPath}/admin/news?action=edit&id=${n.id}"
									class="btn btn-warning btn-sm"><fmt:message key="news.field.edit" /></a> <a
									href="${pageContext.request.contextPath}/admin/news?action=delete&id=${n.id}"
									class="btn btn-danger btn-sm"
									onclick="return confirm('<fmt:message key="news.field.confirmDel" />')"><fmt:message key="news.field.delete" /></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- Modal thêm tin -->
	<div class="modal fade" id="addModal" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<form action="${pageContext.request.contextPath}/admin/news"
					method="post" enctype="multipart/form-data">
					<div class="modal-header">
						<h5 class="modal-title">
							<c:choose>
								<c:when test="${cat != null}"><fmt:message key="news.modal.edit"/></c:when>
								<c:otherwise><fmt:message key="news.modal.add"/></c:otherwise>
							</c:choose>
						</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>
					<div class="modal-body">
						<!-- hidden action -->
						<input type="hidden" name="action"
							value="${cat != null ? 'update' : ''}"> 
						<c:if test="${cat != null}">
							<input name="id"
								class="form-control mb-3" placeholder="<fmt:message key="news.modal.id"/>"
								value="${cat != null ? cat.id : ''}" required> 
						</c:if>
						<input
							name="title" class="form-control mb-3" placeholder="<fmt:message key="news.modal.text"/>"
							value="${cat != null ? cat.title : ''}" required>

						<textarea name="content" class="form-control mb-3" rows="4"
							placeholder="<fmt:message key="news.modal.content"/>">${cat != null ? cat.content : ''}</textarea>

						<input type="file" name="image" class="form-control mb-3"
							accept="image/*" ${cat == null ? 'required' : ''}> <select
							name="categoryId" class="form-select mb-3">
							<c:forEach items="${categories}" var="c">
								<option value="${c.id}"
									${cat != null && cat.categoryId == c.id ? 'selected' : ''}>
									${c.name}</option>
							</c:forEach>
						</select>

						<div class="form-check">
							<input type="checkbox" name="home" class="form-check-input"
								${cat != null && cat.home ? 'checked' : ''}> <label><fmt:message key="news.modal.showOnFeed"/></label>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal"><fmt:message key="news.modal.cancel"/></button>
						<button type="submit" class="btn btn-primary"><fmt:message key="news.modal.save"/></button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<c:if test="${cat != null}">
		<script>
			document.addEventListener("DOMContentLoaded", function() {
				var editModal = new bootstrap.Modal(document
						.getElementById('addModal'));
				editModal.show();
			});
		</script>
	</c:if>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

<!-- Script hiển thị toast -->
<%
if (toastType != null && toastMessage != null) {
%>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const toastEl = document.getElementById('mainToast');
        const toastIcon = document.getElementById('toastIcon');
        const toastText = document.getElementById('toastText');
        
        // Reset classes
        toastEl.className = 'toast align-items-center border-0';
        
        // Set style dựa trên type
        <%if ("success".equals(toastType)) {%>
            toastEl.classList.add('bg-success', 'text-white');
            toastIcon.className = 'bi bi-check-circle-fill me-2 fs-5';
        <%} else if ("error".equals(toastType)) {%>
            toastEl.classList.add('bg-danger', 'text-white');
            toastIcon.className = 'bi bi-exclamation-circle-fill me-2 fs-5';
        <%} else if ("warning".equals(toastType)) {%>
            toastEl.classList.add('bg-warning', 'text-dark');
            toastIcon.className = 'bi bi-exclamation-triangle-fill me-2 fs-5';
        <%} else if ("info".equals(toastType)) {%>
            toastEl.classList.add('bg-info', 'text-white');
            toastIcon.className = 'bi bi-info-circle-fill me-2 fs-5';
        <%}%>
        
        // Set message
        toastText.textContent = '<%=toastMessage.replace("'", "\\'")%>
	';

		// Show toast với animation
		const toast = new bootstrap.Toast(toastEl, {
			autohide : true,
			delay : 4000
		// 4 giây
		});
		toast.show();
	});
</script>
<%
}
%>

</html>

