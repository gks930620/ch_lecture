---
layout: default
title: ch10_자바API
description: ch10_자바API 통합 문서
---

# ch10_자바API

통합 문서입니다.

---

## 1. 날짜와 시간 API

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

---

## 2. 문자열 처리

# 문자열 처리

## 학습 목표
- `String` 불변성과 문자열 풀 동작을 이해하고 성능 이슈를 설명할 수 있다.
- 문자열 검색/치환/분할/정규식 처리 패턴을 실무에 적용할 수 있다.
- 대량 문자열 조합 시 `StringBuilder`를 적절히 사용할 수 있다.

---

## 1. String의 핵심 특성

`String`은 불변 객체다.

```java
String s = "A";
s = s + "B";
```

내용을 수정하는 것이 아니라 새 문자열이 생성된다.

의미:
1. 스레드 안전성 향상
2. 해시 안정성 보장
3. 반복 조합 시 객체 생성 비용 증가 가능

---

## 2. 문자열 처리 파이프라인

실무에서 입력 문자열은 보통 다음 순서로 처리한다.

1. 정규화(trim/lowercase)
2. 검증(regex/rule)
3. 파싱(split/substring)
4. 저장/출력 포맷팅

![문자열 처리 파이프라인]({{ '/assets/images/java_basic/ch10/string-processing-pipeline.svg' | relative_url }})

---

## 3. 비교 규칙

### 3.1 `==` vs `equals`

```java
String a = new String("java");
String b = new String("java");
System.out.println(a == b);      // false
System.out.println(a.equals(b)); // true
```

문자열 내용 비교는 `equals`가 기본.

### 3.2 null-safe 비교

```java
"ADMIN".equals(role)
```

---

## 4. 자주 쓰는 메소드

- 길이/검색: `length`, `contains`, `indexOf`, `startsWith`, `endsWith`
- 추출/변환: `substring`, `replace`, `replaceAll`, `toUpperCase`, `toLowerCase`
- 분리/결합: `split`, `String.join`
- 정리: `trim`, `strip`, `isBlank`

주의:
- `replaceAll`은 정규식 기반
- 단순 문자 치환이면 `replace`가 더 직관적

---

## 5. StringBuilder와 성능

루프에서 `+`를 반복하면 중간 문자열이 많이 생성된다.

```java
String result = "";
for (int i = 0; i < 10000; i++) {
    result += i;
}
```

권장:

```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 10000; i++) {
    sb.append(i);
}
String result = sb.toString();
```

멀티스레드 동기화가 필요한 경우 `StringBuffer` 검토.

---

## 6. 정규표현식(Regex) 기초

검증/파싱에 자주 사용된다.

```java
boolean ok = email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
```

복잡한 패턴은 `Pattern`을 미리 컴파일해 재사용하면 성능에 유리하다.

```java
Pattern p = Pattern.compile("\\d+");
Matcher m = p.matcher("ab12cd");
```

---

## 7. 인코딩과 문자 깨짐

문자열은 내부 UTF-16 표현을 사용하지만  
파일/네트워크 입출력 시 인코딩을 명시하지 않으면 깨질 수 있다.

```java
byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
String restored = new String(bytes, StandardCharsets.UTF_8);
```

항상 UTF-8 등 명시적 인코딩 사용을 권장한다.

---

## 8. 문자열 전처리 실무 패턴

1. 사용자 입력 trim
2. 필요 시 대소문자 통일
3. 허용 문자 검증
4. 비즈니스 규칙 검증
5. 저장 전 표준화

예:

```java
String normalized = input == null ? "" : input.trim();
if (normalized.isBlank()) throw new IllegalArgumentException("empty input");
```

---

## 9. 자주 하는 실수

1. 문자열 비교에 `==` 사용
2. `split(".")`처럼 정규식 메타문자 미인식
3. 인코딩 미지정
4. 대량 문자열 조합에 `+` 남용
5. 입력 정규화 없이 곧바로 비교/저장

---

## 10. 정리

- 문자열 처리는 기능 호출보다 "불변성/성능/인코딩/검증" 이해가 핵심이다.
- `equals`, `StringBuilder`, 정규식, 인코딩 명시 습관이 실무 품질을 크게 높인다.

---

## 3. 입출력 IO

# 입출력 (I/O)

## 학습 목표
- 바이트 스트림/문자 스트림의 차이를 정확히 구분할 수 있다.
- 버퍼링, 인코딩, 자원 해제를 포함한 안전한 파일 I/O 코드를 작성할 수 있다.
- `java.io`와 `java.nio.file`의 기본 사용법과 선택 기준을 이해할 수 있다.

---

## 1. I/O 기본 개념

I/O는 프로그램과 외부 자원 간 데이터 이동이다.

- 입력(Input): 외부 -> 프로그램
- 출력(Output): 프로그램 -> 외부

대상:
- 파일
- 네트워크
- 메모리 버퍼
- 콘솔

---

## 2. 스트림 계층

Java I/O는 스트림 기반이며, 여러 스트림을 감싸(layering) 기능을 조합한다.

![I/O 스트림 계층 구조]({{ '/assets/images/java_basic/ch10/io-stream-layer.svg' | relative_url }})

핵심 분류:
- 바이트 스트림: `InputStream`, `OutputStream`
- 문자 스트림: `Reader`, `Writer`

선택 기준:
- 바이너리 데이터(이미지/압축파일): 바이트 스트림
- 텍스트 처리: 문자 스트림 + 인코딩 명시

---

## 3. 파일 읽기/쓰기 기초

### 3.1 텍스트 읽기

```java
Path path = Path.of("data.txt");
try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
}
```

### 3.2 텍스트 쓰기

```java
Path path = Path.of("out.txt");
try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
    bw.write("hello");
    bw.newLine();
}
```

---

## 4. 버퍼링(Buffering)

버퍼링은 작은 I/O를 묶어서 시스템 호출 횟수를 줄여 성능을 높인다.

```java
new BufferedInputStream(new FileInputStream("a.bin"));
new BufferedReader(new FileReader("a.txt"));
```

대량 파일 처리/반복 읽기에서는 버퍼 유무에 따라 성능 차이가 크다.

---

## 5. 인코딩(Encoding)

문자 I/O에서는 인코딩을 명시해야 한다.

```java
new InputStreamReader(in, StandardCharsets.UTF_8);
```

미명시 시 플랫폼 기본 인코딩에 의존해 문자 깨짐 가능.

실무 권장:
1. UTF-8 고정
2. 시스템 경계(파일/API)에서 명시적 변환

---

## 6. try-with-resources

파일/소켓은 반드시 닫아야 한다.  
`try-with-resources`가 표준 방식이다.

```java
try (InputStream in = Files.newInputStream(path)) {
    // use stream
}
```

자동 close 보장 + 코드 간결성 + 예외 정보 보존.

---

## 7. `java.io` vs `java.nio.file`

### 7.1 `java.io`
- 스트림 중심 저수준 API
- 오래된 코드와 호환성 높음

### 7.2 `java.nio.file`
- `Path`, `Files` 중심
- 파일 조작 API가 간결하고 현대적
- 실무에서 기본 선택으로 많이 사용

예:

```java
Files.exists(path);
Files.createDirectories(path.getParent());
Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
```

---

## 8. 대용량 파일 처리 전략

1. 전체 파일을 한 번에 메모리에 올리지 말 것
2. 스트리밍 방식으로 라인/청크 단위 처리
3. 버퍼 크기/Flush 시점 전략화
4. 장애 시 부분 기록/재시도 전략 고려

---

## 9. 예외 처리와 로그

I/O는 실패 가능성이 높으므로 예외 전략이 중요하다.

```java
try {
    ...
} catch (IOException e) {
    throw new FileProcessingException("파일 처리 실패: " + path, e);
}
```

원인(cause) 보존과 파일 경로/상황 로그 기록이 핵심.

---

## 10. 실무에서 자주 하는 실수

1. 스트림 close 누락
2. 인코딩 미지정
3. 전체 파일 메모리 로딩으로 OOM 유발
4. 예외를 무시하고 계속 진행
5. 상대경로 기준 혼동

---

## 11. 정리

- I/O 핵심은 스트림 선택, 버퍼링, 인코딩, 자원 해제다.
- 텍스트는 문자 스트림 + UTF-8 명시, 바이너리는 바이트 스트림이 기본이다.
- 실패를 전제로 설계(예외 처리/재시도/로그)해야 실서비스에서 안정적이다.

---

## 4. 문제

# 문제

`ch10` 범위(`java.time`, 문자열 처리, I/O) 종합 문제입니다.

---

## A. 날짜/시간 API

1. 오늘 날짜 기준으로 100일 뒤 날짜를 구하시오.
2. 생년월일을 입력받아 현재 나이를 계산하시오.
3. 두 `LocalDateTime` 차이를 `Duration`으로 계산하시오.
4. UTC `Instant`를 `Asia/Seoul` 시간으로 변환하시오.

---

## B. 포맷팅/파싱

1. `yyyy-MM-dd HH:mm:ss` 패턴으로 현재 시각을 문자열로 출력하시오.
2. 문자열 `"2026-04-09 15:30:00"`을 `LocalDateTime`으로 파싱하시오.
3. 파싱 실패 시 예외를 처리하고 사용자 안내 메시지를 출력하시오.

---

## C. 문자열 처리

1. 사용자 입력 문자열에서 앞뒤 공백 제거 + 소문자 정규화를 수행하시오.
2. 이메일 형식 검증을 정규표현식으로 구현하시오.
3. CSV 한 줄을 `split`으로 분해해 각 필드를 출력하시오.
4. 문자열 뒤집기와 회문 판별을 구현하시오.

---

## D. StringBuilder 성능

1. 숫자 1~100000을 문자열로 이어 붙일 때 `+` 방식과 `StringBuilder` 방식을 각각 구현하시오.
2. 두 방식의 실행 시간을 측정하고 결과를 비교하시오.

---

## E. 파일 I/O

1. 텍스트 파일을 UTF-8로 저장하시오.
2. 저장한 파일을 라인 단위로 읽어 출력하시오.
3. `try-with-resources`를 사용해 자원 해제를 보장하시오.
4. 파일이 없을 때 예외를 처리하고 사용자 메시지를 출력하시오.

---

## F. 응용 문제

1. 로그 파일에서 `"ERROR"`가 포함된 라인만 추출해 별도 파일로 저장하시오.
2. 사용자 입력 일정(`yyyy-MM-dd`)을 받아 D-day를 계산하는 콘솔 앱을 작성하시오.
3. 회원 데이터 CSV를 읽어 객체 리스트로 변환하시오.

---

## G. 챌린지

1. 타임존이 다른 두 지역(서울, 뉴욕)의 현재 시각을 동시에 출력하시오.
2. 대용량 텍스트 파일에서 단어 빈도수를 계산하시오.
3. 파일 처리 중 발생한 `IOException`을 도메인 예외로 변환해 전파하시오.

---

## 제출 체크리스트

1. 시간값 저장/표시에서 타임존을 명확히 구분했는가?
2. 문자열 비교에 `equals`를 사용했는가?
3. 파일 I/O에서 인코딩을 명시했는가?
4. 자원 해제를 자동화했는가(try-with-resources)?


