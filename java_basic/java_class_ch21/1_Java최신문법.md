# 17장. Java 최신 문법 (Java 11~21)

## 학습 목표
- var 타입 추론을 사용할 수 있다
- Record 클래스를 이해한다
- 텍스트 블록을 활용할 수 있다

---

## 1. var (Java 10+)

```java
// 타입 추론
var message = "Hello";  // String
var number = 100;       // int
var list = new ArrayList<String>();  // ArrayList<String>

// 반복문에서 사용
for (var item : list) {
    System.out.println(item);
}
```

---

## 2. 텍스트 블록 (Java 15+)

```java
String json = """
    {
        "name": "홍길동",
        "age": 25,
        "email": "hong@example.com"
    }
    """;

String html = """
    <html>
        <body>
            <h1>Hello</h1>
        </body>
    </html>
    """;
```

---

## 3. Record 클래스 (Java 16+)

```java
// 기존 방식
public class Person {
    private final String name;
    private final int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() { return name; }
    public int getAge() { return age; }
    
    // equals, hashCode, toString 구현...
}

// Record 사용
public record Person(String name, int age) {
    // 자동으로 생성자, getter, equals, hashCode, toString 생성
}

Person p = new Person("홍길동", 25);
System.out.println(p.name());  // getter
System.out.println(p);  // toString
```

---

## 4. Switch 표현식 개선 (Java 14+)

```java
// 기존
String result;
switch (day) {
    case 1:
        result = "월요일";
        break;
    case 2:
        result = "화요일";
        break;
    default:
        result = "기타";
}

// 개선
String result = switch (day) {
    case 1 -> "월요일";
    case 2 -> "화요일";
    case 3 -> "수요일";
    default -> "기타";
};
```

---

## 5. instanceof 패턴 매칭 (Java 16+)

```java
// 기존
if (obj instanceof String) {
    String str = (String) obj;
    System.out.println(str.length());
}

// 패턴 매칭
if (obj instanceof String str) {
    System.out.println(str.length());  // 바로 사용
}
```

---

## 6. 정리

### 핵심 개념
✅ **var**: 타입 추론  
✅ **텍스트 블록**: 여러 줄 문자열  
✅ **Record**: 불변 데이터 클래스  
✅ **Switch 표현식**: 값 반환  
✅ **패턴 매칭**: 간결한 타입 체크  

---

## 학습 완료
축하합니다! Java 기초 학습을 완료했습니다! 🎉

