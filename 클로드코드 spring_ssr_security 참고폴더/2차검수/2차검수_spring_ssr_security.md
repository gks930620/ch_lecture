# spring_ssr_security 2차 검수 보고서

- 검수일: 2026-07-05
- 대상: 1차작업(`1차작업/00~02`) 반영 후의 `docs/spring_ssr_security/*` + `spring_ssr_security/src/**`
- 방식: 작업 보고서를 신뢰하지 않고 **실제 파일을 직접 열어 재검증** + 새로 생긴 문제/누락 탐색
- 결론: **1차 지적 9건 중 8건 정상 반영 확인. 1건(#3 클래스명)만 1줄 누락**. 그 외 신규 경미 1건.

---

## 1차 지적 반영 검증 결과

| 1차# | 항목 | 검증 결과 | 근거 |
|------|------|----------|------|
| 1 | BCrypt "자동 디코딩" | ✅ 완료 | security설명.md:15 → "encode()로 해시 저장, matches()로 해시 비교 (단방향 — 복호화 불가)" |
| 2 | 이미지 8개 깨짐 | ✅ 완료 | `docs/.../spring_ssr_security_images/`에 security1~8.png 8개 존재, 문서 8곳 모두 `relative_url` 경로로 교체 확인 |
| 3 | 클래스명 CustomUserDetails→CustomUserAccount | ⚠️ **1줄 누락** | 프로젝트_구조_정리.md:78 (아래 §신규-A) |
| 4 | anyRequest().permitAll() 보완 | ✅ 완료 | security설명.md:61~68 화이트리스트/fail-close 안내 박스 추가 (삭제 아닌 설명 추가 — 적절) |
| 5 | 테스트 yml (doll_gacha) | ✅ 완료 | test/resources/application.yml → `test` DB + `data-users.sql` 하나만, oauth2/jwt/없는 SQL 전부 제거 |
| 6 | index.html 죽은 링크/없는 기능 | ✅ 완료 | /community·/api/test 제거 → /mypage·/admin, 카드 4개 실제 기능(세션로그인/BCrypt/권한제어/Thymeleaf)로 교체 |
| 7-1 | UserDetails default 시점 | ✅ 완료 | security설명.md:208~209 → "Spring Security 6.1(= Boot 3.1)부터"로 정정 |
| 7-2 | getAuthorities 람다 → SimpleGrantedAuthority | ✅ 완료 | CustomUserAccount.java:5,71 (import+구현), 문서 security설명.md:197 동기화 |
| 7-3 | 회원가입 이메일 빈 문자열 | ✅ 완료 | mypage.html:139~140 → `#strings.isEmpty(user.email)` 사용 |

**전반적으로 반영 품질이 높습니다.** 특히 #4를 코드 삭제가 아니라 "실무는 이렇게, 예제는 학습용이라 반대"라는 설명 추가로 처리한 판단이 적절하고, 이미지도 원본 유지 후 복사 방식으로 안전하게 처리했습니다.

---

## 🟠 신규-A. (1차 #3 잔여) `프로젝트_구조_정리.md:78` 클래스명 1줄 누락

**위치**: `docs/spring_ssr_security/프로젝트_구조_정리.md:78`

```
로그인 흐름 (Security가 자동 처리)
  ↓ CustomUserDetailsService.loadUserByUsername(username) 자동 호출   ← 정상 (Service 클래스 맞음)
  ↓ DB에서 사용자 조회 → CustomUserDetails로 감싸서 반환            ← ❌ 여기가 누락
```

이 줄의 "**CustomUserDetails**로 감싸서 반환"은 서비스가 아니라 **감싸는 객체(UserDetails 구현체)**를 가리키므로 실제 클래스명인 **`CustomUserAccount`**여야 합니다.
1차작업 보고서(01_문서수정_상세.md#3)가 치환 대상 라인을 "49,65,69,70,95,97,112"로 열거했는데, **라인 78이 목록에서 빠졌습니다.** (Service 클래스명과 헷갈려 제외된 것으로 보이나, 여기는 Service가 아님)

**수정 제안**:
```diff
-  ↓ DB에서 사용자 조회 → CustomUserDetails로 감싸서 반환
+  ↓ DB에서 사용자 조회 → CustomUserAccount로 감싸서 반환
```

> 참고: 전체 재검색 결과, `CustomUserDetailsService`가 아닌 순수 `CustomUserDetails` 잔존은 **이 1곳이 유일**합니다. (security설명.md에는 없음)

---

## ⚪ 신규-B. (경미·선택) index.html "관리자" 링크가 모든 사용자에게 노출

**위치**: `spring_ssr_security/src/main/resources/templates/index.html:54~57`

1차작업에서 헤더에 `/admin`(관리자) 링크를 새로 추가했는데, `sec:authorize` 분기 없이 **항상 노출**됩니다.
- 비로그인 사용자가 클릭 → `/login` 리다이렉트 (자연스러움, 문제 없음)
- **일반 USER가 클릭 → 403 Forbidden (Whitelabel 에러 페이지)** — 초보자가 흰 에러 화면을 보고 당황할 수 있음

동작상 버그는 아니며 "권한 제어가 실제로 막는다"를 보여주는 데모로 볼 수도 있습니다. 다만 더 깔끔하게 하려면 ADMIN에게만 노출하는 것을 고려:

```html
<!-- 관리자 링크는 ADMIN 권한일 때만 노출 -->
<a th:if="${#authorization.expression('hasAuthority(''ADMIN'')')}" href="/admin" class="nav-item"> ... </a>
<!-- 또는 sec 태그 -->
<th:block sec:authorize="hasAuthority('ADMIN')">
  <a href="/admin" class="nav-item"> ... </a>
</th:block>
```

> 우선순위 낮음. 마이페이지(/mypage) 링크는 로그인만 하면 USER·ADMIN 모두 접근 가능하므로 그대로 두어도 무방합니다.

---

## ⏸ 보류 항목 확인 (1차 #6 일부) — 판단 타당

작업자가 **보류**한 미사용 보일러플레이트(QueryDSL 설정/의존성, `application.yml`의 파일 업로드·`api.service-key`, aop/validation, `@EnableScheduling`)는 현재 그대로 남아있습니다. 재확인 결과:

- **보류 판단은 타당합니다.** 삭제 시 `build.gradle` ↔ `QuerydslConfig` ↔ `sourceSets` ↔ QClass가 연쇄로 얽혀 빌드가 깨질 위험이 있고, 동작에는 지장이 없습니다.
- 다만 `application.yml:87`의 `api.service-key: ${API_SERVICE_KEY}`는 남아 있어도 **앱 구동을 막지는 않습니다** (어떤 `@Value`/`@ConfigurationProperties`에도 바인딩되지 않는 순수 커스텀 프로퍼티라, `.env`가 없어도 placeholder를 즉시 해석하지 않음). 즉 **지금 당장의 위험은 없음.**
- `application.yml:80~81` 파일 업로드 주석의 경로가 여전히 `spring_ssr/uploads`(이 프로젝트명 아님)로 되어 있음 — 잔재 정리 시 함께 손보면 됨.

→ **권장**: 1차작업 보고서 제안대로, 이 잔재 일괄 정리는 사용자 확인 후 별도 세션에서 진행. (2차 검수에서도 "지금 강제로 지울 필요는 없음"으로 동의)

---

## 종합 의견

- **필수 조치는 신규-A 1건뿐**입니다 (문서 1줄). 반영하면 1·2차 지적이 사실상 모두 정리됩니다.
- 신규-B는 선택, 보일러플레이트 정리는 사용자 확인 후 별도 진행이면 충분합니다.
- **미검증 사항**: 이 검수 환경에 JDK가 없어 `./gradlew compileJava` / 테스트 실행은 못 했습니다. `SimpleGrantedAuthority` import 및 test yml은 정적 검토상 정확하나, 로컬에서 **빌드 1회 + `contextLoads()` 테스트 1회**(MariaDB `test` 필요) 확인을 권장합니다.

### 우선순위
1. **바로 수정**: 신규-A (프로젝트_구조_정리.md:78 한 줄)
2. **여유 시**: 신규-B (index.html 관리자 링크 분기)
3. **사용자 확인 후 별도 세션**: 보일러플레이트 잔재 정리
