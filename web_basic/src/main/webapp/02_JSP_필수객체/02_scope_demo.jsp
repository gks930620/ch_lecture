<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>JSP Scope 데모</title></head>
<body>
    <h1>02. JSP Scope 실습</h1>

    <%-- ===== 1. 각 Scope에 속성 저장 ===== --%>
    <%
        pageContext.setAttribute("pmsg", "page 스코프 메시지");
        request.setAttribute("rmsg", "request 스코프 메시지");
        session.setAttribute("smsg", "session 스코프 메시지");
        application.setAttribute("amsg", "application 스코프 메시지");
    %>

    <%-- ===== 2. Scope별 EL 출력 ===== --%>
    <h2>Scope별 EL 출력</h2>
    <p>page: ${pageScope.pmsg}</p>
    <p>request: ${requestScope.rmsg}</p>
    <p>session: ${sessionScope.smsg}</p>
    <p>application: ${applicationScope.amsg}</p>

    <%-- ===== 3. EL Scope 탐색 우선순위 ===== --%>
    <h2>EL Scope 우선순위</h2>
    <%
        pageContext.setAttribute("msg", "PAGE에서 온 값");
        request.setAttribute("msg", "REQUEST에서 온 값");
        session.setAttribute("msg", "SESSION에서 온 값");
        application.setAttribute("msg", "APPLICATION에서 온 값");
    %>
    <p>${"$"}{msg} = ${msg}</p>
    <%-- page → request → session → application 순으로 탐색 --%>

    <%-- ===== 4. request 객체 정보 ===== --%>
    <h2>request 객체</h2>
    <p>Method: <%= request.getMethod() %></p>
    <p>URI: <%= request.getRequestURI() %></p>
    <p>QueryString: <%= request.getQueryString() %></p>
    <p>RemoteAddr: <%= request.getRemoteAddr() %></p>

    <%-- ===== 5. session 객체 정보 ===== --%>
    <h2>session 객체</h2>
    <p>Session ID: <%= session.getId() %></p>
    <p>isNew: <%= session.isNew() %></p>
    <p>생성 시간: <%= new java.util.Date(session.getCreationTime()) %></p>
    <p>MaxInactiveInterval: <%= session.getMaxInactiveInterval() %>초</p>

    <%-- ===== 6. application 객체 정보 ===== --%>
    <h2>application(ServletContext) 객체</h2>
    <p>ServerInfo: <%= application.getServerInfo() %></p>
    <p>ContextPath: <%= application.getContextPath() %></p>
    <p>Servlet API: <%= application.getMajorVersion() %>.<%= application.getMinorVersion() %></p>

    <%-- ===== 7. out 객체 ===== --%>
    <h2>out(JspWriter) 객체</h2>
    <% out.println("<p>out.println()으로 출력한 내용</p>"); %>

    <hr/>
    <p><a href="${pageContext.request.contextPath}/">← 홈으로</a></p>
</body>
</html>

