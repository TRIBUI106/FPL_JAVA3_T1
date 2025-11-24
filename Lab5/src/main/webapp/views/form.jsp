<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add</title>
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
		.item {
			display: flex;
			margin-left: 0.5rem;
			font-size: 20px;
		}
		.item2 {
			margin-left: 6rem;
		}
		.radio {
			margin-left: 0.2rem;
		}
		.selectdiv {
		  /*Don't really need this just for demo styling*/
		  position: relative;
		  display: flex;
		  width: 100%;
		  margin: 0.3rem 0;
		}
		
		/* IE11 hide native button (thanks Matt!) */
		select::-ms-expand {
			display: none;
		}
		
		.selectdiv:after {
		  content: '<>';
		  font: 17px "Consolas", monospace;
		  color: #1C315E;
		  -webkit-transform: rotate(90deg);
		  -moz-transform: rotate(90deg);
		  -ms-transform: rotate(90deg);
		  transform: rotate(90deg);
		  right: 11px;
		  /*Adjust for position however you want*/
		  
		  top: 18px;
		  padding: 0 0 2px;
		  border-bottom: 1px solid #999;
		  /*left line */
		  
		  position: absolute;
		  pointer-events: none;
		}
		.selectdiv select {
		  -webkit-appearance: none;
		  -moz-appearance: none;
		  appearance: none;
		  /* Add some styling */
		  color: #E6E2C3;
		  background: #88A47C;
		  width: 100%;
		  height: 50px;
		  border-radius: 0.5rem;
		  margin: 5px 0px;
		  padding: 0px 24px;
		  font-size: 16px;
		  line-height: 1.75;
		  border: 1px solid #cccccc;
		}
		.note {
			margin-top: 0.5rem;
			margin-bottom: 2.5rem;
		}
	</style>
<body>
	<h1>Information of staff</h1>
	<form action="<%=request.getContextPath() %>/add" method="post" enctype="multipart/form-data">
		<div class="input-group mb-3">
			<span class="input-group-text" id="basic-addon1">Fullname </span> 
			<input name="fullname"
				type="text" class="form-control" placeholder=""
				aria-label="Username" aria-describedby="basic-addon1">
		</div>
		<div class="input-group mb-3">
		  <input type="file" name="photo_file" class="form-control" id="inputGroupFile02">
		  <label class="input-group-text" for="inputGroupFile02">Image</label>
		</div>
		<div class="input-group mb-3">
			<span class="input-group-text" id="basic-addon1">Birthday </span> 
			<input name="birthday"
				type="date" class="form-control" placeholder=""
				aria-label="Username" aria-describedby="basic-addon1">
		</div>
		<div class="item">
			<div class="item_item">
				<label class="label">Gender: </label>
		        <input class="radio" type="radio" id="checkbox-circle1" name="gender" value="True">
		        <label for="checkbox-circle1">Male</label>
		        <input class="radio" type="radio" id="checkbox-circle1" name="gender" value="False">
		        <label for="checkbox-circle1">Female</label>
			</div> 
			<div class="item_item item2">
		        <input type="checkbox" id="checkbox-circle1" name="married">
		        <label for="checkbox-circle1">Married ?</label>
			</div> 
	    </div>
	    <div class="selectdiv">
	      <select name="country">
	          <option selected> Country </option>
	          <option value="Vietnamese">Vietnamese</option>
	          <option value="United States">United States</option>
	          <option value="United Kingdom">United Kingdom</option>
	      </select>
		</div>
		<div class="item">
			<div class="item_item">
				<label class="label">Hobbies: </label>
		        <input class="radio" type="checkbox" id="checkbox-circle1" name="hobbies" value="Coding">
		        <label for="checkbox-circle1">Coding</label>
		        <input class="radio" type="checkbox" id="checkbox-circle1" name="hobbies" value="Travel">
		        <label for="checkbox-circle1">Travel</label>
		        <input class="radio" type="checkbox" id="checkbox-circle1" name="hobbies" value="Music">
		        <label for="checkbox-circle1">Music</label>
		        <input class="radio" type="checkbox" id="checkbox-circle1" name="hobbies" value="Other">
		        <label for="checkbox-circle1">Other</label>
			</div> 
	    </div>
	    <div class="input-group note">
		  <span class="input-group-text">Notes</span>
		  <textarea name="note" class="form-control" aria-label="With textarea"></textarea>
		</div>
		<div class="wrap">
		  <button type="submit" class="button">Add</button>
		</div>
	</form>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>
		<a href="<%=request.getContextPath() %>/">trang chủ</a>
</body>
</html>