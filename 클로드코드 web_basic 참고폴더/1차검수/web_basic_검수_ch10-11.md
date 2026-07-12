# web_basic 검수 — 10 mybatis board / 11 paging·search

검수일: 2026-07-05
검수 범위: `docs/.../10_mybatis_board.md`, `11_paging_search.md` + `board_list.jsp`, `board_view.jsp`, `board_write.jsp`, `11_paging_demo.jsp`, `BoardController.java`, `BoardDao.java`, `InMemoryBoardDao.java`, `Board.java`, `mybatis-config.xml`, `BoardMapper.xml`, `board_schema.sql`, `pom.xml`

정확성 확인(문제없음): `BoardMapper.xml:3` namespace가 DAO FQN과 일치, id(selectList/selectOne/insert/update/delete)가 DAO 메서드명과 일치. resultMap 컬럼(status→statusCode, created_at→createdAt 등)이 `board_schema.sql:3-9`와 일치. 페이징 경계 계산(offset `(page-1)*size`, `totalPages=ceil(53/10)=6`)은 정확. **이미지 참조 없음.**

> 배경: 이 프로젝트는 실제로는 `InMemoryBoardDao`만 배선되어 동작한다(`BoardController.java:20`이 하드코딩). MyBatis/JDBC 의존성(`pom.xml:61-79`)·`mybatis-config` environments(`mybatis-config.xml:7-19`)가 전부 주석 처리됨. 즉 `BoardMapper.xml`은 "앞으로 MyBatis로 바꿀 때 쓸 참고 매퍼"이며 현재 실행 경로엔 관여하지 않는다. 아래 다수 지적은 이 "문서는 MyBatis 기준 설명 / 소스는 InMemory" 간극에서 나온다.

---

## 10_mybatis_board

### 중간
- `docs/.../10_mybatis_board.md:54` — SQL 인젝션 설명이 `#{}`만 언급하고 `${}`를 전혀 설명하지 않음. 검수 핵심인 "`#{}`(PreparedStatement 바인딩) vs `${}`(문자열 치환, 인젝션 위험)" 대비가 빠져 초보자가 `${}`의 위험성을 알 수 없음. 또한 "자동 이스케이프가 적용됨"은 부정확 — `#{}`는 이스케이프가 아니라 **PreparedStatement의 `?` 파라미터 바인딩**(값을 SQL 문자열에 끼워넣지 않음)으로 인젝션을 막음. → "`#{}`는 파라미터 바인딩(PreparedStatement), 반대로 `${}`는 문자열을 그대로 치환하므로 인젝션 위험" 식으로 수정 권장.
- `docs/.../10_mybatis_board.md:23` — "Mapper XML: ... 동적 SQL(검색어에 따른 WHERE 절) 사용"이라 했으나, 실제 `BoardMapper.xml`에는 `<if>`/`<where>` 등 동적 SQL이나 검색 WHERE가 전혀 없음(`BoardMapper.xml:14-16` selectList는 `ORDER BY id DESC LIMIT`뿐). 문서↔소스 불일치. 문서를 실제에 맞추거나 매퍼에 동적 SQL 추가 필요.

### 낮음
- `BoardMapper.xml:15` — `selectList`가 항상 `LIMIT #{offset},#{limit}`를 요구. 그런데 DAO 계약(`BoardDao.java:7`)·컨트롤러(`BoardController.java:26`, `dao.selectList(null)`)는 offset/limit 없이 호출 가능. 실제 MyBatis로 전환하면 offset/limit 없을 때 바인딩 오류로 실패함. (현재 InMemory만 써서 미발현.) 계약 불일치.
- `docs/.../10_mybatis_board.md:21` — 모델을 `Board { id, title, content, writer, createdAt }`로 기술했으나 실제 `Board.java:12`엔 `statusCode` 필드가 있음(Ch12 추가). 목록에서 statusCode 누락.
- `docs/.../10_mybatis_board.md:46` — "DAO를 MyBatis 기반 구현으로 교체"라 하지만 실제 MyBatis DAO 구현체·SqlSessionFactory 배선이 없고 의존성/설정이 주석 처리 상태. 제공되는 건 `BoardMapper.xml`뿐 → "교체"는 수강생이 직접 작성해야 하는 미완성. 즉시 실행 가능한 것처럼 오해될 수 있으니 "직접 구현 과제"임을 명시 권장.
- `docs/.../10_mybatis_board.md:50-55` — Spring Transaction, 낙관/비관 락(version), keyset pagination, HikariCP 등이 "처음 개발 시작" 대상에게 설명 없이 등장. "팁"으로 묶여 치명적이진 않으나 초보 기준 난이도 초과.
- `InMemoryBoardDao.java:22-27` — 페이징 경계 잠재 버그. `from = Math.max(0, offset)`가 `list.size()`로 상한 처리되지 않아 offset이 데이터 개수보다 크면 `subList(from, to)`에서 `from > to`가 되어 `IllegalArgumentException`(예: size=1, offset=10 → `subList(10,1)`). 현재 컨트롤러는 `selectList(null)`만 호출해 미발현이나 결함. `from`도 `Math.min(list.size(), Math.max(0, offset))`로 상한 처리 필요.

## 11_paging_search

### 중간
- `docs/.../11_paging_search.md:34` — "MyBatis 매퍼에서 `count` 쿼리와 `selectList` 쿼리를 분리해 구현"이라 했으나 실제 `BoardMapper.xml`에 count 쿼리(예: `selectCount`)가 없음. 전체 건수 조회 매퍼 부재로 불일치. 매퍼에 count select 추가 또는 "예시/향후 구현" 명시 필요.
- `docs/.../11_paging_search.md:22-27` — SQL 예제가 `WHERE title LIKE CONCAT('%', #{q}, '%') ... LIMIT #{offset}, #{limit}`로 검색+페이징을 보여주지만 실제 `BoardMapper.xml:15` selectList엔 WHERE/LIKE가 없음. 예제 서술 자체는(MySQL 문법상) 맞으나 문서가 설명하는 흐름과 실제 매퍼가 다름 → 정렬 필요.

### 낮음
- `11_paging_demo.jsp:21-22` — `Integer.parseInt(pageStr)`에 try/catch 없음 → `?page=abc` 입력 시 `NumberFormatException` 500. 방어 코드 부재.
- `11_paging_demo.jsp:27,39,44` — 검색어 `q`를 `<%= q %>`와 링크 `"&q=" + q`로 이스케이프/URL 인코딩 없이 출력. `board_list.jsp`가 `<c:out>`으로 XSS를 막는 것과 대조 → 반사형 XSS·특수문자 링크 깨짐 소지. 데모라 심각도 낮으나 일관성/보안 관점 지적.

### 이미지 제안
- 페이지 번호 계산(전체 건수 → totalPages, offset=(page-1)*size) 도식.
