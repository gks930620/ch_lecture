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

