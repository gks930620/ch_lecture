# web_basic 검수 — 12 CODE enum / 13 listener / 14 MVC pattern

검수일: 2026-07-05
검수 범위: `docs/.../12_CODE_enum.md`, `13_listener.md`, `14_MVC_pattern.md` + `CodeEnum.java`, `BoardStatus.java`, `code_table.sql`, `AppInitListener.java`, `SessionCountListener.java`, `13_listener_demo.jsp`, `14_bad_example.jsp`, `web.xml`

정확성 확인(문제없음): 코드값(A/I/D, 활성/비활성/삭제)이 `BoardStatus.java`·`code_table.sql`·문서 모두 일치. `CodeEnum.java`는 파일 주석(4-6행)에서 "참고용, 실습 미사용, BoardStatus 사용"이라 명시. 리스너 시그니처(ServletContextListener contextInitialized/Destroyed, HttpSessionListener sessionCreated/Destroyed) 정확, `@WebListener` 등록 ↔ web.xml `<listener>` 주석 처리(`web.xml:144-151`)가 문서 설명과 일치. MVC/Model2/Front Controller(`@WebServlet("/board/*")` + `getPathInfo()` 분기, md:96-110) 및 서블릿MVC→Spring 대응표 정확. **세 문서 모두 이미지 참조 없음.**

---

## 12_CODE_enum

### 중간
- `docs/.../12_CODE_enum.md:114` — 주석 "새 상태 추가 시 컴파일 경고 → 빠뜨릴 수 없음!"이 사실과 다름. Java의 전통적 `switch` **문(statement)**은 enum 상수를 새로 추가해도 case 누락에 대해 javac가 경고를 내지 않음(Tomcat 9 = Java 8 환경). 컴파일 강제성은 IDE 인스펙션이나 Java 14+ `switch` **식(expression)**의 exhaustiveness에서만 성립. 초보자에게 "컴파일러가 잡아준다"는 잘못된 기대를 줄 수 있음 → "IDE가 경고/제안" 정도로 표현 완화 권장.

### 낮음
- `docs/.../12_CODE_enum.md:22-30` — 문서 예시 `CREATE TABLE code(category, code, label)` + `INSERT INTO code VALUES('board_status','A','활성')`(3값). 실제 `code_table.sql:3-8`은 `id INT AUTO_INCREMENT PRIMARY KEY` 포함 4컬럼이라 `VALUES(...)` 3값 삽입은 실패. 문서는 단순화된 별도 예시라 그 자체로 틀린 건 아니고, 실제 sql은 컬럼 명시 `INSERT INTO code(category, code, label) VALUES(...)` 형태(정상). 문서↔실제 파일 형태 차이만 인지.

## 13_listener

### 낮음
- `docs/.../13_listener.md:48-53` — 문서의 SessionCountListener 예시는 `private static int activeSessions;` + `activeSessions++/--`이나, 실제 `SessionCountListener.java:13,17,23`는 `AtomicInteger`의 `incrementAndGet()/decrementAndGet()`을 사용. 실제 소스가 스레드 안전하게 더 정확히 구현된 것이라 버그는 아니고, 세션은 동시 생성될 수 있어 문서의 단순 `int++` 버전은 엄밀히 원자적이지 않음. 문서↔소스 구현 차이만 인지(초보자용 단순화로는 허용 가능).
- `docs/.../13_listener.md:65` — "실시간 접속자 수 추적": 활성 세션 수는 순수 접속자 수와 정확히 같지는 않음(한 사용자가 여러 세션 보유 가능, 세션은 timeout까지 유지). 다만 문서 대부분이 "세션 수"로 표기하고 demo도 "현재 활성 세션 수"라 큰 문제는 아님. 증감 원리(생성 +1, 소멸 -1) 자체는 정확.

## 14_MVC_pattern

### 중간
- `docs/.../14_MVC_pattern.md:77-87` — "JSP에 다 넣으면 생기는 문제 (`14_bad_example.jsp` 참고)"라며 `DriverManager.getConnection(...)` + `ResultSet`/`prepareStatement("SELECT * FROM board")` 스니펫을 보여주지만, 실제 `14_bad_example.jsp`에는 JDBC/DB 접근 코드가 없음. 실제 파일은 스크립틀릿에서 `List<Map<String,String>>`을 하드코딩하고 주석으로 "DB 접근이라고 가정"(`14_bad_example.jsp:15`). "참고"로 파일을 지목하면서 파일에 없는 코드를 예시로 제시 → 문서↔소스 불일치. 문서 스니펫을 "예를 들어 이런 DB 접근이 섞이면"처럼 표현하거나 실제 파일 내용(하드코딩 Map)에 맞추는 것 권장.

### 낮음(정상 확인)
- `14_bad_example.jsp`가 "나쁜 예시"임은 파일명·제목(7행 "나쁜 예")·주석("❌ 이렇게 하면 안 됩니다!", 9-13행)·하단 문제점 정리(47-57행)로 충분히 명시됨. 의도적 안티패턴임이 분명(정상).

### 이미지 제안
- MVC(Model2) 요청 흐름: 요청 → Controller(Servlet) → Model(DAO) → View(JSP) → 응답. Model1(JSP에 로직 혼재)과 대비되는 그림이 있으면 효과적.
