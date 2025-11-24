<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Send Mail</title>

<style>
    body {
        text-align: center;
        background: #E6E2C3;
        color: #1C315E;
        font-family: Arial, sans-serif;
    }
    form {
        width: 30%;
        margin: auto;
        padding: 20px;
    }
    mark {
        background: #E6E2C3;
        color: #1C315E;
    }
    h1 {
        margin: 2rem 0;
    }

    /* INPUT WRAPPER */
    .group {
        text-align: left;
        margin-bottom: 15px;
    }

    .label {
        font-weight: bold;
        margin-bottom: 5px;
        display: block;
    }

    .input, textarea {
        width: 100%;
        padding: 8px 10px;
        border-radius: 5px;
        border: 1px solid #88A47C;
        background: #88A47C;
        color: #E6E2C3;
        font-size: 15px;
    }

    textarea {
        height: 120px;
        resize: vertical;
    }

    .wrap {
        display: flex;
        justify-content: center;
        margin-top: 10px;
    }

    /* NÚT BẤM */
    .button {
        min-width: 200px;
        min-height: 40px;
        font-size: 20px;
        font-weight: 700;
        background: linear-gradient(90deg, rgba(129,230,217,1) 0%, rgba(79,209,197,1) 100%);
        border: none;
        border-radius: 1000px;
        cursor: pointer;
        box-shadow: 12px 12px 24px rgba(79,209,197,.64);
        transition: .3s;
    }

    .button:hover {
        transform: translateY(-6px);
    }

    a {
        color: #1C315E;
        font-weight: bold;
        text-decoration: none;
    }
</style>
</head>

<body>

    <h1>Send Mail</h1>
    <mark>${message}</mark>

    <form action="<%=request.getContextPath() %>/mail" method="post" enctype="multipart/form-data">

        <div class="group">
            <label class="label">From</label>
            <input name="from" type="text" class="input">
        </div>

        <div class="group">
            <label class="label">To</label>
            <input name="to" type="text" class="input">
        </div>

        <div class="group">
            <label class="label">Subject</label>
            <input name="subject" type="text" class="input">
        </div>

        <div class="group">
            <label class="label">Body</label>
            <textarea name="body"></textarea>
        </div>

        <div class="group">
            <label class="label">File đính kèm</label>
            <input type="file" name="photo_file" class="input">
        </div>

        <div class="wrap">
            <button type="submit" class="button">Send</button>
        </div>

    </form>

    <br>
    <a href="<%=request.getContextPath() %>/">Trang chủ</a>

</body>
</html>
