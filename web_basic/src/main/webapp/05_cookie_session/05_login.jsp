<%@ page contentType="text/html;charset=UTF-8" %>
<%
    // 이미 로그인 상태면 대시보드로
    HttpSession loginSession = request.getSession(false);
    if (loginSession != null && loginSession.getAttribute("loginUser") != null) {
        response.sendRedirect(request.getContextPath() + "/05_cookie_session/05_dashboard.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>로그인</title></head>
<body>

<h1>05. 세션 기반 로그인</h1>
<p>실무에서는 Spring Security를 쓰지만, 원리는 동일하다.</p>
<p>로그인 성공 → 세션에 사용자 정보 저장 → 이후 요청마다 세션 확인</p>

<% String error = request.getParameter("error"); %>
<% if ("1".equals(error)) { %>
<p style="color:red;">❌ 아이디 또는 비밀번호가 틀렸습니다.</p>
<% } %>

<form action="${pageContext.request.contextPath}/05_cookie_session/login" method="post">
    <p>
        아이디: <input type="text" name="username" placeholder="admin" />
    </p>
    <p>
        비밀번호: <input type="password" name="password" placeholder="1234" />
    </p>
    <button type="submit">로그인</button>
</form>

<p style="color:gray; font-size:0.9em;">테스트 계정: admin / 1234</p>

<hr/>
<p><a href="${pageContext.request.contextPath}/05_cookie_session/05_notice_popup.jsp">← 공지 팝업 예제로</a></p>

</body>
</html>

