# 1장: HTML - 웹의 구조

## 📋 강의 개요
- **소요 시간**: 약 4-5시간
- **난이도**: ⭐ 입문
- **선수 지식**: 없음
- **학습 목표**: HTML의 기본 개념과 주요 태그를 이해하고 실무에 활용할 수 있다.

---

## 📚 1.1 HTML이란?

### 강의 포인트
- HTML은 **마크업 언어**이지 프로그래밍 언어가 아님을 강조
- 웹 페이지의 3요소(HTML, CSS, JavaScript) 비유: 집을 짓는다면 HTML은 뼈대, CSS는 인테리어, JS는 가전제품

### 핵심 내용
```
HTML (HyperText Markup Language)
- HyperText: 링크를 통해 다른 문서로 이동 가능
- Markup: 태그로 문서 구조를 표시
- Language: 브라우저가 이해하는 언어
```

### 학생들이 자주 묻는 질문
Q: HTML과 HTML5의 차이는?
A: HTML5는 HTML의 최신 버전. 시맨틱 태그, 멀티미디어 지원 강화 등이 특징

Q: 태그는 왜 꺾쇠괄호를 쓰나요?
A: 일반 텍스트와 구분하기 위한 약속입니다.

---

## 📚 1.2 HTML 문서 구조

### 강의 포인트
- 모든 HTML 문서의 기본 뼈대는 동일함을 강조
- head와 body의 역할 구분을 명확히 설명

### 기본 구조 설명
```html
<!DOCTYPE html>              <!-- HTML5 문서임을 선언 -->
<html lang="ko">             <!-- 문서 언어 설정 (SEO, 접근성) -->
<head>                       <!-- 문서의 메타정보 -->
    <meta charset="UTF-8">   <!-- 문자 인코딩 (한글 깨짐 방지) -->
    <title>페이지 제목</title>  <!-- 브라우저 탭에 표시 -->
</head>
<body>                       <!-- 실제 화면에 보이는 내용 -->
    
</body>
</html>
```

### 실습 가이드
1. 빈 텍스트 파일 생성 → .html 확장자로 저장
2. 기본 구조 작성
3. body에 "안녕하세요" 입력
4. 브라우저에서 열어보기

---

## 📚 1.3 텍스트 태그

### 강의 포인트
- h1~h6는 크기가 아니라 **중요도**를 나타냄
- SEO를 위해 h1은 페이지당 하나만 사용 권장
- strong vs b, em vs i의 차이 (의미론적 vs 시각적)

### 태그별 설명

#### 제목 태그 (h1~h6)
```html
<h1>가장 중요한 제목</h1>    <!-- 페이지의 주제 -->
<h2>섹션 제목</h2>            <!-- 큰 섹션 -->
<h3>하위 섹션</h3>            <!-- 작은 섹션 -->
```
💡 **강의 팁**: 목차 구조처럼 생각하면 쉽습니다.

#### 문단 태그 (p)
```html
<p>하나의 문단입니다.</p>
<p>새로운 문단입니다.</p>
```
💡 **강의 팁**: p 태그는 자동으로 위아래 여백이 생깁니다.

#### 텍스트 포맷팅
```html
<strong>중요한 텍스트</strong>  <!-- 의미적으로 중요 (SEO 영향) -->
<b>굵은 텍스트</b>             <!-- 단순 시각적 효과 -->
<em>강조 텍스트</em>            <!-- 의미적 강조 -->
<i>이탤릭</i>                  <!-- 단순 기울임 -->
```

### 실습 과제
1. 자기소개 페이지 만들기 (h1, h2, p 사용)
2. 중요한 부분은 strong, 강조는 em 사용

---

## 📚 1.4 목록 태그

### 강의 포인트
- 순서가 중요한가? → ol 사용
- 순서가 중요하지 않은가? → ul 사용
- 네비게이션 메뉴는 보통 ul로 만듭니다

### ul (순서 없는 목록)
```html
<ul>
    <li>커피</li>
    <li>홍차</li>
    <li>우유</li>
</ul>
```
**사용 예**: 쇼핑 목록, 메뉴, 기능 리스트

### ol (순서 있는 목록)
```html
<ol>
    <li>재료 준비</li>
    <li>물 끓이기</li>
    <li>재료 넣기</li>
</ol>
```
**사용 예**: 레시피, 순위, 단계별 설명

### type 속성
- `type="1"`: 1, 2, 3... (기본값)
- `type="A"`: A, B, C...
- `type="a"`: a, b, c...
- `type="I"`: I, II, III...
- `start="5"`: 5번부터 시작

---

## 📚 1.5 링크 태그

### 강의 포인트
- 웹의 핵심은 "연결"입니다 (하이퍼링크)
- target="_blank" 사용 시 보안 이슈 설명
- 상대경로 vs 절대경로 개념

### 기본 링크
```html
<a href="https://www.google.com">구글</a>
```

### target 속성
```html
<a href="url" target="_self">현재 창 (기본값)</a>
<a href="url" target="_blank">새 창</a>
<a href="url" target="_blank" rel="noopener noreferrer">안전한 새 창</a>
```

💡 **보안 팁**: `target="_blank"` 사용 시 `rel="noopener noreferrer"` 추가하여 보안 취약점 방지

### 페이지 내 이동 (앵커)
```html
<a href="#section1">섹션 1로 이동</a>

<h2 id="section1">섹션 1</h2>
```

### 기타 링크
```html
<a href="mailto:email@example.com">이메일</a>
<a href="tel:010-1234-5678">전화</a>
<a href="file.pdf" download>다운로드</a>
```

---

## 📚 1.6 이미지 태그

### 강의 포인트
- alt 속성의 중요성 (접근성, SEO)
- 이미지 최적화 (용량, 형식)

### 기본 사용법
```html
<img src="image.jpg" alt="이미지 설명" width="300">
```

### alt 속성이 중요한 이유
1. 이미지가 로드 안 될 때 대체 텍스트 표시
2. 스크린 리더가 읽음 (시각장애인 접근성)
3. 검색 엔진이 이미지 내용을 이해

### 이미지 형식 선택
- **JPG**: 사진, 복잡한 이미지 (압축률 높음)
- **PNG**: 투명 배경, 로고, 아이콘
- **GIF**: 애니메이션
- **SVG**: 벡터 이미지 (확대해도 깨지지 않음)
- **WebP**: 차세대 형식 (용량 작고 화질 좋음)

---

## 📚 1.7 테이블 태그

### 강의 포인트
- 테이블은 **데이터 표현용**입니다 (레이아웃 용도 ❌)
- thead, tbody, tfoot로 구조화하면 가독성 ↑

### 기본 구조
```html
<table>
    <thead>
        <tr>
            <th>이름</th>
            <th>나이</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>홍길동</td>
            <td>25</td>
        </tr>
    </tbody>
</table>
```

### colspan과 rowspan
```html
<td colspan="2">2개 열 병합</td>
<td rowspan="2">2개 행 병합</td>
```

💡 **실무 팁**: 복잡한 테이블은 Excel에서 구조 잡고 HTML로 변환

---

## 📚 1.8-1.9 폼 요소와 Input 타입

### 강의 포인트
- 폼은 사용자 입력을 받는 핵심 요소
- HTML5에서 다양한 input 타입 추가 (validation 자동화)

### 주요 input 타입
```html
<input type="text">         <!-- 텍스트 -->
<input type="password">     <!-- 비밀번호 -->
<input type="email">        <!-- 이메일 (자동 검증) -->
<input type="number">       <!-- 숫자 -->
<input type="date">         <!-- 날짜 -->
<input type="checkbox">     <!-- 체크박스 (다중 선택) -->
<input type="radio">        <!-- 라디오 (단일 선택) -->
```

### 유용한 속성
- `required`: 필수 입력
- `placeholder`: 힌트 텍스트
- `minlength`, `maxlength`: 길이 제한
- `pattern`: 정규식 검증

---

## 📚 1.10 시맨틱 태그

### 강의 포인트
- 시맨틱 = "의미있는"
- div 대신 시맨틱 태그를 사용하면 SEO, 접근성, 가독성 향상
- "태그 이름만 봐도 용도를 알 수 있어야 한다"

### 주요 시맨틱 태그
```html
<header>   <!-- 머리글 (로고, 네비게이션) -->
<nav>      <!-- 네비게이션 링크 -->
<main>     <!-- 주요 콘텐츠 (페이지당 하나) -->
<section>  <!-- 독립적인 구획 -->
<article>  <!-- 독립적으로 배포 가능한 콘텐츠 -->
<aside>    <!-- 사이드바, 부가 정보 -->
<footer>   <!-- 바닥글 (저작권, 연락처) -->
```

### 시맨틱 vs Non-semantic
```html
<!-- ❌ Non-semantic -->
<div id="header">
<div class="navigation">
<div id="main-content">

<!-- ✅ Semantic -->
<header>
<nav>
<main>
```

---

## 📚 1.11-1.12 div, span과 속성

### div vs span
- **div**: 블록 레벨 (한 줄 전체 차지)
- **span**: 인라인 레벨 (콘텐츠 크기만큼)

### 주요 속성
#### id
- 페이지당 **하나만** 존재
- CSS: `#id`, JS: `getElementById()`

#### class
- **여러 개** 가능, 재사용 가능
- CSS: `.class`, JS: `getElementsByClassName()`

#### data-* (사용자 정의 속성)
```html
<div data-user-id="123" data-role="admin">
```
JavaScript에서 `element.dataset.userId`로 접근

---

## 🎯 강의 진행 팁

### 1시간차: HTML 소개와 기본 구조
- HTML이란?
- 문서 구조
- 텍스트 태그
- **실습**: 자기소개 페이지

### 2시간차: 목록과 링크
- 목록 태그
- 링크 태그
- **실습**: 네비게이션 메뉴

### 3시간차: 이미지와 테이블
- 이미지 태그
- 테이블 태그
- **실습**: 상품 목록 테이블

### 4시간차: 폼 요소
- 폼 태그
- Input 타입들
- **실습**: 회원가입 폼

### 5시간차: 시맨틱과 종합
- 시맨틱 태그
- div/span과 속성
- **종합 실습**: 블로그 레이아웃

---

## 📝 평가 기준

### 이해도 체크리스트
- [ ] HTML 문서의 기본 구조를 외우고 있다
- [ ] h1~h6의 용도를 이해하고 있다
- [ ] ul과 ol의 차이를 설명할 수 있다
- [ ] a 태그의 target 속성을 사용할 수 있다
- [ ] 테이블의 colspan/rowspan을 사용할 수 있다
- [ ] 다양한 input 타입을 상황에 맞게 사용할 수 있다
- [ ] 시맨틱 태그의 필요성을 이해하고 있다
- [ ] id와 class의 차이를 설명할 수 있다

### 실습 과제
1. 자기소개 페이지 제작
2. 레스토랑 메뉴판 제작
3. 회원가입 폼 제작
4. 간단한 블로그 레이아웃 제작

---

## 💡 자주하는 실수와 해결법

### 실수 1: 태그를 닫지 않음
```html
❌ <p>문단입니다.
✅ <p>문단입니다.</p>
```

### 실수 2: 태그 중첩 순서 오류
```html
❌ <strong><em>텍스트</strong></em>
✅ <strong><em>텍스트</em></strong>
```

### 실수 3: id 중복 사용
```html
❌ <div id="box"></div>
   <div id="box"></div>

✅ <div id="box1"></div>
   <div id="box2"></div>
```

### 실수 4: 인라인 요소에 블록 요소 넣기
```html
❌ <span><div>내용</div></span>
✅ <div><span>내용</span></div>
```

---

## 📚 추가 학습 자료

### 필수 자료
- MDN HTML 문서: https://developer.mozilla.org/ko/docs/Web/HTML
- W3C HTML 표준: https://www.w3.org/TR/html52/

### 연습 사이트
- CodePen: https://codepen.io/
- JSFiddle: https://jsfiddle.net/

### 추천 도서
- "HTML & CSS, 웹 표준의 정석" (고경희)
- "Do it! HTML+CSS+자바스크립트 웹 표준의 정석"

---

## 🎓 다음 단계

HTML을 마스터했다면:
1. **CSS** → 웹 페이지를 아름답게 꾸미기
2. **JavaScript** → 동적인 기능 추가하기
3. **반응형 웹** → 모바일 대응하기

**축하합니다! 1장 HTML을 완료했습니다! 🎉**

