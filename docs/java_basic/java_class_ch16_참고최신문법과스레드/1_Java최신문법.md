# Java 최신 문법 개요

## 학습 목표
- 최근 Java 문법(`var`, `record`, switch expression, pattern matching)의 의도를 이해할 수 있다.
- 새 문법을 "짧은 코드"가 아니라 "명확한 코드" 관점으로 적용할 수 있다.
- 프로젝트 버전 호환성과 팀 코드 규칙을 함께 고려해 도입할 수 있다.

---

## 1. 최신 문법을 보는 관점

최신 문법 도입의 목적은 생산성보다 **의도 전달력 향상**이다.

1. 불필요한 보일러플레이트 제거
2. 오류 가능성 낮춘 안전한 문법 제공
3. 데이터/분기 처리 표현력 개선

![현대 Java 기능 지도]({{ '/assets/images/java_basic/ch16/modern-java-feature-map.svg' | relative_url }})

---

## 2. `var` (지역 변수 타입 추론)

```java
var name = "Kim";   // String
var count = 10;     // int
```

장점:
- 중복 타입 선언 감소

주의:
- 타입이 명확하지 않은 코드는 오히려 가독성 저하
- 필드/매개변수 타입에는 사용 불가

---

## 3. `record` (데이터 중심 타입)

```java
public record User(long id, String name) {}
```

자동 생성:
- 생성자
- getter(컴포넌트 접근자)
- `equals/hashCode/toString`

적합한 경우:
- 불변 데이터 전달 객체(DTO, 응답 모델)

---

## 4. switch 표현식

```java
String grade = switch (score / 10) {
    case 10, 9 -> "A";
    case 8 -> "B";
    default -> "C";
};
```

장점:
- 값 반환 구조 명확
- fall-through 실수 감소

---

## 5. 패턴 매칭

### 5.1 `instanceof` 패턴

```java
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

### 5.2 switch 패턴(지원 버전 기준)

타입/패턴에 따른 분기를 간결하게 작성 가능.

---

## 6. 텍스트 블록

멀티라인 문자열 작성 가독성 향상.

```java
String sql = """
    SELECT id, name
    FROM users
    WHERE status = 'ACTIVE'
    """;
```

SQL/JSON/HTML 템플릿 처리에 유용하다.

---

## 7. 컬렉션 팩토리 메소드

```java
List<String> list = List.of("A", "B");
Map<String, Integer> map = Map.of("A", 1, "B", 2);
```

불변 컬렉션을 간결하게 생성 가능.

---

## 8. 도입 전략

1. 프로젝트 JDK와 런타임 버전 일치 확인
2. 팀 합의된 코딩 컨벤션 수립
3. 파일 단위로 점진 도입
4. 코드리뷰에서 가독성 이득 검증

---

## 9. 자주 하는 실수

1. `var` 남용으로 타입 의도 숨김
2. switch 표현식을 기존 switch 습관으로 작성해 복잡화
3. record를 엔티티/가변 객체에 무리하게 적용
4. 버전 호환성 확인 없이 문법 사용

---

## 10. 정리

- 최신 문법은 문법 자체보다 설계 의도 전달을 위해 도입해야 한다.
- 프로젝트 표준 버전과 팀 가독성 규칙이 먼저다.
- "짧은 코드"보다 "명확하고 안전한 코드"가 도입 기준이다.

