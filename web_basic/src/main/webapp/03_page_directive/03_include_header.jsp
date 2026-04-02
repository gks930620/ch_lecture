<%@ page contentType="text/html;charset=UTF-8" %>

<%-- compile-time include: @include로 포함되는 헤더 파일 --%>
<div>
    <h2>서블릿/JSP 강의 - 공통 헤더</h2>
    <nav>
        <a href="${pageContext.request.contextPath}/">홈</a> |
        <a href="${pageContext.request.contextPath}/01_WAS와웹서버/01_basic.jsp">Ch01</a> |
        <a href="${pageContext.request.contextPath}/02_JSP_필수객체/02_scope_demo.jsp">Ch02</a> |
        <a href="${pageContext.request.contextPath}/03_page_directive/03_page_directives.jsp">Ch03</a>
    </nav>
</div>
<hr/>

