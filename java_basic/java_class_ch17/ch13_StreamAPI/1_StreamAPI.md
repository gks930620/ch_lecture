# 13장. Stream API

## 학습 목표
- Stream의 개념을 이해한다
- 중간 연산과 최종 연산을 활용할 수 있다
- Optional을 사용할 수 있다

---

## 1. Stream이란?

- 컬렉션 데이터를 **선언적으로 처리**
- **함수형 프로그래밍** 스타일
- 원본 데이터를 변경하지 않음

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// 기존 방식
int sum = 0;
for (int num : numbers) {
    if (num % 2 == 0) {
        sum += num * 2;
    }
}

// Stream 방식
int sum = numbers.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * 2)
    .reduce(0, Integer::sum);
```

---

## 2. Stream 생성

```java
// 컬렉션
List<String> list = Arrays.asList("A", "B", "C");
Stream<String> stream = list.stream();

// 배열
String[] arr = {"A", "B", "C"};
Stream<String> stream = Arrays.stream(arr);

// 직접 생성
Stream<String> stream = Stream.of("A", "B", "C");

// 범위
IntStream intStream = IntStream.range(1, 10);  // 1~9
IntStream intStream = IntStream.rangeClosed(1, 10);  // 1~10
```

---

## 3. 중간 연산

### 3.1 filter (조건 필터링)
```java
list.stream()
    .filter(s -> s.startsWith("A"))
    .forEach(System.out::println);
```

### 3.2 map (변환)
```java
list.stream()
    .map(String::toUpperCase)
    .forEach(System.out::println);
```

### 3.3 sorted (정렬)
```java
list.stream()
    .sorted()
    .forEach(System.out::println);
```

### 3.4 distinct (중복 제거)
```java
list.stream()
    .distinct()
    .forEach(System.out::println);
```

### 3.5 limit, skip
```java
list.stream()
    .limit(5)  // 처음 5개
    .skip(2)   // 처음 2개 건너뛰기
    .forEach(System.out::println);
```

---

## 4. 최종 연산

### 4.1 forEach (반복)
```java
list.stream().forEach(System.out::println);
```

### 4.2 collect (수집)
```java
List<String> result = list.stream()
    .filter(s -> s.length() > 3)
    .collect(Collectors.toList());

Set<String> set = list.stream()
    .collect(Collectors.toSet());

String joined = list.stream()
    .collect(Collectors.joining(", "));
```

### 4.3 reduce (리듀싱)
```java
int sum = numbers.stream()
    .reduce(0, (a, b) -> a + b);

int product = numbers.stream()
    .reduce(1, (a, b) -> a * b);
```

### 4.4 count, min, max
```java
long count = list.stream().count();

Optional<Integer> min = numbers.stream().min(Integer::compare);
Optional<Integer> max = numbers.stream().max(Integer::compare);
```

### 4.5 anyMatch, allMatch, noneMatch
```java
boolean anyMatch = list.stream().anyMatch(s -> s.startsWith("A"));
boolean allMatch = list.stream().allMatch(s -> s.length() > 0);
boolean noneMatch = list.stream().noneMatch(s -> s.isEmpty());
```

---

## 5. Optional

```java
Optional<String> optional = Optional.of("Hello");

// 값 존재 여부
optional.isPresent();  // true

// 값 가져오기
String value = optional.get();

// 값 없을 때 기본값
String result = optional.orElse("Default");

// 값 없을 때 예외
String result = optional.orElseThrow(() -> new Exception());

// 값이 있을 때 실행
optional.ifPresent(System.out::println);
```

---

## 6. 정리

### 핵심 개념
✅ **Stream**: 데이터 흐름 처리  
✅ **중간 연산**: filter, map, sorted 등 (지연 실행)  
✅ **최종 연산**: forEach, collect, reduce 등 (즉시 실행)  
✅ **Optional**: null 안전 처리  

---

## 다음 학습
- 14장. 날짜와 시간 API

