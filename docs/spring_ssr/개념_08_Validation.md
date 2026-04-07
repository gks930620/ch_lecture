# Validation (@Valid)

> ⚠️ 이 프로젝트의 게시글 작성/수정은 현재 `@RequestParam`으로 개별 파라미터를 받고 있음 (파일 업로드와 함께 처리하기 위해).  
> 아래는 **@Valid + DTO 바인딩 방식의 정석적인 사용법** 정리.

---

## 1. 의존성

```groovy
implementation 'org.springframework.boot:spring-boot-starter-validation'
```

---

## 2. DTO에 제약 조건 선언

```java
// CommunityCreateDTO.java
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityCreateDTO {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 200, message = "제목은 200자 이하로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
```

---

## 3. Controller에서 @Valid 적용

```java
@PostMapping("/write")
public String write(@Valid @ModelAttribute CommunityCreateDTO createDTO,
                    BindingResult bindingResult) { // 반드시 @Valid 바로 다음에 위치

    if (bindingResult.hasErrors()) {
        return "community/write"; // 검증 실패 → 다시 폼으로
    }

    Long id = communityService.createCommunity(createDTO, null);
    return "redirect:/community/" + id;
}
```

> `BindingResult`가 없으면 검증 실패 시 바로 예외 발생  
> `BindingResult`가 있으면 직접 처리 가능

---

## 4. Thymeleaf에서 오류 메시지 표시

```html
<form th:action="@{/community/write}" method="post" th:object="${communityCreateDTO}">

    <div class="form-group">
        <label>제목</label>
        <input type="text" th:field="*{title}">
        <!-- 검증 실패 시 오류 메시지 표시 -->
        <span th:if="${#fields.hasErrors('title')}"
              th:errors="*{title}"
              style="color:red;">
        </span>
    </div>

    <div class="form-group">
        <label>내용</label>
        <textarea th:field="*{content}"></textarea>
        <span th:if="${#fields.hasErrors('content')}"
              th:errors="*{content}"
              style="color:red;">
        </span>
    </div>

    <button type="submit">저장</button>
</form>
```

---

## 5. 주요 애노테이션

| 애노테이션 | 설명 | 예시 |
|-----------|------|------|
| `@NotNull` | null 불가 | 숫자, 날짜 등 |
| `@NotBlank` | null, 빈 문자열, 공백만 불가 | String |
| `@NotEmpty` | null, 빈 값 불가 (공백은 허용) | String, Collection |
| `@Size` | 길이/크기 범위 | `@Size(min=2, max=200)` |
| `@Min` | 최솟값 | `@Min(0)` |
| `@Max` | 최댓값 | `@Max(100)` |
| `@Email` | 이메일 형식 | `@Email` |
| `@Pattern` | 정규식 | `@Pattern(regexp = "...")` |
| `@Positive` | 양수 | `@Positive` |

