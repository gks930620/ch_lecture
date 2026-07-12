# java_basic 검수사항 — ch15 JDBC / ch16 참고최신문법과스레드 / index·README

검수일: 2026-07-02
검수 범위: `docs/java_basic/ch15_JDBC.md`, `ch16_참고최신문법과스레드.md`, `index.md`, `README.md` + `java_basic/java_class_ch15_JDBC`, `java_class_ch16_참고최신문법과스레드` 소스 전체 + 이미지 폴더

이미지 링크(ch15 2개, ch16 3개)와 index.md의 챕터 링크 16개는 모두 정상.

---

## ch15_JDBC

### 기술적 오류 / 소스코드 문제
- [높음] `src/DAO/MemberDAO.java:82,113,143,174,199` 및 `src/P2JDBC기본/JDBC기본1.java:24` 외 P2 전체 — **실제 원격 DB 접속 정보(`jdbc:oracle:thin:@nextit.or.kr:1521:xe`, 계정 `std225`, 비밀번호 `oracle21c`)가 하드코딩되어 공개 저장소에 노출**. 상수/설정 파일로 분리하고 더미 값(`localhost` 등)으로 교체 필요. 문서 11절 "보안 체크포인트"와도 모순됨.
- [높음] `src/P2JDBC기본/JDBC기본2Select에서매핑.java:14,44`, `…매핑DTO.java:13,40`, `…매핑DTO2.java:15,44`, `src/DAO/MemberDAO.java:18` — `new RuntimeException(e);` 앞에 **`throw` 누락**. 예외가 조용히 삼켜져 프로그램이 잘못된 상태로 계속 진행됨. 초보자가 그대로 따라 칠 위험이 큰 실제 버그.
- [높음] `src/P2JDBC기본/JDBC기본2Select에서매핑.java:33~37` — `map.put(rs.getString("mem_id"), rs.getString("mem_id"))`처럼 **key 자리에 컬럼명이 아니라 컬럼 값**을 넣고 있음. 의도는 `map.put("mem_id", rs.getString("mem_id"))`. 출력이 `{a001=a001, 김민지=김민지…}`처럼 나와 Map 매핑 개념 학습을 방해.
- [높음] `src/P2JDBC기본/JDBC기본1.java:19~35` — conn/stmt/rs를 열고 finally/try-with-resources 없이 종료(리소스 누수). 문서 7절 "반드시 닫아야 한다"와 정면 모순. "첫 예제라 생략" 주석이라도 필요.
- [중간] `src/P2JDBC기본/JDBC기본2SelectWhere.java:27~28` — 문자열 연결 SQL(인젝션 위험). 다음 파일(Pstmt)에서 개선하는 흐름은 좋으나, 파일 안에 "이 방식은 SQL 인젝션에 취약하다"는 경고 주석이 없어 초보자가 위험성을 모르고 복사할 수 있음.
- [중간] `src/P3Dao확인/Insert메인.java:13`, `Update메인.java:13` — `mem_bir`(Oracle DATE 컬럼)에 `setString("2024-06-20")` 바인딩. NLS_DATE_FORMAT 기본값에서는 ORA-01861로 실패할 수 있음. `setDate()` 또는 `TO_DATE(?, 'YYYY-MM-DD')` 권장.
- [중간] `src/P3Dao확인/Insert메인.java:17~18`, `src/P2JDBC기본/JDBC기본2Insert.java:31~32` — 실제로 보이는 개인 전화번호(010-8033-…)·이메일·주소가 하드코딩. 더미 값으로 교체 필요.
- [낮음] `src/quest/Q1~Q4.java:9~12` — quest는 MySQL(`jdbc:mysql://localhost:3306/testdb`) 기준인데 동봉된 드라이버는 `src/필요한파일/ojdbc11.jar`(Oracle)뿐 → `No suitable driver` 오류. `.iml`에 ojdbc11.jar 라이브러리 등록도 없어 프로젝트를 열자마자 컴파일/실행 불가.
- [낮음] `src/quest/Q4.java:33~35` — 롤백 후 `setAutoCommit(true)` 복원 없음(문서 6절 예제는 복원함).
- [낮음] `src/P3Dao확인/SelectAll메인.java:7` — 미사용 `import java.util.TreeSet`.
- [낮음] `src/DAO/FreeBoardDAO.java:8~27` — 전부 스텁(return 0/null). 실습 과제로 보이나 문서 어디에도 "직접 채우는 과제"라는 안내가 없음.

### 문서-코드 불일치 / 환경 문제
- [중간] `docs/java_basic/ch15_JDBC.md:47,56` vs 소스 전체 — **문서는 H2 인메모리(+Gradle), quest는 MySQL, 강의 본 소스는 Oracle로 3종 DB가 혼재**. 소스 프로젝트는 Gradle이 아닌 IntelliJ 일반 프로젝트라 문서의 Gradle 안내가 실습 환경과 안 맞음. 기준 DB를 하나로 정하고 "IntelliJ에 jar 추가" 절차를 문서에 추가 권장.
- [중간] `docs/java_basic/ch15_JDBC.md:199~249`(문제 A~F) vs `src/quest` — quest는 Q1(=A-1), Q2(=B-1), Q3(=A-3), Q4(=C-1) 4개뿐. B-2/3, C-2/3, D, E, F 대응 코드 없음. 문서 예제는 users/student/account 테이블, 강의 소스는 member 테이블로 스키마도 불일치.
- [중간] 문서 전체 — 실제 소스 폴더 구조(P1JDBC개요/P2JDBC기본/P3Dao확인)나 `src/필요한파일`의 DDL 스크립트(member.sql 등)를 한 번도 언급하지 않음. "어떤 파일을 열고 어떤 SQL을 먼저 실행하라"는 연결 고리 필요.

### 초보자 관점
- [중간] `docs/java_basic/ch15_JDBC.md:35~57` — DB 설치(Oracle XE/MySQL, 테이블 생성) 안내가 전혀 없음. 이 챕터는 외부 환경 준비가 필수라 초보자가 2절에서 바로 막힘. `필요한파일/member.sql` 실행 안내 절 추가 권장.
- [낮음] `docs/java_basic/ch15_JDBC.md:37,44` — 소스는 전부 `Class.forName()`을 쓰는데 문서는 언급 없음. "JDBC 4.0+에서는 자동 로딩되어 생략 가능하나 전통적으로 이렇게 썼다" 한 줄 필요.
- [낮음] `docs/java_basic/ch15_JDBC.md:148~155`(8절 DAO) — DAO/Service/Controller 구조가 글로만 설명됨. 계층 다이어그램 보완 추천.

### 오타
- [낮음] `src/P2JDBC기본/JDBC기본2Insert.java:22`, `JDBC기본2Select.java:22`, `JDBC기본2SelectWhere.java:26` — 주석 "리 실행하는 객체" → "쿼리 실행하는 객체".
- [낮음] `src/P1JDBC개요/JDBC개요메인2.java:30` — "SQLExcetpion" → "SQLException".

---

## ch16_참고최신문법과스레드

- [중간] `docs/java_basic/ch16_참고최신문법과스레드.md:428~483`(문제 A~F) vs `src/quest` — quest는 Q1(=A-1·A-2), Q2(=A-3), Q3(=C-1), Q4(=C-3) 4개뿐. 특히 **D(동시성 제어: synchronized/Atomic/volatile), E(race condition/데드락 재현)는 문서 본문의 핵심 주제인데 대응 예제·quest 코드가 하나도 없음**(강의 소스에도 synchronized/ExecutorService 예제 파일 부재 — Part1_ThreadExample.java는 스레드 생성만 다룸).
- [중간] `src/Part1_ThreadExample.java:37~53` — 스레드 종료 대기를 `join()`이 아닌 `Thread.sleep(600)`으로 처리. 타이밍에 따라 섹션 출력이 섞이고 마지막 t5는 대기 없이 "메인 스레드 종료"가 먼저 찍힘. 문서 C-1이 join을 요구하고 문서 10절이 "시간 의존성 통제"를 강조하는 것과 모순 — join으로 교체 권장(의도적이라면 "출력이 섞이는 것을 관찰하라" 주석 필요).
- [낮음] `src/Part1_ThreadExample.java` 파일명 — Modern 예제와 스레드 예제가 둘 다 "Part1_" 접두사. 스레드는 Part2가 자연스러움.
- [낮음] `docs/java_basic/ch16_….md:305~310` — `LinkedBlockingQueue`는 기본 생성 시 사실상 무제한(Integer.MAX_VALUE)이라 "가득 차 있으면 대기" 설명이 기본 생성자 예제와 맞지 않음. `new LinkedBlockingQueue<>(10)`처럼 용량 지정 예제로 수정 권장.
- [낮음] `docs/java_basic/ch16_….md:50~63` — record(Java 16+), 텍스트 블록(15+) 등 기능별 최소 버전 표기 없음. 버전 병기 권장.
- [낮음] `docs/java_basic/ch16_….md:164` 이후 — 한 문서에 H1(`#`)이 4개로 구조가 평탄함. 내비게이션을 위해 H2로 낮추기 권장.
- 참고: 5.2절 "Java 17 preview → 21 정식화" 설명, race condition 예제, volatile 설명은 모두 기술적으로 정확. 이미지 3개 링크 정상.

---

## index / README

- [정상] `docs/java_basic/index.md:14~29` — ch1~ch16 링크 16개 모두 실제 파일과 일치.
- [낮음] `README.md:1~4` — java_basic 관련 링크가 전혀 없음(GitHub Pages URL 한 줄뿐). 저장소 첫 화면에서 java_basic 문서로 가는 경로가 없으므로 `docs/java_basic/index.md` 링크 추가 권장.

---

## 요약(시급 순)
1. ch15 소스의 **실 DB 접속 정보·개인정보 하드코딩 노출**
2. `throw` 누락으로 예외가 삼켜지는 버그 4개 파일
3. Map 매핑 예제의 key/value 오류
4. 문서(H2/Gradle)–quest(MySQL)–소스(Oracle) 3종 환경 혼재로 초보자가 실습을 시작조차 못 하는 문제
