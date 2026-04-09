# JDBC

## 학습 목표
- JDBC의 역할과 기본 구조를 이해한다.
- DB 연결부터 조회/수정까지 기본 흐름을 익힌다.
- `PreparedStatement`, 트랜잭션, 자원 관리의 중요성을 이해한다.

---

## 1. JDBC란?
- Java에서 관계형 데이터베이스에 접근하기 위한 표준 API
- DB 벤더별 드라이버를 통해 SQL을 실행한다.

---

## 2. 기본 처리 흐름
1. 드라이버 준비
2. `Connection` 획득
3. `Statement`/`PreparedStatement` 생성
4. SQL 실행 (`executeQuery`, `executeUpdate`)
5. `ResultSet` 처리
6. 자원 종료

---

## 3. PreparedStatement 사용 이유
- SQL 인젝션 방지
- 파라미터 바인딩으로 가독성 향상
- SQL 재사용 시 성능상 이점 가능

---

## 4. 트랜잭션
- 여러 SQL을 하나의 작업 단위로 묶는다.
- 성공 시 `commit`, 실패 시 `rollback`
- 데이터 정합성을 지키는 핵심 장치

---

## 5. 실무 포인트
- 커넥션 풀을 사용해 연결 비용을 줄인다.
- DB 자원은 `try-with-resources`로 안전하게 정리한다.
- DAO 계층으로 SQL 접근을 분리해 유지보수성을 높인다.

---

## 정리
- JDBC 핵심은 "안전한 SQL 실행 + 자원 관리 + 트랜잭션 제어"다.
