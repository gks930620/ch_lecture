# 1.1 설치 방법 및 Hello World 실행

## 학습 목표
- Java 개발 환경을 준비한다.
- 첫 Java 프로그램을 작성하고 실행한다.
- 컴파일과 실행의 기본 흐름을 이해한다.

---

## 1. 개발 환경 준비

### 1.1 JDK 설치
- Java 개발에는 JDK가 필요하다.
- 설치 후 `java -version`, `javac -version`으로 정상 설치를 확인한다.

### 1.2 IDE 준비
- IntelliJ IDEA, Eclipse, VS Code 등 원하는 IDE를 사용하면 된다.
- 교육용 실습에서는 실행 버튼과 터미널 실행을 둘 다 익히는 것이 좋다.

---

## 2. 첫 프로그램 작성

Java 프로그램은 클래스 안에 `main` 메서드가 있어야 실행된다.

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
```

---

## 3. 실행 흐름

1. 소스 파일 작성 (`.java`)
2. 컴파일 (`javac`)
3. 클래스 파일 생성 (`.class`)
4. 실행 (`java`)

```bash
javac HelloWorld.java
java HelloWorld
```

---

## 4. 자주 발생하는 문제
- JDK가 아닌 JRE만 설치한 경우 `javac`를 사용할 수 없다.
- 클래스명과 파일명이 다르면 컴파일 오류가 발생한다.
- 경로(classpath) 설정이 잘못되면 실행 시 클래스를 찾지 못할 수 있다.

---

## 정리
- Java 입문의 핵심은 "작성 → 컴파일 → 실행" 흐름을 몸에 익히는 것이다.
- IDE 실행과 터미널 실행을 모두 해보면 개념 이해가 빨라진다.
