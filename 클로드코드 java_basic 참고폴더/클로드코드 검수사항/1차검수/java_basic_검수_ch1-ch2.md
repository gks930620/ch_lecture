# java_basic 검수사항 — ch1 시작하기 / ch2 변수와자료형

검수일: 2026-07-02
검수 범위: `docs/java_basic/ch1_시작하기.md`, `ch2_변수와자료형.md` + `java_basic/java_class_ch1_시작하기`, `java_class_ch2_변수와자료형` 소스 전체 + 이미지 폴더

문서 내 이미지 링크는 전부 실제 파일이 존재함(깨진 링크 없음). 자바 동작 설명 자체(오버플로우 값, 0.1+0.2, 타입 승격 등)는 실제와 일치함을 확인. 아래는 발견된 문제점.

---

## ch1_시작하기

### 중간
- `docs/java_basic/ch1_시작하기.md:46~68` — 문서는 클래스명 `HelloWorld`(`javac HelloWorld.java`)로 실습을 안내하지만, 실제 소스 폴더에는 `HelloWorld.java`가 없고 `Part1_HelloWorld.java`만 있음. 문서를 그대로 따라 하면 파일을 찾지 못함. 소스에 `HelloWorld.java`를 추가하거나 문서에 "실습 코드는 Part1_HelloWorld.java"라고 명시 필요.
- `java_basic/java_class_ch1_시작하기/src/Main.java:1~14` — IntelliJ 자동 생성 기본 템플릿(영문 TIP 주석)이 그대로 남아 있음. `printf("Hello and welcome!")` 뒤 개행이 없어 다음 출력이 같은 줄에 붙어 `Hello and welcome!i = 1`로 출력됨. 강의 자료로는 삭제 또는 정리 필요.

### 낮음
- `docs/java_basic/ch1_시작하기.md:7` — 본문 중간에 BOM 문자(U+FEFF)가 포함되어 있어 렌더러에 따라 이상한 문자가 보일 수 있음.
- `docs/java_basic/ch1_시작하기.md:13` — "컴파일 ,"(쉼표 앞 공백), "클래스 로딩,  GC"(이중 공백) 표기 오류.
- `docs/java_basic/ch1_시작하기.md:40` — "인텔리제이 java 설치는 환경변수 설정 없이 인텔리제이 시스템 설정파일을 통해 java 실행"은 비문. "IntelliJ로 설치한 JDK는 환경변수(PATH) 등록 없이 IntelliJ 내부 설정을 통해 실행된다" 정도로 다듬기 권장.
- `docs/java_basic/ch1_시작하기.md:147~152` — 4.6 Heap/Stack 설명에 아직 배우지 않은 "객체", "메서드 호출", "참조" 개념이 등장. "지금은 용어만 눈에 익히면 된다, ch6에서 다시 배운다" 안내 한 줄 추가 권장.
- ch1 전체 — ch2와 달리 연습문제 섹션이 없음(소스에도 quest 없음). 챕터 구성 일관성을 위해 간단한 확인 문제(JDK/JRE/JVM 구분 퀴즈 등) 추가 검토.

---

## ch2_변수와자료형

### 중간
- `java_basic/java_class_ch2_변수와자료형/src/Main.java:6~20` — 챕터와 무관한 코딩테스트 풀이 코드(페인트 구역 계산 `solution`)가 src 루트에 남아 있음. 입문 강의 자료로는 혼란을 주므로 삭제 또는 교체 권장.
- `java_basic/java_class_ch2_변수와자료형/src/ch2_variable/P6연산식자동타입변환.java:7` — 주석 `// or (byte)x+byte(y)`가 이중으로 잘못됨: ① `byte(y)`는 유효한 문법이 아님(`(byte)y`의 오타), ② 설령 `(byte)x+(byte)y`라도 연산 결과는 int로 승격되므로 `byte z=`에 대입하면 컴파일 오류. 잘못된 대안을 제시하는 주석이므로 삭제 필요.
- `docs/java_basic/ch2_변수와자료형.md:413~465` vs `src/quest/Q1~Q4.java` — 문서의 연습문제(A~F, 20여 문항)와 실제 quest 코드(Q1 합/곱, Q2 직사각형, Q3 원기둥, Q4 동전 합계)가 서로 대응되지 않음. 예: 문서 B1은 "합/차/곱/몫/나머지"인데 Q1은 합/곱만 구현. quest가 어느 문제의 답안인지 매핑을 명시하거나 문제 목록을 소스와 맞추기 필요.
- 문서-소스 주제 불일치 — 소스의 `P7변수사용범위와입력.java`, `P8Scanner.java`, quest Q2~Q4는 모두 `Scanner` 입력을 사용하지만 문서에는 Scanner 설명이 전혀 없음. 반대로 문서의 핵심 예제(오버플로우 §2.2, String equals §4.2, 배열 복사 §5.1, final/var §7)는 대응하는 실습 소스가 없음. 문서→실습으로 넘어갈 때 단절 발생.
- `docs/java_basic/ch2_변수와자료형.md:252~261, 434~449` — 배열 예제와 문제 B3/B4/D2/F2가 아직 안 배운 for 반복문(ch4), 배열 심화(ch5)를 전제로 함. F1에만 선수 챕터 안내가 있고 나머지는 없음. §5 for문 예제에 "반복문은 ch4에서 배운다" 안내와, 해당 문제들에 선수 챕터 표기 필요.

### 낮음
- `docs/java_basic/ch2_변수와자료형.md:277` — `Arrays.copyOf` 예제에 `import java.util.Arrays;` 언급 없음. 초보자가 그대로 입력하면 컴파일 오류.
- `docs/java_basic/ch2_변수와자료형.md:229` — "리터럴끼리는 `==`가 우연히 `true`가 나오기도 해서"는 부정확. 리터럴끼리는 String Pool 공유로 **항상** true이며 '우연'이 아니라 규칙. "리터럴끼리는 ==가 true라서 오히려 착각하기 쉽다"로 수정 권장.
- `docs/java_basic/ch2_변수와자료형.md:334~338` — §6.4 `instanceof String s` 패턴 매칭(Java 16+)이 Object/instanceof 개념 설명 없이 등장. 이 블록을 ch7 이후로 미루는 것도 고려.
- `src/ch2_variable/P2정수.java:15` — 주석 "소문자ㄴ, 대문자 사용하세요"가 깨진 표기. "소문자 l은 숫자 1과 헷갈리니 대문자 L을 사용하세요"로 수정 필요.
- `src/ch2_variable/P4문자.java:5` — 주석 "asc22 코드"는 "ASCII 코드"의 오타. 14행 "//알파벳 전부 출력"은 구현 없는 TODO로 남아 있음.
- `src/ch2_variable/P3논리.java:11` — "뉴진스에 매력에" → "뉴진스의 매력에" 오타. 5행 주석의 boolean 크기 설명이 문서 77행 표와 상충(표현 통일 필요).
- `src/quest/Q2.java:8,15` — 변수명 `permiter`는 `perimeter`의 오타.
- `src/quest/Q4.java:8~14` — "갯수"는 표준어 "개수"의 오타(4곳).
- `src/ch2_variable/P7변수사용범위와입력.java:7` — `throws IOException`이 불필요(Scanner는 IOException을 던지지 않음). ch9 이전에 설명 없는 throws 등장으로 혼란 소지. 13~14행에서 읽은 값도 사용되지 않음.
- `src/과제_계산기.java:1~6` — 빈 TODO 스텁. 의도된 과제 파일이라면 요구사항(입력/출력 예시) 주석 추가 필요.
