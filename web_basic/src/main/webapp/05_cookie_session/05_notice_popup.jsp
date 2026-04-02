<%@ page contentType="text/html;charset=UTF-8" %>
<%
    // "1주일간 안보기" 쿠키 확인
    boolean hideNotice = false;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
            if ("hideNotice".equals(c.getName()) && "true".equals(c.getValue())) {
                hideNotice = true;
                break;
            }
        }
    }
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>공지 팝업 예제</title></head>
<body>

<h1>메인 페이지 (공지 팝업 예제)</h1>
<p>쿠키를 이용해 "1주일간 공지 안보기" 기능을 구현한다.</p>
<p>쿠키가 없으면 팝업을 보여주고, 있으면 숨긴다.</p>

<%-- 서버 사이드에서 쿠키 여부에 따라 팝업 노출 결정 --%>
<% if (!hideNotice) { %>
<div id="notice-popup" style="border:2px solid #333; padding:20px; width:400px;">
    <h2>📢 중요 공지사항</h2>
    <p>서버 점검이 예정되어 있습니다. (매주 일요일 02:00 ~ 04:00)</p>

    <%-- "1주일간 안보기" → 서버에 POST 요청 → 쿠키 설정 후 리다이렉트 --%>
    <form action="${pageContext.request.contextPath}/05_cookie_session/notice" method="post">
        <button type="submit" name="action" value="hide">1주일간 안보기</button>
    </form>
    <form action="${pageContext.request.contextPath}/05_cookie_session/notice" method="post" style="display:inline;">
        <button type="submit" name="action" value="close">닫기 (오늘만)</button>
    </form>
</div>
<% } else { %>
<p style="color:green;">✅ 공지 팝업이 숨겨진 상태입니다.</p>
<p>
    <form action="${pageContext.request.contextPath}/05_cookie_session/notice" method="post" style="display:inline;">
        <button type="submit" name="action" value="reset">쿠키 삭제 (팝업 다시 보기)</button>
    </form>
</p>
<% } %>

<hr/>
<p>현재 hideNotice 쿠키 값: <strong><%= hideNotice ? "true (1주일간 안보기 설정됨)" : "없음 (팝업 표시)" %></strong></p>
<p><a href="${pageContext.request.contextPath}/05_cookie_session/05_login.jsp">→ 세션 로그인 실습</a></p>
<p><a href="${pageContext.request.contextPath}/05_cookie_session/05_cookie_demo.jsp">→ 쿠키 기본 실습</a></p>

</body>
</html>

