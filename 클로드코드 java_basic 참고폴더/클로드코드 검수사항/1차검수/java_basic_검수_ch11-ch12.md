# java_basic 검수사항 — ch11 제네릭 / ch12 컬렉션

검수일: 2026-07-02
검수 범위: `docs/java_basic/ch11_제네릭.md`, `ch12_컬렉션.md` + `java_basic/java_class_ch11_제네릭`, `java_class_ch12_컬렉션` 소스 전체 + 이미지 폴더

두 소스 폴더 모두 JDK 17 컴파일 성공(ch11의 unchecked 경고는 raw type 시연용으로 의도된 것). 이미지 링크 5개(ch11 2개, ch12 3개) 모두 실존. ch12 문서 106행의 "ch6 Objects.equals/hash 예제 참고"는 유효한 상호 참조임을 확인.

---

## ch11_제네릭

### 기술적 오류
- [높음] `docs/java_basic/ch11_제네릭.md:126-129` — "직접 확인해 보자"며 제시한 예제가 실행하면 죽음. `List<? extends Number> nums = new ArrayList<Integer>();` 직후 `nums.get(0)`을 호출하는데 리스트가 비어 있어 `IndexOutOfBoundsException` 발생. 초보자는 이 예외를 extends 제약 때문이라고 오해하기 쉬움. 와일드카드 없는 `List<Integer>`에 값을 넣은 뒤 `List<? extends Number>`로 참조하는 형태로 수정 필요.
- [낮음] `docs/java_basic/ch11_제네릭.md:127` — "extends는 읽기 전용"은 과한 단순화. 정확히는 `add`가 금지(단 null은 예외)이고 `remove`/`clear` 같은 구조 변경은 가능. 각주 권장.
- [낮음] `docs/java_basic/ch11_제네릭.md:221` — 문제 A3 "raw type(`Box box`) 사용 시 경고 발생 이유": 경고는 선언 자체가 아니라 unchecked 호출/대입 시 발생. "raw type으로 사용할 때"로 표현 수정.

### 문서-코드 불일치
- [중간] `docs/java_basic/ch11_제네릭.md:211-259` vs `src/quest` — 문제는 A~F 6개 섹션(14개 이상 소문항)인데 quest는 Q1~Q4뿐(Q1=A1·A2, Q2=B1, Q3=C1, Q4=D1·D2 근사). B2, C2, D3, E(타입 소거) 전체, F(Cache 챌린지) 전체 대응 코드 없음. 또 Q3.java:6은 문서 C1이 요구한 상한 타입 파라미터(`<T extends Number>`)가 아닌 와일드카드(`List<? extends Number>`)를 사용해 출제 의도와 다른 개념을 시연.
- [중간] `src` 패키지 번호 오류 — P1제네릭기본, "P1제네릭기본2", P3, P4, P5로 **P2가 없고 P1이 두 개**. "P1제네릭기본2"는 "P2제네릭타입상속" 등으로 개명 필요.
- [중간] 문서 전체 — 문서가 실제 소스 폴더(P1~P5의 Box, Workable/Architect/Chef, Applicant/Course 예제)를 한 번도 언급하지 않음. 특히 5절 PECS는 P5와일드카드타입파라미터의 Course.register1/2/3 예제와 정확히 대응되므로 연결 필요.
- [중간] `docs/java_basic/ch11_제네릭.md:32,64,90,119,126` — 문서 예제 대부분이 `List`/`ArrayList` 기반인데 컬렉션은 다음 장(ch12)에서 배움. "List는 ch12에서 배우니 '값을 여러 개 담는 통' 정도로만 이해하라" 안내 필요.

### 소스코드 문제
- [낮음] `src/Main.java:1-2,6` — 미사용 import(List, Map). "제네릭은 예제없이 간다"는 주석이 P1~P5 예제가 실제 존재하는 것과 모순.
- [낮음] `src/P1제네릭기본/제네릭기본Main.java:15-16` — `int a=(Integer)box3.getContent();`에서 ClassCastException이 발생해 16행은 실행되지 않음. "런타임에 에러를 알게됨" 주석 위치를 15행으로 옮기고 항상 예외로 종료됨을 명시 권장.
- [낮음] `src/P5와일드카드타입파라미터/와일드카드타입파라미터Main.java:11` — 주석 "Applicant<>에서 <>따라 달라지는것까지 오버로딩X"는 타입 소거가 원인이라는 설명 없이 등장해 이해 불가. 문서 6절(타입 소거)과 연결 설명 필요.
- [낮음] `src/P1제네릭기본2/Architect.java:4` — 주석 "인터페이스에 제네릭이 전부 있기때문에 새로하면 안됨"은 부정확. `implements Workable<Building>`으로 T를 확정했기 때문에 다시 선언하지 않는 것.
- [낮음] `docs/java_basic/ch11_제네릭.md:152-154` — 타입 소거 우회(Class<T> 토큰, 팩토리)가 코드 예제 없이 두 줄 글로만 있음. 문제 E2가 이걸 요구하므로 짧은 예제 코드나 다이어그램 보완 권장.

---

## ch12_컬렉션

### 기술적 오류
- [높음] `docs/java_basic/ch12_컬렉션.md:149` — 8.2절 `Comparator.comparing(User::getAge).thenComparing(User::getName)`은 바로 위 8.1절(131-141행)에 정의된 `User`(age 필드만 있고 getter·name 없음) 기준으로 **컴파일되지 않음**. User에 name 필드와 getAge/getName을 추가하거나 `Comparator.comparingInt(u -> u.age)` 형태로 수정 필요.

### 문서-코드 불일치
- [중간] `docs/java_basic/ch12_컬렉션.md:200-257` vs `src/quest` — Q1=B1·B3 근사, Q2=B2, Q3=C1·C2, Q4=D 근사 대응이며 A(List), E(equals/hashCode), F(반복자), G(챌린지) 전체에 quest 코드 없음. E1·E2는 `셋/Person.java`+`셋해쉬코드main2.java`가 사실상 커버하는데 어디에도 연결이 없음. Q4.java:29-32는 "점수 내림차순+이름 오름차순"인데 문서 D2는 "이름 오름차순+나이 내림차순"을 요구해 기준이 다름.
- [중간] `docs/java_basic/ch12_컬렉션.md:112-121(7절), 127-150(8절), 154-163(9절)` — Iterator 안전 삭제, Comparable/Comparator, 불변 컬렉션에 대응하는 소스 예제가 전혀 없음(소스는 List/Set/Map 기본만 커버). 문서-수업 코드 범위 간극이 큼.
- [낮음] `docs/java_basic/ch12_컬렉션.md:257(G3)` — 문서 10절(168행)에서 "멀티스레드는 ch16에서 배운다"고 해놓고 챌린지 G3에서 멀티스레드 안전 집계를 요구. "ch16 학습 후 도전" 표시 필요.

### 소스코드 문제
- [중간] `src/리스트/타입선얼을제네릭List로해야하는이유Main.java`, `…Main2.java` — **파일명 오타 "타입선얼" → "타입선언"**. Main2는 Main의 활성 코드(9-18행)와 완전히 동일한 중복 파일이라 하나로 정리 필요.
- [중간] `src/Main.java:13-46` — 프로그래머스 "베스트앨범" 풀이가 `Collectors.groupingBy`, `mapToInt` 등 스트림(ch14 내용)을 사용해 ch12 수강생이 이해 불가. 클래스명 `Genere`(48행)는 `Genre` 오타. `sum2-sum1`(27행)·`o2.play-o1.play`(31행)의 뺄셈 비교는 오버플로 위험 패턴 — `Integer.compare()` 권장. 응용Main.java:10이 안내한 문제 목록에도 없는 문제.
- [중간] 빈 스텁 파일들 — `로또중복없이List메인.java`(주석 한 줄뿐), `LinkedListMain.java`(빈 main + 미사용 import), `해쉬예제/폰켓몬.java`·`완주하지못한선수.java`(빈 클래스). 라이브 코딩용이라면 TODO 주석으로 완성 조건 명시, 아니라면 풀이 채우기 필요.
- [낮음] `src/맵/맵Main.java:21` — `getOrDefault` 결과 변수 `희진`을 출력하지 않아 시연 효과 없음.
- [낮음] `src/리스트/Array리스트메인.java:28-30` — `set(3,"강해린"); set(4,"이혜인")` 이후이므로 실제 삭제되는 값은 "이혜인"/"강해린"인데 주석과 출력문은 "혜인/해린"으로 어긋남.
- [낮음] `src/리스트/Bunny.java:4` — 미사용 import `java.util.Iterator`.
- [낮음] `src/맵/응용Main.java:12` — "고생할 필요가 없쥐" 등 구어체 주석. 배포 자료라면 정리 권장.

### 이미지 보완 제안
- [낮음] 7절 ConcurrentModificationException(for-each 중 삭제 vs Iterator.remove 흐름), 8절 Comparable(내부 기준) vs Comparator(외부 주입) 비교는 글로만 설명됨 — 다이어그램 1장씩 추가 권장. 기존 3개 이미지는 모두 존재하고 배치 적절.
