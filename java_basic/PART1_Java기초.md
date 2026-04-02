일단 ch5까지만 정리했음
나머진 천천히   
그리고 프로그래머스 프로젝트나 github pages 프로젝트 ㄱㄱ 하자 


# 📚 PART 1: Java 기초

> Java 설치부터 최신 문법까지

[← 목차로 돌아가기](./아직 내용은없지만 추가하거나 고려해야할참고)

---

## 1장. Java 시작하기
- 1.1 JDK 설치 및 환경설정 (JAVA_HOME, PATH)
- 1.2 IDE 설치 (IntelliJ IDEA / Eclipse / VS Code)
- 1.3 Hello World 작성
- 1.4 컴파일과 실행 원리 (JVM, JRE, JDK 차이)
- 1.5 바이트코드와 클래스 파일

## 2장. 변수와 자료형
- 2.1 기본 자료형 (int, long, double, float, boolean, char, byte, short)
- 2.2 참조 자료형 (String, 배열)
- 2.3 형변환 (자동 형변환 / 강제 형변환)
- 2.4 상수 (final)
- 2.5 리터럴
- 2.6 var 키워드 (Java 10+)

## 3장. 연산자와 제어문
- 3.1 산술 연산자 (+, -, *, /, %)
- 3.2 비교 연산자 (==, !=, >, <, >=, <=)
- 3.3 논리 연산자 (&&, ||, !)
- 3.4 대입 연산자 (=, +=, -=)
- 3.5 삼항 연산자
- 3.6 비트 연산자
- 3.7 조건문 (if, else if, else)
- 3.8 switch문 (기본 / Java 14+ 개선된 switch)
- 3.9 반복문 (for, while, do-while)
- 3.10 break, continue, label

## 4장. 메소드와 스코프
- 4.1 메소드 선언과 호출
- 4.2 매개변수와 반환값
- 4.3 메소드 오버로딩
- 4.4 가변인자 (varargs)
- 4.5 재귀 메소드
- 4.6 변수의 스코프 (지역변수, 전역변수)
- 4.7 Call by Value vs Call by Reference

## 5장. 배열   (메소드와 스코프)
- 5.1 1차원 배열 선언과 초기화
- 5.2 2차원 배열
- 5.3 배열 복사 (얕은 복사 / 깊은 복사)
- 5.4 Arrays 유틸리티 클래스
- 5.5 향상된 for문 (for-each)


## 6장. 객체지향 프로그래밍 (OOP) - 기초
- 6.1 클래스와 객체 개념
- 6.2 필드 (멤버 변수)
- 6.3 생성자 (기본 생성자, 매개변수 생성자)
- 6.4 this 키워드
- 6.5 접근 제어자 (public, private, protected, default)
- 6.6 Getter / Setter
- 6.7 static 키워드 (정적 변수, 정적 메소드)
- 6.8 인스턴스 vs 클래스 멤버
- 6.9 싱글톤 패턴 기초

## 7장. 객체지향 프로그래밍 (OOP) - 심화
- 7.1 상속 (extends)
- 7.2 super 키워드
- 7.3 메소드 오버라이딩
- 7.4 다형성 (Polymorphism)
- 7.5 업캐스팅 / 다운캐스팅
- 7.6 instanceof 연산자
- 7.7 추상 클래스 (abstract)
- 7.8 인터페이스 (interface)
- 7.9 default 메소드 (Java 8+)
- 7.10 다중 상속과 인터페이스
- 7.11 내부 클래스 (Inner Class)
- 7.12 익명 클래스
- 7.13 final 클래스와 메소드
- 7.14 Object 클래스 (toString, equals, hashCode)

## 8장. 컬렉션 프레임워크
- 8.1 컬렉션 개요
- 8.2 List 인터페이스 (ArrayList, LinkedList, Vector)
- 8.3 Set 인터페이스 (HashSet, TreeSet, LinkedHashSet)
- 8.4 Map 인터페이스 (HashMap, TreeMap, LinkedHashMap)
- 8.5 Queue / Deque (PriorityQueue, ArrayDeque)
- 8.6 Stack
- 8.7 Iterator와 ListIterator
- 8.8 Comparable vs Comparator
- 8.9 Collections 유틸리티 클래스

## 9장. 예외 처리
- 9.1 예외란? (Error vs Exception)
- 9.2 try-catch-finally
- 9.3 다중 catch
- 9.4 try-with-resources (Java 7+)
- 9.5 throws와 throw
- 9.6 Checked Exception vs Unchecked Exception
- 9.7 사용자 정의 예외 클래스
- 9.8 예외 계층 구조 (Throwable)
- 9.9 예외 처리 best practices

## 10장. 문자열 처리
- 10.1 String 클래스 메소드
- 10.2 String 불변성 (Immutability)
- 10.3 StringBuilder vs StringBuffer
- 10.4 String Pool
- 10.5 정규표현식 (Pattern, Matcher)
- 10.6 문자열 포맷팅 (String.format, printf)
- 10.7 텍스트 블록 (Java 15+)

## 11장. 제네릭
- 11.1 제네릭이란?
- 11.2 제네릭 클래스
- 11.3 제네릭 메소드
- 11.4 타입 파라미터 제한 (extends)
- 11.5 와일드카드 (?, extends, super)
- 11.6 제네릭의 타입 소거
- 11.7 제네릭과 상속

## 12장. 람다와 함수형 프로그래밍
- 12.1 람다 표현식 기초
- 12.2 함수형 인터페이스
- 12.3 java.util.function 패키지 (Consumer, Supplier, Function, Predicate)
- 12.4 메소드 참조 (Method Reference)
- 12.5 생성자 참조

## 13장. Stream API
- 13.1 Stream이란?
- 13.2 Stream 생성
- 13.3 중간 연산 (filter, map, flatMap, sorted, distinct, limit, skip)
- 13.4 최종 연산 (forEach, collect, reduce, count, anyMatch, allMatch)
- 13.5 Collectors 클래스 (toList, toSet, toMap, groupingBy, joining)
- 13.6 Optional 클래스
- 13.7 병렬 스트림 (parallelStream)
- 13.8 기본형 스트림 (IntStream, LongStream, DoubleStream)

## 14장. 날짜와 시간 API
- 14.1 LocalDate, LocalTime, LocalDateTime
- 14.2 ZonedDateTime, Instant
- 14.3 Period, Duration
- 14.4 DateTimeFormatter
- 14.5 레거시 Date/Calendar와 변환

## 15장. 입출력 (I/O)
- 15.1 File 클래스
- 15.2 바이트 스트림 (InputStream, OutputStream)
- 15.3 문자 스트림 (Reader, Writer)
- 15.4 BufferedReader, BufferedWriter
- 15.5 직렬화 (Serializable)
- 15.6 NIO (Files, Paths)
- 15.7 Properties 파일 읽기

## 16장. 멀티스레드
- 16.1 프로세스 vs 스레드
- 16.2 Thread 클래스와 Runnable 인터페이스
- 16.3 스레드 생명주기
- 16.4 동기화 (synchronized)
- 16.5 wait(), notify(), notifyAll()
- 16.6 스레드 풀 (ExecutorService)
- 16.7 Callable과 Future
- 16.8 CompletableFuture (Java 8+)
- 16.9 volatile 키워드
- 16.10 동시성 컬렉션 (ConcurrentHashMap 등)

## 17장. Java 최신 문법 (Java 11~21)
- 17.1 var 지역 변수 타입 추론 (Java 10)
- 17.2 텍스트 블록 (Java 15)
- 17.3 Record 클래스 (Java 16)
- 17.4 Sealed 클래스 (Java 17)
- 17.5 패턴 매칭 for instanceof (Java 16)
- 17.6 Switch 표현식 개선 (Java 14)
- 17.7 Virtual Threads (Java 21)

---

**다음: [PART 2: 데이터베이스](./PART2_데이터베이스.md)**

