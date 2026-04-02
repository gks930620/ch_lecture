<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>JSP 기본 문법</title></head>
<body>
    <h1>01. JSP 기본 문법 실습</h1>

    <%-- 1. 선언문 (Declaration) --%>
    <%!
        private int visitCount = 0;

        public String greet(String name) {
            return "안녕하세요, " + name + "님!";
        }
    %>

    <%-- 2. 스크립틀릿 (Scriptlet) --%>
    <%
        visitCount++;
        String currentTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new java.util.Date());
    %>

    <%-- 3. 표현식 (Expression) --%>
    <p>현재 시간: <%= currentTime %></p>
    <p>방문 횟수: <%= visitCount %></p>
    <p>인사말: <%= greet("학생") %></p>

    <%-- 4. 주석 비교 --%>
    <!-- HTML 주석: 소스보기에서 보임 -->
    <%-- JSP 주석: 클라이언트에게 전달 안됨 --%>

    <hr/>
    <p><a href="01_intro.jsp">WAS vs WS 실습</a></p>
    <p><a href="01_server_info.jsp">서버 정보</a></p>
</body>
</html>

