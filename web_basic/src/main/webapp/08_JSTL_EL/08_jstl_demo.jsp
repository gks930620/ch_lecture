<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>JSTL/EL 데모</title></head>
<body>
    <h1>08. JSTL / EL 실습</h1>

    <%-- ===== 1. EL 기본 ===== --%>
    <h2>1) EL 기본</h2>
    <p>1 + 2 = ${1 + 2}</p>
    <p>10 > 5 = ${10 > 5}</p>
    <p>param.q = ${empty param.q ? '(없음)' : param.q}</p>
    <p>param.page = ${param.page}</p>

    <%-- ===== 2. c:set, c:out ===== --%>
    <h2>2) c:set / c:out</h2>
    <c:set var="name" value="학생" />
    <c:set var="score" value="85" />
    <p>이름: <c:out value="${name}" /> / 점수: <c:out value="${score}" /></p>

    <%-- ===== 3. c:if ===== --%>
    <h2>3) c:if</h2>
    <c:if test="${score >= 80}">
        <p>합격 (${score}점)</p>
    </c:if>

    <%-- ===== 4. c:choose ===== --%>
    <h2>4) c:choose</h2>
    <c:choose>
        <c:when test="${score >= 90}"><p>등급: A</p></c:when>
        <c:when test="${score >= 80}"><p>등급: B</p></c:when>
        <c:when test="${score >= 70}"><p>등급: C</p></c:when>
        <c:otherwise><p>등급: F</p></c:otherwise>
    </c:choose>

    <%-- ===== 5. c:forEach (List) ===== --%>
    <h2>5) c:forEach</h2>
    <%
        java.util.List<String> fruits = new java.util.ArrayList<>();
        fruits.add("사과");
        fruits.add("바나나");
        fruits.add("포도");
        fruits.add("딸기");
        request.setAttribute("fruits", fruits);
    %>
    <c:forEach var="fruit" items="${fruits}" varStatus="st">
        <p>${st.count}. ${fruit} (first=${st.first}, last=${st.last})</p>
    </c:forEach>

    <%-- ===== 6. c:url ===== --%>
    <h2>6) c:url</h2>
    <c:url var="boardUrl" value="/board/list" />
    <p><a href="${boardUrl}">${boardUrl}</a></p>

    <%-- ===== 7. c:forEach 숫자 범위 ===== --%>
    <h2>7) c:forEach 숫자 범위</h2>
    <p>
    <c:forEach var="i" begin="1" end="5">
        [<a href="?page=${i}">${i}</a>]
    </c:forEach>
    </p>

    <hr/>
    <p><a href="${pageContext.request.contextPath}/">← 홈으로</a></p>
</body>
</html>

