# java_basic 검수사항 — ch7 상속 / ch8 인터페이스

검수일: 2026-07-02
검수 범위: `docs/java_basic/ch7_상속.md`, `ch8_인터페이스.md` + `java_basic/java_class_ch7_상속`, `java_class_ch8_인터페이스` 소스 전체 + 이미지 폴더

두 챕터 소스 전체 JDK 17 컴파일 통과. 이미지 링크 4개 모두 실제 존재. 문서 본문의 자바 기술 설명 자체에는 치명적 오류 없음(캐스팅/ClassCastException, super 첫 줄 규칙, 인터페이스 암묵적 public 등 모두 정확). 주요 문제는 **문서-소스 불일치와 소스 주석/네이밍 오류**에 집중됨.

---

## ch7_상속

### 문서-코드 불일치
- [높음] `docs/java_basic/ch7_상속.md` 본문 전체 — 문서 예제(Animal/Dog, Shape, Car/Engine)와 실제 소스(P1상속기본 Book/ChildrenBook/HorrorBook, P2타입변환 Animal/Cat, P3다형성 Idol/Vehicle, P4추상클래스 Pet)가 완전히 다른 체계이고, 문서 어디에도 소스 폴더/파일 참조가 없음. 초보자가 문서와 실습 코드를 연결할 수 없으므로 각 절에 대응 소스 경로 명시 필요.
- [중간] `docs/java_basic/ch7_상속.md:225~273`(문제 A~F) vs `src/quest` — quest는 Q1~Q4만 존재(Q1≈B1, Q2≈D1·D2, Q3≈C1, Q4≈C3). A(super/생성자 호출), B2~B4, C4(ClassCastException 재현), D3·D4, E(설계), F(챌린지)는 대응 코드가 전혀 없음.
- [중간] `docs/java_basic/ch7_상속.md:245` vs `src/quest/Q3.java:25` — 문제 C1은 "Dog, Cat, Bird 3종"을 요구하지만 Q3는 Cat/Dog만 사용(Bird 클래스 없음).
- [중간] `docs/java_basic/ch7_상속.md:272~273` — 문제 F2의 `final`, F3의 "리스코프 치환 원칙(LSP)"이 본문에 한 번도 설명되지 않은 채 문제로만 등장. 본문 10절 근처에 final 키워드와 LSP 한 단락 추가 필요.

### 소스코드 문제
- [중간] `src/P2타입변환/강제타입변환Main.java:11` — 실행하면 항상 ClassCastException으로 비정상 종료(실행 확인함). 의도된 데모지만 해당 줄을 주석 처리하고 "주석을 풀면 예외 발생"으로 안내하거나 instanceof 검사 버전을 함께 제시 권장. 8행 `Cat cat` 변수 선언 후 미사용.
- [낮음] `src/P1상속기본/상속기본Main.java:8` — 주석 오타 "봉인(seald)" → "sealed". 5행 "타입변화" → "타입변환".
- [낮음] `src/P1상속기본/상속기본Main.java:19~20` — 예제 데이터 오류: "그리고 아무도 없었다"의 저자는 애거사 크리스티인데 "에드거 어쩌구?"로 표기. 공포소설(HorrorBook) 예시로도 추리소설이라 어긋남.
- [낮음] `src/P1상속기본/Book.java:4~6` — 필드가 전부 public. ch6에서 캡슐화를 배운 직후라 퇴행으로 보일 수 있으니 "예제 단순화를 위해 public 사용" 주석 추가 권장.

### 문서 표현 / 이미지
- [낮음] `docs/java_basic/ch7_상속.md:70` — "`@Override`를 반드시 붙여 컴파일 타임 검증 권장": "반드시"와 "권장"이 모순. "항상 붙이는 것을 권장"으로 수정.
- [낮음] `docs/java_basic/ch7_상속.md:138~151(6절 super), 171~186(8절 조합)` — 글로만 설명됨. 부모→자식 생성자 호출 순서(super() 체인) 다이어그램과 상속 vs 조합 구조 비교 다이어그램 추가 권장(현재 ch7 이미지는 2장뿐).

---

## ch8_인터페이스

### 문서-코드 불일치
- [높음] `docs/java_basic/ch8_인터페이스.md` 본문 vs `src/Comparable`, `src/Comparator` — 소스 예제의 절반이 Comparable/Comparator(Student 정렬)인데 **문서 본문에는 Comparable/Comparator 설명이 전혀 없음**(챌린지 F1에서만 갑자기 등장). "자바 표준 인터페이스 예: Comparable/Comparator" 절을 본문에 추가하고 소스 경로 연결 필요.
- [중간] `docs/java_basic/ch8_인터페이스.md:221~224` vs `src/quest/Q1.java` — 문제 A1은 `play()`, `stop()` 두 메소드를 요구하지만 Q1의 Playable에는 `play()`만 있고, A4의 VideoPlayer 교체 확인도 없음.
- [중간] `docs/java_basic/ch8_인터페이스.md:238~259`(문제 C, D, E) — default/static 메소드(C), 함수형 인터페이스/람다/Predicate(D), PaymentService 설계(E)에 대응하는 quest/예제 소스가 전혀 없음. 특히 본문 4절에서 default/static을 비중 있게 다루면서 실습 코드가 하나도 없음.
- [중간] `docs/java_basic/ch8_인터페이스.md:265` vs `src/quest/Q4.java:16~18` — F1은 "Comparator로 이름/점수 기준 정렬"을 요구하지만 Q4는 Comparable 구현 + 점수 내림차순 단일 기준만 구현.

### 소스코드 문제
- [중간] `src/Comparator/Compartor메인.java:1~6` — 파일명·클래스명 오타: `Compartor메인` → `Comparator메인`. 8행 주석에도 "Compartor" 오타 반복.
- [중간] `src/Comparable`, `src/Comparator` 패키지 선언 — 패키지명이 대문자 시작이며 JDK 타입명(`java.lang.Comparable`, `java.util.Comparator`)과 동일. 컴파일은 되지만 자바 소문자 컨벤션 위반이고 초보자가 패키지와 인터페이스를 혼동하기 쉬움. `comparable_ex` 등으로 변경 권장.
- [낮음] `src/Comparable/Comparable메인.java:17~19` — 주석 "원래는 에러남. 왜? 객체를 정렬할 수 없음."은 컴파일 오류처럼 읽히지만 실제로는 Comparable 미구현 시 **런타임** ClassCastException. "상속받아"도 "구현하여(implements)"가 정확. 나이 기준 정렬이 내림차순이라는 사실도 주석에 빠짐.
- [낮음] `src/P1인터페이스/인터페이스Main.java:8,11` — 8행 "인터페이스는 다중상속 허용" → 정확히는 "다중 구현". 11행 주석은 문장이 깨져 의미 전달 불가 — 정리 필요.
- [낮음] `src/P2익명객체구현/익명객체구현Main.java:18` — 주석이 모호해 "익명 객체는 아무 일도 못 한다"로 오독 가능. "생성만으로는 sing()이 실행되지 않는다" 등으로 명확화 권장.

### 문서 표현 / 이미지
- [낮음] `docs/java_basic/ch8_인터페이스.md:39, 267` — "Mock/Fake", "전략 패턴"이 설명 없이 등장. 한 줄 정의 추가 권장.
- [낮음] `docs/java_basic/ch8_인터페이스.md:136~151`(7절) — 인터페이스 vs 추상 클래스 비교가 글로만 됨. 비교 다이어그램(또는 Idol이 Singable+Danceable을 구현하는 다중 구현 구조도) 추가 권장. 3.2절 다중 구현에서 default 메소드 충돌(다이아몬드) 시 재정의 필요 언급 없음.

---

## 총평
컴파일 오류·치명적 기술 오설명은 없음. 핵심 개선 포인트: (1) 문서-실습 소스 연결 고리 부재, (2) 문제(A~F) 대비 quest 커버리지 부족과 명세 불일치, (3) ch8의 Comparable/Comparator 본문 공백, (4) 소스 주석의 오타·부정확한 표현.
