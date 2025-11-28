<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý Đăng ký Nhận Tin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <style>
        .switch { transform: scale(1.3); }
        .table th, .table td { vertical-align: middle; }
    </style>
</head>
<body>
<div class="d-flex min-vh-100">
    <%@ include file="../layout/admin-sidebar.jsp" %>
    <div class="flex-grow-1 bg-light">
        <%@ include file="../layout/admin-header.jsp" %>
        
        <div class="container my-5">
            <h2 class="text-primary fw-bold mb-4">QUẢN LÝ ĐĂNG KÝ NHẬN TIN</h2>
            <div class="card mb-4 shadow-sm">
                <div class="card-body">
                    <form id="addForm" class="row g-3 align-items-center">
                        <div class="col-auto flex-grow-1">
                            <input type="email" class="form-control form-control-lg" id="newEmail" 
                                   placeholder="Nhập email mới để thêm vào danh sách nhận tin..." required>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-success btn-lg">Thêm Email</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="card shadow">
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-hover mb-0">
                            <thead class="table-dark">
                                <tr>
                                    <th width="80">STT</th>
                                    <th>Email</th>
                                    <th width="160" class="text-center">Trạng thái</th>
                                    <th width="120" class="text-center">Bật/Tắt</th>
                                    <th width="100" class="text-center">Xóa</th>
                                </tr>
                            </thead>
                            <tbody id="newsletterTable">
                                <c:forEach items="${listNewsletters}" var="n" varStatus="stt">
                                <tr>
                                    <td>${stt.count}</td>
                                    <td><strong>${n.email}</strong></td>
                                    <td class="text-center">
                                        <span class="badge ${n.enabled ? 'bg-success' : 'bg-secondary'} px-3 py-2">
                                            ${n.enabled ? 'Hoạt động' : 'Tạm dừng'}
                                        </span>
                                    </td>
                                    <td class="text-center">
                                        <div class="form-check form-switch d-inline-block">
                                            <input class="form-check-input switch toggle-status" type="checkbox"
                                                   data-email="${n.email}" ${n.enabled ? 'checked' : ''}>
                                        </div>
                                    </td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/admin/newsletter?action=delete&email=${n.email}"
                                           class="btn btn-danger btn-sm" onclick="return confirm('Xóa email này khỏi danh sách nhận tin?')">
                                            Xóa
                                        </a>
                                    </td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
$(function() {
    $('#addForm').submit(function(e) {
        e.preventDefault();
        const email = $('#newEmail').val().trim();
        if (!email) return;

        $.post('${pageContext.request.contextPath}/admin/newsletter', {
            action: 'add',
            email: email
        }, function(res) {
            if (res.success) {
                const stt = $('#newsletterTable tr').length + 1;
                const newRow = '<tr>' +
                    '<td>' + stt + '</td>' +
                    '<td><strong>' + email + '</strong></td>' +
                    '<td class="text-center"><span class="badge bg-success px-3 py-2">Hoạt động</span></td>' +
                    '<td class="text-center">' +
                        '<div class="form-check form-switch d-inline-block">' +
                            '<input class="form-check-input switch toggle-status" type="checkbox" data-email="' + email + '" checked>' +
                        '</div>' +
                    '</td>' +
                    '<td class="text-center">' +
                        '<a href="${pageContext.request.contextPath}/admin/newsletter?action=delete&email=' + email + '" ' +
                           'class="btn btn-danger btn-sm" onclick="return confirm(\'Xóa email này?\')">Xóa</a>' +
                    '</td>' +
                '</tr>';
                $('#newsletterTable').append(newRow);
                $('#newEmail').val('');
                showToast('success', res.message);
            } else {
                alert(res.message || 'Email đã tồn tại hoặc lỗi!');
            }
        }, 'json');
    });

    // Bật/tắt trạng thái (giữ nguyên)
    $(document).on('change', '.toggle-status', function() {
        const email = $(this).data('email');
        const enabled = this.checked ? 1 : 0;
        const $badge = $(this).closest('tr').find('.badge');

        $.post('${pageContext.request.contextPath}/admin/newsletter', {
            action: 'toggle',
            email: email,
            enabled: enabled
        }, function(res) {
            if (res.success) {
                $badge.text(enabled ? 'Hoạt động' : 'Tạm dừng')
                      .removeClass('bg-success bg-secondary')
                      .addClass(enabled ? 'bg-success' : 'bg-secondary');
                showToast(enabled ? 'success' : 'secondary', 'Đã cập nhật trạng thái!');
            } else {
                alert('Lỗi!');
                $(this).prop('checked', !this.checked);
            }
        }, 'json');
    });
    function showToast(bg, msg) {
        const toast = '<div class="toast align-items-center text-white bg-' + bg + ' border-0 position-fixed top-0 end-0 m-3" style="z-index: 9999;">' +
            '<div class="d-flex">' +
                '<div class="toast-body">' + msg + '</div>' +
                '<button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>' +
            '</div>' +
        '</div>';
        $('body').append(toast);
        new bootstrap.Toast($('.toast').last()[0]).show();
        setTimeout(function() { $('.toast').last().remove(); }, 4000);
    }
});
</script>
</body>
</html>