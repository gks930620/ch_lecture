<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>상세보기</title></head>
<body>
    <h1>10. 게시글 상세보기</h1>

    <table border="1" cellpadding="5">
        <tr><th>번호</th><td>${requestScope.board.id}</td></tr>
        <tr><th>제목</th><td><c:out value="${requestScope.board.title}" /></td></tr>
        <tr><th>작성자</th><td><c:out value="${requestScope.board.writer}" /></td></tr>
        <%-- [Ch12] Enum label 표시 --%>
        <tr><th>상태</th><td>${requestScope.statusLabel} (${requestScope.board.statusCode})</td></tr>
        <tr><th>작성일</th><td>${requestScope.board.createdAt}</td></tr>
        <tr><th>내용</th><td><c:out value="${requestScope.board.content}" /></td></tr>
    </table>

    <p>
        <a href="${pageContext.request.contextPath}/board/list">목록으로</a>
    </p>
</body>
</html>

