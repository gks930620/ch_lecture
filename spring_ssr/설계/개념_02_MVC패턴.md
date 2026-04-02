# Spring MVC 패턴 (SSR)

---

## 1. MVC란?

| 역할 | 담당 | 설명 |
|------|------|------|
| **M** (Model) | `Model`, DTO, Entity | 화면에 전달할 데이터 |
| **V** (View) | Thymeleaf (.html) | 화면 렌더링 |
| **C** (Controller) | `@Controller` | 요청 처리, 모델에 데이터 담기 |

---

## 2. 요청 흐름 (간단 버전)

```
브라우저 요청 (GET /community)
    ↓
DispatcherServlet (Spring이 모든 요청을 여기서 받음)
    ↓
CommunityController.list()
    ↓
CommunityService.getCommunityList()  (비즈니스 로직)
    ↓
Model에 데이터 담기
    ↓
"community/list" 뷰 이름 반환
    ↓
Thymeleaf가 list.html 렌더링
    ↓
완성된 HTML을 브라우저에 응답
```

---

## 2-1. Spring MVC 내부 동작 (상세 버전)

> 면접에서도 자주 나오는 "그 유명한 그림"

```
                          Spring MVC 내부
    ┌──────────────────────────────────────────────────────┐
    │                                                      │
    │  ① 요청        ② 핸들러 조회       ③ 핸들러 호출     │
    │  ──────→ DispatcherServlet ──→ HandlerMapping        │
    │          (Front Controller)     "이 URL은 어떤       │
    │                │                 Controller?"         │
    │                │                    │                 │
    │                │         ┌──────────┘                 │
    │                ▼         ▼                            │
    │           HandlerAdapter ──→ Controller               │
    │           "이 핸들러를       (실제 비즈니스 로직)      │
    │            실행해줘"            │                      │
    │                │               │ ④ ModelAndView 반환  │
    │                │←──────────────┘   (뷰 이름 + 데이터) │
    │                │                                      │
    │                ▼ ⑤ 뷰 이름으로 실제 뷰 찾기           │
    │           ViewResolver                                │
    │           "community/list"                            │
    │              → templates/community/list.html          │
    │                │                                      │
    │                ▼ ⑥ 뷰 렌더링                          │
    │             View (Thymeleaf)                          │
    │             Model 데이터 + HTML 템플릿 → 완성된 HTML   │
    │                │                                      │
    └────────────────│──────────────────────────────────────┘
                     ▼ ⑦ 응답
                  브라우저 (완성된 HTML 수신)
```

### 단계별 설명

| 단계 | 구성요소 | 역할 |
|------|---------|------|
| ① | **DispatcherServlet** | 모든 HTTP 요청의 진입점 (Front Controller 패턴) |
| ② | **HandlerMapping** | URL → 어떤 Controller의 어떤 메서드? 매핑 정보 조회 |
| ③ | **HandlerAdapter** | 찾은 Controller 메서드를 실행 (`@GetMapping` 등 처리) |
| ④ | **Controller** | 비즈니스 로직 수행, Model에 데이터 담기, **뷰 이름 반환** |
| ⑤ | **ViewResolver** | 뷰 이름(`"community/list"`) → 실제 파일 경로 변환 |
| ⑥ | **View** | Model 데이터를 HTML 템플릿에 넣어 렌더링 |
| ⑦ | **응답** | 완성된 HTML을 브라우저에 전송 |

### ViewResolver 설정 (자동)

```yaml
# application.yml - Spring Boot가 자동 설정해줌
spring:
  thymeleaf:
    prefix: classpath:/templates/   # ← 뷰 이름 앞에 붙는 경로
    suffix: .html                   # ← 뷰 이름 뒤에 붙는 확장자
```

```
Controller가 반환: "community/list"
ViewResolver 변환: classpath:/templates/ + community/list + .html
실제 파일 경로:    src/main/resources/templates/community/list.html
```

### @Controller vs @ResponseBody (= @RestController)

```
@Controller (SSR)
    Controller → 뷰 이름 반환 → ViewResolver → View 렌더링 → HTML 응답

@ResponseBody (REST API)
    Controller → 객체 반환 → HttpMessageConverter → JSON 응답
    ※ ViewResolver를 거치지 않음!
```

```java
// SSR: ViewResolver 사용
@Controller
public class CommunityController {
    @GetMapping("/community")
    public String list(Model model) {
        return "community/list";  // → ViewResolver → HTML
    }
}

// REST API: ViewResolver 사용 안 함
@RestController  // = @Controller + @ResponseBody
public class CommentApiController {
    @GetMapping("/api/comments")
    public List<CommentDTO> list() {
        return commentService.getComments();  // → JSON 직접 반환
    }
}
```

> 이 프로젝트에서 **게시글(SSR)**은 `@Controller` + ViewResolver,  
> **댓글 API**는 `@RestController` + JSON 응답으로 두 방식을 모두 사용 중

---

## 3. Controller 핵심 애노테이션

```java
@Controller               // Bean 등록 + 뷰 반환
@RequestMapping("/community")  // 공통 URL prefix
@RequiredArgsConstructor
public class CommunityController {

    @GetMapping            // GET /community
    public String list(@RequestParam(defaultValue = "0") int page,
                        Model model) {
        // ...
        model.addAttribute("communities", communities); // 뷰에 데이터 전달
        return "community/list"; // templates/community/list.html
    }

    @PostMapping("/write") // POST /community/write
    public String write(@ModelAttribute CommunityCreateDTO dto) {
        // ...
        return "redirect:/community"; // 리다이렉트
    }

    @GetMapping("/{id}")   // GET /community/1
    public String detail(@PathVariable Long id, Model model) {
        // ...
        return "community/detail";
    }
}
```

### 주요 애노테이션 정리

| 애노테이션 | 설명 |
|-----------|------|
| `@GetMapping` | GET 요청 처리 |
| `@PostMapping` | POST 요청 처리 |
| `@PathVariable` | URL 경로 변수 `/community/{id}` |
| `@RequestParam` | 쿼리 파라미터 `?page=0&keyword=` |
| `@ModelAttribute` | form 데이터를 DTO로 바인딩 |
| `@ResponseBody` | 뷰 대신 데이터(JSON 등) 직접 반환 |

---

## 4. Model - 뷰에 데이터 전달

```java
@GetMapping
public String list(Model model) {
    Page<CommunityDTO> communities = communityService.getCommunityList(...);

    model.addAttribute("communities", communities);  // 키-값으로 전달
    model.addAttribute("searchType", searchType);
    return "community/list";
}
```

Thymeleaf에서 꺼내 쓰기:
```html
<!-- th:each로 반복 -->
<tr th:each="post : ${communities.content}">
    <td th:text="${post.title}">제목</td>
</tr>

<!-- th:if로 조건 -->
<button th:if="${session.loginUser != null}">글쓰기</button>
```

---

## 5. Thymeleaf 주요 문법

| 문법 | 설명 | 예시 |
|------|------|------|
| `th:text` | 텍스트 출력 | `th:text="${post.title}"` |
| `th:if` | 조건부 렌더링 | `th:if="${session.loginUser != null}"` |
| `th:each` | 반복 | `th:each="post : ${posts}"` |
| `th:href` | 링크 | `th:href="@{/community/{id}(id=${post.id})}"` |
| `th:action` | form action | `th:action="@{/community/write}"` |
| `th:value` | input 값 | `th:value="${keyword}"` |
| `th:classappend` | 클래스 추가 | `th:classappend="${active} ? 'active'"` |
| `${session.xxx}` | 세션 접근 | `${session.loginUser.nickname}` |
| `#temporals` | 날짜 포맷 | `${#temporals.format(date, 'yyyy-MM-dd')}` |

---

## 6. SSR vs CSR 비교

| | SSR (이 프로젝트) | CSR (REST API) |
|--|------------------|----------------|
| 응답 | 완성된 HTML | JSON 데이터 |
| 렌더링 위치 | 서버 | 브라우저 (React, Vue) |
| 뷰 기술 | Thymeleaf | 없음 |
| Controller 반환 | 뷰 이름 (String) | 데이터 (`@ResponseBody`) |
| 장점 | 초기 로딩 빠름, SEO 유리 | 화면 전환 부드러움 |

---

## 7. redirect vs forward

```java
// redirect - 브라우저가 새로운 GET 요청을 보냄 (URL 변경됨)
return "redirect:/community";        // /community로 이동
return "redirect:/community/" + id; // /community/1로 이동

// forward - 서버 내부에서 뷰로 이동 (URL 변경 안 됨)
return "community/list";  // 그냥 뷰 이름 반환 = forward
```

> POST 후에는 반드시 `redirect` → **PRG 패턴 (Post-Redirect-Get)**  
> 새로고침 시 중복 제출 방지

