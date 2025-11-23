<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main Page</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<jsp:include page="header.jsp" />

<div class="container">
    <div class="row">
        <!-- MAIN CONTENT -->
        <div class="col-md-9 mb-4">
            <jsp:include page="${page}" />
        </div>

        <!-- ASIDE -->
        <div class="col-md-3">
            <jsp:include page="aside.jsp" />
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>
