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
// CommunityService.java (일부 발췌·단순화 — 실제 getCommunityDetail은 @Transactional + 조회수 증가 포함)
public CommunityDTO getCommunityDetail(Long communityId) {
    CommunityEntity community = communityRepository.findById(communityId)
            .orElseThrow(() -> EntityNotFoundException.of("게시글", communityId));
            // 없으면 EntityNotFoundException 발생 → 404

    return CommunityDTO.from(community);
}

public void updateCommunity(Long communityId, CommunityUpdateDTO dto, String username) {
    CommunityEntity community = communityRepository.findById(communityId)
            .orElseThrow(() -> EntityNotFoundException.of("게시글", communityId));

    // 비로그인(username == null)이거나 작성자 본인이 아니면 → 403
    if (username == null || !community.isWrittenBy(username)) {
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
// 이 프로젝트에서는 SSR 예외를 GlobalExceptionHandler가,
// API 예외를 ApiExceptionHandler가 나눠 처리하도록 구성
@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {

    // 404 - 리소스를 찾을 수 없음
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(EntityNotFoundException e, Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("message", e.getMessage());
        return "error"; // error.html로 이동
    }

    // 403 - 접근 거부 (다른 사람의 글을 수정/삭제하려 할 때)
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException e, Model model) {
        model.addAttribute("status", 403);
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    // 400 - 비즈니스 규칙 위반
    // BusinessException의 하위 예외 중 위에서 먼저 매칭되지 않은 것들이 여기로 옴
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBusiness(BusinessException e, Model model) {
        model.addAttribute("status", 400);
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    // 500 - 예상치 못한 예외 (최후의 방어선)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e, Model model) {
        model.addAttribute("status", 500);
        model.addAttribute("message", "서버 내부 오류가 발생했습니다.");
        return "error";
    }
}
```

> **매칭 순서**: 예외 핸들러는 **구체적인 예외 타입부터 먼저 매칭**된다.  
> `EntityNotFoundException`은 `BusinessException`의 자식이지만 404 핸들러가 먼저 잡고,  
> 어떤 핸들러에도 매칭되지 않으면 최후의 `Exception` 핸들러(500)로 간다.
>
> ※ `DuplicateResourceException`(409)은 SSR 쪽에 전용 핸들러가 없어 `BusinessException` 핸들러로
> 잡히면서 **400 페이지로 표시**된다. (API 쪽 `ApiExceptionHandler`는 `e.getStatus()`를 그대로 쓰므로 409 정상 반환)
>
> ※ 참고: `@RestController`도 내부에 `@Controller`를 포함하므로
> `annotations = Controller.class`가 "@Controller**만** 잡는다"고 단정할 수는 없다.
> 엄밀하게 분리하려면 `basePackages` 옵션으로 패키지를 나누는 방법도 있다.

---

## 4. @ControllerAdvice vs @RestControllerAdvice

| | `@ControllerAdvice` | `@RestControllerAdvice` |
|--|---------------------|------------------------|
| 반환 | 뷰 이름 (SSR) | JSON 데이터 (REST API) |
| 적합 | SSR (Thymeleaf) | REST API |

```java
// REST API 방식 (일부 발췌 — 실제로는 403/BusinessException/500 핸들러도 있어 총 4개.
//   단 BusinessException 핸들러는 아래처럼 상태코드를 고정하지 않고 e.getStatus()를 그대로 사용해
//   409(중복) 등을 정상 반환한다 → 126행 각주 참고)
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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

