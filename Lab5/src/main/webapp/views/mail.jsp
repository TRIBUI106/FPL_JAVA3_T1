<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Send Mail</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        .mail-card {
            background: rgba(255, 255, 255, 0.96);
            border-radius: 20px;
            box-shadow: 0 20px 50px rgba(0,0,0,0.2);
        }
        .btn-send {
            background: linear-gradient(45deg, #ff9a9e, #fad0c4);
            border: none;
            border-radius: 50px;
            padding: 14px 50px;
            font-weight: 600;
        }
        .btn-send:hover {
            transform: translateY(-4px);
            box-shadow: 0 15px 35px rgba(_all(255, 154, 158, 0.5);
        }
        h1 {
            background: linear-gradient(45deg, #ff9a9e, #fad0c4);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }
    </style>
</head>
<body class="d-flex align-items-center py-5">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="mail-card p-5">
                    <div class="text-center mb-5">
                        <i class="fas fa-paper-plane fa-4x text-danger mb-4"></i>
                        <h1 class="display-5 fw-bold">Send Mail</h1>
                    </div>

                    <c:if test="${not empty message}">
                        <div class="alert alert-success text-center">${message}</div>
                    </c:if>

                    <form action="<%=request.getContextPath()%>/mail" method="post" enctype="multipart/form-data">
                        <div class="row g-4">
                            <div class="col-md-6">
                                <label class="form-label fw-bold">From</label>
                                <input name="from" type="email" class="form-control form-control-lg" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-bold">To</label>
                                <input name="to" type="email" class="form-control form-control-lg" required>
                            </div>
                            <div class="col-12">
                                <label class="form-label fw-bold">Subject</label>
                                <input name="subject" type="text" class="form-control form-control-lg" required>
                            </div>
                            <div class="col-12">
                                <label class="form-label fw-bold">Message</label>
                                <textarea name="body" class="form-control" rows="6" required></textarea>
                            </div>
                            <div class="col-12">
                                <label class="form-label fw-bold">Attachment</label>
                                <input type="file" name="photo_file" class="form-control form-control-lg">
                            </div>
                            <div class="col-12 text-center mt-4">
                                <button type="submit" class="btn btn-danger btn-send text-white">
                                    <i class="fas fa-paper-plane me-2"></i> Send Mail
                                </button>
                            </div>
                        </div>
                    </form>

                    <div class="text-center mt-4">
                        <a href="<%=request.getContextPath()%>/">Back to Home</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>