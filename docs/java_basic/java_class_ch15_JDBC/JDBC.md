# JDBC

## 학습 목표
- JDBC의 계층 구조와 표준 실행 흐름을 이해할 수 있다.
- `Connection`, `PreparedStatement`, `ResultSet`를 안전하게 사용할 수 있다.
- 트랜잭션, 커넥션 풀, DAO 분리 등 실무 핵심 패턴을 적용할 수 있다.

---

## 1. JDBC란

JDBC(Java Database Connectivity)는 Java에서 RDBMS에 접근하는 표준 API다.

구성:
1. 애플리케이션 코드
2. JDBC API
3. DB 벤더 드라이버
4. 실제 데이터베이스

![JDBC 아키텍처와 트랜잭션 흐름]({{ '/assets/images/java_basic/ch15/jdbc-architecture-transaction.svg' | relative_url }})

---

## 2. 기본 실행 흐름

1. `DataSource` 또는 `DriverManager`로 `Connection` 획득
2. SQL 준비 (`PreparedStatement` 권장)
3. 파라미터 바인딩
4. 실행 (`executeQuery` / `executeUpdate`)
5. 결과 매핑 (`ResultSet`)
6. 자원 해제

---

## 3. Connection과 자동 커밋

기본적으로 JDBC는 auto-commit이 켜져 있다.

```java
conn.setAutoCommit(false);
```

여러 SQL을 하나의 원자 작업으로 묶으려면 직접 `commit/rollback`을 관리해야 한다.

---

## 4. PreparedStatement (필수)

```java
String sql = "INSERT INTO users(name, age) VALUES(?, ?)";
try (PreparedStatement ps = conn.prepareStatement(sql)) {
    ps.setString(1, "Kim");
    ps.setInt(2, 20);
    ps.executeUpdate();
}
```

장점:
1. SQL 인젝션 방지
2. 파라미터 바인딩 명확
3. SQL 파싱/실행 계획 재사용 가능성

---

## 5. ResultSet 매핑

```java
String sql = "SELECT id, name, age FROM users";
try (PreparedStatement ps = conn.prepareStatement(sql);
     ResultSet rs = ps.executeQuery()) {
    while (rs.next()) {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        int age = rs.getInt("age");
    }
}
```

매핑 시 컬럼명 오타/NULL 처리/타입 매핑 주의가 필요하다.

---

## 6. 트랜잭션 제어

```java
conn.setAutoCommit(false);
try {
    // SQL 1
    // SQL 2
    conn.commit();
} catch (Exception e) {
    conn.rollback();
    throw e;
}
```

원칙:
- 비즈니스 단위로 트랜잭션 경계 설정
- 실패 시 반드시 롤백
- 예외 원인 로그 보존

---

## 7. 자원 관리

`Connection`, `Statement`, `ResultSet`는 반드시 닫아야 한다.

```java
try (Connection conn = ds.getConnection();
     PreparedStatement ps = conn.prepareStatement(sql);
     ResultSet rs = ps.executeQuery()) {
    ...
}
```

try-with-resources가 표준 패턴이다.

---

## 8. DAO 계층 분리

권장 구조:
- `Repository/DAO`: SQL 및 매핑
- `Service`: 트랜잭션과 비즈니스 규칙
- `Controller`: 요청/응답 처리

SQL 접근을 분리하면 테스트/유지보수성이 크게 향상된다.

---

## 9. 커넥션 풀(DataSource)

매 요청마다 물리 연결을 새로 만들면 비용이 크다.  
실무에서는 HikariCP 같은 커넥션 풀을 사용한다.

효과:
1. 연결 재사용
2. 지연 감소
3. 연결 수 제한/모니터링 가능

---

## 10. SQL 예외 처리 전략

1. 기술 예외(`SQLException`)를 도메인 예외로 변환
2. 쿼리/파라미터/트랜잭션 맥락 로그 기록
3. 민감 정보는 로그에 직접 노출 금지

---

## 11. 보안/성능 체크포인트

1. 문자열 연결 SQL 금지 (항상 바인딩)
2. N+1 쿼리 패턴 점검
3. 인덱스 전략 확인
4. 대량 처리 시 배치(`addBatch/executeBatch`) 검토

---

## 12. 정리

- JDBC 핵심은 안전한 SQL 실행, 자원 해제, 트랜잭션 관리다.
- PreparedStatement + try-with-resources + DataSource는 기본 규칙이다.
- 코드 구조를 DAO/Service로 분리하면 실무 품질이 안정된다.

