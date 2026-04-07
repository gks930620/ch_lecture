# 날짜와 시간 API (java.time)

## 학습 목표
- `java.time` 패키지의 핵심 타입을 이해한다.
- 날짜/시간 생성, 계산, 포맷팅 방식을 익힌다.
- 기존 `Date`/`Calendar` 대비 장점을 이해한다.

---

## 1. 핵심 클래스
- `LocalDate`: 날짜(년/월/일)
- `LocalTime`: 시간(시/분/초)
- `LocalDateTime`: 날짜+시간
- `ZonedDateTime`: 타임존 포함 날짜/시간
- `Period`, `Duration`: 기간/시간 간격

---

## 2. 주요 특징
- 불변(immutable) 객체 기반
- 메서드 체이닝으로 가독성 높은 날짜 계산 가능
- 타임존 처리와 포맷팅 기능이 표준화되어 있음

---

## 3. 날짜 계산
- `plusDays`, `minusMonths` 등으로 직관적 계산
- `ChronoUnit`으로 두 시점의 차이 계산 가능

---

## 4. 포맷팅/파싱
- `DateTimeFormatter` 사용
- 표준 포맷(ISO) 및 사용자 정의 패턴 지원

---

## 5. 실무 팁
- 시스템 시간은 가능한 한 한곳에서 주입해 테스트 가능하게 만든다.
- 타임존이 개입되는 서비스는 `Instant`/`ZonedDateTime` 기준으로 설계한다.

---

## 정리
- `java.time`은 안전하고 일관된 날짜/시간 처리를 위한 표준 API다.
