<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Forward vs Redirect</title></head>
<body>
    <h1>04. Forward vs Redirect 실습</h1>

    <%-- mode에 forward 또는 redirect를 입력하여 동작 비교 --%>
    <form action="${pageContext.request.contextPath}/04_forward_redirect/handle" method="post">
        <label>mode: </label>
        <input type="text" name="mode" placeholder="forward 또는 redirect 입력"/>
        <button type="submit">전송</button>
    </form>

    <hr/>
    <p><a href="${pageContext.request.contextPath}/">← 홈으로</a></p>
</body>
</html>

