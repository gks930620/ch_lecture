# java_basic 2차 검수 — ch15 JDBC / ch16 참고최신문법과스레드 / index·README

검수일: 2026-07-02 (2차)

**현재 상태: 1차 지적 중 고쳐진 항목 없음.** ch15/ch16 소스·문서, index.md, README.md 모두 1차 검수 시점과 동일. 특히 보안 관련 [높음] 4건이 그대로 남아 있어 **이 챕터가 전체에서 가장 시급함**.

---

## 1차 잔존 (시급순)

- [1차 잔존][**높음**] **실 DB 접속 정보 하드코딩 노출** — `jdbc:oracle:thin:@nextit.or.kr:1521:xe` / 계정 `std225` / 비밀번호 `oracle21c`가 여전히 8곳 이상에 존재: `java_basic/java_class_ch15_JDBC/src/P2JDBC기본/JDBC기본1.java:24`, `JDBC기본2Insert.java:20`, `JDBC기본2Select.java:20`, `JDBC기본2SelectWhere.java:24`, `JDBC기본2SelectWhere2Pstmt.java:24`, `JDBC기본2Select에서매핑.java:24`, `…매핑DTO.java:23`, `…매핑DTO2.java:25`, `src/DAO/MemberDAO.java:82,113,143,174,199`
- [1차 잔존][**높음**] **`new RuntimeException(e)` 앞 `throw` 누락(예외 삼킴)** — `JDBC기본2Select에서매핑.java:14,44`, `…매핑DTO.java:14,40`, `…매핑DTO2.java:16,44`, `MemberDAO.java:18` 모두 그대로.
- [1차 잔존][**높음**] **Map 매핑 key/value 버그** — `JDBC기본2Select에서매핑.java:33~37` 여전히 key에 컬럼 값이 들어감.
- [1차 잔존][**높음**] `JDBC기본1.java:19~35` — 리소스를 열고 finally/try-with-resources 없이 종료(누수). 문서 7절과 모순.
- [1차 잔존][중간] 개인정보 하드코딩 — `P3Dao확인/Insert메인.java:17~18`(전화번호, naver 이메일), `JDBC기본2Insert.java:31`(동일 전화번호).
- [1차 잔존][중간] Oracle DATE 컬럼에 setString — `Insert메인.java:13`, `Update메인.java:13` (NLS 설정에 따라 ORA-01861 가능).
- [1차 잔존][중간] SQL 인젝션 경고 주석 없음 — `JDBC기본2SelectWhere.java:27~28`.
- [1차 잔존][중간] DB 환경 3종 혼재 — 문서(H2+Gradle) vs quest(MySQL) vs 본 소스(Oracle). `.iml`에 ojdbc11.jar 등록 없음, quest용 MySQL 드라이버 부재.
- [1차 잔존][중간] 문제-quest 불일치 — ch15 quest 4개만 대응. ch16 D(동시성 제어)·E(데드락 재현) 대응 코드 전무.
- [1차 잔존][중간] ch15 문서에 DB 설치·`필요한파일/member.sql` 실행 안내·소스 폴더 구조 언급 없음.
- [1차 잔존][중간] ch16 `Part1_ThreadExample.java:37~53` — `join()` 대신 `Thread.sleep(600)`, t5는 대기 없이 "메인 스레드 종료" 먼저 출력.
- [1차 잔존][낮음] ch15 `quest/Q4.java:33~36` 롤백 후 auto-commit 미복원 / `SelectAll메인.java:7` 미사용 import / `FreeBoardDAO.java` 스텁 안내 없음 / 오타("리 실행하는 객체" 3곳, "SQLExcetpion") / `Class.forName()` 설명 없음 / ch16 LinkedBlockingQueue 기본 생성자 예제 / 버전 표기 없음 / H1 4개 평탄 구조 / 파일명 둘 다 "Part1_" / README에 java_basic 링크 부재.

## 신규 발견

- [신규][중간] **index.md가 문제답안 폴더를 안내하지 않음** — `docs/java_basic/index.md:12~29`에 챕터 링크 16개뿐, `문제답안/` 링크 없음. 답안 문서가 있어도 학습자가 도달할 경로가 없음.
- [신규][중간] **ch15/ch16 문제답안 문서 부재** — `문제답안/`에는 ch1·ch2·ch13·ch14만 존재. ch15(199~259행)·ch16(428~491행)에 문제 A~F가 있는데 답안 없음(ch3~ch12도 동일).
- [신규][낮음] **개인정보 노출 범위 확대** — 1차 지적 외 추가 위치: `Insert메인.java:10`(실계정 ID `gks930620`), `:12`(실명), `Update메인.java:18`(gmail 주소), `필요한파일/member_insert더미데이터.sql:7`(실명), `free_board_insert더미데이터.sql:6`(실명).
- [신규][낮음] `Insert메인.java:10` — mem_id가 고정 상수라 두 번 실행하면 ORA-00001(unique constraint). 시퀀스 사용 또는 안내 주석 필요.
- [신규][낮음] `docs/java_basic/ch15_JDBC.md:137` — 7절 예제가 미정의 변수 `ds`(`ds.getConnection()`)를 사용하는데 DataSource는 9절에서 처음 소개됨. "DataSource는 9절에서" 한 줄 안내 필요.
- [신규][낮음] `docs/java_basic/ch16_참고최신문법과스레드.md:306` — `queue.put()`의 "가득 차 있으면 **빌 때까지** 대기"는 부정확(빈자리가 하나 생길 때까지 대기가 정확).

## 정상 확인
- 이미지 ch15 2개·ch16 3개 모두 실존, 경로 일치.
- index.md의 챕터 링크 16개 모두 실제 파일과 일치.
- ch16 문서의 기술 설명(race condition, volatile, switch 패턴 매칭 버전 이력)과 quest 4개 코드는 기술적으로 정확, 컴파일 문제 없음.
