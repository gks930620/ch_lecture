<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>필터 테스트</title></head>
<body>
    <h1>09. Filter 실습</h1>
    <p>이 페이지에 접속하면 LoggingFilter가 서버 콘솔에 요청 URI를 출력합니다.</p>

    <%-- 다른 페이지로 이동하여 필터 로그 확인 --%>
    <p><a href="${pageContext.request.contextPath}/">홈</a> |
       <a href="${pageContext.request.contextPath}/board/list">게시판</a> |
       <a href="${pageContext.request.contextPath}/02_JSP_필수객체/02_scope_demo.jsp">Scope 데모</a>
    </p>

    <hr/>
    <p><a href="${pageContext.request.contextPath}/">← 홈으로</a></p>
</body>
</html>

