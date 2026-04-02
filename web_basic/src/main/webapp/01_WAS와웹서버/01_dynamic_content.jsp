<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>동적 vs 정적 컨텐츠</title></head>
<body>
    <h1>동적 컨텐츠 vs 정적 컨텐츠</h1>

    <%-- 정적: 항상 동일 --%>
    <p>정적: 이 문장은 항상 동일합니다.</p>

    <%-- 동적: 매 요청마다 변경 --%>
    <%
        long timestamp = System.currentTimeMillis();
        int randomNum = (int)(Math.random() * 100) + 1;
    %>
    <p>서버 시간(ms): <%= timestamp %></p>
    <p>랜덤 숫자(1~100): <%= randomNum %></p>
    <p>현재 날짜: <%= new java.util.Date() %></p>

    <%-- 요청 정보 --%>
    <h2>요청 정보</h2>
    <p>Method: <%= request.getMethod() %></p>
    <p>Protocol: <%= request.getProtocol() %></p>
    <p>RemoteAddr: <%= request.getRemoteAddr() %></p>
    <p>User-Agent: <%= request.getHeader("User-Agent") %></p>

    <hr/>
    <p><a href="01_basic.jsp">JSP 기본 문법</a></p>
</body>
</html>

