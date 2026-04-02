<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Server Info</title>
</head>
<body>
    <h2>서버 정보</h2>
    <!-- application, request 등의 암시적 객체 사용 예 -->
    <p>Server Info: <%= application.getServerInfo() %></p>
    <p>Context Path: <%= request.getContextPath() %></p>
    <p>요청 URL: <%= request.getRequestURL() %></p>
</body>
</html>

