<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>MVC 나쁜 예</title></head>
<body>
    <h1>14. MVC — 나쁜 예 (JSP에 모든 것이 섞인 경우)</h1>

    <%--
        ❌ 이렇게 하면 안 됩니다!
        JSP 안에서 비즈니스 로직 + 데이터 생성 + 출력을 모두 처리하고 있음.
        유지보수, 테스트, 역할 분담이 전부 불가능해진다.
    --%>
    <%
        // JSP 안에서 직접 데이터를 만들고 있음 (DB 접근이라고 가정)
        List<Map<String, String>> boards = new ArrayList<>();
        Map<String, String> b1 = new HashMap<>();
        b1.put("id", "1");
        b1.put("title", "첫 번째 글");
        b1.put("writer", "admin");
        boards.add(b1);

        Map<String, String> b2 = new HashMap<>();
        b2.put("id", "2");
        b2.put("title", "두 번째 글");
        b2.put("writer", "user1");
        boards.add(b2);

        // 비즈니스 로직까지 JSP에서 처리
        int totalCount = boards.size();
    %>

    <p>총 게시글 수: <%= totalCount %></p>

    <table border="1" cellpadding="5">
        <tr><th>번호</th><th>제목</th><th>작성자</th></tr>
        <% for (Map<String, String> b : boards) { %>
            <tr>
                <td><%= b.get("id") %></td>
                <td><%= b.get("title") %></td>
                <td><%= b.get("writer") %></td>
            </tr>
        <% } %>
    </table>

    <hr/>
    <p>
        ▶ 위 코드의 문제:<br/>
        1. 데이터 접근(DAO 역할)이 JSP 안에 있음<br/>
        2. 비즈니스 로직이 JSP 안에 있음<br/>
        3. 화면(HTML)과 로직이 뒤섞여 유지보수 불가능<br/>
        <br/>
        ▶ MVC로 분리하면:<br/>
        - Model: Board.java + BoardDao → 데이터/로직<br/>
        - Controller: BoardController(서블릿) → 요청 처리, forward<br/>
        - View: board_list.jsp → EL로 출력만
    </p>

    <hr/>
    <p><a href="${pageContext.request.contextPath}/board/list">→ MVC 방식의 게시판 (올바른 예)</a></p>
    <p><a href="${pageContext.request.contextPath}/">← 홈으로</a></p>
</body>
</html>

