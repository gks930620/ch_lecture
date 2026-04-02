# 08 JSTL / EL — 상세 강의노트

목차
- EL(Expression Language) 개요
- EL 문법과 주요 연산자
- JSTL(Core) 태그 소개 및 사용 예제
- 국제화(fmt), SQL 태그 소개(안전하게 사용하기)
- 실습: 리스트 바인딩, 조건문, URL 생성

1) EL 개요
- EL은 JSP에서 표현식을 간결하게 작성하도록 도와주는 언어로 `${...}` 형태를 사용.
- 객체 접근 우선순위: `pageScope -> requestScope -> sessionScope -> applicationScope`.

2) EL 주요 기능
- 속성 접근: `${param.name}`, `${requestScope.user}`, `${sessionScope.count}`
- 연산자: `+ - * /`, 비교 연산자(`==`, `!=`, `<`, `>`), 논리 연산자(`and`, `or`, `not`)
- 컬렉션 접근: `${list[0]}`, `${map['key']}`
- 기본값: `${param.page ne null ? param.page : 1}` (EL 2.2 이상 또는 JSTL로 처리)

3) JSTL Core 태그
- `<c:set var="x" value="..."/>` : 변수 설정
- `<c:forEach var="item" items="${list}">...</c:forEach>` : 반복
- `<c:if test="${...}">...</c:if>` : 조건
- `<c:choose><c:when test="..."/> <c:otherwise/> </c:choose>` : 선택문
- `<c:url value="/path"/>` : 컨텍스트 경로를 고려한 URL 생성

예제
```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="list" value="${requestScope.items}" />
<ul>
  <c:forEach var="it" items="${list}">
    <li>${it.name} - ${it.price}</li>
  </c:forEach>
</ul>
```

4) 국제화(fmt)와 SQL 태그(간단)
- `fmt` 태그로 메시지 번들 사용, 날짜/숫자 포맷팅 가능.
- `sql` 태그는 간단한 쿼리 테스트에 유용하지만 실무에서는 JDBC/MyBatis 같은 레이어를 사용해야 안전함(주입 공격 취약성).

5) 실습 지침
- `08_jstl_demo.jsp`에서 리스트를 바인딩하고 `<c:forEach>`로 출력.
- `c:url`을 사용해 링크를 만들고 컨텍스트 경로 의존성을 제거.
- EL을 사용해 파라미터와 스코프 간 우선순위를 실험해보세요.

6) 주의사항
- JSTL 사용을 위해서는 JSTL 라이브러리가 필요(`javax.servlet.jsp.jstl` 등). 프로젝트에 의존성이 없으면 추가해야 함.
- 가능하면 로직은 서블릿/비즈니스 레이어로 두고 JSP는 표시만 하도록 설계.

이 문서는 EL과 JSTL의 핵심을 실습 중심으로 자세히 설명해, 강의 중 예제와 실습 문제로 바로 활용할 수 있게 구성했습니다.
