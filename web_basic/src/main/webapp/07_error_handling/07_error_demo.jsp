<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>에러 처리 데모</title></head>
<body>
    <h1>07. 에러 처리 실습</h1>

    <%-- 500 에러: RuntimeException 발생 --%>
    <p><a href="07_error_throw.jsp">500 에러 발생시키기</a></p>

    <%-- 404 에러: 존재하지 않는 페이지 --%>
    <p><a href="${pageContext.request.contextPath}/없는페이지.jsp">404 에러 발생시키기</a></p>

    <hr/>
    <p><a href="${pageContext.request.contextPath}/">← 홈으로</a></p>
</body>
</html>

