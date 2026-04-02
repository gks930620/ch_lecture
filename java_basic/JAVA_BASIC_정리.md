# 📚 java_basic 프로젝트 전체 정리

> 모듈별 개념 및 예시 코드 구조 총정리

---

## 📁 프로젝트 구조 개요

| 모듈 (IntelliJ 모듈) | 논리 단원 | 주제 |
|---|---|---|
| `java_class_ch1` | **1장** | Java 시작하기 |
| `java_class_ch2` | **2장** | 변수와 자료형 |
| `java_class_ch3` | **3장** | 연산자 |
| `java_class_ch4` | **4장** | 제어문 |
| `java_class_ch5` | **5장** | 배열 / 메소드와 스코프 |
| `java_class_ch6` | **6장** | 객체지향 프로그래밍 – 기초 |
| `java_class_ch7` | **7장** | 객체지향 프로그래밍 – 심화 (상속~추상클래스) |
| `java_class_ch8` | **8장** | 인터페이스 / 익명 객체 / Comparable·Comparator |
| `java_class_ch11` | **11장** | 예외 처리 |
| `java_class_ch12` | **12장** | 문자열 처리 / 날짜·시간 API / 입출력 IO / 기본 API 클래스 |
| `java_class_ch13` | **13장** | 제네릭 |
| `java_class_ch15` | **15장** | 컬렉션 프레임워크 |
| `java_class_ch16` | **16장** | 람다와 함수형 프로그래밍 |
| `java_class_ch17` | **17장** | Stream API |
| `java_class_ch20` | **20장** | JDBC (데이터베이스 연동) |
| `java_class_ch21` | **21장** | 멀티스레드 / Java 최신 문법 |

---

## 1장. Java 시작하기 — `java_class_ch1`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `ch1_시작하기/1_설치 방법 및 HelloWorld실행.md` | IntelliJ 설치 안내 (간략 메모) |
| `ch1_시작하기/2_JVM_JRE_JDK_차이.md` | JVM·JRE·JDK 개념, 컴파일 → 바이트코드 → 실행 흐름, javac/java 명령 |

### 💻 예시 코드 (`src/`)
| 파일 | 내용 |
|---|---|
| `Part1_HelloWorld.java` | Hello World 출력 |
| `Part2_CompileTest.java` | 컴파일·실행 테스트 |
| `과제_HelloWorld응용.java` | ★☆☆☆☆ 과제 |

---

## 2장. 변수와 자료형 — `java_class_ch2`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `ch2_변수와자료형/1_기본자료형.md` | 8가지 Primitive Type (byte~double), 2의 보수, IEEE 754, 정밀도 |
| `ch2_변수와자료형/2_참조자료형_String_배열.md` | String, 배열의 참조 특성 |
| `ch2_변수와자료형/3_형변환.md` | 자동 형변환 / 강제 형변환 |
| `ch2_변수와자료형/4_상수_리터럴_var.md` | final 상수, 리터럴, var(Java 10+) |

### 💻 예시 코드 (`src/ch2_variable/`)
| 파일 | 내용 |
|---|---|
| `P1변수.java` | 변수 선언·초기화 |
| `P2정수.java` | 정수형 타입 실습 |
| `P2실수.java` | 실수형 타입 실습 |
| `P3논리.java` | boolean 타입 |
| `P4문자.java` | char 타입 |
| `P5문자열.java` | String 타입 |
| `P6자동타입변환.java` | 자동 형변환 |
| `P6강제타입변환.java` | 강제(명시적) 형변환 |
| `P6연산식자동타입변환.java` | 연산 시 자동 형변환 |
| `P7변수사용범위와입력.java` | 변수 스코프, 콘솔 입력 |
| `P8Scanner.java` | Scanner 사용법 |

### 📝 연습 문제 (`src/practice/`)
| `Q1.java` ~ `Q5.java` | 변수·자료형 관련 연습 문제 5개 |

### 🎯 과제
| `과제_계산기.java` | ★★☆☆☆ 간단한 계산기 |

---

## 3장. 연산자 — `java_class_ch3`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `연산자/연산자.md` | 산술·증감·비교·논리·삼항·비트 연산자 정리 |

### 💻 예시 코드 (`src/`)
| 파일 | 내용 |
|---|---|
| `P1부호증감대입.java` / `P1부호증감대입2.java` | 부호·증감·대입 연산자 |
| `P2산술연산자.java` | 산술 연산자 |
| `P3오버플로우.java` | 오버플로우 현상 |
| `P4정확한계산.java` | 실수 정밀도 문제 |
| `P5NanInfinity인데굳이하지말자.java` | NaN, Infinity 개념 |
| `P6비교논리연산자.java` / `P6비교논리연산자2.java` | 비교·논리 연산자 |
| `P6삼항연산자.java` | 삼항 연산자 |
| `P7비트논리이동연산.java` | 비트 연산자 |

### 🎯 과제
| `과제_성적관리.java` | ★★☆☆☆ 성적 관리 프로그램 |

---

## 4장. 제어문 — `java_class_ch4`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `제어문/1_연산자와제어문.md` | if문, switch문(기본 + Java 14+ 표현식), for/while/do-while, break/continue |

### 💻 예시 코드 (`src/`)
| 파일 | 내용 |
|---|---|
| `P1if문.java` / `P1if문2.java` | if-else if-else |
| `P2switch문.java` / `P2switch문2.java` | switch + switch 표현식 |
| `P3for문.java` | for 반복문 |
| `P3향상된for문.java` | for-each 반복문 |
| `P4While문.java` | while / do-while |
| `P5break와continue.java` | break, continue |
| `P7예제.java` | 종합 예제 |

### 🎯 과제
| `과제_유틸리티클래스.java` | ★★☆☆☆ 유틸리티 메소드 만들기 |

---

## 5장. 배열 / 메소드와 스코프 — `java_class_ch5`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `ch5_배열/1_배열.md` | 1차원·2차원 배열, 배열 복사(얕은/깊은), Arrays 유틸리티, for-each |
| `ch5_배열/1_메소드와스코프.md` | 메소드 선언·호출, 오버로딩, 가변인자, 재귀, 변수 스코프, Call by Value vs Reference |

### 💻 예시 코드 (`src/`)
| 파일 | 내용 |
|---|---|
| `P1메모리영역.java` / `P1메모리영역2.java` / `P1메모리영역3.java` | JVM 메모리 구조 (Stack, Heap, Method Area) |
| `P2Null과NullpointerException.java` (1~3) | null과 NPE |
| `P4String.java` / `P4String2.java` | String 기본 특성 |
| `P5배열.java` / `P5배열2.java` | 배열 선언·초기화·순회 |
| `P6Enum.java` / `Week.java` | 열거형(Enum) 기초 |
| `P7알아두면좋은메소드.java` | 유용한 내장 메소드 |
| `P8메소드.java` | 메소드 정의·호출·오버로딩 |
| `P8재귀메소드맛보기.java` | 재귀 메소드 |
| `P9자료구조.java` | 자료구조 맛보기 |
| `Part3_ArrayCopy.java` | 배열 복사 |
| **`array/`** | `P1ArrayBasic.java` (배열 기본), `P2MultiDimensionalArray.java` (다차원 배열) |
| **`정렬/`** | `선택정렬.java`, `선택정렬2.java` |

### 🎯 과제
| `과제_로또번호생성기.java` | ★★★☆☆ 로또 번호 생성기 |

---

## 6장. 객체지향 프로그래밍 (OOP) – 기초 — `java_class_ch6`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `ch6_객체지향_기초/1_클래스와객체.md` | 클래스·객체, 필드, 생성자, this, 접근제어자, Getter/Setter, static, 싱글톤 |

### 💻 예시 코드 (`ch6_객체지향_기초/`)
| 파일 | 내용 |
|---|---|
| `Part1_Person.java` | 클래스 정의 (필드, 생성자, 메소드) |
| `Part2_PersonTest.java` | 객체 생성·사용 테스트 |
| `Part3_StaticExample.java` | static 키워드 |
| `Part4_Singleton.java` | 싱글톤 패턴 |
| `Part5_BankAccount.java` | 은행 계좌 예제 (종합) |

### 💻 추가 예시 코드 (`src/이전/`)
| 폴더 | 내용 |
|---|---|
| `p1_2클래스와객체/` | 클래스와 객체 기본 |
| `p3_9클래스와객체필드생성자메소드기본/` | 필드·생성자·메소드 기본 |
| `p10_11정적멤버와final/` | static 멤버, final |
| `p12/` ~ `p15/` | 추가 OOP 예제 |

### 🎯 과제
| `과제_은행시스템.java` | ★★★☆☆ 은행 계좌 관리 시스템 |

---

## 7장. 객체지향 프로그래밍 (OOP) – 심화 — `java_class_ch7`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `ch7_객체지향_심화/1_상속과다형성.md` | 상속(extends), super, 오버라이딩, 다형성, 업/다운캐스팅, instanceof, 추상 클래스, 인터페이스, 내부·익명 클래스, Object 클래스 |

### 💻 예시 코드 (`ch7_객체지향_심화/`)
| 파일 | 내용 |
|---|---|
| `Part1_InheritanceExample.java` | 상속 예제 |
| `Part2_AbstractExample.java` | 추상 클래스 예제 |
| `Part3_InterfaceExample.java` | 인터페이스 예제 |

### 💻 심화 예시 코드 (`src/`)
| 폴더 | 파일들 | 내용 |
|---|---|---|
| `P1상속기본/` | `Book.java`, `ChildrenBook.java`, `HorrorBook.java`, `상속기본Main.java` | 상속 기본 (Book 계층) |
| `P2타입변환/` | `Animal.java`, `Cat.java`, `자동타입변환Main.java`, `강제타입변환Main.java` | 업캐스팅 / 다운캐스팅 |
| `P3다형성/` | **driver/**: `Vehicle.java`, `Car.java`, `Autobike.java`, `Driver.java` | 필드·매개변수 다형성 |
|  | **Idol/**: `Idol.java`, `Ive.java`, `Newjeans.java`, `Fan.java` | 다형성 응용 |
|  | `매개변수다형성Main.java`, `필드의다형성Main.java` | 다형성 메인 |
| `P4추상클래스/` | `Pet.java`(추상), `Cat.java`, `Puppy.java`, `Turtle.java`, `추상클래스Main.java` | 추상 클래스 |

### 🎯 과제
| `과제_도형계산기.java` | ★★★★☆ 도형 계산기 (상속/다형성 활용) |

---

## 8장. 인터페이스 / Comparable·Comparator — `java_class_ch8`

### 💻 예시 코드 (`src/`)
| 폴더 | 파일들 | 내용 |
|---|---|---|
| `P1인터페이스/` | `Singable.java`, `Danceable.java`, `Singer.java`, `Dancer.java`, `Idol.java`, `Arcademachine.java`, `인터페이스Main.java` | 인터페이스 정의·구현·다중 구현 |
| `P2익명객체구현/` | `Singable.java`, `익명객체구현Main.java` | 익명 객체로 인터페이스 구현 |
| `Comparable/` | `Student.java`, `Comparable메인.java` | Comparable 인터페이스 (자연 정렬) |
| `Comparator/` | `Student.java`, `Compartor메인.java` | Comparator 인터페이스 (커스텀 정렬) |

---

## 11장. 예외 처리 — `java_class_ch11`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `ch9_예외처리/1_예외처리.md` | Error vs Exception, try-catch-finally, 다중 catch, try-with-resources, throws/throw, Checked vs Unchecked, 사용자 정의 예외 |

### 💻 예시 코드 (`ch9_예외처리/`)
| 파일 | 내용 |
|---|---|
| `Part1_ExceptionExample.java` | 예외 처리 기본 |
| `Part2_CustomExceptionExample.java` | 사용자 정의 예외 |

### 💻 심화 예시 코드 (`src/`)
| 폴더 | 파일들 | 내용 |
|---|---|---|
| `예외처리기본/` | `예외처리기본Main.java` (1~3) | try-catch, 다중 catch, finally |
| `예외떠넘기기/` | `예외떠넘기기런타임예외Main.java`, `예외떠넘기기일반예외Main.java` | throws 키워드 |
| `사용자정의예외/` | `Account.java`, `InsufficientException.java`, `뱅크에서사용자정의예외Main.java` | 커스텀 예외 클래스 |

### 🎯 과제
| `과제_ATM시스템.java` | ★★★☆☆ ATM 시스템 (예외 처리 활용) |

---

## 12장. 문자열 처리 / 날짜·시간 API / 입출력 IO / 기본 API 클래스 — `java_class_ch12`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `ch10_문자열처리/1_문자열처리.md` | String 불변성, 주요 메소드, StringBuilder vs StringBuffer, String Pool, 정규표현식, 텍스트 블록 |
| `ch14_날짜와시간API/1_날짜와시간API.md` | LocalDate/Time/DateTime, Period/Duration, DateTimeFormatter |
| `ch15_입출력IO/1_입출력IO.md` | FileWriter/Reader, BufferedReader, NIO(Files, Paths) |

### 💻 예시 코드 (`ch10_문자열처리/`)
| 파일 | 내용 |
|---|---|
| `Part1_StringExample.java` | String 메소드 실습 |
| `Part2_StringBuilderExample.java` | StringBuilder 실습 |

### 💻 예시 코드 (`ch14_날짜와시간API/`)
| 파일 | 내용 |
|---|---|
| `Part1_DateTimeExample.java` | 날짜·시간 API 실습 |

### 💻 예시 코드 (`ch15_입출력IO/`)
| 파일 | 내용 |
|---|---|
| `Part1_FileIOExample.java` | 파일 입출력 실습 |

### 💻 심화 예시 코드 (`src/`)
| 폴더 | 파일들 | 내용 |
|---|---|---|
| `Object/` | `Object메인.java`, `Object메인2.java`, `Student.java` | Object 클래스 (toString, equals, hashCode) |
| `문자열/` | `StringMain.java`, `StringBuilderMain.java` | 문자열 처리 |
| `포장/` | `Wrapper메인.java` | 래퍼 클래스 (Boxing/Unboxing) |
| `수학/` | `수학main.java`, `수학복권만들기Main.java` | Math 클래스 |
| `날짜와시간클래스/` | `날짜와시간Main.java` | java.time API |
| `System/` | `System메인.java` | System 클래스 (exit, gc, currentTimeMillis 등) |
| `리플렉션과어노테이션/` | `P1~P4 리플렉션 관련`, `Square.java` | 리플렉션 (Class 객체, 필드·메소드 조회) |

### 🎯 과제
| `과제_텍스트분석기.java` | ★★★☆☆ 텍스트 분석기 |
| `과제_일정관리.java` | ★★★☆☆ 일정 관리 시스템 |
| `과제_파일관리.java` | ★★★★☆ 파일 관리 시스템 |

---

## 13장. 제네릭 — `java_class_ch13`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `ch11_제네릭/1_제네릭.md` | 제네릭 클래스·메소드, 타입 파라미터 제한(extends), 와일드카드(?, extends, super), 타입 소거 |

### 💻 예시 코드 (`ch11_제네릭/`)
| 파일 | 내용 |
|---|---|
| `Part1_GenericExample.java` | 제네릭 클래스 기본 |
| `Part2_GenericBoundsExample.java` | 제한된 타입 파라미터 |

### 💻 심화 예시 코드 (`src/`)
| 폴더 | 파일들 | 내용 |
|---|---|---|
| `P1제네릭기본/` | `Box.java`, `제네릭기본Main.java`, `제네릭기본2Main.java` | 제네릭 Box 클래스 |
| `P1제네릭기본2/` | `Workable.java`, `Chef.java`/`Architect.java`, `Food.java`/`Building.java`, `제네릭타입Main.java` | 제네릭 인터페이스 활용 |
| `P3제네릭제한된타입/` | `Animal.java`, `Cat.java`, `Dog.java`, `Box.java`, `제한된타입Main.java` | `<T extends Animal>` 제한 |
| `P4제네릭메소드/` | `Box.java`, `제네릭메소드Main.java` | 제네릭 메소드 |
| `P5와일드카드타입파라미터/` | `Course.java`, `Human.java`/`Student.java`/`Worker.java`, `Applicant.java`, `와일드카드타입파라미터Main.java` | `<?>`, `<? extends>`, `<? super>` |

### 🎯 과제
| `과제_제네릭컬렉션.java` | ★★★★☆ 제네릭 컬렉션 구현 |

---

## 15장. 컬렉션 프레임워크 — `java_class_ch15`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `ch8_컬렉션프레임워크/1_컬렉션프레임워크.md` | Collection 계층 구조, List(ArrayList, LinkedList), Set(HashSet, TreeSet), Map(HashMap, TreeMap), Queue/Deque, Iterator, Comparable vs Comparator, Collections 유틸 |

### 💻 예시 코드 (`ch8_컬렉션프레임워크/`)
| 파일 | 내용 |
|---|---|
| `Part1_CollectionExample.java` | 컬렉션 기본 사용 |
| `Part2_ComparatorExample.java` | Comparator 활용 |

### 💻 심화 예시 코드 (`src/`)
| 폴더 | 파일들 | 내용 |
|---|---|---|
| `리스트/` | `Array리스트메인.java`, `LinkedListMain.java`, `Bunny.java`, `로또중복없이List메인.java`, `타입선언을제네릭List로해야하는이유Main.java` (1~2) | ArrayList vs LinkedList, 제네릭 List |
| `셋/` | `Person.java`, `셋main.java`, `셋해쉬코드main2.java` | HashSet, hashCode/equals |
| `맵/` | `Member.java`, `맵Main.java`, `맵반복하기Main.java`, `응용Main.java` | HashMap, 반복 순회, 응용 |
| `해쉬예제/` | `완주하지못한선수.java`, `폰켓몬.java` | 해시 활용 프로그래머스 문제 풀이 |

### 🎯 과제
| `과제_학생관리시스템.java` | ★★★★☆ 학생 관리 시스템 |

---

## 16장. 람다와 함수형 프로그래밍 — `java_class_ch16`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `ch12_람다와함수형/1_람다와함수형.md` | 람다 표현식, 함수형 인터페이스, java.util.function 패키지 (Consumer, Supplier, Function, Predicate), 메소드 참조, 생성자 참조 |

### 💻 예시 코드 (`ch12_람다와함수형/`)
| 파일 | 내용 |
|---|---|
| `Part1_LambdaExample.java` | 람다 기본 예제 |
| `Part2_MethodReferenceExample.java` | 메소드 참조 |

### 💻 심화 예시 코드 (`src/`)
| 폴더 | 파일들 | 내용 |
|---|---|---|
| `P1함수형프로그래밍과람다/` | `Calculable.java`, `함수형람다Main.java` (1~2) | 함수형 인터페이스 정의, 람다 기본 |
| `P2람다만들기/` | `Maxable.java`, `Printable.java`, `Singable.java`, `Sumable.java`, `Workable.java`, `람다기본메인.java` (1~5) | 다양한 함수형 인터페이스 + 람다 실습 |
| `P3메소드참조/` | `메소드참조main.java` | `Class::method` 형태의 메소드 참조 |

### 🎯 과제
| `과제_함수형프로그래밍.java` | ★★★☆☆ 함수형 프로그래밍 연습 |

---

## 17장. Stream API — `java_class_ch17`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `ch13_StreamAPI/1_StreamAPI.md` | Stream 개념, 생성, 중간 연산 (filter, map, flatMap, sorted, distinct, limit, skip), 최종 연산 (forEach, collect, reduce, count, match), Collectors, Optional, 병렬 스트림, 기본형 스트림 |

### 💻 예시 코드 (`ch13_StreamAPI/`)
| 파일 | 내용 |
|---|---|
| `Part1_StreamExample.java` | 스트림 기본 예제 |
| `Part2_StreamPractice.java` | 스트림 연습 |

### 💻 심화 예시 코드 (`src/`)
| 폴더 | 파일들 | 내용 |
|---|---|---|
| `P1스트림이란/` | `스트림메인.java` | 스트림 개념·생성 |
| `P2스트림중간처리/` | `Person.java`, `스트림1스트림얻기메인.java`, `스트림2필터링메인.java`, `스트림3요소변환메인.java`, `스트림4요소flat메인.java`, `스트림5요소정렬메인.java` | 필터링, map, flatMap, sorted |
| `P3스트림최종처리/` | `Human.java`, `Person.java`, `스트림1요소처리메인.java`, `스트림2요소조건만족메인.java`, `스트림3요소기본집계.java`, `스트림3요소커스텀집계.java`, `스트림4요소수집.java` | forEach, match, reduce, collect |
| `스트림연습문제/` | `베스트앨범.java` | 프로그래머스 문제 풀이 |

### 🎯 과제
| `과제_데이터처리.java` | ★★★★☆ Stream API 데이터 처리 |

---

## 20장. JDBC — `java_class_ch20`

### 💻 예시 코드 (`src/`)
| 폴더 | 파일들 | 내용 |
|---|---|---|
| `DTO/` | `MemberDTO.java`, `FreeBoardDTO.java` | 데이터 전송 객체 |
| `DAO/` | `MemberDAO.java`, `FreeBoardDAO.java` | 데이터 접근 객체 (CRUD) |
| `P1JDBC개요/` | `Hello.java`, `JDBC개요메인1~3.java` | JDBC 드라이버 로딩, Connection 획득 |
| `P2JDBC기본/` | `JDBC기본1.java`, `JDBC기본2Insert.java`, `JDBC기본2Select.java`, `JDBC기본2SelectWhere.java`, `JDBC기본2SelectWhere2Pstmt.java`, `JDBC기본2Select에서매핑.java`, `JDBC기본2Select에서매핑DTO.java` (1~2) | INSERT, SELECT, WHERE, PreparedStatement, DTO 매핑 |
| `P3Dao확인/` | `Insert메인.java`, `SelectAll메인.java`, `SelectOne메인.java`, `Update메인.java`, `Delete메인.java` | DAO 패턴 CRUD 확인 |
| `필요한파일/` | `member.sql`, `free_board.sql`, 더미데이터 SQL, `ojdbc11.jar`, `VO_생성쿼리.txt` | DB 스키마, JDBC 드라이버 |

---

## 21장. 멀티스레드 / Java 최신 문법 — `java_class_ch21`

### 📖 강의 자료 (md)
| 파일 | 내용 |
|---|---|
| `1_멀티스레드.md` | 멀티스레드 개념, Thread 클래스, Runnable 인터페이스, 동기화 |
| `1_Java최신문법.md` | Java 최신 문법 (Records, Sealed Classes, Pattern Matching 등) |

### 💻 예시 코드 (`src/`)
| 파일 | 내용 |
|---|---|
| `Part1_ThreadExample.java` | 멀티스레드 예제 |
| `Part1_ModernJavaExample.java` | Java 최신 문법 예제 |

---

## 📊 전체 학습 순서 요약

> 수업 진행은 `java_class_chN` 모듈 순서에 맞춰 진행합니다.  
> **단원 번호 = 모듈 번호** 입니다.

```
 1장  Java 시작하기                          ← java_class_ch1
 2장  변수와 자료형                           ← java_class_ch2
 3장  연산자                                 ← java_class_ch3
 4장  제어문                                 ← java_class_ch4
 5장  배열 / 메소드와 스코프                   ← java_class_ch5
 6장  OOP 기초                              ← java_class_ch6
 7장  OOP 심화 (상속~추상클래스)               ← java_class_ch7
 8장  인터페이스 / Comparable·Comparator      ← java_class_ch8
11장  예외 처리                              ← java_class_ch11
12장  문자열 / 날짜·시간 API / 입출력 IO       ← java_class_ch12
13장  제네릭                                 ← java_class_ch13
15장  컬렉션 프레임워크                       ← java_class_ch15
16장  람다와 함수형 프로그래밍                  ← java_class_ch16
17장  Stream API                            ← java_class_ch17
20장  JDBC (데이터베이스 연동)                 ← java_class_ch20
21장  멀티스레드 / Java 최신 문법              ← java_class_ch21
```

---

## 📝 과제 목록 총정리

| 단원 | 과제 파일 | 주제 | 난이도 |
|---|---|---|---|
| 1장 | `과제_HelloWorld응용.java` | Hello World 응용 | ★☆☆☆☆ |
| 2장 | `과제_계산기.java` | 간단한 계산기 | ★★☆☆☆ |
| 3장 | `과제_성적관리.java` | 성적 관리 프로그램 | ★★☆☆☆ |
| 4장 | `과제_유틸리티클래스.java` | 유틸리티 메소드 | ★★☆☆☆ |
| 5장 | `과제_로또번호생성기.java` | 로또 번호 생성기 | ★★★☆☆ |
| 6장 | `과제_은행시스템.java` | 은행 계좌 관리 | ★★★☆☆ |
| 7장 | `과제_도형계산기.java` | 도형 계산기 (상속/다형성) | ★★★★☆ |
| 11장 | `과제_ATM시스템.java` | ATM 시스템 (예외 처리) | ★★★☆☆ |
| 12장 | `과제_텍스트분석기.java` | 텍스트 분석기 | ★★★☆☆ |
| 12장 | `과제_일정관리.java` | 일정 관리 시스템 | ★★★☆☆ |
| 12장 | `과제_파일관리.java` | 파일 관리 시스템 | ★★★★☆ |
| 13장 | `과제_제네릭컬렉션.java` | 제네릭 컬렉션 구현 | ★★★★☆ |
| 15장 | `과제_학생관리시스템.java` | 학생 관리 시스템 | ★★★★☆ |
| 16장 | `과제_함수형프로그래밍.java` | 함수형 프로그래밍 연습 | ★★★☆☆ |
| 17장 | `과제_데이터처리.java` | Stream API 데이터 처리 | ★★★★☆ |
