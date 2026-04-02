<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Redirect 결과</title></head>
<body>
    <h2>Redirect 결과 페이지</h2>
    <p><strong>요청 속성 msg:</strong> ${requestScope.msg}</p>
    <p>▶ Redirect이므로 클라이언트가 새 요청을 보냅니다. request 속성은 사라집니다.</p>
    <p><strong>브라우저 주소창을 확인하세요:</strong> URL이 이 JSP 경로로 바뀌었습니다.</p>
    <hr/>
    <p><a href="${pageContext.request.contextPath}/04_forward_redirect/04_form.jsp">← 폼으로 돌아가기</a></p>
</body>
</html>

