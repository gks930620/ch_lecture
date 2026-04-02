<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>500 서버 오류</title></head>
<body>
    <h2>서버 오류가 발생했습니다 (500)</h2>

    <h3>에러 정보</h3>
    <table border="1" cellpadding="5">
        <tr><td>상태 코드</td><td>${requestScope['javax.servlet.error.status_code']}</td></tr>
        <tr><td>요청 URI</td><td>${requestScope['javax.servlet.error.request_uri']}</td></tr>
        <tr><td>예외 메시지</td><td>${requestScope['javax.servlet.error.message']}</td></tr>
        <tr><td>예외 타입</td><td>${requestScope['javax.servlet.error.exception_type']}</td></tr>
        <tr><td>예외 객체</td><td>${requestScope['javax.servlet.error.exception']}</td></tr>
    </table>

    <p>⚠ 프로덕션에서는 이 정보를 사용자에게 노출하면 안 됩니다! (로그로만 기록)</p>

    <hr/>
    <p><a href="${pageContext.request.contextPath}/">홈으로 돌아가기</a></p>
</body>
</html>

