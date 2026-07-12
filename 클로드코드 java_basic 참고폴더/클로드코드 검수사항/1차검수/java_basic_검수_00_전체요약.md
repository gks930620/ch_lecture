# java_basic 강의 검수 — 전체 요약

검수일: 2026-07-02
검수 범위: `docs/java_basic/` 문서 16개 챕터 + index.md + `java_basic/` 소스 프로젝트 16개(자바 파일 284개) + 이미지 폴더 전체

챕터별 상세 내용은 같은 폴더의 `java_basic_검수_chN-chM.md` 파일 참고.

## 전체 상태 한눈에 보기

- **이미지 링크**: 전 챕터에서 문서가 참조하는 이미지가 모두 실제 존재. 깨진 링크 없음.
- **컴파일**: 검증한 소스(ch3~4, 7~12 등)는 JDK 17 기준 컴파일 오류 없음. 단, 화살표 switch 등 때문에 Java 14+ 필수 — 수강생 환경 안내 필요.
- **문서의 자바 설명**: 대체로 정확함. 단 아래 "즉시 수정" 항목의 예외 있음.
- **가장 큰 구조적 문제**: 거의 모든 챕터에서 ① 문서가 실습 소스 파일을 한 번도 참조하지 않아 연결이 끊겨 있고, ② 문서의 연습문제(A~F 문항) 대비 quest 풀이가 4개 안팎만 존재하며 어느 문항의 답인지 표시가 없음.

## 즉시 수정 필요 (높음)

| # | 위치 | 문제 |
|---|------|------|
| 1 | ch15 `MemberDAO.java`, `P2JDBC기본/*` | **실제 원격 DB 접속 정보(호스트/계정/비밀번호)와 실제 개인 전화번호·이메일이 하드코딩되어 공개 저장소에 노출** — 보안 문제, 최우선 조치 |
| 2 | ch15 `JDBC기본2Select에서매핑*.java`, `MemberDAO.java:18` | `new RuntimeException(e);` 앞 `throw` 누락 4곳 — 예외가 조용히 삼켜짐 |
| 3 | ch15 `JDBC기본2Select에서매핑.java:33~37` | Map의 key 자리에 컬럼명 대신 컬럼 값을 넣는 버그 |
| 4 | ch14 `P3스트림최종처리/Human.java` | Person 복붙 버그 — toString이 "Person{" 출력, equals가 `instanceof Person` 검사라 Human의 distinct가 절대 동작 안 함 |
| 5 | ch6 `p5/engineer/Calculator.java:11-16` | `pow(2,3)`이 8이 아닌 256을 반환하는 로직 버그 |
| 6 | ch5 `P9자료구조.java:17-22` | bubbleSort 내부 루프가 `j=i`에서 시작해 정렬이 안 되는 버그 |
| 7 | ch10 `수학복권만들기Main.java` | "중복없이" 주석과 달리 한 조합 안에 중복 숫자 가능 |
| 8 | ch10 `Object/Object메인.java:21-22` | hashCode 주석이 현재 코드(재정의됨)와 정반대 설명 |
| 9 | ch11 문서 126-129행 | 와일드카드 예제가 빈 리스트에 get(0) 호출 → 실행하면 IndexOutOfBoundsException |
| 10 | ch12 문서 149행 | 8.2절 Comparator 예제가 8.1절 User 정의 기준으로 컴파일 불가 |
| 11 | ch4 `P2switch문2.java:8` | "switch변수에는 타입 뭐가 오든 상관없음" — 명백히 잘못된 규칙 주석 |
| 12 | ch9 문서 vs 소스 | 사용자 정의 예외를 문서는 unchecked(RuntimeException), 소스는 checked(Exception)로 만들어 서로 반대 |
| 13 | ch10 문서 vs 소스 | 소스의 Object/Wrapper/Math/리플렉션이 문서에 없고, 문서의 I/O는 소스가 없음 — 주제 구성 자체가 어긋남 |
| 14 | ch5 문서 vs 소스 | 소스의 String/null·NPE/Enum/정렬이 문서에 없음 — 커버리지 불일치 |
| 15 | ch7·ch8 문서 vs 소스 | 문서 예제와 소스 예제가 완전히 다른 체계 + ch8 소스 절반(Comparable/Comparator)이 문서 본문에 없음 |

## 공통(챕터 횡단) 문제

1. **문서 ↔ 실습 소스 연결 부재**: 문서가 소스 폴더/파일명을 참조하지 않음. 각 절에 "실습: src/…" 경로 표기 권장.
2. **연습문제 ↔ quest 불일치**: 전 챕터에서 문서 문제(A~F, 챕터당 14~23문항) 대비 quest는 4개 안팎이며 어느 문항의 답인지 표시 없음. 매핑 주석 추가 또는 문항 수 조정 필요.
3. **선수 개념 순서 문제**: ch2 문제에 for문(ch4)·배열(ch5), ch3 문제에 for문, ch4 문제에 배열, ch9에 파일 I/O(ch10), ch11에 List(ch12) 등 아직 안 배운 개념이 예고 없이 등장. "chN 학습 후 도전" 표기 필요.
4. **개발 메모/구어체 주석 노출**: "굳이 실습하지는 말자", "블로그 글 고치자", 안내사항.txt 등 강사 개인 메모가 배포 소스에 그대로 남아 있음.
5. **미사용 import·빈 스텁 파일·IDE 템플릿 잔재**: 다수 챕터에서 발견(상세는 각 파일 참고).
6. **파일/패키지 번호 체계 혼란**: ch3(P6 중복), ch5(P3 없음), ch6(p2 없음, `fianl` 오타 폴더), ch11(P2 없음·P1 두 개), ch12("타입선얼" 파일명 오타) 등.
7. **이미지 보완 후보**(참고내용.md의 "글로만 된 설명은 이미지로" 방침 관련): ch3 오버플로우 wrap-around, ch4 break vs continue 흐름, ch5 2차원 배열(배열의 배열), ch6 캡슐화 구조, ch7 super() 생성자 체인, ch8 인터페이스 vs 추상클래스, ch9 try-catch-finally 실행 순서, ch10 StringBuilder 원리, ch12 Iterator 안전 삭제·Comparable vs Comparator, ch13 함수 조합 파이프라인, ch14 groupingBy 버킷, ch15 DAO 계층 구조.

## 챕터별 검수 파일

- [ch1-ch2](java_basic_검수_ch1-ch2.md) — HelloWorld 파일 부재, Scanner 문서 누락, 주석 오타 다수
- [ch3-ch4](java_basic_검수_ch3-ch4.md) — switch 타입 주석 오류(높음), quest 챕터 배치 오류, do-while 소스 부재
- [ch5-ch6](java_basic_검수_ch5-ch6.md) — bubbleSort/pow 버그(높음), 문서-소스 커버리지 불일치, 복사 용어 충돌
- [ch7-ch8](java_basic_검수_ch7-ch8.md) — 문서-소스 예제 체계 상이, Comparable/Comparator 문서 공백
- [ch9-ch10](java_basic_검수_ch9-ch10.md) — 사용자 정의 예외 방침 충돌, ch10 주제 구성 어긋남, 로또 중복 버그
- [ch11-ch12](java_basic_검수_ch11-ch12.md) — 문서 예제 2건 실행/컴파일 불가(높음), 파일명 오타, 빈 스텁
- [ch13-ch14](java_basic_검수_ch13-ch14.md) — Human.java 복붙 버그(높음), reduce 비결합 예제, 무관 import
- [ch15-ch16 + index](java_basic_검수_ch15-ch16_index.md) — **DB 접속 정보 노출(최우선)**, throw 누락, 3종 DB 환경 혼재
