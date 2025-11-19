<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background: #f5f5f5;
    }

    .container {
        width: 450px;
        margin: 3em auto;
        padding: 25px 30px;
        background: white;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.08);
        display: flex;
        flex-direction: column;
        gap: 15px;
    }

    label {
        font-weight: 600;
        font-size: 15px;
        color: #333;
        margin-bottom: 4px;
    }

    input[type="text"],
    input[type="password"],
    select {
        width: 100%;
        padding: 10px;
        border-radius: 8px;
        border: 1px solid #ccc;
        font-size: 15px;
        box-sizing: border-box;
        transition: 0.2s;
    }

    input:focus, select:focus {
        outline: none;
        border-color: #777;
        box-shadow: 0 0 0 2px #e2e2e2;
    }

    .radio-group, .checkbox-group {
        display: flex;
        flex-direction: column;
        gap: 5px;
        margin-top: -5px;
        padding-left: 3px;
    }

    hr {
        margin-top: 10px;
        border: none;
        border-top: 1px solid #ddd;
    }

    button {
        padding: 10px 0;
        background: #4285f4;
        border: none;
        color: white;
        border-radius: 8px;
        font-size: 16px;
        cursor: pointer;
        transition: 0.2s;
        margin-top: 10px;
    }

    button:hover {
        background: #3367d6;
    }

</style>


</head>
<body>
	<jsp:include page="index.jsp"></jsp:include>

	<c:if test="${status == 'new' }">

		<form action="user/reg" method="post" class="container">
	
	    <label>Username</label>
	    <input type="text" name="username">
	
	    <label>Password</label>
	    <input type="password" name="password">
	
	    <label>Gender</label>
	    <div class="radio-group">
	        <label><input type="radio" name="gender" value="male" checked> Male</label>
	        <label><input type="radio" name="gender" value="female"> Female</label>
	    </div>
	
	    <label>
	        <input type="checkbox" name="isHaveFamily"> Have a family?
	    </label>
	
	    <label>Country</label>	
	    <select name="country">
	        <option value="VN" ${user.country == 'VN' ? 'selected' : ''}>Vietnam</option>
	        <option value="US" ${user.country == 'US' ? 'selected' : ''}>United State</option>
	        <option value="CN" ${user.country == 'CN' ? 'selected' : ''}>China</option>
	    </select>
	
	    <label>Hobby</label>
	    <div class="checkbox-group">
	        <label><input type="checkbox" name="hobby" value="read"> Reading books</label>
	        <label><input type="checkbox" name="hobby" value="travel"> World traveling</label>
	        <label><input type="checkbox" name="hobby" value="music"> Feel the music</label>
	        <label><input type="checkbox" name="hobby" value="code"> Coding</label>
	        <label><input type="checkbox" name="hobby" value="other"> Other...</label>
	    </div>
	
	    <label>Note</label>
	    <input type="text" name="note">
	
	    <hr>
	
	    <button type="submit">Register</button>
		</form>
	</c:if>
	
	<c:if test="${status == 'user'}">
	
    <div class="container">
        <h2>Registration Success 🎉</h2>

        <p><strong>Username:</strong> ${user.username}</p>
        <p><strong>Password:</strong> ${user.password}</p>
        <p><strong>Gender:</strong> ${user.gender}</p>
        <p><strong>Have Family:</strong> ${user.haveFamily ? "Yes" : "No"}</p>
        <p><strong>Country:</strong> ${user.country}</p>

        <p><strong>Hobbies:</strong>
            <c:forEach items="${user.hobby}" var="h" varStatus="i">
			    ${h}${!i.last ? ", " : ""}
			</c:forEach>

        </p>

        <p><strong>Note:</strong> ${user.note}</p>

        <hr>

        <form action="${pageContext.request.contextPath}/bai3" method="get">
            <button style="padding: 20px;" >Register another user</button>
        </form>
    </div>

	</c:if>


</body>
</html>