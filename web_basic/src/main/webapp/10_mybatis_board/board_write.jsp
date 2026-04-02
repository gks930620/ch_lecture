<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>글쓰기</title></head>
<body>
    <h1>10. 글쓰기</h1>

    <form action="${pageContext.request.contextPath}/board/create" method="post">
        <table border="1" cellpadding="5">
            <tr><th>제목</th><td><input type="text" name="title" placeholder="제목을 입력하세요"/></td></tr>
            <tr><th>작성자</th><td><input type="text" name="writer" placeholder="작성자"/></td></tr>
            <tr><th>내용</th><td><textarea name="content" rows="5" cols="40"></textarea></td></tr>
            <%-- [Ch12] Enum 값으로 상태 선택 --%>
            <tr>
                <th>상태</th>
                <td>
                    <select name="status">
                        <c:forEach var="s" items="${statusValues}">
                            <option value="${s.code}">${s.label} (${s.code})</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <p>
            <button type="submit">저장</button>
            <a href="${pageContext.request.contextPath}/board/list">취소</a>
        </p>
    </form>
</body>
</html>

