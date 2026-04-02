<%@ page contentType="text/html;charset=UTF-8" %>
<%-- 이 페이지는 의도적으로 예외를 발생시킵니다 --%>
<%-- web.xml의 error-page 설정에 의해 /07_error_handling/error/500.jsp로 이동합니다 --%>
<% throw new RuntimeException("테스트 예외: 이 예외는 의도적으로 발생시켰습니다."); %>

