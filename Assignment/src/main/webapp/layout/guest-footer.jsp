<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<footer class="text-white py-4" style="background: linear-gradient(90deg, #0a1930, #102542);">
    <div class="container d-flex flex-column flex-md-row justify-content-between align-items-center">

        <!-- Form đăng ký -->
        <form class="d-flex gap-2 mb-3 mb-md-0" method="POST" action="${pageContext.request.contextPath}/notifyRegister">
            <input type="email"
                   name="email"
                   class="form-control form-control-sm rounded-3"
                   placeholder="Nhập email của bạn"
                   required>
            <button class="btn btn-warning btn-sm fw-bold rounded-3" type="submit">
                ĐĂNG KÝ
            </button>
        </form>

        <!-- Credit -->
        <div class="text-center text-md-end opacity-75">
            <p class="mb-0">&copy; 2025 ABC News</p>
            <p class="mb-0">Thiết kế bởi <strong class="text-warning">yeume2</strong></p>
        </div>

    </div>

    <p class="text-center small opacity-50 mt-2 mb-0">
        Tin tức chuẩn xác – Cập nhật liên tục – Uy tín hàng đầu
    </p>
</footer>

<!-- Toast Container -->
<div class="toast-container position-fixed bottom-0 end-0 p-3">
    <div id="newsletterToast" class="toast align-items-center border-0" role="alert">
        <div class="d-flex">
            <div class="toast-body"></div>
            <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>
    </div>
</div>

<!-- Script hiển thị toast nếu có message từ session -->
<%
    String toastType = (String) session.getAttribute("toastType");
    String toastMessage = (String) session.getAttribute("toastMessage");
    
    if (toastType != null && toastMessage != null) {
        // Xóa message khỏi session sau khi đọc
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
%>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const toastEl = document.getElementById('newsletterToast');
        const toastBody = toastEl.querySelector('.toast-body');
        
        // Set màu và message
        <% if ("success".equals(toastType)) { %>
            toastEl.classList.add('bg-success', 'text-white');
        <% } else { %>
            toastEl.classList.add('bg-danger', 'text-white');
        <% } %>
        
        toastBody.textContent = '<%= toastMessage %>';
        
        // Show toast
        const toast = new bootstrap.Toast(toastEl, {
            autohide: true,
            delay: 3000
        });
        toast.show();
    });
</script>
<%
    }
%>