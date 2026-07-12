# java_basic 검수사항 — ch13 람다와함수형 / ch14 stream

검수일: 2026-07-02
검수 범위: `docs/java_basic/ch13_람다와함수형.md`, `ch14_stream.md` + `java_basic/java_class_ch13_람다와함수형`, `java_class_ch14_stream` 소스 전체 + 이미지 폴더

문서에서 참조하는 이미지 4개(ch13: lambda-functional-flow.svg, method-reference-map.svg / ch14: stream-pipeline-lifecycle.svg, map-vs-flatmap.svg)는 모두 존재하며 링크 깨짐 없음. 문서 내 코드 스니펫의 출력 예시(reduce 24, flatMap [a, b, c] 등)도 검증 결과 정확함. 아래는 발견된 문제점.

---

## ch13_람다와함수형

### 중간
- `docs/java_basic/ch13_람다와함수형.md:67-84, 130-138, 156-163` — 문서-코드 불일치. 문서의 핵심 축인 §3 표준 함수형 인터페이스(Predicate/Function/Consumer/Supplier), §6 함수 조합(andThen), §8 예외 처리에 대응하는 예제가 소스 폴더에 전혀 없음. 소스는 커스텀 인터페이스(P1, P2)와 메소드 참조(P3)까지만 다룸. 표준 함수형 인터페이스·andThen 데모 파일 추가 필요.
- `docs/java_basic/ch13_람다와함수형.md:193-246` vs `src/quest/Q1~Q4.java` — 문서의 문제 A~F 중 quest 코드는 A-1(Q1), B-1(Q2), B-2(Q3), C-1 유사(Q4)만 커버. D(함수 조합), E(실무 시나리오), F(챌린지)에 해당하는 코드가 없어 문제 세트와 실습 코드 범위가 어긋남.

### 낮음
- `src/P2람다만들기/Workable.java:3`, `Maxable.java:3`, `Printable.java:3`, `Singable.java:3`, `Sumable.java:3` — 문서 §2는 `@FunctionalInterface`를 강조하는데 P2의 인터페이스 5개에는 모두 미부착(Calculable만 부착). 교육 일관성을 위해 부착 권장. `Maxable`은 어떤 메인에서도 사용되지 않는 죽은 코드.
- `src/P2람다만들기/람다기본메인3.java:15` — 주석은 "() 생략가능. 매개변수가 1개일때만"인데 코드는 `(a)`로 괄호를 유지해 혼동 소지. 괄호 생략 버전을 나란히 보여주기 권장.
- `src/P1함수형프로그래밍과람다/함수형람다Main2.java:3-6` — ArrayList/Collections/Comparator/List 4개 import 전부 미사용. `메소드참조main.java:3(Workable), :7(Comparator)`도 미사용 import.
- `docs/java_basic/ch13_람다와함수형.md:130-138` — §6 함수 조합이 글+3줄 코드뿐. 데이터가 두 함수를 통과하는 파이프라인 다이어그램(" Hello " → trim → lower → "hello") 추가 권장. §7 부작용/불변성(144-152행)도 "외부 상태를 건드리는 람다 vs 순수 람다" 비교 그림 후보.
- `docs/java_basic/ch13_람다와함수형.md:80-83` — `isBlank()`(Java 11+), `UUID`가 설명 없이 등장. 한 줄 주석 필요.

---

## ch14_stream

### 높음
- `java_basic/java_class_ch14_stream/src/P3스트림최종처리/Human.java:16, 23-27` — Person을 복사한 뒤 수정을 빠뜨린 복붙 버그.
  1. `toString`이 `"Person{"`을 반환해 `스트림4요소수집.java:29`의 humanList 출력이 전부 `Person{...}`으로 찍힘(학습자가 변환 결과를 오해).
  2. `equals`가 `o instanceof Person person`을 검사해 Human끼리 비교하면 항상 false — Human에 distinct를 쓰면 중복 제거가 절대 동작하지 않음.
  → `"Human{"` / `instanceof Human`으로 수정 필요.

### 중간
- `src/P3스트림최종처리/스트림3요소커스텀집계.java:17-18` — `reduce(0, (a, b) -> a/10*10 + b/10*10)`은 누적값 a에도 절삭을 적용하는 비결합적(non-associative) 누산기로 reduce 계약 위반. 이 데이터에서는 우연히 80이 나오지만 parallelStream으로 바꾸면 결과가 달라질 수 있음. `map(p -> p.age/10*10).reduce(0, Integer::sum)` 형태로 수정 권장.
- `src/P3스트림최종처리/스트림4요소수집.java:3` — 같은 패키지에 Person이 있는데 `import P2스트림중간처리.Person;`으로 다른 패키지의 동명 클래스를 가져옴. 컴파일은 되지만 초보자에게 매우 혼란스러운 구조. 또한 33행 groupingBy 결과를 출력하는 코드가 없어 실행해도 그룹핑 결과를 볼 수 없음.
- `src/Solution.java:1` — `import com.sun.jdi.Value;` 디버거 내부 API를 미사용 import. 파일 자체도 `스트림연습문제/베스트앨범.java`와 중복이고 main이 없으며, 34-40행의 외부 리스트에 forEach로 누적하는 방식은 문서 §9 "부작용 피하기"에서 지양하라고 한 패턴 그대로. 삭제 또는 통합 권장.
- `src/P1스트림이란/스트림메인.java:3` — `import jdk.jfr.Period;` (Java Flight Recorder 어노테이션) 미사용·무관 import. 삭제 필요.

### 낮음
- `src/P3스트림최종처리/스트림2요소조건만족메인.java:16-21` — 조건은 `age > 10`(초과)인데 출력 문구는 "모두 10살 이상"(이상 = >=). `>= 10`으로 바꾸거나 문구를 "10살 초과"로. 18행 `> 20` / "20 살 이상"도 동일한 불일치.
- 주석 오타: `스트림2필터링메인.java:17` "distcinct"→distinct, `스트림3요소기본집계.java:18` "avreage"→average, :25 "단수히"→단순히 / "도우ㅏ줌"→도와줌, `스트림메인.java:15` "이럴땐는"→이럴 때는.
- `src/P3스트림최종처리/스트림3요소기본집계.java:17` — 주석이 findFirst를 "요소 집계"로 분류. findFirst는 탐색 연산(문서 ch14 98행도 탐색으로 분류)이라 문서와 소스 주석이 어긋남.
- `docs/java_basic/ch14_stream.md:104-113` — `Function.identity()`, `Collectors.counting()`이 설명 없이 첫 collector 예제에 등장. "identity(): 요소를 그대로 키로 사용" 정도의 주석 필요. groupingBy가 키별 버킷을 만드는 과정은 다이어그램(요소들 → 키 분류 → Map) 추가 후보.
- `docs/java_basic/ch14_stream.md:117-131, 134-144` — §7 Optional, §8 병렬 스트림에 대응하는 소스 예제 없음(주석 처리된 orElse만 존재). 문서 문제 E-3(병렬 시간 측정), F(챌린지)도 quest에 대응 코드 없음(quest Q1~Q4는 A-1, A-2, D-2, 상위3 평균만 커버).

---

## 총평
문서의 기술적 설명 자체는 정확하고 이미지 링크도 모두 유효함. 가장 시급한 것은 **ch14 Human.java의 복붙 버그(잘못된 toString/equals)**, 그다음으로 reduce 비결합 예제와 무관한 import 2건(jdk.jfr, com.sun.jdi) 정리, 문서(표준 함수형 인터페이스·Optional·병렬)와 실습 소스 간 커버리지 격차 해소.
