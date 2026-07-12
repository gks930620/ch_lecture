# java_basic 2차 검수 — ch5 배열 / ch6 객체지향기초

검수일: 2026-07-02~03 (2차)
검증 방법: 전 파일 재독, javac(JDK 17) 전체 컴파일(ch5 26개·ch6 16개 전부 통과), **핵심 버그 런타임 재현**, 이미지 6개 실존 확인.

**현재 상태: 1차 지적 중 고쳐진 것 없음.** ch5/ch6은 1차 이후 미변경. 1차 최우선 버그 2건을 실행으로 재확인: `bubbleSort({5,4,3,2,1})` → `[4,2,1,3,5]`(미정렬), `pow(2,3)` → `256`, `pow(5,0)` → `5`.

---

## [1차 잔존] — 전부 미수정 확인 (요약)

### ch5
- [높음] `src/P9자료구조.java:18` — bubbleSort `j=i` 시작 버그 (런타임 재현 완료).
- [높음] 문서 전체 vs src — 주제 커버리지 불일치(P1메모리영역/P2NPE/P4String/P6Enum/P9정렬이 문서에 없고, 오버로딩·Shadowing은 소스 없음). 소스 참조 0건.
- [중간] `P7알아두면좋은메소드.java:7,10` 잘못된 주석(int[]에 reverseOrder 컴파일 불가, `Long.parse`) / `P2Null과NullpointerException3.java:7` 즉시 NPE로 뒤 예제 실행 불가(재현 완료) / B-3 재귀 문제 vs "재귀 안함" 소스 방침 충돌 / 문제 23문항 vs quest 4개 / 복사 용어 충돌(얕은/깊은 vs 참조/실제).
- [낮음] 오타류 전부 잔존: "eqauls", "화인", "replaceFisrt", "5X 2", `sellectionSort`, "가탇", import 미사용 다수, P3 번호 누락, 빈 과제 파일 등.

### ch6
- [높음] `src/p5/engineer/Calculator.java:11-16` — pow 버그 (런타임 재현: pow(2,3)=256, pow(3,2)=81, pow(5,0)=5).
- [중간] equals/hashCode 절 대응 소스 없음 / 패키지·import 주제가 문서에 없음(P5 main 빈 채) / 문제 D·E quest 없음, Q4(싱글톤)는 문서에 없는 주제 / 캡슐화 다이어그램 부재 / `p4정적멤버와fianl` 폴더명 오타.
- [낮음] `SingltonTest.java` 클래스명 오타+개발 메모, `사람사는Main.java` 출력 없음, Q2 예외 미사용, p2 번호 누락 등.

---

## [신규] 2차에서 새로 발견

- [신규][중간] `docs/java_basic/문제답안/` — **ch5/ch6 답안 문서 부재**(ch1/ch2/ch13/ch14만 존재). 문서 문제가 ch5 23문항·ch6 18문항인데 quest 4+4개 외 답안 공백 — 수정된 챕터들과 일관성 결여.
- [신규][중간] git 작업트리 — `java_basic/java_class_ch6_객체지향기초/ch6_객체지향기초/` 하위 `Part1_Person.java`~`Part5_BankAccount.java`, `과제_은행시스템.java` 6개 파일이 **미커밋 삭제 상태**. 특히 ch6 과제 파일이 사라져 과제 체계 공백. 의도된 삭제라면 커밋 필요.
- [신규][낮음] `src/P2Null과NullpointerException2.java:10` — 주석 해제 시연용 라인이 `Sycstem.out.println` 오타 — 주석을 풀면 예외 시연 대신 컴파일 오류. "ArraysOutof"도 부정확(`ArrayIndexOutOfBoundsException`).
- [신규][낮음] `src/P4String2.java:22-23` — "문자열 대체 replace…" 주석만 있고 실제 replace 시연 코드 없음. 37행에서 갑자기 replaceFirst 사용.
- [신규][낮음] `src/P5배열2.java:10` — 내부 루프가 `studentScores[0].length` 사용. 문서 373행의 "`arr[i].length` 사용" 권장과 정면 불일치.
- [신규][낮음] `docs/java_basic/ch5_배열.md:397` — "깊게 복사되지 **않을 수 있다**"는 부정확. clone/copyOf/arraycopy는 참조형 배열에서 요소 객체를 **절대** 깊게 복사하지 않음 — "않는다"로 단정 필요.
- [신규][낮음] ch5 `src/quest/Q1.java`, `Q2.java` — 문서 C-1은 "최댓값·최솟값·**합계**·평균" 요구인데 합계 출력이 어디에도 없음.
- [신규][낮음] ch6 `src/p6/P6접근제한자.java` — 실행해도 출력 전무, 주석 처리된 8~10행에 "컴파일 오류 확인용" 안내 없음.
- [신규][낮음] ch6 `src/quest/Q1.java` — 문서 A-4는 "인스턴스 3개 생성" 요구인데 1개만 생성.
- [신규][낮음] `src/P1메모리영역.java` vs `P1메모리영역2.java` — 사실상 동일한 중복 파일.

---

## 우선순위
1. **bubbleSort·pow 버그** — 1차 최우선 2건이 그대로 방치(런타임으로 오동작 재확인). 여전히 최우선.
2. P2NPE3 즉사 코드, 문서-소스 커버리지 불일치, 복사 용어 충돌.
3. ch5/ch6 답안 문서 부재, ch6 과제 파일 미커밋 삭제(신규).
4. 오타·미사용 import·빈 스텁·개발 메모는 일괄 정리 수준.
