# 15장. 입출력 (I/O)

## 학습 목표
- 파일을 읽고 쓸 수 있다
- 바이트 스트림과 문자 스트림을 이해한다
- NIO (Files, Paths)를 사용할 수 있다

---

## 1. 파일 읽기/쓰기 (문자 스트림)

```java
// 파일 쓰기
try (FileWriter fw = new FileWriter("test.txt")) {
    fw.write("Hello, World!\n");
    fw.write("Java I/O");
} catch (IOException e) {
    e.printStackTrace();
}

// 파일 읽기
try (FileReader fr = new FileReader("test.txt");
     BufferedReader br = new BufferedReader(fr)) {
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

---

## 2. NIO (Java 7+)

```java
// 파일 읽기
List<String> lines = Files.readAllLines(Paths.get("test.txt"));
for (String line : lines) {
    System.out.println(line);
}

// 파일 쓰기
List<String> lines = Arrays.asList("Line 1", "Line 2", "Line 3");
Files.write(Paths.get("output.txt"), lines);

// 파일 존재 확인
boolean exists = Files.exists(Paths.get("test.txt"));

// 파일 삭제
Files.deleteIfExists(Paths.get("old.txt"));

// 파일 복사
Files.copy(Paths.get("source.txt"), Paths.get("dest.txt"));
```

---

## 3. 정리

### 핵심 개념
✅ **FileReader/FileWriter**: 문자 스트림  
✅ **BufferedReader/BufferedWriter**: 버퍼링  
✅ **Files**: NIO 파일 작업 (권장)  
✅ **try-with-resources**: 자동 close()  

---

## 다음 학습
- 16장. 멀티스레드

