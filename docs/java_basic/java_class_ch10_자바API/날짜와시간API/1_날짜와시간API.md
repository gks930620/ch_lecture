# 날짜와 시간 API (java.time)

## 학습 목표
- `java.time` 핵심 타입(`LocalDateTime`, `Instant`, `ZoneId`)의 역할을 구분할 수 있다.
- 날짜/시간 계산, 포맷팅/파싱, 타임존 변환을 안전하게 처리할 수 있다.
- 서비스 설계에서 시간값 저장/표시 전략을 정할 수 있다.

---

## 1. 왜 `java.time`이 필요한가

기존 `Date`, `Calendar`는 다음 문제가 있었다.

1. 가변 객체로 인한 버그 위험
2. API 사용성/가독성 부족
3. 타임존 처리 복잡

`java.time`은 불변 객체 기반으로 설계되어 안전성과 일관성이 높다.

---

## 2. 핵심 타입 지도

주요 타입:
- `LocalDate`: 날짜(년-월-일)
- `LocalTime`: 시각(시-분-초-나노)
- `LocalDateTime`: 날짜+시각(타임존 없음)
- `Instant`: UTC 기준 절대 시점
- `ZonedDateTime`: 타임존 포함 날짜/시각
- `Period`: 날짜 단위 기간
- `Duration`: 시간 단위 간격

![java.time 타입 지도]({{ '/assets/images/java_basic/ch10/java-time-type-map.svg' | relative_url }})

---

## 3. 객체 생성과 현재 시각

```java
LocalDate date = LocalDate.now();
LocalTime time = LocalTime.now();
LocalDateTime dt = LocalDateTime.now();
Instant instant = Instant.now();
```

특정 값 생성:

```java
LocalDate birth = LocalDate.of(2000, 5, 10);
LocalDateTime meeting = LocalDateTime.of(2026, 4, 20, 14, 30);
```

---

## 4. 날짜/시간 계산

불변 객체이므로 계산 결과는 새 객체로 반환된다.

```java
LocalDate today = LocalDate.now();
LocalDate nextWeek = today.plusWeeks(1);
LocalDate prevMonth = today.minusMonths(1);
```

기간 계산:

```java
long days = ChronoUnit.DAYS.between(startDate, endDate);
Period p = Period.between(startDate, endDate);
Duration d = Duration.between(startTime, endTime);
```

---

## 5. 포맷팅과 파싱

```java
DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
String text = LocalDateTime.now().format(f);
LocalDateTime parsed = LocalDateTime.parse("2026-04-09 10:30:00", f);
```

주의:
- 패턴 문자 의미를 정확히 구분 (`MM` 월, `mm` 분)
- 입력 형식이 다르면 `DateTimeParseException`

---

## 6. 타임존 처리

`LocalDateTime`은 타임존 정보가 없다.  
서버/클라이언트가 다른 지역이면 `Instant` + `ZoneId` 기준 설계가 안전하다.

```java
Instant nowUtc = Instant.now();
ZoneId seoul = ZoneId.of("Asia/Seoul");
ZonedDateTime seoulTime = nowUtc.atZone(seoul);
```

전략 권장:
1. 저장: `Instant`(UTC)
2. 표시: 사용자 `ZoneId`로 변환

---

## 7. 실무 패턴

1. DB에는 UTC 시점 저장
2. API 응답은 ISO-8601 권장
3. 테스트에서 `Clock` 주입으로 시간 고정

```java
Clock fixed = Clock.fixed(Instant.parse("2026-04-09T00:00:00Z"), ZoneOffset.UTC);
LocalDate today = LocalDate.now(fixed);
```

---

## 8. 자주 하는 실수

1. `LocalDateTime.now()`를 여기저기 직접 호출해 테스트 불가
2. 타임존 없는 시간으로 글로벌 서비스 설계
3. 문자열 포맷을 비즈니스 로직 중간에서 반복 변환
4. `Period`/`Duration`을 혼동

---

## 9. 정리

- `java.time`은 불변 기반의 표준 시간 API다.
- 시간값은 "절대 시점(Instant)"과 "표시 시각(ZonedDateTime)"을 구분해 설계해야 한다.
- 포맷팅/파싱/타임존 전략을 초기에 정하면 운영 이슈를 크게 줄일 수 있다.

