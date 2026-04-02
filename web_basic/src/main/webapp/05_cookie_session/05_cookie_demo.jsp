<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>쿠키 데모</title></head>
<body>
<h1>05. 쿠키(Cookie) 기본 실습</h1>

<%-- 쿠키 생성/삭제 폼 --%>
<h2>1. 쿠키 생성</h2>
<form action="${pageContext.request.contextPath}/05_cookie_session/cookie" method="post">
    이름: <input type="text" name="cookieName" placeholder="myCookie" />
    값: <input type="text" name="cookieValue" placeholder="hello" />
    유지기간(초): <input type="number" name="maxAge" placeholder="-1 (세션쿠키)" />
    <button type="submit" name="action" value="set">쿠키 설정</button>
</form>

<h2>2. 쿠키 삭제</h2>
<form action="${pageContext.request.contextPath}/05_cookie_session/cookie" method="post">
    삭제할 이름: <input type="text" name="cookieName" placeholder="myCookie" />
    <button type="submit" name="action" value="delete">쿠키 삭제 (maxAge=0)</button>
</form>

<%-- 현재 쿠키 목록 출력 --%>
<h2>3. 현재 쿠키 목록</h2>
<%
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
%>
<p><code><%= c.getName() %></code> = <code><%= c.getValue() %></code></p>
<%
        }
    } else {
%>
<p>쿠키 없음</p>
<% } %>

<hr/>
<p>
    <a href="${pageContext.request.contextPath}/05_cookie_session/05_notice_popup.jsp">→ 실용 예제: 1주일간 안보기</a> |
    <a href="${pageContext.request.contextPath}/05_cookie_session/05_session_demo.jsp">→ 세션 기본 실습</a> |
    <a href="${pageContext.request.contextPath}/05_cookie_session/05_login.jsp">→ 세션 로그인 실습</a>
</p>
</body>
</html>

