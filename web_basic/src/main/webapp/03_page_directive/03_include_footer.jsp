<%@ page contentType="text/html;charset=UTF-8" %>
<%-- runtime include: <jsp:include>로 포함되는 푸터 파일 --%>
<hr/>
<div>
    <p>&copy; <%= new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()) %> 서블릿/JSP 강의 | 현재 시간: <%= new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) %></p>
    <p>이 푸터는 &lt;jsp:include&gt;(runtime include)로 포함되어 매 요청마다 시간이 갱신됩니다.</p>
</div>


