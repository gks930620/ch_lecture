# 14장. 날짜와 시간 API (Java 8+)

## 학습 목표
- LocalDate, LocalTime, LocalDateTime을 사용할 수 있다
- 날짜와 시간을 계산할 수 있다
- DateTimeFormatter로 포맷팅할 수 있다

---

## 1. 주요 클래스

| 클래스 | 설명 |
|--------|------|
| **LocalDate** | 날짜 (년-월-일) |
| **LocalTime** | 시간 (시:분:초) |
| **LocalDateTime** | 날짜 + 시간 |
| **ZonedDateTime** | 날짜 + 시간 + 시간대 |
| **Instant** | 타임스탬프 (UTC 기준) |
| **Period** | 날짜 간격 |
| **Duration** | 시간 간격 |

---

## 2. LocalDate (날짜)

```java
// 현재 날짜
LocalDate today = LocalDate.now();

// 특정 날짜
LocalDate date = LocalDate.of(2024, 3, 15);
LocalDate date = LocalDate.parse("2024-03-15");

// 조회
int year = today.getYear();
int month = today.getMonthValue();
int day = today.getDayOfMonth();
DayOfWeek dayOfWeek = today.getDayOfWeek();

// 계산
LocalDate tomorrow = today.plusDays(1);
LocalDate nextWeek = today.plusWeeks(1);
LocalDate nextMonth = today.plusMonths(1);
LocalDate yesterday = today.minusDays(1);
```

---

## 3. LocalTime (시간)

```java
// 현재 시간
LocalTime now = LocalTime.now();

// 특정 시간
LocalTime time = LocalTime.of(14, 30, 0);

// 조회
int hour = now.getHour();
int minute = now.getMinute();
int second = now.getSecond();

// 계산
LocalTime after1Hour = now.plusHours(1);
LocalTime before30Min = now.minusMinutes(30);
```

---

## 4. LocalDateTime (날짜 + 시간)

```java
// 현재 날짜와 시간
LocalDateTime now = LocalDateTime.now();

// 특정 날짜시간
LocalDateTime dt = LocalDateTime.of(2024, 3, 15, 14, 30);

// LocalDate + LocalTime
LocalDate date = LocalDate.now();
LocalTime time = LocalTime.now();
LocalDateTime dateTime = LocalDateTime.of(date, time);
```

---

## 5. Period와 Duration

```java
// Period (날짜 간격)
LocalDate start = LocalDate.of(2024, 1, 1);
LocalDate end = LocalDate.of(2024, 12, 31);
Period period = Period.between(start, end);

System.out.println(period.getYears());   // 0
System.out.println(period.getMonths());  // 11
System.out.println(period.getDays());    // 30

// Duration (시간 간격)
LocalTime time1 = LocalTime.of(9, 0);
LocalTime time2 = LocalTime.of(17, 30);
Duration duration = Duration.between(time1, time2);

System.out.println(duration.toHours());  // 8
System.out.println(duration.toMinutes());  // 510
```

---

## 6. DateTimeFormatter (포맷팅)

```java
LocalDateTime now = LocalDateTime.now();

// 기본 포맷
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
String formatted = now.format(formatter);
System.out.println(formatted);  // "2024-03-15 14:30:45"

// 파싱
LocalDateTime parsed = LocalDateTime.parse("2024-03-15 14:30:45", formatter);

// 다양한 포맷
formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
formatter = DateTimeFormatter.ofPattern("a h:mm");  // "오후 2:30"
```

---

## 7. 정리

### 핵심 개념
✅ **LocalDate**: 날짜만  
✅ **LocalTime**: 시간만  
✅ **LocalDateTime**: 날짜 + 시간  
✅ **Period**: 날짜 간격  
✅ **Duration**: 시간 간격  
✅ **DateTimeFormatter**: 포맷팅  

---

## 다음 학습
- 15장. 입출력 (I/O)

