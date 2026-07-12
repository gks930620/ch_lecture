# spring_ssr_security 1차 검수 보고서

- 검수일: 2026-07-05
- 대상: `docs/spring_ssr_security/*` 문서 + `spring_ssr_security/src/**` 소스코드
- 기준: **처음 개발을 시작하는 사람**이 보는 강의. 설명이 실제 코드/기술과 맞는지, 이상한 점이 없는지.
- 참고: 검수자도 틀릴 수 있으니 각 항목에 **근거(파일:라인)**를 붙였습니다. 작업자가 판단해서 맞는 것만 반영하면 됩니다.

---

## 요약 (심각도순)

| # | 심각도 | 항목 | 위치 |
|---|--------|------|------|
| 1 | 🔴 치명 | BCrypt "자동 디코딩" 은 틀린 설명 (단방향 해시라 디코딩 불가) | security설명.md:15 |
| 2 | 🔴 중요 | 문서가 참조하는 이미지 8개가 docs 폴더에 없음 (전부 깨짐) | security설명.md:99~272 |
| 3 | 🟠 중요 | 클래스명 불일치: 문서 `CustomUserDetails` ↔ 실제 `CustomUserAccount` | 프로젝트_구조_정리.md:65~112 |
| 4 | 🟠 권고 | `anyRequest().permitAll()` — Security 강의에서 위험한 습관 (fail-open) | SecurityConfig.java:44 |
| 5 | 🟡 잔재 | 테스트 설정이 다른 프로젝트(doll_gacha) 것 → 테스트 깨짐 | src/test/resources/application.yml |
| 6 | 🟡 잔재 | 강의 범위 밖 미사용 보일러플레이트 다수 (QueryDSL, 파일업로드, API 등) | 여러 파일 |
| 7 | ⚪ 사소 | UserDetails default 메서드 시점, getAuthorities 람다 스타일 등 | 아래 상세 |

---

## 🔴 1. BCrypt "자동 디코딩" 은 틀린 설명 (반드시 수정)

**위치**: `docs/spring_ssr_security/security설명.md:15`

```java
@Bean  // 비밀번호를 BCrypt로 인코딩해서 DB에 저장, 비교 시 자동 디코딩
public PasswordEncoder passwordEncoder() {
```

**문제**: BCrypt는 **단방향 해시 함수**라 원래 비밀번호로 되돌리는 **"디코딩"이 존재하지 않습니다.**
로그인 시 Security는 저장된 해시를 디코딩하는 게 아니라, 입력한 비밀번호를 **같은 salt로 다시 해시해서 두 해시값을 비교**(`matches()`)합니다.
초보자에게 "디코딩"이라고 하면 "저장된 해시를 평문으로 복원한다"는 잘못된 개념이 박힙니다.

**근거 (같은 프로젝트 안에서 코드 주석은 올바르게 설명하고 있음)** — `SecurityConfig.java:23`:
```java
// BCrypt: 단방향 해시 함수 — 같은 비밀번호도 매번 다른 해시값 생성 (salt 내장)
// 회원가입 시 encode(), 로그인 시 matches()를 Security가 자동 호출
```
즉 **코드 주석은 맞고 문서만 틀린** 상황이라 문서를 코드 주석 수준으로 맞추면 됩니다.

**수정 제안**:
> `// 회원가입 시 encode()로 해시 저장, 로그인 시 matches()로 해시 비교 (단방향 — 복호화 불가)`

---

## 🔴 2. 문서가 참조하는 이미지가 docs 폴더에 없음 (전부 깨짐)

**위치**: `security설명.md` 의 이미지 8개 (`security1.png` ~ `security8.png`), 예: 라인 99, 110, 112, 116, 258, 260, 264, 272

문서는 `./seucirty 이미지/security1.png` 형태로 참조하는데, **`docs/spring_ssr_security/` 아래에 `seucirty 이미지` 폴더가 없습니다.**
실제 이미지는 소스 쪽 설계 폴더에 있습니다:

```
있는 곳:  spring_ssr_security/설계/seucirty 이미지/security1~8.png
찾는 곳:  docs/spring_ssr_security/seucirty 이미지/  ← 없음
```

로그인 흐름도(`security2·3·5·6·7.png`)와 SecurityContextHolder 구조도(`security8.png`)는 이 문서에서 **"핵심"이라고 강조한 자료**인데 지금 문서 사이트에서는 전부 깨져 보입니다.

**참고**: 최근 커밋에서 `java_basic` 은 이미지를 `java_basic_images/` 로 docs 안에 정리했습니다(현재 git status에 반영됨). spring_ssr_security 도 같은 방식으로 이미지를 docs 폴더 안으로 복사하고 경로를 맞춰야 합니다.

**추가**: 폴더명 오타 `seucirty`(→ `security`)도 정리하는 김에 바로잡는 것을 권장합니다.

---

## 🟠 3. 클래스명 불일치: 문서 `CustomUserDetails` ↔ 실제 코드 `CustomUserAccount`

**위치**: `docs/spring_ssr_security/프로젝트_구조_정리.md` 라인 65, 66, 95, 97, 112

프로젝트_구조_정리.md 는 계속 **`CustomUserDetails`** 로 부릅니다:
```java
// 프로젝트_구조_정리.md:97
public String mypage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
```

하지만 실제 클래스명은 **`CustomUserAccount`** 입니다 (`user/CustomUserAccount.java`, `UserController.java:82`).
다행히 `security설명.md` 는 `CustomUserAccount` 로 올바르게 씁니다 → **두 문서 사이에서도 이름이 엇갈립니다.**

초보자는 문서 보고 `CustomUserDetails` 를 찾다가 없어서 헤맵니다. **프로젝트_구조_정리.md 전체를 `CustomUserAccount` 로 통일**하세요.

---

## 🟠 4. `anyRequest().permitAll()` — Security 강의에서는 위험한 기본값

**위치**: `SecurityConfig.java:44`, 문서 `security설명.md:27`, `프로젝트_구조_정리.md:90`

```java
.anyRequest().permitAll()   // 나머지는 허용
```

동작 자체는 정상입니다. 다만 **"명시하지 않은 모든 URL을 누구에게나 허용"**(fail-open)은 실무에서 사고가 나는 대표 패턴이라, 하필 **Security 강의**에서 초보자에게 이 습관을 심는 건 아쉽습니다.

실무 권장은 **화이트리스트 방식(fail-close)**:
```java
.anyRequest().authenticated()   // 명시 안 한 URL은 로그인 필수 (안전한 기본값)
```

이 데모는 대부분 공개 페이지라 `permitAll` 이 의도된 것일 수 있습니다. 그렇다면 문서에 **한 줄이라도** 짚어주는 걸 권장합니다:
> "실무에서는 보통 `anyRequest().authenticated()`로 두고 공개 URL만 `permitAll()`로 여는 화이트리스트 방식을 씁니다. 이 예제는 학습용이라 반대로 열어둔 것."

(우선순위는 낮음 — 내용이 틀린 건 아니고 교육적 보완 성격)

---

## 🟡 5. 테스트 설정이 다른 프로젝트(doll_gacha) 것 → 테스트가 깨짐

**위치**: `spring_ssr_security/src/test/resources/application.yml`

이 파일 내용이 이 프로젝트와 전혀 관계없는 **doll_gacha** 프로젝트 것입니다:
- DB: `doll_gacha` / 계정·비번도 다름
- `data-dollshop.sql`, `data-files.sql`, `data-review.sql` 참조 → **이 프로젝트에 그 파일들이 없음**
- OAuth2 kakao, JWT 설정 → 이 프로젝트에 관련 코드 없음

결과적으로 `DemoApplicationTests.contextLoads()` 가 (SQL 초기화 실패로) **깨질 가능성이 높습니다.**
이 프로젝트용으로 test yml을 교체하거나(예: H2 인메모리 + data-users.sql만), 최소한 존재하지 않는 SQL 참조는 제거해야 합니다.

---

## 🟡 6. 강의 범위 밖 미사용 보일러플레이트 (동작엔 지장 없지만 초보자 혼란)

이 프로젝트의 주제는 **"Spring Security 세션 로그인"** 인데, 다른 템플릿에서 복사돼 온 무관한 요소가 많습니다. 동작은 하지만 "처음 개발하는 사람"에게는 "이건 왜 있지?" 하는 노이즈입니다.

| 잔재 | 위치 | 비고 |
|------|------|------|
| QueryDSL 설정/의존성 | `QuerydslConfig.java`, `build.gradle:33~35,57~65` | 이 프로젝트에 QueryDSL 사용처 **0** (UserRepository는 순수 JpaRepository) |
| 파일 업로드 설정 | `application.yml:9~14, 79~81` | MultipartFile 쓰는 코드 없음. 주석 경로도 `spring_ssr/uploads` 로 잘못됨(이 프로젝트명 아님) |
| 공공데이터 API 키 | `application.yml:83~87` (`api.service-key`) | 사용처 없음. `.env` 없으면 혼란 |
| aop, validation 의존성 | `build.gradle:44~45` | 사용처 없음 |
| `@EnableScheduling` | `DemoApplication.java` | 스케줄러 없음 |
| `index.html` 링크/카드 | `index.html:50,54,82,88~107` | `/community`·`/api/test` → 404. 카드가 게시판·파일업로드·QueryDSL 등 **없는 기능** 소개 |

**권고**: 강의 범위에 맞게 정리(제거)하거나, 최소한 문서/주석에서 "이건 보일러플레이트라 이번 강의와 무관"이라고 안내해 주세요. 특히 `index.html` 의 "커뮤니티/API 테스트" 링크는 클릭하면 흰색 에러(Whitelabel)라 초보자가 당황합니다.

---

## ⚪ 7. 사소/개선 (반영은 선택)

1. **UserDetails default 메서드 시점** — `security설명.md:199~201`
   > "isAccountNonExpired ... → Spring Boot 3부터 default 메소드로 제공"
   정확히는 **Spring Security 6.1(= Spring Boot 3.1)부터** 추가됐습니다. Boot 3.0(Security 6.0)엔 없었습니다. 이 프로젝트는 Boot 3.3.4라 해당되어 **코드는 정상 컴파일**되지만, "3.1부터"로 적으면 더 정확합니다.

2. **`getAuthorities()` 의 람다** — `CustomUserAccount.java:68`
   ```java
   collection.add(() -> role);   // 동작은 정상
   ```
   초보자에게는 `new SimpleGrantedAuthority(role)` 가 더 명확하고 관례적입니다. 문서(security설명.md:188)에서 "람다로 GrantedAuthority 구현"이라 설명은 해두었으니 치명적이진 않지만, 강의라면 관례형을 권합니다.

3. **회원가입 이메일 빈 값 처리** — `mypage.html:139`
   `signup` 에서 email은 선택인데, 폼이 빈 문자열 `""` 을 보내면 null이 아니라서 mypage에서 "설정되지 않음"이 안 뜨고 빈칸이 보입니다. 아주 사소한 UX 이슈.

4. **`data-users.sql` 의 수동 DROP/CREATE** — `ddl-auto: create` 로 Hibernate가 만든 테이블을 SQL이 다시 drop/create 합니다. 동작하지만 초보자에겐 "왜 두 번 만들지?" 혼란 요소. 주석(1~2줄)에 이유(이전 스키마 잔재 제거용)를 이미 적어두긴 했습니다.

---

## 잘 되어 있는 점 (참고)

- `hasAuthority("ADMIN")` 사용이 코드·문서 **일관**됩니다. (roles를 `ROLE_` 접두어 없이 저장하므로 `hasRole` 이 아니라 `hasAuthority` 가 맞음 — 흔한 실수인데 잘 맞췄습니다.)
- `SessionUserDTO` 에서 password를 의도적으로 제외하고, Entity를 세션에 직접 넣지 않는 설계 설명이 명확합니다.
- 로그인 흐름(필터 → loadUserByUsername → matches → 세션 저장) 설명 순서가 정확합니다.
- SecurityContextHolder / ThreadLocal / 세션 복사 설명이 기술적으로 맞습니다.
- 코드 주석의 밀도와 초보자 친화도가 전반적으로 좋습니다.

---

## 작업자에게: 우선순위 제안

1. **먼저 고칠 것**: #1(BCrypt 디코딩 문구), #2(이미지 경로), #3(클래스명 통일) — 명백한 오류/깨짐.
2. **그 다음**: #5(테스트 yml), #6(index.html 죽은 링크만이라도).
3. **여유 되면**: #4(anyRequest 보완 설명), #7 사소 항목.
