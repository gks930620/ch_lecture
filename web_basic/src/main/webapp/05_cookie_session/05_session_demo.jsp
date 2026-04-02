<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>세션 데모</title></head>
<body>
    <h1>05. 세션(Session) 실습</h1>

    <%-- 세션에 값 저장 폼 --%>
    <h2>세션에 값 저장</h2>
    <form action="${pageContext.request.contextPath}/05_cookie_session/session" method="post">
        <label>저장할 값: </label>
        <input type="text" name="sval" placeholder="값 입력"/>
        <button type="submit">세션 저장</button>
    </form>

    <%-- 현재 세션 정보 출력 --%>
    <h2>현재 세션 정보</h2>
    <p>Session ID: <%= session.getId() %></p>
    <p>isNew: <%= session.isNew() %></p>
    <p>저장된 값(sval): <%= session.getAttribute("sval") %></p>
    <p>생성 시간: <%= new java.util.Date(session.getCreationTime()) %></p>
    <p>마지막 접근: <%= new java.util.Date(session.getLastAccessedTime()) %></p>
    <p>타임아웃: <%= session.getMaxInactiveInterval() %>초</p>

    <hr/>
    <p>
        <a href="${pageContext.request.contextPath}/05_cookie_session/05_cookie_demo.jsp">← 쿠키 기본 실습</a> |
        <a href="${pageContext.request.contextPath}/05_cookie_session/05_login.jsp">→ 세션 로그인 실습</a>
    </p>
</body>
</html>

