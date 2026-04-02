<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Forward 결과</title></head>
<body>
    <h2>Forward 결과 페이지</h2>
    <p><strong>요청 속성 msg:</strong> ${requestScope.msg}</p>
    <p>▶ Forward이므로 서블릿에서 설정한 request 속성이 그대로 전달됩니다.</p>

    <p><strong>브라우저 주소창을 확인하세요:</strong> URL이 <code>/04_forward_redirect/handle</code>로 유지됩니다.</p>
    <hr/>
    <p><a href="${pageContext.request.contextPath}/04_forward_redirect/04_form.jsp">← 폼으로 돌아가기</a></p>
</body>
</html>

