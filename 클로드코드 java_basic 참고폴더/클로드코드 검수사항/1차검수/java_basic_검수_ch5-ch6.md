# java_basic 검수사항 — ch5 배열 / ch6 객체지향기초

검수일: 2026-07-02
검수 범위: `docs/java_basic/ch5_배열.md`, `ch6_객체지향기초.md` + `java_basic/java_class_ch5_배열`, `java_class_ch6_객체지향기초` 소스 전체 + 이미지 폴더

이미지 링크는 ch5 4개, ch6 2개 모두 실제 파일 존재 확인(정상).

---

## ch5_배열

### 기술적 오류 / 소스 버그
- [높음] `java_basic/java_class_ch5_배열/src/P9자료구조.java:17-22` — `bubbleSort`의 내부 루프가 `for(int j=i ; j<arr.length-i-1 ; j++)`로 j를 i에서 시작해, `{5,4,3,2,1}` 입력 시 `[4,2,1,3,5]`처럼 **정렬이 되지 않음**. 표준 버블 정렬은 `j=0`에서 시작해야 함. 현재 main에서 호출되지 않아 드러나지 않지만 학생이 참고하면 잘못 배움.
- [중간] `src/P7알아두면좋은메소드.java:7` — 주석의 `Arrays.sort(arr1, Collections.reverseOrder())`는 `int[]`에는 컴파일되지 않음(`Integer[]` 등 객체 배열만 가능). 학생이 그대로 따라 치면 컴파일 오류. 같은 파일 10행 `Long.parse(문자열)`도 실제 메소드명은 `Long.parseLong`.
- [중간] `src/P2Null과NullpointerException3.java:7` — 첫 번째 `if(c.isEmpty() && c!=null)`에서 즉시 NPE로 프로그램이 죽어, 정작 보여주려는 "올바른 순서" 예제(10행)와 `Integer a+3` 예제(16행)는 영원히 실행되지 않음. 잘못된 줄은 주석 처리하고 하나씩 해제하며 시연하도록 안내 주석 필요.
- [낮음] `src/P2Null과NullpointerException.java:23` — `b.length();`가 주석 없이 살아 있어 실행하면 항상 NPE로 종료. 의도된 시연이라면 "실행 시 NPE 발생" 주석 필요. 1~4행 import 4개 전부 미사용.
- [낮음] `src/P1메모리영역.java:9` — "참조타입= literal 제외하고 전부"는 부정확(기본형 8종을 제외한 타입이 참조형이며, String 리터럴도 참조형 객체). 1행 `LocalDateTime` import 미사용.

### 문서-코드 불일치
- [높음] `docs/java_basic/ch5_배열.md` 전체 vs src 폴더 — 문서는 "메소드/스코프 + 배열"만 다루는데, 실제 수업 소스의 절반 이상이 문서에 없는 주제(P1메모리영역, P2Null/NPE, P4String 비교·메소드, P6Enum, P9자료구조/정렬)를 다룸. 반대로 문서의 오버로딩(151행), Shadowing(198행)은 대응 소스가 없음. 문서에 소스 파일 참조가 하나도 없어 연결이 끊김. 문서 목차를 소스 커버리지에 맞추거나 소스 파일 매핑 표 추가 권장.
- [중간] `docs/java_basic/ch5_배열.md:494` vs `src/P8재귀메소드맛보기.java:5` — 문서 연습문제 B-3은 "재귀 팩토리얼 작성"인데 소스 주석은 "재귀메소드 안함. 어려움"이라며 비워둠. 강의 방침과 문제가 충돌.
- [중간] `docs/java_basic/ch5_배열.md:475-539` vs `src/quest/Q1~Q4` — 문서 문제는 A~F 23문항인데 quest 풀이는 4개뿐(Q1=최대/최소, Q2=평균, Q3=역순, Q4=로또). A(메소드), B(스코프), E(복사), F(챌린지) 전체가 풀이 없음.
- [중간] `src/Part3_ArrayCopy.java:11,21` vs `docs/java_basic/ch5_배열.md:377-399` — **복사 용어 충돌**. 코드는 `int[] shallow = original`(참조 복사)을 "얕은 복사", 요소 복사를 "깊은 복사"라 부르는데, 문서(및 array-copy-shallow-deep.svg)는 "참조 복사 vs 실제 복사"로 구분. 두 자료를 같이 보면 혼란 — 용어 통일 필요.
- [낮음] `src/과제_로또번호생성기.java:1-6` — 빈 TODO 파일인데 동일 과제가 quest/Q4.java에 이미 구현됨. 하나로 정리 권장.
- [낮음] `src/Week.java:3` — "상수에 대해서는 6장에서"라고 했으나 ch6 문서에는 상수/final 절이 없음.

### 초보자 관점
- [중간] `docs/java_basic/ch5_배열.md:123-127` — "레코드 반환", "컬렉션 반환", "출력 파라미터 패턴" 등 미학습 개념(record는 ch16, 컬렉션은 ch12)이 설명 없이 등장. "나중에 배운다" 주석 필요.
- [중간] `docs/java_basic/ch5_배열.md:355-373` — 2차원 배열이 "배열의 배열"이라는 핵심(외부 배열 요소가 내부 배열의 참조)을 글로만 설명. jagged 구조 다이어그램 추가 권장(현재 ch5 이미지 4개 중 다차원 배열 그림 없음).
- [낮음] `docs/java_basic/ch5_배열.md:17,75` — "부작용(side effect)", "함수형 스타일" 용어가 정의 없이 사용됨.
- [낮음] `docs/java_basic/ch5_배열.md:139-141` — `sumOf`가 null 검사 없이 `arr.length` 접근. 같은 문서 7.2절/10절의 "null 먼저 검사" 원칙과 어긋남.

### 오타/표기
- [낮음] `src/P4String.java:24` — 출력 문자열 `"member1.eqauls(member1New)"` → equals 오타(실행 화면에 노출). import 미사용 2건, Scanner 미종료.
- [낮음] `src/P4String2.java:11,22` — "화인"→"확인", "replaceFisrt"→"replaceFirst".
- [낮음] `src/P5배열2.java:7` — 주석 "5X 2 배열"은 실제 코드 `new int[5][3]`과 불일치("5X3"이어야 함).
- [낮음] `src/P9자료구조.java:23` — 메소드명 `sellectionSort` → `selectionSort` 오타.
- [낮음] `src/P6Enum.java:4` — "것 가탇"→"것 같다".
- [낮음] `src/P5배열.java:1-7` — import 6개 전부 미사용(`throws IOException`도 불필요). 24-27행에서 sum을 계산만 하고 출력하지 않아 시연 효과 없음.
- [낮음] `src/정렬/선택정렬2.java:8-10` — 시간/윤년 관련 주석은 ch4 잔재로 파일 내용과 무관.
- [낮음] `src/` — 파일 번호가 P1,P2,P4…로 P3이 없음(순서 혼동 소지).

---

## ch6_객체지향기초

### 기술적 오류 / 소스 버그
- [높음] `java_basic/java_class_ch6_객체지향기초/src/p5/engineer/Calculator.java:11-16` — `pow(a,b)` 로직 오류. 루프에서 `a *= a`로 매번 제곱하므로 `pow(2,3)`은 8이 아니라 256을 반환하고, `b=0`이면 1이 아닌 a를 반환. Javadoc(`@return a^b`)과 실제 동작이 다름. `int result=1; for(...) result*=a; return result;`로 수정 필요.
- [낮음] `src/p3클래스와객체필드생성자메소드기본/사람사는Main.java:4-14` — 실행해도 아무 출력이 없어 필드 변화(earn/giveMoney 결과)를 확인할 수 없음. 결과 출력 추가 권장.

### 문서-코드 불일치
- [중간] `docs/java_basic/ch6_객체지향기초.md:173-204` — 문서의 핵심 절인 `==` vs `equals`/`hashCode` 재정의에 대응하는 ch6 소스 예제가 전혀 없음. User equals/hashCode 예제 파일 추가 권장.
- [중간] `src/p5`(패키지와 import) — 소스는 동일 이름 클래스(basic/engineer Calculator)로 패키지 개념을 가르치는데, **문서에는 패키지/import 절이 아예 없음**. 초보자 필수 개념이므로 문서에 절 추가 필요. `P5패키지와import.java`의 main도 비어 있어 실제 import 시연 코드가 없음.
- [중간] `docs/java_basic/ch6_객체지향기초.md:267-314` vs `src/quest` — 문서 문제 D(Order/OrderItem 설계), E(불변 Money, equals/hashCode)는 quest 풀이가 없고, 반대로 quest/Q4.java(싱글톤)는 문서 문제 목록에 없는 주제(소스 p8 주석도 "설명하지 말자"라며 스킵 선언). 문제-풀이 목록 정합 필요.
- [낮음] `docs/java_basic/ch6_객체지향기초.md:284-296` vs `src/quest/Q2.java` — 문서 B-3은 "잔액 부족·음수 금액 시 예외를 던지도록"인데 Q2는 예외 없이 boolean 반환이고 음수 입금 검증도 없음. C-1/C-2(생성자 오버로딩, `this(...)`)도 quest에 없음.

### 초보자 관점
- [중간] `docs/java_basic/ch6_객체지향기초.md:87-116` — 캡슐화 절이 글+코드만으로 구성. "외부 → public 메소드(검증) → private 필드" 접근 차단 구조 다이어그램 추가 권장(현재 ch6 이미지 2개뿐, 캡슐화 그림 없음).
- [낮음] `docs/java_basic/ch6_객체지향기초.md:204` — `HashSet`, `HashMap`이 설명 없이 등장. "ch12에서 배운다" 예고 필요.
- [낮음] `docs/java_basic/ch6_객체지향기초.md:169, 214` — "동시성", "스레드 안전" 용어가 정의 없이 사용됨.
- [낮음] `src/` — 폴더 번호가 p1, p3, p4…로 p2가 없음(순서 혼동 소지).

### 오타/표기
- [중간] `src/p4정적멤버와fianl` — 패키지/폴더명 오타 `fianl` → `final`. 패키지 선언(P4정적멤버.java:1)에도 그대로 들어가 있어 눈에 잘 띔.
- [낮음] `src/p8/SingltonTest.java:3` — 클래스명 오타 `Singlton` → `Singleton`.

---

## 종합 우선순위
1. ch6 engineer `Calculator.pow` 버그, ch5 `bubbleSort` 버그 — 잘못된 지식을 그대로 가르칠 수 있어 최우선 수정 대상.
2. ch5 문서-소스 주제 커버리지 불일치(String/null/enum이 문서에 없음), 복사 용어(얕은/깊은 vs 참조/실제) 통일.
3. 나머지는 오타·미사용 import·빈 파일 정리 수준.
