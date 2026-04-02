<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>페이징 & 검색 데모</title></head>
<body>
    <h1>11. 페이징 & 검색 실습</h1>

    <%-- 검색 폼 --%>
    <form method="get">
        <input name="q" placeholder="검색어" value="${param.q}"/>
        <input name="page" type="hidden" value="1"/>
        <button type="submit">검색</button>
    </form>

    <%-- 파라미터 파싱 & offset 계산 --%>
    <%
        String pageStr = request.getParameter("page");
        String sizeStr = request.getParameter("size");
        String q = request.getParameter("q");
        int currentPage = (pageStr != null) ? Integer.parseInt(pageStr) : 1;
        int pageSize = (sizeStr != null) ? Integer.parseInt(sizeStr) : 10;
        int offset = (currentPage - 1) * pageSize;
    %>

    <h2>현재 파라미터</h2>
    <p>page=<%= currentPage %>, size=<%= pageSize %>, q=<%= q != null ? q : "(없음)" %>, offset=<%= offset %></p>

    <%-- 페이지 네비게이션 --%>
    <h2>페이지 네비게이션</h2>
    <%
        int totalItems = 53;
        int totalPages = (int)Math.ceil((double)totalItems / pageSize);
    %>
    <p>전체 <%= totalItems %>건 / <%= totalPages %>페이지</p>
    <p>
    <%
        for (int i = 1; i <= totalPages; i++) {
            String qParam = (q != null && !q.isEmpty()) ? "&q=" + q : "";
            if (i == currentPage) {
    %>
                <strong>[<%= i %>]</strong>
    <%      } else { %>
                <a href="?page=<%= i %>&size=<%= pageSize %><%= qParam %>"><%= i %></a>
    <%      }
        }
    %>
    </p>

    <hr/>
    <p><a href="${pageContext.request.contextPath}/">← 홈으로</a></p>
</body>
</html>

