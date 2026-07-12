---
layout: default
title: ch14_stream
description: Stream 파이프라인, 중간/최종 연산, Optional
---

# ch14_stream


---



## 학습 목표
- Stream의 지연 연산 모델과 파이프라인 구조를 이해할 수 있다.
- 중간 연산/최종 연산/수집 연산을 올바르게 조합할 수 있다.
- 부작용 없는 선언형 데이터 처리 코드를 설계할 수 있다.

---

## 1. Stream이란

Stream은 컬렉션 데이터를 선언적으로 처리하기 위한 API다.

핵심 특징:
1. 원본 컬렉션 불변 처리
2. 내부 반복(Internal Iteration)
3. 지연 평가(Lazy Evaluation)

![스트림 파이프라인 생명주기]({{ '/java_basic/java_basic_images/ch14/stream-pipeline-lifecycle.svg' | relative_url }})

---

## 2. 파이프라인 구조

1. 소스 생성: `list.stream()`, `Arrays.stream(...)`
2. 중간 연산: `filter`, `map`, `distinct`, `sorted`, `limit`
3. 최종 연산: `collect`, `forEach`, `reduce`, `count`, `anyMatch`

```java
List<String> result = names.stream()
    .filter(s -> !s.isBlank())
    .map(String::trim)
    .map(String::toUpperCase)
    .sorted()
    .toList();
```

---

## 3. 지연 연산과 실행 시점

중간 연산은 즉시 실행되지 않고, 최종 연산이 호출될 때 전체 파이프라인이 실행된다.

```java
Stream<String> s = names.stream().filter(x -> x.length() > 3); // 아직 실행 안 됨
long count = s.count(); // 여기서 실행
```

스트림은 1회성이다. 최종 연산 후 재사용 불가.

---

## 4. 자주 쓰는 중간 연산

1. `filter`: 조건 통과 요소만
2. `map`: 타입/값 변환
3. `flatMap`: 중첩 구조 평탄화

```java
List<List<String>> nested = List.of(List.of("a", "b"), List.of("c"));
List<String> flat = nested.stream()
        .flatMap(List::stream)
        .toList(); // [a, b, c]
```

![map은 스트림의 스트림, flatMap은 평탄화]({{ '/java_basic/java_basic_images/ch14/map-vs-flatmap.svg' | relative_url }})

4. `distinct`: 중복 제거
5. `sorted`: 정렬
6. `peek`: 디버깅 보조(실무 로직용 남용 금지)

---

## 5. 자주 쓰는 최종 연산

1. `collect`: 컬렉션/맵 수집
2. `reduce`: 누적 계산

```java
int product = List.of(1, 2, 3, 4).stream().reduce(1, (a, b) -> a * b); // 24
```

첫 인자(`1`)는 누적 계산의 초기값이다.

3. `count`: 개수
4. `anyMatch/allMatch/noneMatch`: 조건 검사
5. `findFirst/findAny`: 탐색

---

## 6. Collector 실전

```java
Map<String, Long> freq = words.stream()
    .collect(Collectors.groupingBy(
        Function.identity(),      // 요소 자신을 그대로 그룹핑 키로 사용 (w -> w 와 같음)
        Collectors.counting()));  // 각 그룹에 속한 요소 개수를 값으로 집계
```

![Collectors.groupingBy 동작]({{ '/java_basic/java_basic_images/ch14/collectors-groupingby.svg' | relative_url }})

자주 사용하는 collector:
- `toList`, `toSet`, `toMap`
- `groupingBy`, `partitioningBy`
- `joining`
- `summarizingInt`

---

## 7. Optional과 결합

> 실습 소스: `src/P4옵셔널과병렬/옵셔널병렬Main.java` (병렬 스트림 포함)

`Optional<T>`는 값이 있을 수도, 없을 수도 있음을 표현하는 컨테이너다.  
null을 직접 다루는 대신 "없음"을 타입으로 표현해 NullPointerException 발생 가능성을 줄인다.  
탐색 연산은 Optional을 반환할 수 있다.

```java
String first = names.stream()
    .filter(s -> s.startsWith("K"))
    .findFirst()
    .orElse("NONE");
```

`get()` 남용 대신 `orElse`, `orElseGet`, `orElseThrow` 사용 권장.

---

## 8. 병렬 스트림

```java
long sum = nums.parallelStream().mapToLong(Integer::longValue).sum();
```

주의:
1. 항상 빠르지 않음
2. 데이터 크기/CPU 코어/연산 비용에 따라 효과 다름
3. 공유 가변 상태와 결합하면 위험

---

## 9. 부작용(Side Effect) 피하기

지양:

```java
List<Integer> out = new ArrayList<>();
list.stream().filter(x -> x > 0).forEach(out::add); // 외부 상태 변경
```

권장:

```java
List<Integer> out = list.stream().filter(x -> x > 0).toList();
```

부작용 최소화가 Stream 코드의 핵심 원칙이다.

---

## 10. 성능/가독성 균형

1. 짧고 의미 있는 단계로 파이프라인 구성
2. 지나치게 긴 체인은 중간 변수/메소드로 분리
3. 단순 루프가 더 읽기 쉬우면 루프 사용도 허용

---

## 11. 정리

- Stream은 선언형 데이터 처리 도구이며, 지연 평가 모델을 이해해야 한다.
- 중간 연산 + 최종 연산 + collector 조합이 핵심이다.
- 부작용을 줄이고 가독성을 유지하는 방향으로 사용하는 것이 실무 품질을 높인다.

---


# 문제

`ch14` 범위(Stream 생성/중간연산/최종연산/Collector/Optional) 문제입니다.

> 정답 예시: [ch14 문제 답안](문제답안/ch14_문제답안.md)

---

## A. 기초 파이프라인

1. 정수 리스트에서 짝수만 골라 합계를 구하시오.
2. 문자열 리스트를 trim + 대문자 변환 후 정렬하시오.
3. 길이가 3 이상인 문자열 개수를 구하시오.

---

## B. 중간 연산

1. 중복 제거(`distinct`) 후 상위 5개(`limit`)를 출력하시오.
2. 2차원 리스트를 `flatMap`으로 평탄화하시오.
3. `sorted`와 `Comparator`를 조합해 객체 리스트를 정렬하시오.

---

## C. 최종 연산

1. `reduce`로 곱셈 누적 결과를 계산하시오.
2. `anyMatch/allMatch/noneMatch`로 조건 검사 코드를 작성하시오.
3. `findFirst` 결과를 `Optional` 안전 처리하시오.

---

## D. Collector

1. 단어 리스트를 빈도수 맵으로 집계하시오.
2. 사용자 리스트를 부서별 그룹핑하시오.
3. 점수 리스트의 요약 통계(`IntSummaryStatistics`)를 구하시오.

---

## E. 부작용/성능

1. 외부 리스트를 `forEach`로 수정하는 나쁜 예를 만들고 개선하시오.
2. 같은 로직을 루프 버전/스트림 버전으로 작성해 가독성을 비교하시오.
3. 병렬 스트림과 일반 스트림 시간을 간단 측정하시오.

---

## F. 챌린지

1. 주문 리스트를 받아 상품 카테고리별 총매출을 계산하시오.
2. 로그 문자열 리스트에서 ERROR 코드만 추출하고 빈도수 순 정렬하시오.
3. Optional + Stream 조합으로 null-safe 파이프라인을 작성하시오.

---

## 제출 체크리스트

1. 스트림 재사용 오류가 없는가?
2. 부작용 없는 파이프라인으로 구성했는가?
3. 파이프라인이 너무 길어 가독성을 해치지 않는가?


