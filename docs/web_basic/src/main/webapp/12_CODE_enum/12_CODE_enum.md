# 12. CODE 관리 — Java Enum vs DB 코드 테이블

## 핵심 질문
> 게시글 상태가 "활성/비활성/삭제" 3가지라면, 이걸 어떻게 관리할 것인가?

---

## 1) 방법 비교

### ① 문자열(String) 하드코딩
```java
board.setStatus("A");  // "A"가 뭐지? 활성? Active?

// 어딘가에서 오타
if ("a".equals(board.getStatus())) { ... }  // 소문자 a로 잘못 씀 → 버그
```
- 장점: 없음
- 단점: 오타 가능, 의미 불명확, IDE 자동완성 불가, 리팩토링 불가

### ② DB 코드 테이블
```sql
CREATE TABLE code (
    category VARCHAR(50),
    code     VARCHAR(20),
    label    VARCHAR(100)
);
INSERT INTO code VALUES ('board_status', 'A', '활성');
INSERT INTO code VALUES ('board_status', 'I', '비활성');
INSERT INTO code VALUES ('board_status', 'D', '삭제');
```
```java
// 서비스에서 DB 조회해서 코드값 사용
String status = codeDao.getLabel("board_status", "A");  // "활성"
```
- 장점: 운영 중 코드 추가/변경 가능 (배포 불필요)
- 단점: **매번 DB 조회 필요**, 코드값이 String이라 여전히 타입 안전하지 않음, 오타 가능

### ③ Java Enum ★ 추천
```java
public enum BoardStatus {
    ACTIVE("A", "활성"),
    INACTIVE("I", "비활성"),
    DELETED("D", "삭제");

    private final String code;
    private final String label;
    // ... 생성자, getter, fromCode()
}
```
```java
board.setStatus(BoardStatus.ACTIVE);      // 컴파일 타임 체크!
board.setStatus(BoardStatus.fromCode("A")); // DB에서 읽은 값 → Enum 변환
```
- 장점: **컴파일 시점 체크**, IDE 자동완성, 오타 불가능, switch문 활용 가능
- 단점: 코드 추가 시 배포 필요 (하지만 코드값 추가는 비즈니스 로직 변경이므로 당연히 배포해야 함)

---

## 2) 왜 Enum이 맞는가?

### 코드(Code)는 "정해진 값"이다
- 게시글 상태: 활성/비활성/삭제 → **미리 정의된 유한한 값**
- 결제 수단: 카드/계좌이체/포인트 → **미리 정의된 유한한 값**
- 회원 등급: 일반/실버/골드/VIP → **미리 정의된 유한한 값**

→ 이런 값은 **코드 추가 = 비즈니스 로직 변경**이므로 어차피 코드 수정 + 배포가 필요하다.
→ 그러면 **컴파일 시점에 검증되는 Enum이 안전하다.**

### ⚠ 자주 하는 오해: "동적으로 늘어나는 것은 코드가 아니다"
- 사용자가 만드는 **카테고리**, **해시태그**, **게시판 종류** 등은 코드가 아니라 **데이터**다.
- 이런 것은 당연히 DB 테이블로 관리해야 한다. (category 테이블, tag 테이블 등)
- 코드 테이블에 넣으면 안 되는 것: 사용자 입력에 의해 무한히 늘어나는 값

```
[코드] 게시글 상태: 활성/비활성/삭제  → Enum ✅
[코드] 결제 수단: 카드/계좌이체/포인트  → Enum ✅
[데이터] 해시태그: #맛집 #여행 #개발 ...  → DB 테이블 ✅ (Enum ✗)
[데이터] 카테고리: 사용자가 생성  → DB 테이블 ✅ (Enum ✗)
```

---

## 3) Enum 활용 패턴

### fromCode() — DB 값 → Enum 변환
```java
public static BoardStatus fromCode(String code) {
    for (BoardStatus s : values()) {
        if (s.code.equals(code)) return s;
    }
    throw new IllegalArgumentException("Unknown code: " + code);
}
```

### DB 저장 시
```java
// Enum → String(code)으로 저장
board.setStatusCode(BoardStatus.ACTIVE.getCode());  // "A"
```

### DB 조회 후 화면 출력
```java
// DB에서 읽은 "A" → Enum → label
BoardStatus status = BoardStatus.fromCode(board.getStatusCode());
// status.getLabel() → "활성"
```

### switch문 활용
```java
switch (status) {
    case ACTIVE:   /* 활성 로직 */ break;
    case INACTIVE: /* 비활성 로직 */ break;
    case DELETED:  /* 삭제 로직 */ break;
    // 새 상태 추가 시 컴파일 경고 → 빠뜨릴 수 없음!
}
```

---

## 4) 실습: Board에 status 적용

### 실습 파일 구조
```
code/
  BoardStatus.java    ← Enum (ACTIVE, INACTIVE, DELETED)
board/
  model/Board.java    ← statusCode(String) 필드 추가
  controller/BoardController.java ← Enum 사용
```

### 실습 포인트
1. `board_write.jsp`에서 상태를 select로 선택
2. Board 모델에 `statusCode` 필드 (DB에는 String "A"/"I"/"D"로 저장)
3. `BoardController`에서 `BoardStatus.fromCode()`로 변환하여 검증
4. `board_list.jsp`에서 상태 label 표시

### MyBatis와 함께 사용할 경우 (참고)
```xml
<!-- BoardMapper.xml -->
<insert id="insert">
    INSERT INTO board(title, content, writer, status)
    VALUES(#{title}, #{content}, #{writer}, #{statusCode})
</insert>
<!-- statusCode에 Enum.getCode() 값("A")이 들어감 -->
```
DB에는 VARCHAR로 "A", "I", "D"가 저장되고, Java에서는 Enum으로 안전하게 다루는 구조.

---

## 5) 정리

| 방식 | 타입 안전 | IDE 지원 | 동적 추가 | 적합한 경우 |
|------|----------|---------|----------|------------|
| String 하드코딩 | ✗ | ✗ | ✗ | 쓰지 마세요 |
| DB 코드 테이블 | ✗ | ✗ | ✅ | 거의 없음 (동적이면 코드가 아니라 데이터) |
| **Java Enum** | **✅** | **✅** | ✗ | **대부분의 코드값** |

> **결론: 코드값은 Enum으로 관리하자.**
> "운영 중 동적으로 추가되는 값"은 코드가 아니라 데이터이므로 별도 테이블로 관리한다.

---

## 참고 자료
- [우아한형제들 기술블로그 — Java Enum 활용기](https://techblog.woowahan.com/2527/)

