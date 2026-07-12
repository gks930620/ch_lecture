# java_basic 검수사항 — ch9 예외처리 / ch10 자바API

검수일: 2026-07-02
검수 범위: `docs/java_basic/ch9_예외처리.md`, `ch10_자바API.md` + `java_basic/java_class_ch9_예외처리`, `java_class_ch10_자바API` 소스 전체 + 이미지 폴더

두 챕터 소스 전체 JDK 17 컴파일 성공(경고 2건씩). 이미지 링크 6개(ch9 2개, ch10 4개) 모두 실존. ch9 문서의 출력 예시("/ by zero"), ch10의 FileReader/텍스트 블록/정규식 예시는 모두 정확함을 확인.

---

## ch9_예외처리

### 문서-코드 불일치
- [높음] `docs/java_basic/ch9_예외처리.md:231-283` vs `src/quest` — 문서 연습문제는 A~F 총 19문항인데 quest 코드는 Q1~Q4 4개뿐이고 어느 문항의 답인지 표시도 없음(Q1=A1, Q3=D1~D3, Q4≈C2, Q2는 B와 느슨하게 대응). A2, A3(NumberFormatException), A4(finally), B3, C1/C3, E, F는 대응 코드가 전혀 없음. 문항 번호를 quest 파일에 주석으로 명시하고 누락 문항을 보충하거나 문항 수 축소 필요.
- [높음] 문서 전체 vs src 폴더 — 문서가 실습 소스를 한 번도 언급하지 않음. 특히 **문서 8절(169행)은 사용자 정의 예외를 `extends RuntimeException`(unchecked)으로 만들고, 소스 `사용자정의예외/InsufficientException.java:5`는 `extends Exception`(checked)으로 만들어 서로 반대**. 어느 쪽을 표준으로 할지 통일하고, 다르게 둘 거면 차이를 문서에서 설명 필요.

### 초보자 관점 / 순서 문제
- [중간] `docs/java_basic/ch9_예외처리.md:55,132-134,152-155,248-258` — `Path`, `Files.readString`, `BufferedReader` 등 파일 I/O가 ch10보다 먼저 등장. "파일 읽기는 ch10에서 배운다, 지금은 '실패할 수 있는 외부 작업'으로만 보면 된다" 안내 필요. 문제 B1, C1~C3도 I/O 선행 지식 요구.
- [중간] `docs/java_basic/ch9_예외처리.md:125,182-198,272-274,280-282` — Controller/Repository/Service/배치/에러 코드 체계 등 레이어드 아키텍처 어휘가 초보 수위를 넘음. 9절(예외 변환)과 문제 E·F는 "심화/실무 맛보기"로 명시 분리 권장.
- [중간] `docs/java_basic/ch9_예외처리.md:75-90` — try-catch-finally 실행 순서(예외 O/X 두 경로, finally 합류)가 글로만 설명됨. 4절용 블록 실행 순서 다이어그램 추가 권장. checked/unchecked 선택 플로차트(3절)도 후보.
- [낮음] `docs/java_basic/ch9_예외처리.md:186-194` — 9절 예시는 `OrderSaveException` 미정의 의사코드. "그대로는 컴파일되지 않는 개념 코드"임을 한 줄 명시하면 안전.

### 소스코드 문제
- [중간] `src/사용자정의예외/뱅크에서사용자정의예외Main.java:8` — 주석 "일반예외니까 컴파일에러 뜸"이 오해 소지. 실제로는 try-catch로 감싸서 컴파일 에러가 나지 않음. "try-catch(또는 throws) 없으면 컴파일 에러"로 수정 필요.
- [중간] `src/예외처리기본/예외처리기본Main.java:7-14` — 주석은 "try-catch 문으로 처리 가능"이라 하지만 코드에 try-catch가 없어 숫자 아닌 입력 시 그대로 죽음. 의도적이라면 "일부러 처리 안 함, Main2에서 처리" 주석 추가. NumberFormatException을 실제로 처리하는 예제가 챕터에 없음(문서 문제 A3과도 연결).
- [낮음] `src/예외처리기본/예외처리기본Main2.java:9,13`, `src/예외떠넘기기/예외떠넘기기일반예외Main.java:9,23` — 메소드명 오타 `findClassAndPrinInfo` → `findClassAndPrintInfo`.
- [낮음] `src/예외처리기본/예외처리기본Main3.java:12` — 주석 오타 "try-cath" → "try-catch". 28행 멀티캐치 주석 "Exception도 클래스"도 의미 불분명.
- [낮음] `src/안내사항.txt:1` — 오타 "예쩨코드" → "예제코드". 개발 메모 파일이라 학습자 배포 src에 두지 않는 게 좋음.
- [낮음] `src/quest/Q2.java:6-10` — 값 검증 실패에 `IOException`을 던지는 것은 부자연스러움(문서 B1은 "파일 읽기 메소드" 요구). `IllegalArgumentException` 또는 실제 파일 읽기 예제로 교체 권장.
- [낮음] `src/Main.java:1-5` — IDE 템플릿 잔재(삭제 권장). `src/사용자정의예외/Account.java:4`의 `public int money` 공개 필드는 캡슐화 원칙과 어긋남.

---

## ch10_자바API

### 문서-코드 불일치
- [높음] 문서 전체 vs `src` — **문서와 소스의 주제 구성이 크게 어긋남**. 소스에는 Object(equals/hashCode/toString), System, Math/Random, Wrapper(오토박싱), 리플렉션이 있으나 문서에 단 한 줄도 없음. 반대로 문서의 3대 주제 중 "입출력(I/O)"은 실습 소스가 전혀 없음(quest/Q3.java 하나뿐). 문서에 Object·Wrapper·Math 절을 추가하거나 소스 커리큘럼을 문서에 맞춰 정리 필요.
- [중간] `docs/java_basic/ch10_자바API.md:516-577` vs `src/quest` — 문제 A~G 약 23문항 대비 quest는 4개(Q1=C4, Q4≈C2, Q2≈F2, Q3≈E1/E2). Q2는 "사용자 입력 일정"(F2)이 아니라 크리스마스를 하드코딩했고, Q3은 main에 `throws IOException`으로 던져버려 문서 E3(try-with-resources)·E4(예외 처리)와 제출 체크리스트를 스스로 위반. 모범 답안이라면 문서 요구사항대로 수정 필요.

### 소스코드 문제
- [높음] `src/Object/Object메인.java:21-22` — 주석 "Student는 필드의 데이터가 같아도 hashCode가 다르기떄문에 hashSet에서 중복으로 취급 안함"은 **현재 코드와 정반대**. Student.java:26-29에서 `hashCode()`를 `Objects.hash(name, age)`로 재정의했으므로 student1/student2는 hashCode가 같고 HashSet에서 중복으로 취급됨. "재정의하지 않았다면"이라는 조건 명시 또는 HashSet에 실제로 넣어보는 검증 코드 필요. (오타 "떄문에"→"때문에"도 수정)
- [높음] `src/수학/수학복권만들기Main.java:8,13-15,20-22` — 8행 주석 "중복없이"와 달리 `random.nextInt(45)+1`을 6번 뽑기만 해서 한 조합 안에 중복 숫자가 나올 수 있음(실제 로또와 다름). Set 또는 뽑은 수 제외 로직으로 수정 필요. 8천만 회 반복(13행)은 수업 중 실행하기에 매우 오래 걸림 — 횟수 축소 또는 사전 안내 권장.
- [중간] `src/리플렉션과어노테이션/P4Class로부터객체얻기메소드사용Main.java:14` — `clazz.newInstance()`는 Java 9부터 deprecated(컴파일 경고 확인됨). `clazz.getDeclaredConstructor().newInstance()`로 교체 권장. 같은 줄 `(Square)` 캐스트도 불필요.
- [중간] `src/리플렉션과어노테이션/P1리플렉션객체얻기Main.java:5` — "제네릭 하고나서 하자. 블로그 제네릭 글 살짝 고치자" 개인 메모가 코드에 남아 있음. `Class<Square>`, `Class<?>` 등 제네릭 문법은 ch11에서 배우므로 리플렉션을 ch10에서 다루는 순서 자체가 초보자에게 맞지 않음(ch11 이후로 이동 권장).
- [중간] `src/날짜와시간클래스/날짜와시간Main.java:23-30` — `dateTime.plusDays(3)` 등 호출 결과를 전부 버림. java.time은 불변이라 반환값을 변수에 받아 출력하지 않으면 초보자가 "호출하면 바뀐다"고 오해하기 쉬움. 3,8,9행의 SimpleDateFormat/Calendar/Date import 미사용.
- [낮음] `src/리플렉션과어노테이션/P2리플렉션필드Main.java:11-12` — 변수명 오타 `fileds` → `fields`. P1:13, P2:8, P3:9의 `clazz2`, P3:19-20의 메소드 변수들 미사용(P3:3 `Field` import 미사용).
- [낮음] `src/수학/수학main.java:18` — 주석 "0~10사이의 값"은 10 포함처럼 읽힘. "0 이상 10 미만"으로 명시 권장.
- [낮음] `src/문자열/StringMain.java:13,16` — `str.getBytes("UTF-8")` 대신 `StandardCharsets.UTF_8` 사용 권장(예외 처리도 사라지고 문서 8절 권장 코드와 일치). 16행 `new String(arr2)`는 인코딩 미지정으로 문서 "자주 하는 실수 3번"과 정면 배치.
- [낮음] `src/포장/Wrapper메인.java:10` — `integer==integer2`가 false인 이유(1000은 Integer 캐시 -128~127 범위 밖)를 주석으로 보완 권장.
- [낮음] `src/안내사항.txt:1-3` — "중간중간 예제 및 문제필요", "강의 준비해야되겠다" 등 개발 메모가 배포 src에 노출됨. 별도 관리 권장.

### 초보자 관점 / 이미지
- [중간] `docs/java_basic/ch10_자바API.md:122-131,472-477` — Clock 주입, DB UTC 저장, ISO-8601, OOM/재시도 전략 등 실무 설계 내용이 초보 수위를 넘음. "실무 미리보기" 박스로 분리 또는 축약 권장.
- [중간] `docs/java_basic/ch10_자바API.md:229-250` — StringBuilder가 왜 빠른지(문자열 `+` 반복 시 중간 객체 생성 vs 내부 버퍼 append)가 글로만 설명됨. 비교 다이어그램 1장 추가 권장(기존 4개 이미지에 해당 그림 없음).
