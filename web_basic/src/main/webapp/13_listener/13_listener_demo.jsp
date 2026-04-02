<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.chlecture.listener.SessionCountListener" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Listener 데모</title></head>
<body>
    <h1>13. Listener 실습</h1>

    <%-- ServletContextListener: 서버 콘솔에서 시작/종료 로그 확인 --%>
    <h2>AppInitListener</h2>
    <p>서버 콘솔에서 <code>[AppInitListener] 웹앱 시작됨</code> 로그를 확인하세요.</p>

    <%-- HttpSessionListener: 현재 활성 세션 수 --%>
    <h2>SessionCountListener</h2>
    <p>현재 활성 세션 수: <strong><%= SessionCountListener.getActiveSessions() %></strong></p>
    <p>내 세션 ID: <%= session.getId() %></p>
    <p>다른 브라우저에서 접속하면 세션 수가 증가합니다.</p>

    <hr/>
    <p><a href="${pageContext.request.contextPath}/">← 홈으로</a></p>
</body>
</html>

