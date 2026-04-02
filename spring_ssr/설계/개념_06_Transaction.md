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
    public Page<CommunityDTO> getCommunityList(...) { ... }

    @Transactional  // 쓰기 필요 → readOnly = false로 오버라이드
    public Long createCommunity(CommunityCreateDTO dto) { ... }

    @Transactional  // 쓰기 필요
    public void updateCommunity(Long id, CommunityUpdateDTO dto) { ... }

    @Transactional  // 쓰기 필요
    public void deleteCommunity(Long id) { ... }
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
전파는 빼자..  
기본적으로 service1에서 service2 호출.  
service2에서 에러나도 service1,2 둘다 롤백
어쨋든 @Trasactional 있는 다른 service호출할 때는 주의
알림메일 발송 기록 (메일 실패 상관없이 실패했다는  내역은 DB에 남기기) 등. 
 

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
    // 로그 저장 실패해도 외부 트랜잭션은 그대로 진행
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
        sendNotification(); // ❌ 트랜잭션 적용 안 됨!
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendNotification() {
        // 같은 클래스 내부 호출은 프록시를 거치지 않아서
        // @Transactional이 무시됨
    }
}
```

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

// rollbackFor로 명시적 지정
@Transactional(rollbackFor = Exception.class)
public void process() throws IOException {
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
    public Page<CommunityDTO> getCommunityList(...) { ... }
    public CommunityDTO getCommunityDetail(Long id) { ... }

    // 변경 → @Transactional 오버라이드
    @Transactional
    public Long createCommunity(...)  { ... }

    @Transactional
    public void updateCommunity(...) { ... }

    @Transactional
    public void deleteCommunity(...) { ... }
}
```

