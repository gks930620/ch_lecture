<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Page Directive & Include</title></head>
<body>

    <%-- compile-time include (@include) --%>
    <%@ include file="03_include_header.jsp" %>

    <h1>03. Page Directive & Include 실습</h1>

    <%-- import한 클래스 사용 --%>
    <%
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatted = sdf.format(now);
    %>
    <p>현재 시간(포맷팅): <strong><%= formatted %></strong></p>

    <hr/>
    <p><a href="${pageContext.request.contextPath}/">← 홈으로</a></p>

    <%-- runtime include (<jsp:include>) --%>
    <jsp:include page="03_include_footer.jsp" />

</body>
</html>

