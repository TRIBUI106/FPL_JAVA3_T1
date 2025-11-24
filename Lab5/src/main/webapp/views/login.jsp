<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
	<style>
		body {
			text-align: center;
			background: #E6E2C3;
			color: #1C315E;
		}
		form {
			width: 30%;
			margin-left: auto;
			margin-right: auto;
		}
		h1 {
			color: #1C315E;
			margin: 2rem 0;
		}
		.input-group-text {
			color: #E6E2C3;
			background: #88A47C;
		}
		.form-control {
			color: #E6E2C3;
			background: #88A47C;
		}
		.wrap {
		  height: 100%;
		  display: flex;
		  align-items: center;
		  justify-content: center;
		}
		
		.button {
		  min-width: 200px;
		  min-height: 40px;
		  font-family: 'Nunito', sans-serif;
		  font-size: 22px;
		  text-transform: uppercase;
		  letter-spacing: 1.3px;
		  font-weight: 700;
		  color: #313133;
		  background: #4FD1C5;
		  background: linear-gradient(90deg, rgba(129,230,217,1) 0%, rgba(79,209,197,1) 100%);
		  border: none;
		  border-radius: 1000px;
		  box-shadow: 12px 12px 24px rgba(79,209,197,.64);
		  transition: all 0.3s ease-in-out 0s;
		  cursor: pointer;
		  outline: none;
		  position: relative;
		  padding: 10px;
		  }
		
		button::before {
		content: '';
		  border-radius: 1000px;
		  min-width: calc(200px + 12px);
		  min-height: calc(40px + 12px);
		  border: 6px solid #00FFCB;
		  box-shadow: 0 0 60px rgba(0,255,203,.64);
		  position: absolute;
		  top: 50%;
		  left: 50%;
		  transform: translate(-50%, -50%);
		  opacity: 0;
		  transition: all .3s ease-in-out 0s;
		}
		
		.button:hover, .button:focus {
		  color: #313133;
		  transform: translateY(-6px);
		}
		
		button:hover::before, button:focus::before {
		  opacity: 1;
		}
		
		button::after {
		  content: '';
		  width: 30px; height: 30px;
		  border-radius: 100%;
		  border: 6px solid #00FFCB;
		  position: absolute;
		  z-index: -1;
		  top: 50%;
		  left: 50%;
		  transform: translate(-50%, -50%);
		  animation: ring 1.5s infinite;
		}
		
		button:hover::after, button:focus::after {
		  animation: none;
		  display: none;
		}
		
		@keyframes ring {
		  0% {
		    width: 30px;
		    height: 30px;
		    opacity: 1;
		  }
		  100% {
		    width: 200px;
		    height: 200px;
		    opacity: 0;
		  }
		}
		mark{
			background: #E6E2C3;
		}
		.form-check-label {
			float: left;
		}
	</style>
<body>
	<h1>Login</h1>
	<mark style="color: red;">${message}</mark>
	<form action="<%=request.getContextPath() %>//login" method="post">
		<div class="input-group mb-3">
			<span class="input-group-text" id="basic-addon1">Username </span> 
			<input name="username" value="${username}"
				type="text" class="form-control" placeholder=""
				aria-label="Username" aria-describedby="basic-addon1">
		</div>
		<div class="input-group mb-3">
			<span class="input-group-text" id="basic-addon1">Password </span> 
			<input name="password" value="${password}"
				type="password" class="form-control" placeholder=""
				aria-label="Username" aria-describedby="basic-addon1">
		</div>
		<div class="form-check form-switch">
		  <input name="remember" class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault">
		  <label class="form-check-label" for="flexSwitchCheckDefault">Remember me?</label>
		</div>
		<div class="wrap">
		  <button type="submit" class="button">Login</button>
		</div>
	</form>
	<a href="<%=request.getContextPath() %>/">Trang chủ</a>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>
</body>
</html>