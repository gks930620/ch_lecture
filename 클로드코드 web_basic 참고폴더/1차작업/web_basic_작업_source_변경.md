# web_basic 1차 작업 — 소스 변경 상세

작업일: 2026-07-05

소스 변경은 "실습이 실제로 깨지거나 시연 목적이 성립하지 않는" 검수 항목에 한정해 최소로 반영했다.

---

## 1. ch06 — WEB-INF hidden.jsp 이동 (실습 정상화)

### 파일 이동
- 삭제: `web_basic/src/main/webapp/06_WEB-INF/WEB-INF/hidden.jsp` (및 빈 폴더 `06_WEB-INF/`, `06_WEB-INF/WEB-INF/`)
- 생성: `web_basic/src/main/webapp/WEB-INF/06_WEB-INF/hidden.jsp`

### 왜?
톰캣(StandardContextValve)의 접근 차단은 요청 경로가 `/WEB-INF/` 또는 `/META-INF/`로 **시작**할 때만 404를 반환한다. 기존 위치 `/06_WEB-INF/WEB-INF/hidden.jsp`는 `/06_WEB-INF/`로 시작하므로 **차단되지 않아** 브라우저로 직접 접근하면 그대로 렌더링됐다. 즉 "직접 접근 불가"를 보여주려는 실습이 정반대로 동작. 컨텍스트 루트 `WEB-INF/` 하위로 옮겨야 실제로 보호된다.

### WebInfForwardServlet.java
```diff
- req.getRequestDispatcher("/06_WEB-INF/WEB-INF/hidden.jsp").forward(req, resp);
+ req.getRequestDispatcher("/WEB-INF/06_WEB-INF/hidden.jsp").forward(req, resp);
```
- 서블릿 매핑 `@WebServlet("/06_WEB-INF/hidden")`은 그대로. forward는 서블릿 코드라 어떤 경로든 동작하므로 서블릿 경유 접근은 계속 성공한다.
- hidden.jsp 본문 안내 문구도 "컨텍스트 루트의 /WEB-INF/ 안에 있어서..."로 정확화.

### 실습 시 기대 동작(수정 후)
- 브라우저로 `.../WEB-INF/06_WEB-INF/hidden.jsp` 직접 접근 → **404** (차단 확인)
- 브라우저로 `.../06_WEB-INF/hidden` (서블릿) 접근 → 정상 렌더링 (forward 제공 확인)

---

## 2. ch05 — 공지팝업 "닫기"가 세션 쿠키를 설정하도록 수정

### LoginServlet.java (handleNotice, action=close 분기)
```diff
  } else if ("close".equals(action)) {
-     // 오늘만 닫기: 쿠키를 설정하지 않고 그냥 리다이렉트
-     // 브라우저가 닫히거나 새 요청을 보내면 다시 팝업이 뜬다
-
+     // 이번 브라우저 세션 동안만 닫기: 세션 쿠키(maxAge 미설정 = -1)
+     // 값은 "1주일간 안보기"와 동일한 hideNotice=true 지만, maxAge만 다르다.
+     // → 브라우저를 닫으면 쿠키가 사라져 팝업이 다시 뜬다(7일 쿠키와 대비되는 포인트).
+     Cookie cookie = new Cookie("hideNotice", "true");
+     cookie.setPath(req.getContextPath() + "/");
+     // setMaxAge를 호출하지 않으면 기본값 -1 → 세션 쿠키
+     cookie.setHttpOnly(true);
+     resp.addCookie(cookie);
  } else if ("reset".equals(action)) {
```
- javadoc 주석의 `action=close → 오늘만 (세션 쿠키, maxAge 설정 안함)` → 실제 동작에 맞게 갱신.

### 05_notice_popup.jsp (버튼 라벨)
```diff
- <button type="submit" name="action" value="close">닫기 (오늘만)</button>
+ <button type="submit" name="action" value="close">닫기 (이번 세션만 - 세션 쿠키)</button>
```

### 왜 코드 수정을 택했나?
검수는 "코드를 세션 쿠키 설정으로 고치거나, 라벨/설명을 실제 동작(아무것도 안 함)에 맞추라"고 두 옵션을 제시했다. **세션 쿠키를 설정하는 쪽**을 택한 이유:
- 팝업 JSP는 `hideNotice=="true"` 쿠키만 확인하므로, 세션 쿠키를 심으면 브라우저 세션 동안 팝업이 숨겨지고 브라우저 종료 시 다시 뜬다 → 라벨 "이번 세션만"이 실제로 성립.
- "1주일간 안보기"(7일 영속 쿠키)와 **maxAge만 다른** 나란한 예제가 되어, **영속 쿠키 vs 세션 쿠키**라는 ch05 핵심 개념을 실습으로 직접 대비해 볼 수 있다(교육 가치 상승).

---

## 3. ch10 — InMemoryBoardDao 페이징 경계 수정 (사용자 요청으로 추가 반영)

### InMemoryBoardDao.java (selectList)
```diff
  int offset = (int) params.get("offset");
  int limit = (int) params.get("limit");
- int from = Math.max(0, offset);
- int to = Math.min(list.size(), offset + limit);
+ // subList(from, to)는 from > to 이면 예외가 난다.
+ // offset이 데이터 개수보다 클 수 있으므로 from에도 상한(list.size())을 씌운다.
+ // → Math.max(0, ...)로 하한, Math.min(list.size(), ...)로 상한을 잡는 습관.
+ int from = Math.min(list.size(), Math.max(0, offset));
+ int to = Math.min(list.size(), from + limit);
  return list.subList(from, to);
```
- 기존 버그: `from`이 `list.size()`로 상한 처리되지 않아 `offset > 데이터 개수`이면 `from > to` → `IllegalArgumentException`. (예: size=1, offset=10 → `subList(10,1)`)
- 수정 후: `from`은 항상 `0 ~ list.size()` 범위, `to = min(size, from+limit)`이므로 `from <= to` 보장 → 빈 리스트를 반환할지언정 예외는 안 남.
- 왜 소스를 고쳤나: 사용자 판단 — `Math.max/Math.min`을 쓰는 코드 자체가 "잘라내기 범위(from/to)를 늘 신경 써야 한다"는 걸 실습생이 자연스럽게 인식하게 하는 교육 효과가 있음.

---

## 컴파일 영향
- 세 Java 파일 모두 기존 import 범위 내 최소 변경. 새 심볼/의존성 추가 없음. (`InMemoryBoardDao`는 `java.util.*`, `Math`는 기본 java.lang.)
