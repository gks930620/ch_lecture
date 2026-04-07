# 예외 처리

---

## 1. 커스텀 예외 구조 (이 프로젝트)

```
RuntimeException
    └── BusinessException (추상 클래스 - 공통 부모)
            ├── EntityNotFoundException  (404)
            ├── AccessDeniedException    (403)
            ├── BusinessRuleException    (400)
            └── DuplicateResourceException (409)
```

```java
// BusinessException.java - 공통 부모
@Getter
public abstract class BusinessException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;

    protected BusinessException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }
}

// EntityNotFoundException.java
public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "NOT_FOUND");
    }

    // 편의 메서드
    public static EntityNotFoundException of(String entityName, Long id) {
        return new EntityNotFoundException(entityName + "을(를) 찾을 수 없습니다: " + id);
    }
}
```

---

## 2. Service에서 예외 던지기

```java
// CommunityService.java
public CommunityDTO getCommunityDetail(Long id) {
    CommunityEntity community = communityRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.of("게시글", id));
            // 없으면 EntityNotFoundException 발생 → 404

    return CommunityDTO.from(community);
}

public void updateCommunity(Long id, CommunityUpdateDTO dto, String username) {
    CommunityEntity community = communityRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.of("게시글", id));

    if (username != null && !community.isWrittenBy(username)) {
        throw AccessDeniedException.forUpdate("게시글"); // 403
    }
    community.update(dto.getTitle(), dto.getContent());
}
```

---

## 3. @ControllerAdvice - 전역 예외 처리

모든 Controller에서 발생하는 예외를 한 곳에서 처리.

```java
// SSR 방식 - 에러 페이지로 이동
// annotations = Controller.class: @Controller에서 발생하는 예외만 잡음
// (@RestController 예외는 ApiExceptionHandler가 JSON으로 처리)
@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(EntityNotFoundException e, Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("message", e.getMessage());
        return "error"; // error.html로 이동
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e, Model model) {
        model.addAttribute("status", 500);
        model.addAttribute("message", "서버 내부 오류가 발생했습니다.");
        return "error";
    }
}
```

---

## 4. @ControllerAdvice vs @RestControllerAdvice

| | `@ControllerAdvice` | `@RestControllerAdvice` |
|--|---------------------|------------------------|
| 반환 | 뷰 이름 (SSR) | JSON 데이터 (REST API) |
| 적합 | SSR (Thymeleaf) | REST API |

```java
// REST API 방식
// annotations = RestController.class: @RestController에서 발생하는 예외만 잡음
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(404)
                .body(new ErrorResponse(404, "NOT_FOUND", e.getMessage()));
    }
}
```

---

## 5. 예외 처리 흐름

```
Service에서 EntityNotFoundException 발생
    ↓
Controller까지 전파 (try-catch 없으면 그냥 올라옴)
    ↓
@ControllerAdvice의 @ExceptionHandler(EntityNotFoundException.class) 가 받음
    ↓
SSR: error.html 렌더링
REST: JSON ErrorResponse 반환
```

