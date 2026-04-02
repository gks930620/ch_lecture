<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>게시판 목록</title></head>
<body>
    <h1>10. 게시판 목록</h1>

    <table border="1" cellpadding="5">
        <tr><th>번호</th><th>제목</th><th>작성자</th><th>상태</th><th>작성일</th></tr>
        <c:forEach var="b" items="${requestScope.list}">
            <tr>
                <td>${b.id}</td>
                <td><a href="${pageContext.request.contextPath}/board/view?id=${b.id}"><c:out value="${b.title}" /></a></td>
                <td><c:out value="${b.writer}" /></td>
                <%-- [Ch12] statusLabels Map에서 id로 label 조회 --%>
                <td>${statusLabels[b.id]}</td>
                <td>${b.createdAt}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty requestScope.list}">
            <tr><td colspan="5">게시글이 없습니다.</td></tr>
        </c:if>
    </table>

    <p><a href="${pageContext.request.contextPath}/board/write">글쓰기</a></p>
    <hr/>
    <p><a href="${pageContext.request.contextPath}/">← 홈으로</a></p>
</body>
</html>

