<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>서블릿/JSP 기초 강의</title></head>
<body>
    <h1>서블릿/JSP 기초 강의</h1>
    <p>현재 서버 시간: <%= new java.util.Date() %></p>
    <p>Context Path: <%= request.getContextPath() %></p>
    <p>서버 정보: <%= application.getServerInfo() %></p>
    <hr/>

    <h2>강의 목차</h2>
    <ol>
        <li><strong>Ch00</strong> 실습환경 구축</li>

        <li><strong>Ch01</strong> WAS와 웹서버 —
            <a href="01_WAS와웹서버/01_basic.jsp">JSP 기본문법</a> |
            <a href="01_WAS와웹서버/01_intro.jsp">WAS vs WS</a> |
            <a href="01_WAS와웹서버/01_server_info.jsp">서버정보</a> |
            <a href="01_WAS와웹서버/01_dynamic_content.jsp">동적컨텐츠</a> |
            <a href="<%= request.getContextPath() %>/lifecycle">서블릿 생명주기/HTTP</a></li>

        <li><strong>Ch02</strong> JSP 필수 객체 —
            <a href="02_JSP_필수객체/02_scope_demo.jsp">Scope 데모</a></li>

        <li><strong>Ch03</strong> Page Directive & Include —
            <a href="03_page_directive/03_page_directives.jsp">Include 실습</a></li>

        <li><strong>Ch04</strong> Forward vs Redirect —
            <a href="04_forward_redirect/04_form.jsp">실습 폼</a></li>

        <li><strong>Ch05</strong> Cookie & Session —
            <a href="05_cookie_session/05_cookie_demo.jsp">쿠키</a> |
            <a href="05_cookie_session/05_session_demo.jsp">세션</a> |
            <a href="05_cookie_session/05_notice_popup.jsp">공지팝업(쿠키활용)</a> |
            <a href="05_cookie_session/05_login.jsp">로그인(세션활용)</a></li>

        <li><strong>Ch06</strong> WEB-INF —
            <a href="06_WEB-INF/WEB-INF/hidden.jsp">직접 접근 시도</a> |
            <a href="<%= request.getContextPath() %>/06_WEB-INF/hidden">forward로 접근</a></li>

        <li><strong>Ch07</strong> 에러 처리 —
            <a href="07_error_handling/07_error_demo.jsp">에러 데모</a></li>

        <li><strong>Ch08</strong> JSTL / EL —
            <a href="08_JSTL_EL/08_jstl_demo.jsp">JSTL/EL 데모</a></li>

        <li><strong>Ch09</strong> Filter —
            <a href="09_filter/09_filter_test.jsp">필터 테스트</a></li>

        <li><strong>Ch10</strong> MyBatis 게시판 —
            <a href="<%= request.getContextPath() %>/board/list">게시판 목록</a> |
            <a href="<%= request.getContextPath() %>/board/write">글쓰기</a></li>

        <li><strong>Ch11</strong> 페이징 & 검색 —
            <a href="11_paging_search/11_paging_demo.jsp">페이징 데모</a></li>

        <li><strong>Ch12</strong> CODE 테이블 & Java Enum — 강의노트 참조</li>

        <li><strong>Ch13</strong> Listener —
            <a href="13_listener/13_listener_demo.jsp">리스너 데모</a></li>

        <li><strong>Ch14</strong> MVC 패턴 —
            <a href="14_MVC_pattern/14_bad_example.jsp">나쁜 예(JSP에 로직)</a> |
            <a href="<%= request.getContextPath() %>/board/list">올바른 예(MVC 게시판)</a></li>
    </ol>

    <hr/>
    <p>Java 코드(Servlet/Filter) 수정 → 서버 Restart 필요</p>
    <p>JSP/HTML 수정 → 브라우저 새로고침만 하면 반영</p>
</body>
</html>

