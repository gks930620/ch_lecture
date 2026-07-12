# java_basic 2차 검수 — ch11 제네릭 / ch12 컬렉션

검수일: 2026-07-02~03 (2차)
검증 방법: git diff 대조(1차 이후 미변경 확인), JDK 17 전체 컴파일, **[높음] 2건 javac/실행 재검증**, 이미지 5개 실존·내용 확인.

**현재 상태: 1차 지적 중 고쳐진 항목 0건.** 특히 확인 대상 3건(ch11 빈 리스트 get(0) 예제, ch12 149행 Comparator 컴파일 불가, "타입선얼" 파일명 오타) 모두 잔존 — 앞 2건은 실행/컴파일로 재확인됨.

---

## [1차 잔존] — 전부 미수정 확인

### ch11
- [높음] `docs/java_basic/ch11_제네릭.md:126-129` — 와일드카드 예제가 빈 리스트에 `nums.get(0)` 호출. **재검증: `IndexOutOfBoundsException` 발생 확인.** 초보자가 extends 제약 때문에 죽는다고 오해할 수 있는 형태 그대로.
- [중간] 문제 A~F vs quest Q1~Q4(B2·C2·D3·E·F 미대응, Q3는 C1 요구와 다른 개념 시연) / 패키지 번호 오류(P1 두 개, P2 없음) / 문서가 소스(P1~P5)를 무언급, PECS↔Course.register1/2/3 연결 부재 / List 선행 사용 안내 없음.
- [낮음] "extends는 읽기 전용" 과단순화 / A3 raw type 표현 / 타입 소거 우회 예제 부재 / Main.java 미사용 import+모순 주석 / 제네릭기본Main 주석 위치 / Architect.java 부정확 주석 / 오버로딩 불가 주석 설명 부족.

### ch12
- [높음] `docs/java_basic/ch12_컬렉션.md:149` — 8.2절 Comparator 예제가 8.1절 User 기준 **컴파일 불가. 재검증: `cannot find symbol: getAge()/getName()` 2건 확인.**
- [중간] "타입선얼" 파일명 오타 + Main2 중복 / 문제 A·E·F·G quest 미대응, Q4 정렬 기준이 문서 D2와 다름 / 7·8·9절(Iterator·Comparator·불변) 대응 소스 없음 / src/Main.java 베스트앨범(ch14 스트림 사용, `Genere` 오타, 뺄셈 비교 오버플로 위험) / 빈 스텁 4개.
- [낮음] G3 "ch16 학습 후" 표시 없음 / 맵Main getOrDefault 미출력 / Array리스트메인 삭제값-출력 불일치 / Bunny.java 미사용 import / 구어체 주석 / 7·8절 다이어그램 제안 미반영.

---

## [신규] 2차에서 새로 발견

- [신규][중간] `docs/java_basic/문제답안/` — **ch11/ch12 답안 문서 부재.** quest가 일부만 커버하는 상황과 겹쳐 ch11 B2·C2·D3·E·F와 ch12 A·E·F·G는 어디에도 참조 답안이 없음.
- [신규][낮음] `docs/java_basic/ch12_컬렉션.md:71` — "LinkedHashMap: 삽입 순서 유지(LRU 구현에도 활용…)"은 부정확. LRU 캐시는 **접근 순서 모드**(`new LinkedHashMap<>(cap, 0.75f, true)` + `removeEldestEntry`)가 필요. 챌린지 G2(256행)가 이 구현을 요구하므로 초보자가 삽입 순서로 시도하다 실패할 수 있음.
- [신규][낮음] `docs/java_basic/ch12_컬렉션.md:161` — "구조 변경 불가(add/remove 예외)" 표현이 "add/remove는 예외적으로 허용"으로 오독 가능 — "add/remove 호출 시 UnsupportedOperationException 발생"으로 명시 권장.
- [신규][낮음] `java_basic/java_class_ch12_컬렉션/src/안내사항.txt` — "프로그래머스 문제 풀자. 특별히 예제 준비." 등 강사 개인 메모가 배포 소스에 노출.

---

## 정상 확인
- 두 소스 폴더 JDK 17 컴파일 성공(ch11 rawtypes/unchecked 경고 9건은 시연용 의도).
- 이미지 5개 실존·경로 일치. pecs-wildcard.svg 내용(extends=read/super=write)도 기술적으로 정확.
- ch12 문서 106행의 ch6 Objects.equals/hash 상호 참조 유효.

## 우선순위
[높음] 2건(ch11:126-129, ch12:149)이 최우선이고, 신규로는 ch11/ch12 문제답안 부재가 가장 실질적인 공백.
