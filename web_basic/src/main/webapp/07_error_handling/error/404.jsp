<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>404 Not Found</title></head>
<body>
    <h2>페이지를 찾을 수 없습니다 (404)</h2>
    <p>요청하신 페이지가 존재하지 않습니다.</p>
    <p>요청 URL: ${requestScope['javax.servlet.error.request_uri']}</p>
    <p><a href="${pageContext.request.contextPath}/">홈으로 돌아가기</a></p>
</body>
</html>

