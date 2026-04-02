<%@ page contentType="text/html;charset=UTF-8" %>
<%
    // 로그인 여부 확인 — getSession(false): 세션 없으면 null 반환 (새로 만들지 않음)
    HttpSession loginSession = request.getSession(false);
    if (loginSession == null || loginSession.getAttribute("loginUser") == null) {
        // 미로그인 → 로그인 페이지로
        response.sendRedirect(request.getContextPath() + "/05_cookie_session/05_login.jsp");
        return;
    }
    String loginUser = (String) loginSession.getAttribute("loginUser");
    String loginTime = (String) loginSession.getAttribute("loginTime");
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>대시보드</title></head>
<body>

<h1>05. 로그인 대시보드</h1>
<p>👋 안녕하세요, <strong><%= loginUser %></strong>님!</p>
<p>로그인 시각: <%= loginTime %></p>
<p>세션 ID: <%= loginSession.getId() %></p>
<p>세션 타임아웃: <%= loginSession.getMaxInactiveInterval() %>초</p>

<hr/>
<h2>세션 동작 원리</h2>
<p>이 페이지는 세션에 "loginUser" 속성이 없으면 자동으로 로그인 페이지로 이동한다.</p>
<p>브라우저를 닫거나 타임아웃이 지나면 세션이 소멸되고 다시 로그인해야 한다.</p>

<hr/>
<form action="${pageContext.request.contextPath}/05_cookie_session/logout" method="post">
    <button type="submit">로그아웃 (세션 invalidate)</button>
</form>

<p><a href="${pageContext.request.contextPath}/05_cookie_session/05_notice_popup.jsp">← 공지 팝업 예제로</a></p>

</body>
</html>

