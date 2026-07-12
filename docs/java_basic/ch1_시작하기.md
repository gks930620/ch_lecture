---
layout: default
title: ch1_시작하기
description: IntelliJ 설치, Hello World, JDK/JRE/JVM, 컴파일과 실행 원리
---

# 시작하기

## 학습 목표
- IntelliJ IDEA에서 JDK를 설치하고 프로젝트에 연결할 수 있다.
- Hello World 프로그램을 IDE와 터미널에서 모두 실행할 수 있다.
- `JDK/JRE/JVM`의 역할 차이와 Java 컴파일-실행 원리를 설명할 수 있다.
- 바이트코드, 컴파일, 클래스 로딩, GC까지 Java 런타임의 큰 흐름을 이해한다.

---



## 1. IntelliJ에서 JDK 설치와 프로젝트 생성

이 강의는 **IntelliJ IDEA 하나로 개발 환경을 준비**하는 방식을 기준으로 한다.

### 1.0 IntelliJ IDEA 설치
1. [jetbrains.com/idea/download](https://www.jetbrains.com/idea/download/) 접속
2. 무료 버전 다운로드 (이 강의의 모든 실습은 무료 버전으로 충분)
   - 구버전은 **Community Edition**을 선택하고, 통합 배포판(2025.3 이후)은 설치 후 무료 티어로 사용하면 된다
3. 설치 중 옵션은 기본값 그대로 진행해도 됨

### 1.1 새 프로젝트를 만들면서 JDK 설치
1. IntelliJ IDEA 실행 후 `New Project` 클릭
2. 좌측에서 `Java` 선택
3. `JDK` 영역(버전에 따라 `Project SDK`로 표기)에서 `Download JDK` 선택
4. 수업 기준 버전인 **17** 선택
5. `Create`를 누르면 JDK 다운로드와 프로젝트 생성이 함께 진행됨

### 1.2 기존 프로젝트에 JDK 연결
1. `File > Project Structure` 열기
2. `Project` 탭에서 `SDK`를 선택
3. SDK가 없으면 `Add SDK > Download JDK`로 설치
4. `Project language level`도 SDK 버전과 맞춤
5. IntelliJ로 설치한 JDK는 환경변수(PATH) 등록 없이 IntelliJ 내부 설정을 통해 실행된다

---
## 2. Hello World 작성과 실행

### 2.1 코드 작성
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
```
Java 애플리케이션 시작점은 `main` 메서드다.

> `public class`, `static`, `String[] args` 같은 키워드는 이후 챕터에서 하나씩 배운다.
> 지금은 "자바 프로그램의 정해진 틀"이라고 생각하고 그대로 작성하면 된다.

### 2.2 IDE에서 실행
- `main` 옆 초록색 실행 버튼 클릭
- 콘솔에 `Hello World` 출력 확인

### 2.3 터미널에서 실행
IDE 없이 JDK만으로도 컴파일하고 실행할 수 있다. `HelloWorld.java`가 있는 폴더에서:

```text
javac HelloWorld.java   # 컴파일
java HelloWorld         # 실행
```

- 첫 줄: 소스(`.java`)를 바이트코드(`.class`)로 컴파일
- 둘째 줄: JVM이 `.class`를 로딩해 실행

> IntelliJ의 `Download JDK`로 설치한 JDK는 PATH 환경변수에 등록되지 않는다.
> 터미널 실습은 IntelliJ 하단의 내장 터미널(Terminal 탭)에서 하면 편하다.

---

## 3. JDK, JRE, JVM 차이
Java를 정확히 이해하려면 이 세 가지를 구분해야 한다.

### 3.1 JVM (Java Virtual Machine)
- `.class` 바이트코드를 실제로 실행하는 가상 머신
- OS마다 구현은 다르지만(Java HotSpot 등), 바이트코드 실행 규약은 동일
- 클래스 로딩, 바이트코드 검증, 실행 엔진, 메모리 관리(GC)를 담당

### 3.2 JRE (Java Runtime Environment)
- **JVM + 표준 라이브러리**
- Java 프로그램을 "실행"하는 데 필요한 환경
- 컴파일러 `javac`는 포함되지 않음

> 참고: Java 11부터는 JRE가 따로 배포되지 않고 JDK만 배포된다.
> 다만 "JVM + 라이브러리 = 실행 환경"이라는 개념 구분은 지금도 유효하다.

### 3.3 JDK (Java Development Kit)
- **JRE + 개발 도구**
- `javac`, `jar`, `javadoc`, `jdb` 등 개발 도구 포함
- Java를 "개발 + 실행"하려면 JDK가 필요

관계:
```text
JDK > JRE > JVM
```
![JDK JRE JVM 관계도]({{ '/java_basic/java_basic_images/ch1/jdk-jre-jvm.svg' | relative_url }})


`JDK` 안에 `JRE`, `JRE` 안에 `JVM`이 포함되는 구조를 시각적으로 정리한 그림이다.

---

## 4. Java 컴파일과 실행 원리

### 4.1 큰 흐름

```text
소스코드(.java)
  -> javac 컴파일
바이트코드(.class)
  -> java 명령으로 JVM 실행
클래스 로딩/검증/링크/초기화
  -> 인터프리터 + JIT 컴파일
OS 위에서 동작
```

![Java 컴파일 실행 파이프라인]({{ '/java_basic/java_basic_images/ch1/java-execution-pipeline.svg' | relative_url }})


### 4.2 컴파일 단계 (`javac`)
- 문법 검사
- 타입 검사
- 바이트코드 생성
- 오류가 있으면 `.class` 생성이 되지 않음

### 4.3 클래스 로딩 단계
- `java HelloWorld`를 실행하면 JVM 클래스 로더가 필요한 클래스를 메모리로 로드
- 사용자 클래스뿐 아니라 `String`, `System` 같은 표준 라이브러리 클래스도 로드됨

### 4.4 검증/링크/초기화
- **검증(Verify)**: 바이트코드가 JVM 규칙을 어기지 않는지 검사
- **링크(Link)**: 심볼 참조를 실제 메모리 구조와 연결
- **초기화(Initialize)**: 클래스가 처음 사용될 때 준비 작업(`static` 필드 초기화 등)이 실행됨 — `static`은 객체지향 챕터에서 배운다

### 4.5 실행 엔진 (Interpreter + JIT)
- 처음에는 인터프리터가 바이트코드를 해석 실행
- 반복 실행되는 "핫 코드"는 JIT가 기계어로 컴파일해 성능 향상
- 이 구조 덕분에 이식성과 성능을 함께 확보

### 4.6 메모리와 GC
- 객체는 Heap에 생성
- 메서드 호출 정보는 Stack 프레임에 저장
- 더 이상 참조되지 않는 객체는 GC가 자동 정리

> "객체", "참조", "메서드" 같은 용어는 ch6(객체지향)에서 제대로 배운다.
> 지금은 "자바가 메모리를 자동으로 관리해 준다"는 큰 그림만 잡으면 된다.

![Heap과 Stack, GC 동작]({{ '/java_basic/java_basic_images/ch1/heap-stack-gc.svg' | relative_url }})

---

## 5. 바이트코드와 플랫폼 독립성

Java 소스는 OS별 기계어로 바로 가지 않고, 중간 형태인 바이트코드로 컴파일된다.  
같은 바이트코드는 각 OS의 JVM에서 실행되므로 다음이 가능하다.

- 개발 환경: Windows
- 배포 환경: Linux
- 코드 재컴파일 최소화

이 개념을 흔히 `WORA(Write Once, Run Anywhere)`로 설명한다.

---



## 정리
- 입문 단계에서는 IntelliJ의 `Download JDK` 기능으로 환경 구성을 단순화하는 것이 가장 안정적이다.
- Java 실행은 단순 "컴파일 후 실행"이 아니라, 클래스 로딩/검증/링크/초기화, JIT, GC를 포함한 런타임 파이프라인이다.
- 이 흐름을 이해하면 이후 예외 처리, 컬렉션, 스레드, 성능 튜닝 학습이 훨씬 쉬워진다.

---

## 확인 문제

1. JDK, JRE, JVM의 역할을 각각 한 문장으로 설명하시오.
2. `javac HelloWorld.java`를 실행하면 어떤 파일이 만들어지는가? 그 파일 안에는 무엇이 들어있는가?
3. "Write Once, Run Anywhere"가 가능한 이유를 바이트코드와 JVM 관점에서 설명하시오.
4. Java를 "개발"하려면 JRE가 아니라 JDK가 필요한 이유는 무엇인가?
5. 컴파일 오류와 실행 중 오류는 어느 단계에서 각각 발견되는가? (`javac` / `java` 중)

정답 예시: [ch1 문제 답안](문제답안/ch1_문제답안.md)

