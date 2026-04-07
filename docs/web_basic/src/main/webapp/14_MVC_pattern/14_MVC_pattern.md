# 14. MVC 패턴 — 우리가 이미 해본 것의 이름

## 핵심
> Ch10 게시판에서 했던 "서블릿이 요청 받고 → DAO 호출하고 → JSP로 forward" 구조,
> 그게 MVC 패턴이다.

---

## 1) 돌아보기: 이 프로젝트의 흐름 변화

### ① Ch01~03: JSP 하나에 다 넣음
```
브라우저 → JSP (로직 + 출력 모두 처리)
```
- `01_basic.jsp`에서 선언문, 스크립틀릿으로 로직 + HTML 출력
- 서블릿 없음. 파일 하나가 모든 걸 함

### ② Ch04~06: 서블릿이 등장
```
브라우저 → 서블릿(처리) → JSP(출력)
```
- `ForwardRedirectServlet` — 파라미터 받아서 forward/redirect
- `CookieSessionServlet` — 쿠키/세션 처리 후 JSP로 redirect
- **처리와 출력이 분리**되기 시작했지만, 데이터 클래스(Model)는 없었음

### ③ Ch10: 게시판 → 자연스럽게 완성된 MVC
```
브라우저 → BoardController(서블릿) → BoardDao → JSP
```
- `Board.java`, `BoardDao` — 데이터와 DB 접근
- `BoardController` — 파라미터 파싱, DAO 호출, `req.setAttribute()`, forward
- `board_list.jsp` — EL로 출력만

**이 구조에 이름을 붙인 것이 MVC 패턴이다.**

---

## 2) MVC 패턴이란

| 구분 | 역할 | Ch10에서 | Spring에서 |
|------|------|---------|-----------|
| **Model** | 데이터 + 비즈니스 로직 | `Board`, `BoardDao`, `InMemoryBoardDao` | 동일 (+ Service 계층) |
| **View** | 화면 출력 | `board_list.jsp`, `board_view.jsp` | JSP, Thymeleaf 등 |
| **Controller** | 요청 → 로직 호출 → 뷰 선택 | `BoardController` (HttpServlet) | `@Controller` |

### Ch10 코드로 보면

**Controller** — BoardController.java
```java
@WebServlet("/board/*")
public class BoardController extends HttpServlet {
    protected void doGet(...) {
        List<Board> list = dao.selectList(null);   // 2. Model 호출
        req.setAttribute("list", list);            // 3. 결과 저장
        req.getRequestDispatcher("...").forward(); // 4. View로 전달
    }
}
```

**Model** — Board.java + BoardDao
```java
public class Board { /* 데이터 */ }
public interface BoardDao { List<Board> selectList(...); }
```

**View** — board_list.jsp
```jsp
<c:forEach var="b" items="${list}">
    ${b.title}  <%-- 출력만. 로직 없음 --%>
</c:forEach>
```

---

## 3) 왜 이렇게 분리하는가?

### JSP에 다 넣으면 생기는 문제 (`14_bad_example.jsp` 참고)
```jsp
<%-- ❌ JSP에 DB 접근 + 로직 + HTML이 전부 섞여있음 --%>
<%
    Connection conn = DriverManager.getConnection(...);
    ResultSet rs = conn.prepareStatement("SELECT * FROM board").executeQuery();
%>
<% while(rs.next()) { %>
    <p><%= rs.getString("title") %></p>
<% } %>
```
- 유지보수 불가능: 어디가 로직이고 어디가 화면인지 구분 안됨
- 테스트 불가능: JSP를 단위 테스트할 수 없음
- 역할 분담 불가: 화면 수정하다가 Java 코드가 깨짐

→ 그래서 **처리는 서블릿, 데이터는 Java 클래스, 출력은 JSP**로 분리한 것.

---

## 4) Front Controller 패턴

Ch10의 `BoardController`를 다시 보자:
```java
@WebServlet("/board/*")  // /board/로 시작하는 모든 요청을 이 서블릿이 받음
```
```java
String path = req.getPathInfo();
if ("/list".equals(path)) { ... }
if ("/view".equals(path)) { ... }
if ("/write".equals(path)) { ... }
```

하나의 서블릿이 `/board/*` 요청을 전부 받아서 pathInfo로 분기한다.
이것이 **Front Controller 패턴**이고, **Spring의 DispatcherServlet**이 바로 이걸 프레임워크 수준으로 만든 것이다.

```
[서블릿 방식]                         [Spring 방식]
BoardController (@WebServlet)     →  DispatcherServlet (프레임워크 제공)
  pathInfo로 if/else 분기          →    @RequestMapping으로 자동 매핑
  req.getParameter() 수동 파싱     →    메서드 파라미터에 자동 바인딩
  req.setAttribute() + forward    →    return "viewName"
  new InMemoryBoardDao() 직접 생성 →    @Autowired (DI)
```

---

## 5) 서블릿 MVC에서 불편했던 것 → Spring이 해결

| 서블릿 MVC | Spring MVC |
|-----------|-----------|
| 서블릿마다 `@WebServlet` 등록 | `@Controller` + `@RequestMapping` |
| `req.getParameter()` 수동 파싱 | 메서드 파라미터에 자동 바인딩 |
| `req.setAttribute()` + `forward` | `return "viewName"` |
| 공통 처리(인코딩, 인증) 중복 | 인터셉터, AOP |
| `new BoardDao()` 직접 생성 | DI (의존성 주입) |

→ **서블릿으로 MVC를 직접 해봤기 때문에, Spring이 왜 이렇게 설계되었는지 이해할 수 있다.**

---

## 6) 정리

이 프로젝트에서 배운 것을 돌아보면:

```
Ch01~03  JSP만 → "왜 서블릿이 필요한가?" 체감
Ch04~06  서블릿 등장 → Controller와 View 분리 시작
Ch10     게시판 → Model 추가 → MVC 완성
Ch14     ← 지금 여기: "그게 MVC였다"
         → 다음: Spring MVC ("더 편하게 MVC 하는 법")
```

