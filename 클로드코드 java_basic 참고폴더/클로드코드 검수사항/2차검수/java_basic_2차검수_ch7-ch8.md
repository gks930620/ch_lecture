# java_basic 2차 검수 — ch7 상속 / ch8 인터페이스

검수일: 2026-07-02 (2차)
검증 방법: 소스 전체 JDK 17 `javac -Xlint:all` 컴파일(모두 통과), 주요 데모 직접 실행, 이미지 4개 실존 확인.

**현재 상태: 1차 지적 21건 전부 미반영(잔존).** ch7/ch8은 1차 검수 이후 실질 수정 없음. 답안 문서(`문제답안/`)에도 ch7/ch8 파일 없음.

---

## ch7_상속

### 1차 잔존
- [1차 잔존][높음] 문서 전체 — 문서 예제(Animal/Dog, Shape, Car/Engine)와 소스(Book/ChildrenBook, Idol/Vehicle, Pet) 체계가 완전히 다르고 소스 폴더 참조가 전무.
- [1차 잔존][중간] 문제 A~F vs `src/quest/` — Q1~Q4뿐. A, B2~B4, C4, D3~D4, E, F 대응 코드 없음.
- [1차 잔존][중간] `ch7_상속.md:245` vs `src/quest/Q3.java:25` — C1은 Dog/Cat/Bird 3종 요구, Q3는 Bird 없음.
- [1차 잔존][중간] `ch7_상속.md:272~273` — F2 `final`, F3 LSP가 본문 설명 없이 문제로만 등장.
- [1차 잔존][중간] `src/P2타입변환/강제타입변환Main.java:11` — 실행 시 항상 ClassCastException 비정상 종료(재실행 확인). 안내 주석 없음. 7행 `Cat cat` 미사용.
- [1차 잔존][낮음] `상속기본Main.java:5,8` 오타("타입변화", "seald") / 19~20행 저자 오류(애거사 크리스티) / `Book.java` 전 필드 public / 문서 70행 "반드시…권장" 모순 / super() 체인·상속 vs 조합 다이어그램 미추가.

### 신규 발견
- [신규][중간] `docs/java_basic/문제답안/` — ch7 답안 문서 부재. quest가 문제 일부만 커버하는 상황에서 학습자가 정답 확인 불가.
- [신규][낮음] `ch7_상속.md:129~131` — instanceof 패턴 매칭 예제가 `d.bark()`를 호출하지만 문서의 Dog 예제(57~63행)에는 `sound()`만 있고 `bark()`는 정의된 적 없음. 그대로 따라 치면 컴파일 오류 — `d.sound()`로 통일 권장.
- [신규][낮음] `ch7_상속.md:256` — 문제 D3 "Shape 타입 컬렉션으로 합계"에서 컬렉션(ch12)이 안내 없이 등장. ch8 문제에는 각주가 있는데 ch7 D3에는 없음 — 배열로 대체하거나 각주 필요.
- [신규][낮음] `ch7_상속.md:271` — F1 클래스명 `Character`가 `java.lang.Character`와 충돌. `GameCharacter` 등 권장.
- [신규][낮음] `src/P4추상클래스/Puppy.java:7` — "부끄처럼 걷습니다" 의미 불명 표현.

---

## ch8_인터페이스

### 1차 잔존
- [1차 잔존][높음] 문서 본문 vs `src/Comparable`, `src/Comparator` — 소스 절반이 Comparable/Comparator인데 본문에 설명 절이 여전히 없음.
- [1차 잔존][중간] 문제 A1 vs `src/quest/Q1.java:4~6` — `stop()` 누락, A4 VideoPlayer 교체 확인 없음.
- [1차 잔존][중간] 문제 C/D/E — default/static, Predicate, PaymentService 대응 소스 전무.
- [1차 잔존][중간] F1 vs `src/quest/Q4.java:16~18` — Comparator 이름/점수 정렬 요구 vs Comparable 단일 기준.
- [1차 잔존][중간] `src/Comparator/Compartor메인.java` — 파일명·클래스명·주석 "Compartor" 오타 그대로.
- [1차 잔존][중간] `src/Comparable/`, `src/Comparator/` — 패키지명 대문자 + JDK 인터페이스명과 동일.
- [1차 잔존][낮음] `Comparable메인.java:17~18` 주석 부정확(런타임 예외인데 "에러남", implements인데 "상속") / `인터페이스Main.java:8,11` "다중상속"·깨진 메모 / 익명객체 모호 주석 / Mock·전략 패턴 무정의 / 7절 다이어그램 없음·default 충돌 미언급.

### 신규 발견
- [신규][중간] `docs/java_basic/문제답안/` — ch8 답안 문서 부재.
- [신규][낮음] `src/Comparable/Student.java:10~12`, `src/Comparator/Student.java:10~12` — 필드 전부 public(ch7 Book과 동일한 캡슐화 퇴행). "예제 단순화" 주석 권장.

---

## 총평
컴파일 오류·치명적 기술 오설명 없음(실행 검증 포함). 1차 지적 21건 전부 미반영. 신규: ① ch7 문서의 `d.bark()` 미정의 예제(따라 치면 컴파일 오류) ② ch7/ch8 답안 문서 부재 ③ ch7 D3 선행 개념 무안내. 우선순위는 문서-소스 연결(높음 2건)부터.
