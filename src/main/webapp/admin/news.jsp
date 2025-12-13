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
						<button type="button" class="btn btn-success w-40" id="addBtn">
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
									src="${pageContext.request.contextPath}/admin/news?action=image&file=${n.image}" width="80"
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

	<!-- Modal thêm/sửa tin -->
	<div class="modal fade" id="addModal" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<form action="${pageContext.request.contextPath}/admin/news"
					method="post" enctype="multipart/form-data" id="newsForm">
					<div class="modal-header">
						<h5 class="modal-title" id="modalTitle">
							<fmt:message key="news.modal.add"/>
						</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>
					<div class="modal-body">
						<!-- hidden action -->
						<input type="hidden" name="action" id="actionInput" value=""> 
						<input name="id" id="newsId"
							class="form-control mb-3" placeholder="<fmt:message key="news.modal.id"/>"
							value="${latestId}" required> 
						<input
							name="title" id="newsTitle" class="form-control mb-3" placeholder="<fmt:message key="news.modal.text"/>"
							value="" required>

						<textarea name="content" id="newsContent" class="form-control mb-3" rows="4"
							placeholder="<fmt:message key="news.modal.content"/>"></textarea>

						<input type="file" name="image" id="newsImage" class="form-control mb-3"
							accept="image/*" required> 
						<select
							name="categoryId" class="form-select mb-3" id="categorySelect" onchange="updateLatestId()">
							<c:forEach items="${categories}" var="c">
								<option value="${c.id}">
									${c.name}</option>
							</c:forEach>
						</select>

						<div class="form-check">
							<input type="checkbox" name="home" id="newsHome" class="form-check-input"> 
							<label for="newsHome"><fmt:message key="news.modal.showOnFeed"/></label>
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
        toastText.textContent = '<%=toastMessage.replace("'", "\\'")%>';

        // Show toast với animation
        const toast = new bootstrap.Toast(toastEl, {
            autohide: true,
            delay: 4000 // 4 giây
        });
        toast.show();
    });
</script>
<%
}
%>

<script>
    let addModalInstance = null;

    document.addEventListener('DOMContentLoaded', function() {
        // Khởi tạo modal instance một lần duy nhất
        addModalInstance = new bootstrap.Modal(document.getElementById('addModal'), {
            backdrop: 'static',
            keyboard: false
        });
        
        // Gán sự kiện click cho button "Add"
        document.getElementById('addBtn').addEventListener('click', openAddModal);
    });

    function openAddModal() {
        // Reset form về trạng thái mặc định
        resetForm();
        
        // Hiển thị modal
        addModalInstance.show();
    }

    function resetForm() {
        // Reset tất cả input fields
        document.getElementById('newsForm').reset();
        
        // Set lại giá trị mặc định
        document.getElementById('actionInput').value = '';
        document.getElementById('newsTitle').value = '';
        document.getElementById('newsContent').value = '';
        document.getElementById('newsImage').removeAttribute('disabled');
        document.getElementById('newsImage').setAttribute('required', 'required');
        document.getElementById('newsHome').checked = false;
        
        // Set lại category mặc định (category đầu tiên)
        const firstCategory = document.querySelector('#categorySelect option:first-child');
        if (firstCategory) {
            document.getElementById('categorySelect').value = firstCategory.value;
            updateLatestId();
        }
        
        // Update modal title
        document.getElementById('modalTitle').textContent = '<fmt:message key="news.modal.add"/>';
    }

    function updateLatestId() {
        const categoryId = document.getElementById('categorySelect').value;
        
        // Gọi API để lấy latestId mới
        fetch('${pageContext.request.contextPath}/admin/news?action=getLatestId&categoryId=' + categoryId)
            .then(response => response.json())
            .then(data => {
                // Cập nhật giá trị latestId
                document.getElementById('newsId').value = data.latestId;
            })
            .catch(error => console.error('Error:', error));
    }

    // Xử lý khi modal đóng - đảm bảo form được reset
    document.getElementById('addModal').addEventListener('hidden.bs.modal', function() {
        resetForm();
    });

    // Thêm xử lý click vào button edit trong table
    document.addEventListener('click', function(e) {
        if (e.target.classList.contains('btn-warning')) {
            e.preventDefault();
            const href = e.target.getAttribute('href');
            
            // Tải dữ liệu edit
            fetch(href.replace('/admin/news?', '/admin/news?format=json&'))
                .then(response => response.json())
                .then(data => {
                    // Populate form với dữ liệu edit
                    document.getElementById('newsId').value = data.id;
                    document.getElementById('newsTitle').value = data.title;
                    document.getElementById('newsContent').value = data.content;
                    document.getElementById('categorySelect').value = data.categoryId;
                    document.getElementById('newsHome').checked = data.home;
                    document.getElementById('actionInput').value = 'update';
                    document.getElementById('newsImage').removeAttribute('required');
                    document.getElementById('modalTitle').textContent = '<fmt:message key="news.modal.edit"/>';
                    
                    // Hiển thị modal
                    addModalInstance.show();
                })
                .catch(error => {
                    console.error('Error:', error);
                    window.location.href = href;
                });
        }
    });
</script>

</html>