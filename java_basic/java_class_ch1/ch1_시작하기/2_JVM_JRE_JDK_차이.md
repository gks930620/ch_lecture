# 1.4 컴파일과 실행 원리 (JVM, JRE, JDK 차이)

## 학습 목표
- JVM, JRE, JDK의 개념과 차이점을 이해한다
- Java 프로그램의 컴파일과 실행 과정을 이해한다
- 바이트코드의 개념을 이해한다

---

## 1. JVM, JRE, JDK란?

### 1.1 JVM (Java Virtual Machine)
- **자바 가상 머신**
- 바이트코드(.class 파일)를 실행하는 가상의 컴퓨터
- 운영체제에 독립적으로 자바 프로그램을 실행할 수 있게 해줌
- 메모리 관리, 가비지 컬렉션 등을 수행

```
Java 소스코드 (.java)
    ↓ 컴파일
바이트코드 (.class)
    ↓ JVM 실행
운영체제별 기계어로 변환
```

### 1.2 JRE (Java Runtime Environment)
- **자바 실행 환경**
- JVM + 자바 클래스 라이브러리
- 자바 프로그램을 **실행만** 할 수 있음
- 개발 도구는 포함되지 않음

### 1.3 JDK (Java Development Kit)
- **자바 개발 키트**
- JRE + 개발 도구 (javac, javadoc, jar 등)
- 자바 프로그램을 **개발하고 실행**할 수 있음
- 개발자는 반드시 JDK를 설치해야 함

```
┌─────────────────────────────────┐
│            JDK                  │
│  ┌───────────────────────────┐  │
│  │         JRE              │  │
│  │  ┌───────────────────┐   │  │
│  │  │       JVM        │   │  │
│  │  └───────────────────┘   │  │
│  │  + Java Class Library    │  │
│  └───────────────────────────┘  │
│  + Development Tools            │
│    (javac, javadoc, jar)        │
└─────────────────────────────────┘
```

---

## 2. Java 프로그램의 컴파일과 실행 과정

### 2.1 전체 과정

```
1. 작성: HelloWorld.java (소스 파일)
       ↓
2. 컴파일: javac HelloWorld.java
       ↓
3. 생성: HelloWorld.class (바이트코드)
       ↓
4. 실행: java HelloWorld
       ↓
5. JVM이 바이트코드를 해석하여 실행
```

### 2.2 컴파일 (Compile)
```bash
javac HelloWorld.java
```
- javac: 자바 컴파일러
- .java 파일을 .class 파일(바이트코드)로 변환
- 문법 오류를 체크

### 2.3 실행 (Run)
```bash
java HelloWorld
```
- java: JVM을 실행하는 명령어
- .class 파일을 JVM이 해석하여 실행
- 확장자(.class)는 생략

---

## 3. 바이트코드란?

### 3.1 바이트코드의 특징
- JVM이 이해할 수 있는 중간 코드
- 운영체제에 독립적 (Write Once, Run Anywhere - WORA)
- .class 파일로 저장됨
- 사람이 읽기 어려움 (기계어와 소스코드의 중간 형태)

### 3.2 바이트코드 확인하기
```bash
javap -c HelloWorld
```
- javap: 자바 디스어셈블러
- 바이트코드를 사람이 읽을 수 있는 형태로 보여줌

---

## 4. 플랫폼 독립성

### 4.1 Java의 장점: WORA (Write Once, Run Anywhere)

```
        HelloWorld.java (소스)
                ↓ javac 컴파일
        HelloWorld.class (바이트코드)
                ↓
    ┌───────────┼───────────┐
    ↓           ↓           ↓
Windows JVM  Mac JVM   Linux JVM
    ↓           ↓           ↓
  Windows      Mac        Linux
```

- 한 번 작성한 코드를 어떤 OS에서도 실행 가능
- 각 OS별로 JVM만 설치되어 있으면 됨
- 바이트코드는 동일, JVM이 각 OS에 맞게 변환

---

## 5. 실습 예제

### 예제 1: 컴파일과 실행 직접 해보기
```java
// CompileTest.java
package ch1_시작하기;

public class CompileTest {
    public static void main(String[] args) {
        System.out.println("컴파일과 실행 테스트");
        System.out.println("JDK 버전: " + System.getProperty("java.version"));
        System.out.println("OS: " + System.getProperty("os.name"));
    }
}
```

**실행 방법:**
```bash
# 1. 컴파일
javac CompileTest.java

# 2. 실행
java CompileTest

# 3. 바이트코드 확인
javap -c CompileTest
```

---

## 6. 정리

| 구분 | 설명 | 포함 내용 |
|------|------|-----------|
| **JVM** | 바이트코드를 실행하는 가상 머신 | 실행 엔진 |
| **JRE** | 자바 실행 환경 | JVM + 라이브러리 |
| **JDK** | 자바 개발 키트 | JRE + 개발 도구 |

### 핵심 개념
✅ JDK > JRE > JVM (포함 관계)  
✅ 개발자는 JDK를 설치해야 함  
✅ 바이트코드 덕분에 플랫폼 독립성 확보  
✅ 컴파일(.java → .class) → 실행(JVM)  

---

## 다음 학습
- 2장. 변수와 자료형

