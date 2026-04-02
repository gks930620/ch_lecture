<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WAS vs WS 실습</title>
</head>
<body>
    <h1>WAS vs WS 실습</h1>
    <!-- 정적 컨텐츠 예시: HTML은 웹서버에서 바로 제공할 수 있습니다. -->
    <p>정적 예: 이 문장은 HTML로 고정되어 있습니다.</p>
    <!-- 동적 컨텐츠 예시: JSP는 서버에서 자바 코드를 실행하여 결과를 생성합니다. -->
    <p>동적 예: 현재 서버 시간: <%= new java.util.Date() %></p>
    <p>다음 페이지: <a href="01_server_info.jsp">서버 정보 보기</a></p>
</body>
</html>

