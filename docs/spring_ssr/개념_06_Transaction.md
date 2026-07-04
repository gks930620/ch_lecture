# @Transactional (트랜잭션)

---

## 1. 트랜잭션이란?

여러 DB 작업을 **하나의 단위**로 묶는 것.  
중간에 실패하면 전부 롤백, 전부 성공하면 커밋.

```
게시글 작성 시나리오:
1. community 테이블에 INSERT   ← 성공
2. files 테이블에 INSERT       ← 실패!
→ 트랜잭션 없으면: community만 저장되고 파일은 누락 (데이터 불일치)
→ 트랜잭션 있으면: community INSERT도 롤백 → 깔끔
```

---

## 2. 기본 사용법

```java
@Service
@Transactional(readOnly = true)  // 클래스 기본값: 읽기 전용
public class CommunityService {

    // 읽기 전용 (readOnly = true 상속)
    public PageResponse<CommunityDTO> getCommunityList(...) { ... }

    @Transactional  // 쓰기 필요 → readOnly = false로 오버라이드
    public Long createCommunity(CommunityCreateDTO dto, Long userId, String username, String nickname) { ... }

    @Transactional  // 쓰기 필요
    public void updateCommunity(Long id, CommunityUpdateDTO dto, String username) { ... }

    @Transactional  // 쓰기 필요
    public void deleteCommunity(Long id, String username) { ... }
}
```

### readOnly = true 쓰는 이유

- Hibernate가 더티 체킹(변경 감지)을 하지 않음 → 성능 향상
- DB 복제 환경에서 읽기 전용 슬레이브 DB로 자동 라우팅 가능
- 실수로 데이터 변경하는 걸 방지

---

## 3. 트랜잭션 전파 (Propagation)
트랜잭션 안에서 다른 트랜잭션을 호출할 때 어떻게 동작할지 결정.
```java
// 기본값: REQUIRED
@Transactional(propagation = Propagation.REQUIRED)
```
`REQUIRED`(기본값)에서는 Service A가 Service B를 호출하면 둘이 **하나의 트랜잭션에 묶인다.**  
그래서 B에서 예외가 나면 A가 한 작업까지 함께 롤백된다.  
`@Transactional`이 붙은 다른 Service를 호출할 때는 이 점을 항상 주의해야 한다.

메일 발송 실패 기록처럼 "메인 로직이 실패해도(또는 이 작업이 실패해도) 따로 남겨야 하는 작업"은
`REQUIRES_NEW`로 별도 트랜잭션으로 분리하는 것을 고려한다.

| 옵션 | 설명 |
|------|------|
| `REQUIRED` (기본값) | 기존 트랜잭션 있으면 참여, 없으면 새로 생성 |
| `REQUIRES_NEW` | 항상 새 트랜잭션 생성 (기존 트랜잭션 일시 중단) |
| `SUPPORTS` | 트랜잭션 있으면 참여, 없으면 없이 실행 |
| `NEVER` | 트랜잭션 있으면 예외 발생 |

```java
// 실무 예시: 로그 저장은 실패해도 메인 로직에 영향 없게
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void saveLog(String action) {
    // 별도 트랜잭션으로 실행됨
    // ※ 단, 호출하는 쪽에서 이 메서드의 예외를 try-catch로 잡아야
    //    외부 트랜잭션이 영향받지 않음 (예외가 그대로 전파되면 외부도 롤백됨)
}
```

---

## 4. 주의사항 - 같은 클래스 내부 호출

```java
@Service
public class CommunityService {

    @Transactional
    public void createCommunity(...) {
        // ... 게시글 저장
        sendNotification(); // ❌ REQUIRES_NEW가 무시됨!
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendNotification() {
        // 같은 클래스 내부 호출은 프록시를 거치지 않아서
        // 이 메서드에 붙은 @Transactional의 전파 속성(REQUIRES_NEW)이 무시됨
    }
}
```

> ⚠️ **주의 — "트랜잭션이 아예 없는 것"이 아니다.**  
> `createCommunity()`가 이미 `@Transactional`이므로, 내부 호출된 `sendNotification()`의 코드는
> **바깥(createCommunity) 트랜잭션 안에서 그대로 실행**된다. 프록시를 거치지 않아 무시되는 것은
> "`REQUIRES_NEW`로 **별도 트랜잭션을 새로 분리**"하는 부분이다.
> 즉 별도 트랜잭션으로 떨어지길 기대했지만 바깥 트랜잭션에 묶여버린다.
> (바깥 메서드에 `@Transactional`이 아예 없다면 그때는 정말 트랜잭션 없이 실행된다.)

**해결:** 별도 Service 클래스로 분리 후 주입받아 호출.

---

## 5. 롤백 조건

기본적으로 `RuntimeException`과 `Error`에서만 롤백.

```java
// Checked Exception은 기본적으로 롤백 안 됨
@Transactional
public void process() throws IOException {
    // IOException 발생해도 롤백 안 됨 (기본값)
}
```

```java
// rollbackFor로 명시적 지정
@Transactional(rollbackFor = Exception.class)
public void processWithRollback() throws IOException {
    // 모든 예외에서 롤백
}
```

---

## 6. 이 프로젝트에서의 패턴

```java
@Service
@Transactional(readOnly = true)  // ← 기본: 조회는 readOnly
public class CommunityService {

    // 조회 → readOnly 상속
    public PageResponse<CommunityDTO> getCommunityList(...) { ... }

    // 조회 메서드지만 조회수 증가(UPDATE)가 있어 readOnly 해제!
    // incrementViewCount()의 변경이 dirty checking으로 UPDATE를 발생시키기 때문
    @Transactional
    public CommunityDTO getCommunityDetail(Long communityId) { ... }

    // 변경 → @Transactional 오버라이드
    @Transactional
    public Long createCommunity(...)  { ... }

    @Transactional
    public void updateCommunity(...) { ... }

    @Transactional
    public void deleteCommunity(...) { ... }
}
```

