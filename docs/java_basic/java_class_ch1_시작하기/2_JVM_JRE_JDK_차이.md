# 1.4 컴파일과 실행 원리 (JVM, JRE, JDK)

## 학습 목표
- JVM, JRE, JDK의 역할을 구분한다.
- Java 프로그램이 실행되는 과정을 이해한다.
- 바이트코드와 플랫폼 독립성을 설명할 수 있다.

---

## 1. JVM, JRE, JDK

### JVM (Java Virtual Machine)
- `.class` 바이트코드를 실행하는 가상 머신
- 운영체제마다 JVM 구현은 다르지만, 바이트코드 실행 규칙은 같다.

### JRE (Java Runtime Environment)
- JVM + 표준 라이브러리
- Java 프로그램 실행에 필요한 환경

### JDK (Java Development Kit)
- JRE + 개발 도구(`javac`, `jar`, `javadoc` 등)
- Java 개발과 실행 모두 가능

관계:

```text
JDK > JRE > JVM
```

---

## 2. 컴파일과 실행

```text
소스코드(.java) --javac--> 바이트코드(.class) --java--> JVM 실행
```

- 컴파일: 문법 검사 + 바이트코드 생성
- 실행: JVM이 바이트코드를 해석(JIT 포함)하여 동작

---

## 3. 바이트코드와 플랫폼 독립성

- Java는 특정 OS 기계어가 아닌 바이트코드로 먼저 변환된다.
- 같은 바이트코드는 Windows, macOS, Linux의 JVM에서 실행 가능하다.
- 이 개념을 WORA(Write Once, Run Anywhere)라고 부른다.

---

## 4. 핵심 도구
- `javac`: 컴파일러
- `java`: JVM 실행 명령
- `javap`: 바이트코드 확인(디스어셈블)

---

## 정리
- 개발자는 JDK가 필요하다.
- 실행은 JVM이 담당한다.
- Java의 이식성은 바이트코드 + JVM 구조에서 나온다.
