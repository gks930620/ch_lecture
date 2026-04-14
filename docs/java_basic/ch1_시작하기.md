# 시작하기

## 학습 목표
- IntelliJ IDEA에서 JDK를 설치하고 프로젝트에 연결할 수 있다.
- Hello World 프로그램을 IDE와 터미널에서 모두 실행할 수 있다.
- `JDK/JRE/JVM`의 역할 차이와 Java 컴파일-실행 원리를 설명할 수 있다.
- 바이트코드, 클래스 로딩, JIT, GC까지 Java 런타임의 큰 흐름을 이해한다.

---

## 1. IntelliJ에서 JDK 설치와 프로젝트 생성

이 강의는 **IntelliJ IDEA 하나로 개발 환경을 준비**하는 방식을 기준으로 한다.

### 1.1 새 프로젝트를 만들면서 JDK 설치
1. IntelliJ IDEA 실행 후 `New Project` 클릭
2. 좌측에서 `Java` 선택
3. `Project SDK` 영역에서 `Download JDK` 선택
4. 수업 기준 버전(예: 17 또는 21) 선택
5. `Create`를 누르면 JDK 다운로드와 프로젝트 생성이 함께 진행됨

### 1.2 기존 프로젝트에 JDK 연결
1. `File > Project Structure` 열기
2. `Project` 탭에서 `SDK`를 선택
3. SDK가 없으면 `Add SDK > Download JDK`로 설치
4. `Project language level`도 SDK 버전과 맞춤

### 1.3 설치 확인
IntelliJ 하단 `Terminal`에서 확인한다.

```bash
java -version
javac -version
```

- `java`는 실행 런타임 확인
- `javac`는 컴파일러 확인  
둘 다 버전이 출력되어야 개발 준비가 끝난다.

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

### 2.2 IDE에서 실행
- `main` 옆 초록색 실행 버튼 클릭
- 콘솔에 `Hello World` 출력 확인

### 2.3 터미널에서 실행

```bash
javac HelloWorld.java
java HelloWorld
```

- 첫 줄: 소스(`.java`)를 바이트코드(`.class`)로 컴파일
- 둘째 줄: JVM이 `.class`를 로딩해 실행

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

### 3.3 JDK (Java Development Kit)
- **JRE + 개발 도구**
- `javac`, `jar`, `javadoc`, `jdb` 등 개발 도구 포함
- Java를 "개발 + 실행"하려면 JDK가 필요

관계:

```text
JDK > JRE > JVM
```

![JDK JRE JVM 관계도]({{ '/assets/images/java_basic/ch1/jdk-jre-jvm.svg' | relative_url }})

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

![Java 컴파일 실행 파이프라인]({{ '/assets/images/java_basic/ch1/java-execution-pipeline.svg' | relative_url }})

컴파일 단계와 런타임 단계를 하나의 흐름으로 연결한 그림이다.

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
- **초기화(Initialize)**: `static` 필드 초기화, `static {}` 블록 실행

### 4.5 실행 엔진 (Interpreter + JIT)
- 처음에는 인터프리터가 바이트코드를 해석 실행
- 반복 실행되는 "핫 코드"는 JIT가 기계어로 컴파일해 성능 향상
- 이 구조 덕분에 이식성과 성능을 함께 확보

### 4.6 메모리와 GC
- 객체는 Heap에 생성
- 메서드 호출 정보는 Stack 프레임에 저장
- 더 이상 참조되지 않는 객체는 GC가 자동 정리

---

## 5. 바이트코드와 플랫폼 독립성

Java 소스는 OS별 기계어로 바로 가지 않고, 중간 형태인 바이트코드로 컴파일된다.  
같은 바이트코드는 각 OS의 JVM에서 실행되므로 다음이 가능하다.

- 개발 환경: Windows
- 배포 환경: Linux
- 코드 재컴파일 최소화

이 개념을 흔히 `WORA(Write Once, Run Anywhere)`로 설명한다.

---

## 6. 자주 발생하는 문제와 점검 포인트

1. `javac`를 찾지 못함
- JDK가 아닌 JRE만 설치했거나 PATH 설정 문제일 가능성이 큼

2. `public class` 이름과 파일명이 다름
- 예: `HelloWorld.java` 파일에 `public class Main` 선언
- 컴파일 에러 발생

3. IntelliJ에서는 되는데 터미널에서는 안 됨
- 작업 디렉터리, PATH, JDK 버전 차이 점검

4. JDK 버전 불일치
- 프로젝트 SDK와 실행 SDK가 다르면 예상치 못한 문법/라이브러리 오류 발생 가능

---

## 정리
- 입문 단계에서는 IntelliJ의 `Download JDK` 기능으로 환경 구성을 단순화하는 것이 가장 안정적이다.
- Java 실행은 단순 "컴파일 후 실행"이 아니라, 클래스 로딩/검증/링크/초기화, JIT, GC를 포함한 런타임 파이프라인이다.
- 이 흐름을 이해하면 이후 예외 처리, 컬렉션, 스레드, 성능 튜닝 학습이 훨씬 쉬워진다.

