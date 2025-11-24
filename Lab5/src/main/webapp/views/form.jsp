<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add Staff</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            font-family: 'Segoe UI', sans-serif;
        }
        .card {
            background: rgba(255, 255, 255, 0.95);
            border: none;
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            backdrop-filter: blur(10px);
        }
        .btn-submit {
            background: linear-gradient(45deg, #4facfe, #00f2fe);
            border: none;
            border-radius: 50px;
            padding: 12px 40px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            transition: all 0.3s ease;
        }
        .btn-submit:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 20px rgba(79, 209, 197, 0.4);
        }
        .form-label {
            font-weight: 600;
            color: #2c3e50;
        }
        .form-control, .form-select {
            border-radius: 12px;
            padding: 12px 16px;
            border: 2px solid #e0e0e0;
            transition: all 0.3s ease;
        }
        .form-control:focus, .form-select:focus {
            border-color: #4facfe;
            box-shadow: 0 0 0 0.2rem rgba(79, 172, 254, 0.25);
        }
        .form-check-input:checked {
            background-color: #4facfe;
            border-color: #4facfe;
        }
        h1 {
            background: linear-gradient(45deg, #4facfe, #00f2fe);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            font-weight: 800;
        }
    </style>
</head>
<body class="d-flex align-items-center py-5">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-8 col-xl-6">
                <div class="card p-5">
                    <div class="text-center mb-5">
                        <h1 class="display-5 fw-bold">Staff Information</h1>
                        <p class="text-muted">Please fill in all the required information</p>
                    </div>

                    <form action="<%=request.getContextPath()%>/add" method="post" enctype="multipart/form-data">
                        <!-- Fullname -->
                        <div class="mb-4">
                            <label class="form-label">Full Name <span class="text-danger">*</span></label>
                            <input name="fullname" type="text" class="form-control form-control-lg" required>
                        </div>

                        <!-- Photo -->
                        <div class="mb-4">
                            <label class="form-label">Photo</label>
                            <input type="file" name="photo_file" class="form-control form-control-lg" accept="image/*">
                        </div>

                        <!-- Birthday -->
                        <div class="mb-4">
                            <label class="form-label">Birthday <span class="text-danger">*</span></label>
                            <input name="birthday" type="date" class="form-control form-control-lg" required>
                        </div>

                        <!-- Gender & Married -->
                        <div class="row mb-4">
                            <div class="col-md-6">
                                <label class="form-label">Gender <span class="text-danger">*</span></label>
                                <div class="d-flex gap-4 mt-2">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="gender" id="male" value="true" checked>
                                        <label class="form-check-label" for="male">Male</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="gender" id="female" value="false">
                                        <label class="form-check-label" for="female">Female</label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Marital Status</label>
                                <div class="mt-2">
                                    <div class="form-check form-switch">
                                        <input class="form-check-input" type="checkbox" name="married" id="married">
                                        <label class="form-check-label" for="married">Married</label>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Country -->
                        <div class="mb-4">
                            <label class="form-label">Country <span class="text-danger">*</span></label>
                            <select name="country" class="form-select form-select-lg" required>
                                <option value="" disabled selected>Select your country</option>
                                <option value="Vietnamese">Vietnamese</option>
                                <option value="United States">United States</option>
                                <option value="United Kingdom">United Kingdom</option>
                            </select>
                        </div>

                        <!-- Hobbies -->
                        <div class="mb-4">
                            <label class="form-label">Hobbies</label>
                            <div class="row g-3">
                                <div class="col-sm-6 col-md-3">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="hobbies" value="Coding" id="h1">
                                        <label class="form-check-label" for="h1">Coding</label>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-md-3">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="hobbies" value="Travel" id="h2">
                                        <label class="form-check-label" for="h2">Travel</label>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-md-3">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="hobbies" value="Music" id="h3">
                                        <label class="form-check-label" for="h3">Music</label>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-md-3">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="hobbies" value="Other" id="h4">
                                        <label class="form-check-label" for="h4">Other</label>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Note -->
                        <div class="mb-5">
                            <label class="form-label">Notes</label>
                            <textarea name="note" class="form-control" rows="4" placeholder="Anything you want to add..."></textarea>
                        </div>

                        <!-- Submit -->
                        <div class="text-center">
                            <button type="submit" class="btn btn-primary btn-submit text-white px-5">
                                <i class="fas fa-paper-plane me-2"></i> Add Staff
                            </button>
                        </div>
                    </form>

                    <div class="text-center mt-4">
                        <a href="<%=request.getContextPath()%>/">← Back to Home</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>