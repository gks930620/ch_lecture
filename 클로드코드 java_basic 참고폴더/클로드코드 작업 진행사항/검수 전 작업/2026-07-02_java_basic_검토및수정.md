# java_basic 강의 문서 검토 및 수정 (2026-07-02)

## 작업 개요

- **목적**: 입문자 대상 / Java 17 기준으로 ch1~ch16 강의 문서 전체 검토, 오류 수정, 설명용 이미지 보강
- **변경 규모**: md 파일 16개 수정 (+536줄 / -204줄), 신규 SVG 15개 제작
- **커밋 안 함** — 검토 후 직접 커밋하시면 됩니다

## 검토해볼 때 참고

- 변경 전체 비교: `git diff docs/java_basic` (이미지 신규 파일은 `git status`에 untracked로 표시)
- 이미지는 브라우저에서 SVG 파일을 바로 열거나, Jekyll 로컬 서버로 페이지째 확인

---

## 1. 전역 수정: 이미지 링크 복구 (16개 파일 전체)

**문제**: 이미지 파일을 `docs/java_basic/java_basic_images/`로 옮겼는데, 본문 링크 22개는 옛 경로(`/assets/images/java_basic/...`)를 그대로 가리켜 **사이트에서 이미지가 전부 깨지는 상태**였음.

**수정**: 모든 링크를 `{{ '/java_basic/java_basic_images/chN/파일명.svg' | relative_url }}` 형태로 교체.
현재 본문의 이미지 참조 43개 전부 실제 파일과 대조 확인 완료.

---

## 2. 챕터별 본문 수정 내역

### ch1_시작하기.md
- 비어 있던 "1.0 IntelliJ IDEA 설치" 절에 내용 추가 (다운로드 링크, Community Edition 안내)
- JDK 버전 "17 또는 21" → **17로 확정**
- Hello World 코드 아래에 "키워드는 이후 챕터에서 배움" 안내 추가
- **"2.3 터미널에서 실행" 절 신설** — `javac`/`java` 명령 예시 (설명 문장만 있고 명령어 코드가 유실돼 있었음) + IntelliJ 내장 터미널 안내
- JRE 설명에 "Java 11부터 JRE 별도 배포 없음" 보충
- 클래스 초기화 설명에서 static 언급을 입문자 수준으로 완화
- 이미지 추가: 4.6 메모리와 GC 절에 `heap-stack-gc.svg` (신규)

### ch2_변수와자료형.md
- **이미지 참조가 0개였음** — 기존 SVG 5개를 본문에 삽입:
  - `twos-complement-example.svg` (1-2 오버플로우 예제 아래)
  - `ieee754-layout.svg` (1-3 실수 표현, 0.1+0.2 아래)
  - `string-pool-reference.svg` (2-2 String) + 문자열 풀 설명 2문장 추가
  - `array-copy-reference.svg` (2-4 배열 복사)
  - `type-promotion-casting.svg` (3 형변환 도입부)
- 3-4 참조형 캐스팅: "ch7에서 자세히 배움" 안내 추가 (상속 미학습 시점이라)
- 3-5 오토박싱: `Integer` = int의 래퍼 클래스 설명 추가
- StringBuilder 사용 예제 4줄 추가 (언급만 있고 예제가 없었음)
- 챌린지 F: "(예외 처리는 ch9 학습 후 도전)" 표시 추가

### ch3_연산자.md
- **문제 섹션이 이론보다 앞에 있던 것을 교체** — 이론 먼저, 문제 마지막으로 이동
- 비트 연산 출력 주석 오류 수정: `// 0b1000` → `// 8 (0b1000)` 식으로 (실제 출력은 십진수)
- 문제 섹션 앞에 **"입력 받기 안내"(Scanner 예제) 추가** — 문제들이 입력을 전제하는데 입력법 설명이 없었음
- 헤딩 레벨 수정: `## 5.1`, `## 6.1` → `###`

### ch4_제어문과반복문.md
- 헤딩 레벨 통일: `## 2.1`~`## 8.1` → 모두 `###`
- 이미지 추가: 3.1 switch 절에 `switch-fallthrough.svg` (신규 — break 유무 비교)
- 문제 F-3(로또)에 힌트 추가: `Math.random()` 사용법 + 중복 검사 방법

### ch5_배열.md
- 5.3 "조기 반환" 절: 예제가 throw만 보여줘서 제목과 어긋남 → 조기 return 예제 추가, 설명 수정
- **"기본값 자동 초기화는 배열 요소에만 해당" 오류 수정** → 필드도 자동 초기화됨 (같은 문서 7.3과 자기모순이었음)
- 클래스/객체 선행 등장 위치(4.2)에 "ch6에서 배움" 안내 추가
- 9절에 O(1)/O(n) 표기 설명 1줄 추가
- 이미지 추가 2개 (신규): 4절에 `pass-by-value.svg`, 6절에 `array-copy-shallow-deep.svg`

### ch6_객체지향기초.md
- 헤딩 수정: `## 3.1 필드` → `###`
- **접근제어자 `default` 표기 정정** — 실제 키워드가 아니라 "생략 시 package-private"임을 명시
- 8절에 `Objects.equals`/`Objects.hash` 사용한 equals/hashCode 완성 예제 추가 (문제 E2 풀이에 필요한데 본문에 없었음)
- 캡슐화 절에 getter 예제 추가 (문제 B4에 필요)
- 9절 불변 객체에 record 소개 문단 추가 (Java 16+)
- 빌더 패턴/Repository/Anemic Domain Model 등 심화 용어에 완충 문구 추가
- 이미지 추가: 8절에 `equals-vs-reference.svg` (신규)

### ch7_상속.md
- 오버로딩 vs 오버라이딩 비교표 추가 (입문자 단골 혼동 지점)
- OCP 약어에 한 줄 부연
- 다운캐스팅 **실패 예제** 추가 (`ClassCastException` — 문제 C4에 필요한데 성공 예만 있었음)
- **Java 16+ instanceof 패턴 매칭** 관용구 추가 (`if (a instanceof Dog d)`) — Java 17 강의 권장 스타일
- 이미지 추가: 5절에 `reference-type-casting.svg` (신규)

### ch8_인터페이스.md
- **"인터페이스 메소드는 암묵적 public, 구현 시 public 필수" 규칙 추가** (빼면 컴파일 오류 — 설명이 아예 없었음)
- 람다 첫 등장에 문법 최소 설명 + "ch13에서 자세히" 안내
- Runnable 한 줄 설명 추가
- 문제 D3/F1에 제네릭(ch11)/컬렉션(ch12) 선행 안내 추가
- 이미지 추가: 6절 DIP에 `dip-dependency-direction.svg` (신규)

### ch9_예외처리.md
- **실행 가능한 완결 try-catch 예제 추가** (0으로 나누기) — 본문이 전부 pseudo 코드뿐이었음
- "경계 레이어" 용어에 입문자용 부연 ("main 같은 진입점")
- 예외 변환 예제(Repository/SQLException)에 "ch15에서 배움, 패턴만 기억" 안내
- suppressed exception 한 줄 설명 추가
- 이미지 추가: 2절에 `throwable-hierarchy.svg` (신규 — checked/unchecked 트리)

### ch10_자바API.md
- **`new FileReader("a.txt")` → UTF-8 명시 생성자로 수정** (Java 17은 OS 기본 인코딩이라 바로 뒤 "인코딩 명시" 지침과 모순이었음)
- Pattern/Matcher 미완성 예제에 `while (m.find())` 완성 코드 추가
- **"텍스트 블록(`"""`)" 절 신설** (Java 15+, 문자열 챕터 — 이후 절 번호 재조정됨)
- 문자열 풀 설명 추가 (학습 목표에는 있는데 본문에 없었음) + ch2 참조
- StringBuffer 권장 문구 톤 다운 (레거시 수준으로)
- "내부 UTF-16" 표현 정확화, `ChronoUnit` 패키지 명시
- 이미지 추가: 타임존 절에 `timezone-conversion.svg` (신규)

### ch11_제네릭.md
- **스트림 코드를 for 루프로 교체** (ch14 미학습 시점에 스트림+메소드참조+Optional이 한 줄에 등장했음)
- PECS 절에 컴파일 오류 시연 코드 추가 (문제 D-3에 필요한데 없었음)
- 타입 소거 절에 컴파일 전/후 비교 코드 + 기존 이미지 재참조
- 이미지 추가: PECS 절에 `pecs-wildcard.svg` (신규)

### ch12_컬렉션.md
- **LinkedList 설명 정정** — "중간 삽입/삭제 유리"는 오해 소지 (탐색 비용 때문에 실무 기본값은 ArrayList)
- 배열 한계에서 "단일 타입" 제거 (컬렉션도 단일 타입이므로 비교가 성립 안 함)
- equals/hashCode 절에 record 자동 제공 설명 + ch6 예제 참조 (문제 E에 필요)
- Comparable 예제 추가 (`compareTo` — 문제 D에 필요한데 코드 0줄이었음)
- LRU 용어 부연, 동시성 컬렉션에 ch16 안내
- 이미지 추가: `hash-lookup-flow.svg` (신규 — hashCode→버킷→equals 2단계 조회)

### ch13_람다와함수형.md
- **`BinaryOperator` 분류 오류 수정** — Function이 아니라 `BiFunction<T,T,T>`의 특수형. `BiFunction` 항목도 목록에 추가
- 익명 클래스 → 람다 before/after 예제 추가 (문제 A.3에 필요한데 익명 클래스 예제가 없었음)
- **메소드 참조 4종류별 대응 예제 추가** (이름만 나열돼 있었음)
- 문제 F.1에 커스텀 함수형 인터페이스 힌트 추가
- 이미지 추가: `method-reference-map.svg` (신규 — 람다↔메소드참조 대응표)

### ch14_stream.md
- flatMap 예제 추가 (문제 B.2에 필요한데 한 줄 설명뿐이었음)
- reduce 예제 추가 (초기값 개념 — 문제 C.1에 필요)
- Optional 정의 2문장 추가 (첫 등장인데 정의 없이 사용법부터 나왔음)
- 이미지 추가: `map-vs-flatmap.svg` (신규)

### ch15_JDBC.md
- **DB 연결 완결 예제 추가** — 본문에 실제 연결 코드가 한 줄도 없는데 문제 A.1이 연결을 요구했음. H2 URL + `DriverManager.getConnection` + Gradle 의존성 안내
- 트랜잭션 예제에 `finally { conn.setAutoCommit(true); }` 복원 추가 (커넥션 풀 반환 대비)
- record(ch16) 선행 안내, DataSource 순서 안내
- 이미지 추가: 9절에 `connection-pool.svg` (신규 — DriverManager vs 풀)

### ch16_참고최신문법과스레드.md
- **switch 패턴 매칭이 Java 17에선 preview임을 명시** — "Java 21 정식화, 이 강의는 instanceof 패턴까지만 사용". 예시 코드에 "Java 21+" 표시
- **문제 F.2 수정**: "switch 패턴" → "switch 표현식" (17 기본 설정에서 컴파일 불가한 과제였음)
- `Future.get()` 예제에 checked 예외 처리 주석 추가 (그대로는 컴파일 불가였음)
- 라이브락/기아 정의 추가 (제목에만 있고 본문에 없었음)
- **race condition 재현 예제 추가** (두 스레드 count++, 20000이 안 나오는 코드) — 이후 synchronized/Atomic 절과 연결
- BlockingQueue put/take 예제 추가 (문제 F.1 연계)
- 이미지 추가: `race-condition-interleaving.svg` (신규)

---

## 3. 신규 제작 SVG 15개

기존 27개 이미지와 같은 스타일(1100px 폭, 파스텔 톤, 영어 라벨)로 통일.

| 파일 | 내용 |
|---|---|
| `ch1/heap-stack-gc.svg` | Stack 프레임 → Heap 객체 참조, GC 수거 |
| `ch4/switch-fallthrough.svg` | break 유무에 따른 실행 흐름 비교 |
| `ch5/pass-by-value.svg` | 기본형 값 복사 vs 참조형 참조값 복사 |
| `ch5/array-copy-shallow-deep.svg` | 참조 복사 / 얕은 복사 / 깊은 복사 3열 비교 |
| `ch6/equals-vs-reference.svg` | == 참조 비교 vs equals 값 비교 |
| `ch7/reference-type-casting.svg` | 참조 타입 = 보이는 창 + ClassCastException |
| `ch8/dip-dependency-direction.svg` | 구체 클래스 의존 vs 인터페이스 의존 방향 |
| `ch9/throwable-hierarchy.svg` | Throwable 계층 트리 (checked/unchecked 구분) |
| `ch10/timezone-conversion.svg` | 같은 Instant, 타임존별 다른 표시 |
| `ch11/pecs-wildcard.svg` | extends=읽기 / super=쓰기 (PECS) |
| `ch12/hash-lookup-flow.svg` | hashCode→버킷→equals 2단계 조회 |
| `ch13/method-reference-map.svg` | 람다 ↔ 메소드 참조 4종 대응표 |
| `ch14/map-vs-flatmap.svg` | map(스트림의 스트림) vs flatMap(평탄화) |
| `ch15/connection-pool.svg` | 매번 연결 vs 커넥션 풀 재사용 |
| `ch16/race-condition-interleaving.svg` | 두 스레드 count++ 인터리빙 타임라인 |

---

## 4. 수행한 검증

- 본문 이미지 참조 43개 → 실제 파일 존재 전부 확인 (누락 0)
- SVG 42개 (기존 27 + 신규 15) XML 유효성 전부 통과
- ch3 섹션 재배치 무결성, ch16 preview 문구 등 핵심 수정 스팟 체크

## 5. 반영하지 않은 것 (참고)

- ~~**ch2 전체 구조**: 요약 노트 형태로 설명 밀도가 낮음~~ → **2차 작업에서 전면 재작성 완료** (`2026-07-02_ch2보강_이미지추가.md` 참고)
- ~~ch4 while/do-while, ch12 ArrayList/LinkedList 이미지~~ → **2차 작업에서 제작 완료**
- ~~ch3/ch4의 상위 래퍼 헤딩(`## 1. 연산자`)과 내부 헤딩이 같은 레벨인 기존 구조는 유지~~ → **3차 작업에서 전 챕터 정리 완료** (`2026-07-02_헤딩구조정리.md` 참고)
