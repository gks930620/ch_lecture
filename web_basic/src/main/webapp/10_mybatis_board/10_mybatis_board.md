# 10 MyBatis 게시판 — 상세 강의노트

목차
- MyBatis 개요와 장단점
- MyBatis 구성요소(핵심): SqlSessionFactory, Mapper, XML 매퍼
- Board 예제 설계(도메인 모델, DAO 인터페이스, 매퍼)
- 환경 설정: JDBC/커넥션 풀, mybatis-config.xml, pom 의존성
- 실습: In-memory DAO와 MyBatis 매퍼 연동 방식 비교
- 트랜잭션/성능/보안 고려사항

1) MyBatis 개요
- SQL을 XML 또는 애노테이션으로 선언해 Java와 매핑하는 퍼시스턴스 프레임워크.
- 장점: SQL 제어가 용이, 복잡한 쿼리 최적화에 유리. 단점: SQL 관리 필요, ORM보다 반복 코드가 있을 수 있음.

2) 주요 구성요소
- SqlSessionFactory: MyBatis의 진입점. 환경설정으로 생성.
- SqlSession: DB와의 세션(트랜잭션 단위). Mapper 인터페이스의 구현 호출을 통해 SQL 실행.
- Mapper XML: SQL 문과 파라미터/리절트 매핑을 정의.

3) Board 예제 설계
- 모델: `Board { id, title, content, writer, createdAt }`
- DAO(인터페이스): `selectList(Map params)`, `selectOne(int id)`, `insert(Board)`, `update(Board)`, `delete(int id)`
- Mapper XML: parameterType/resultMap 설정, 동적 SQL(검색어에 따른 WHERE 절) 사용.

4) 환경 설정(간단 가이드)
- JDBC 드라이버와 MyBatis 의존성을 `pom.xml`에 추가.
- `mybatis-config.xml`에서 매퍼 리소스/환경(environments) 설정. DataSource는 보통 Tomcat JDBC Pool 또는 HikariCP 사용 권장.

예시 (mybatis-config 연결 요약)
```xml
<environments default="development">
  <environment id="development">
    <transactionManager type="JDBC" />
    <dataSource type="POOLED">
      <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
      <property name="url" value="jdbc:mysql://localhost:3306/dbname"/>
      <property name="username" value="user"/>
      <property name="password" value="pass"/>
    </dataSource>
  </environment>
</environments>
```

5) 실습 전략: In-memory vs MyBatis 실제 DB
- 수업 초반에는 DB 설치 부담을 줄이기 위해 `InMemoryBoardDao`를 제공해 흐름을 실습.
- 이후 MyBatis로 전환: DAO를 MyBatis 기반 구현으로 교체하고 `BoardMapper.xml`의 SQL을 작성해 실제 DB 연동 실습.
- 전환 시 포인트: SqlSessionFactory 설정, 트랜잭션 처리, 매퍼 네임스페이스 일치 여부.

6) 트랜잭션과 동시성
- 여러 엔터티 변경이 필요한 상황에서는 트랜잭션 관리가 중요. MyBatis는 JDBC 트랜잭션 또는 Spring Transaction 연동을 통해 관리.
- 동시성: 게시판의 경우 낙관적 락(version 컬럼)이나 비관적 락을 검토할 수 있음.

7) 보안/성능 팁
- SQL 인젝션: MyBatis의 `#{}` 플레이스홀더를 사용하면 파라미터 바인딩으로 자동 이스케이프가 적용됨.
- 페이징: 대용량 테이블에서는 offset 방식 대신 keyset pagination(커서 기반) 검토.

참고 실습 파일
- `src/main/resources/mappers/BoardMapper.xml` — 기본 select/insert/update/delete 예제 제공
- `src/main/java/.../dao/InMemoryBoardDao.java` — 빠른 실습을 위한 in-memory 구현
- `src/main/java/.../controller/BoardController.java`와 JSP들로 전체 흐름을 확인

이 문서는 MyBatis의 개념부터 실습 전환 가이드까지 단계별로 설명하므로 강의 교재로 바로 사용 가능합니다.
