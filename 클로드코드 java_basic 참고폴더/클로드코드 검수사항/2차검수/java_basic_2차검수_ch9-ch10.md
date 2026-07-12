# java_basic 2차 검수 — ch9 예외처리 / ch10 자바API

검수일: 2026-07-02~03 (2차)
검증 방법: git 이력·내용 대조, JDK 17 전체 컴파일(성공, 경고는 1차와 동일), 이미지 6개 실존 확인.

**현재 상태: 1차 지적 중 수정된 것 단 한 건도 없음.** ch9/ch10은 1차 이후 미변경.

---

## [1차 잔존] 핵심 (전부 미수정)

- [높음] `java_basic/java_class_ch10_자바API/src/Object/Object메인.java:21-22` — hashCode 주석이 여전히 코드와 정반대(Student가 `Objects.hash` 재정의됨 → HashSet에서 중복 취급됨).
- [높음] `src/수학/수학복권만들기Main.java:8,14-15` — "중복없이" 주석과 달리 한 조합 안에 중복 숫자 가능. 8천만 회 반복도 그대로.
- [높음] `docs/java_basic/ch9_예외처리.md:169`(unchecked) vs `src/사용자정의예외/InsufficientException.java:5`(checked) — 사용자 정의 예외 방침 충돌 그대로.
- [높음] ch9 문제 19문항 vs quest 4개(매핑 표시 없음) / ch10 문서-소스 주제 구성 불일치(Object/Math/Wrapper/리플렉션 문서에 없음, I/O 실습 없음).
- [중간] ch9: 파일 I/O 선행 등장 안내 없음 / 문제 E·F 실무 어휘 수위 초과 / try-catch-finally 다이어그램 부재 / "일반예외니까 컴파일에러 뜸" 오해 주석 / 예외처리기본Main 주석-코드 불일치.
- [중간] ch10: quest Q2 크리스마스 하드코딩·Q3 throws 처리(문서 요구 위반) / deprecated `newInstance()` / "블로그 글 고치자" 개인 메모+리플렉션 배치 순서 문제 / 날짜와시간Main 반환값 버림 / 실무 내용 수위 초과 / StringBuilder 다이어그램 부재.
- [낮음] 오타류 전부 잔존: `findClassAndPrinInfo`, "try-cath", "예쩨코드", `fileds`, getBytes("UTF-8"), Integer 캐시 주석 부재, 안내사항.txt 개발 메모 등.

---

## [신규] 2차에서 새로 발견

- [신규][중간] `docs/java_basic/문제답안/` — **ch9/ch10 답안 문서 부재.** ch1/ch2/ch13/ch14 문서는 답안 링크를 제공하는데 ch9/ch10에는 링크도 파일도 없어 챕터 간 체계 불일치. quest 4개가 유일한 답안인데 문항 커버리지 20% 수준.
- [신규][낮음] `docs/java_basic/ch9_예외처리.md:111-112` — "어느 쪽이든 try-catch 이후의 코드는 계속 실행된다"는 일반화가 부정확. catch 타입과 일치하지 않는 예외면 이후 코드는 실행되지 않고 전파됨. "잡히는 예외라면" 조건 필요.
- [신규][낮음] `src/quest/Q3.java:4`(ch9) — 문서 D1 모범답안 격인 `InsufficientBalanceException`이 `extends Exception`(checked)으로 작성되어 문서 8절(unchecked)과 또 충돌. 방침 통일 시 이 파일도 함께 수정 필요(문서 169행 + InsufficientException.java + quest/Q3.java 세 곳 동시).
- [신규][낮음] `src/날짜와시간클래스/날짜와시간Main.java:13`(ch10) — "naver d2 localDate꼭 읽자" 개인 메모 노출(1차 미지적 위치).
- [신규][낮음] `src/quest/Q4.java:8`(ch10) — 이메일 정규식이 문서 7절(277행) 예시보다 느슨해 `a@b`도 통과. 문서 C2 답안이라면 문서 정규식과 일치 권장.
- [신규][낮음] `src/수학/수학main.java:9-20` — ceil/floor/max/min/random 계산 결과를 출력하지 않아 실행해도 동작 확인 불가. println 추가 권장.
- [신규][낮음] `src/포장/Wrapper메인.java:8` — `a==integer`(true, 언박싱 값 비교)와 10행 `integer==integer2`(false, 참조 비교)가 연달아 나오는데 8행이 true인 이유 설명이 없어 혼란 유발.

---

## 우선순위 권장
1. 사용자 정의 예외 checked/unchecked 방침 통일(문서 169행 + InsufficientException.java + quest/Q3.java 동시 수정)
2. Object메인.java hashCode 주석 정정
3. 수학복권만들기 중복 제거 로직
4. ch9/ch10 문제답안 문서 신설 또는 quest 문항 매핑·보충
