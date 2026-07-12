# javascript_basic 검수사항 — ch1 HTML / ch2 CSS

검수일: 2026-07-03
검수 범위: `docs/javascript_basic/ch1_HTML/강의노트_1장_HTML.md`, `ch2_CSS/강의노트_2장_CSS.md` + `javascript_basic/ch1_HTML/*.html`(소스 세트) + `docs/javascript_basic/ch1_HTML/*.html`(docs 세트) + `javascript_basic/ch2_CSS/*.html` + 이미지 폴더

전제: **검수자도 틀릴 수 있으니 확실한 것만 기재**했습니다. 아래는 코드를 정독/대조해 확인한 항목입니다.

---

## ch1_HTML

총평: 강의노트 기술 설명은 대체로 정확하고 참조 SVG 4종(web-trio-house, html-document-tree, semantic-layout, block-vs-inline)은 `docs/javascript_basic/javascript_basic_images/ch1/`에 모두 실재(깨진 링크 없음). **가장 큰 문제는 소스 폴더(`javascript_basic/ch1_HTML/`)와 docs 폴더의 HTML이 서로 완전히 다른 별개 세트**라는 점 — docs 세트가 강의노트 1.x 구성과 정합하는 최신본이고, 소스 세트는 "26.x" 번호가 박힌 구버전.

### 높음
- `javascript_basic/ch1_HTML/part05_이미지태그.html:78,92,118,128,135,142,160,180,198,209,218,228` — 모든 예제 이미지가 `https://via.placeholder.com/...` 사용. 이 서비스는 현재 중단되어 이미지가 로드되지 않고 alt 텍스트만 표시됨. 하필 "이미지 태그" 강의라 실습 목적 자체가 무너짐. (docs 세트의 part05는 이미 data URI SVG로 대체되어 있음 → 저자도 외부 의존을 걷어낸 정황. 소스 세트도 동일 조치 필요)

### 중간
- 소스 세트 전체(`part01~10`의 `<h1>`) — 본문 제목이 `26.1-26.2` … `26.11-26.12`로 되어 있어 폴더명(ch1)·강의노트(1.1~1.12)와 번호 불일치. 예: `part01_HTML기본구조.html:36`은 `<title>Part 1…`인데 `:75`는 `<h1>📚 26.1-26.2…`로 같은 파일 안에서도 어긋남. 타 챕터에서 옮겨온 흔적. (docs 세트는 "실습 0X"로 정리되어 문제 없음)
- 소스 세트 ↔ docs 세트 파일 구성 불일치 — 소스는 `part07 폼요소기초`+`part08 Input타입상세` 분리, `part09 시맨틱`, `part10 div/span`. docs는 `part07 폼과input`(통합), `part08 시맨틱`, `part09 div/span`, `part10 종합실습_블로그레이아웃`. 강의노트는 docs 구성과 일치 → 강의노트의 실제 짝은 docs 세트이고 소스 세트는 구버전. 어느 세트를 배포본으로 쓸지 정리 필요.
- 소스 세트가 JS를 다수 사용(`part04:129` `javascript:void(0)`/`onclick`+`alert`, `part07:153,218` `onclick`/`onsubmit`, `part08:247,260,264` `onchange`/`oninput`, `part10:175` `onclick`) — HTML(1장)에서 아직 안 배운 JavaScript를 예고 없이 노출. docs 세트가 더 깔끔(docs `part09:46-52`는 `<script>` 쓰되 "3장에서 자세히"라고 명시).

### 낮음
- `docs/javascript_basic/ch1_HTML/강의노트_1장_HTML.md:414` — 추가자료로 `https://www.w3.org/TR/html52/`를 "W3C HTML 표준"으로 링크. 이 문서는 폐기(superseded)되어 WHATWG Living Standard로 위임된 상태로, 같은 노트 `:30`의 "Living Standard" 설명과 상충. WHATWG(html.spec.whatwg.org) 링크가 더 적절.
- `javascript_basic/ch1_HTML/part08_Input타입상세.html:168` — `type="url"`을 "http:// 또는 https:// 필요"라 설명. 실제로는 스킴이 있는 절대 URL이면 되고 http/https로 한정되지 않음(`ftp://`도 통과). 엄밀히는 부정확.
- `javascript_basic/ch1_HTML/part04_링크태그.html:128` — 섹션 제목 "9. 링크 비활성화 (JavaScript 방지)"가 오해 소지. `javascript:void(0)`+`onclick`은 JS를 "방지"하는 게 아니라 기본 이동만 막는 것.
- `javascript_basic/ch1_HTML/part02_텍스트태그.html:72` — `<br>`을 "self-closing tag"로 지칭. 정확히는 void element이며 self-closing(`<br/>`)은 XHTML 표기. 통용 표현이라 사소.

---

## ch2_CSS

총평: 선택자·specificity 설명 자체는 입문자 수준에서 대체로 정확하나, **"내부 스타일시트 > 외부 스타일시트" 우선순위 설명은 명백한 사실 오류**. 또 part01의 "외부 CSS" 데모가 존재하지 않는 파일을 링크하고 실제론 내부 CSS로 스타일링되어 가르치려는 개념과 반대로 동작함.

### 높음
- `docs/javascript_basic/ch2_CSS/강의노트_2장_CSS.md:39` 및 `javascript_basic/ch2_CSS/part01_CSS기초와적용방법.html:218,222-235` — **"우선순위: 인라인 > 내부 > 외부"는 사실 오류.** 내부 `<style>`와 외부 `<link>`는 모두 author origin으로 **우선순위(가중치)가 동일**하며, specificity가 같으면 **문서상 나중에 선언된 것**이 이김. part01 비교표의 "내부 ⭐⭐ 중간 / 외부 ⭐ 낮음"도 같은 오류. (part01은 line 8 `<link>` 뒤 line 10 `<style>`라 이 파일에선 내부가 이기지만, 이는 "순서상 뒤라서"이지 "내부가 원래 높아서"가 아님 → 오개념을 심음)

### 중간
- `javascript_basic/ch2_CSS/part01_CSS기초와적용방법.html:8` — 링크된 외부 CSS 파일 `part01_external.css`가 **존재하지 않음(404)**. 게다가 "외부 스타일시트가 적용되었습니다" 데모(line 193-197)의 `.method-external`은 실제로는 내부 `<style>`(line 72-78)에서 정의됨. 즉 "외부 CSS 적용"을 시연한다면서 실제로는 내부 CSS로 렌더링됨 → 가르치는 개념과 코드 동작이 반대. (ch2_CSS 폴더에는 HTML 2개만 존재)
- `docs/javascript_basic/ch2_CSS/강의노트_2장_CSS.md:7` — 학습 목표에 "레이아웃을 구성할 수 있다"가 있으나, 본 장은 CSS 적용법·선택자·우선순위만 다루고 **박스모델/Flexbox/레이아웃은 전혀 다루지 않음**(line 561에서 "다음 단계"로 예고). 목표와 내용 불일치.
- `javascript_basic/ch2_CSS/part01_CSS기초와적용방법.html:100`, `part02_CSS선택자.html:151` — 소스 HTML 제목이 "27.1-27.2", "27.3"인데 강의노트는 "2장(2.1~2.3)". 문서↔소스 챕터 번호 불일치(타 커리큘럼 잔재).

### 낮음
- `docs/javascript_basic/ch2_CSS/강의노트_2장_CSS.md:161,463-465` 및 `part02_CSS선택자.html:223` — `box-sizing: border-box`, `:root`/CSS 변수(`--primary-color`), BEM(line 448) 등 아직 안 배운 개념이 예고 없이 리셋/실무팁에 등장. 각주 권장.
- `javascript_basic/ch2_CSS/part01_CSS기초와적용방법.html:257-261` — 우선순위 코드 주석의 "1순위~5순위" 라벨 방향 모호. 헤더는 "높음 → 낮음"인데 낮은 specificity(`p`)에 "1순위"를 붙여 한국어 관용("1순위=최우선")과 반대로 읽힘.
- `docs/javascript_basic/ch2_CSS/강의노트_2장_CSS.md:418` — "너무 길음"은 어색한 표현("너무 긺/깁니다").
- `javascript_basic/ch2_CSS/part02_CSS선택자.html:332` — 코드 예시는 `[type="text"]`로 표기하나 실제 적용 CSS(line 101)는 `input[type="text"]`이고 색상도 상이. 동작엔 문제없으나 표시 코드와 실제 코드가 다름.
- `docs/javascript_basic/ch2_CSS/강의노트_2장_CSS.md` 전체 — 이미지/도식이 하나도 없음(깨진 링크는 없음). "자손 vs 자식", specificity 점수 같은 헷갈리는 개념은 도식 보강 시 이해에 유리(개선 제안).
