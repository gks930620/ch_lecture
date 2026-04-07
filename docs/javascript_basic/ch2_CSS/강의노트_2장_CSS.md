# 2장: CSS - 웹의 디자인

## 📋 강의 개요
- **소요 시간**: 약 6-8시간
- **난이도**: ⭐⭐ 초급
- **선수 지식**: HTML 기초
- **학습 목표**: CSS로 웹 페이지를 스타일링하고 레이아웃을 구성할 수 있다.

---

## 📚 2.1 CSS란?

### 강의 포인트
- CSS는 HTML의 "옷"입니다
- HTML은 구조, CSS는 디자인을 담당
- Cascading(계단식)의 의미 설명 중요

### 핵심 내용
```
CSS (Cascading Style Sheets)
- Cascading: 여러 스타일이 계단식(우선순위)으로 적용
- Style: 색상, 크기, 위치 등을 정의
- Sheets: 스타일 규칙의 모음
```

### CSS의 역할
```
HTML + CSS + JavaScript
= 건물 뼈대 + 인테리어 + 가전제품
= 구조 + 디자인 + 기능
```

---

## 📚 2.2 CSS 적용 방법

### 강의 포인트
- 3가지 방법이 있지만 **외부 CSS 파일 사용을 강력 권장**
- 우선순위: 인라인 > 내부 > 외부 (하지만 !important가 최우선)

### 1. 인라인 스타일 (Inline Style) ❌ 비권장
```html
<p style="color: red; font-size: 20px;">텍스트</p>
```
**단점**:
- HTML과 CSS가 섞여서 유지보수 어려움
- 재사용 불가능
- 우선순위가 너무 높아서 나중에 수정 어려움

**사용 시기**: 
- 긴급한 테스트
- 이메일 템플릿 (제한적인 환경)

### 2. 내부 스타일시트 (Internal CSS) △ 제한적 사용
```html
<head>
    <style>
        p {
            color: blue;
            font-size: 16px;
        }
    </style>
</head>
```
**장점**: 
- 한 파일 안에서 모두 관리 가능
**단점**: 
- 다른 페이지에서 재사용 불가
- HTML 파일이 길어짐

**사용 시기**:
- 단일 페이지 프로젝트
- 해당 페이지에만 적용되는 특수한 스타일

### 3. 외부 스타일시트 (External CSS) ✅ 강력 권장
```html
<!-- HTML 파일 -->
<head>
    <link rel="stylesheet" href="style.css">
</head>
```
```css
/* style.css 파일 */
p {
    color: green;
    font-size: 18px;
}
```
**장점**:
- 여러 페이지에서 재사용 가능
- HTML과 CSS 분리로 유지보수 용이
- 캐싱으로 성능 향상
- 팀 협업에 유리

**실무 팁**: 
- 프로젝트 구조: `css/`, `js/`, `images/` 폴더로 분리
- 파일명: `style.css`, `main.css`, `common.css` 등

---

## 📚 2.3 CSS 선택자 (Selectors)

### 강의 포인트
- 선택자는 "스타일을 적용할 대상을 선택하는 방법"
- 선택자를 잘 사용하면 코드가 간결해짐
- 우선순위 개념이 매우 중요!

### 1. 기본 선택자

#### 태그 선택자 (Type Selector)
```css
/* 모든 p 태그에 적용 */
p {
    color: blue;
}

/* 모든 h1 태그에 적용 */
h1 {
    font-size: 32px;
}
```
**사용 시기**: 전체적인 기본 스타일 설정

#### 클래스 선택자 (Class Selector) ⭐ 가장 많이 사용
```css
/* class="box"인 모든 요소 */
.box {
    width: 200px;
    height: 200px;
}

/* 여러 클래스 동시 사용 가능 */
.box.red {
    background: red;
}
```
**명명 규칙**:
- 소문자 사용
- 하이픈(-) 또는 언더스코어(_) 사용
- 의미있는 이름: `.main-title`, `.user-card`, `.btn-primary`

#### ID 선택자 (ID Selector)
```css
/* id="header"인 요소 */
#header {
    background: #333;
    color: white;
}
```
**주의사항**:
- 페이지당 하나만 사용
- JavaScript용으로 주로 사용
- CSS에서는 클래스 사용 권장

#### 전체 선택자 (Universal Selector)
```css
/* 모든 요소에 적용 */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
```
**사용 시기**: CSS 리셋용

---

### 2. 복합 선택자

#### 그룹 선택자
```css
/* 여러 선택자에 동일한 스타일 */
h1, h2, h3 {
    font-family: 'Arial', sans-serif;
    color: #333;
}
```

#### 자손 선택자 (Descendant Selector)
```css
/* div 안의 모든 p (깊이 무관) */
div p {
    color: blue;
}
```

#### 자식 선택자 (Child Selector)
```css
/* div의 직접 자식 p만 */
div > p {
    color: red;
}
```

**차이점 설명**:
```html
<div>
    <p>자손 ⭕ 자식 ⭕</p>
    <section>
        <p>자손 ⭕ 자식 ❌</p>
    </section>
</div>
```

#### 인접 형제 선택자
```css
/* h2 바로 다음의 p */
h2 + p {
    font-weight: bold;
}

/* h2 이후의 모든 형제 p */
h2 ~ p {
    color: gray;
}
```

---

### 3. 속성 선택자

```css
/* type이 text인 input */
input[type="text"] {
    border: 2px solid blue;
}

/* href가 https로 시작 */
a[href^="https"] {
    color: green;
}

/* href가 .pdf로 끝남 */
a[href$=".pdf"] {
    background: url('pdf-icon.png');
}

/* class에 btn 포함 */
[class*="btn"] {
    padding: 10px 20px;
}
```

---

### 4. 가상 클래스 선택자 (Pseudo-class)

```css
/* 마우스 올렸을 때 */
a:hover {
    color: red;
}

/* 포커스 받았을 때 */
input:focus {
    border-color: blue;
}

/* 첫 번째 자식 */
li:first-child {
    font-weight: bold;
}

/* 마지막 자식 */
li:last-child {
    border-bottom: none;
}

/* n번째 자식 */
li:nth-child(2) {
    color: red;
}

/* 짝수 번째 */
tr:nth-child(even) {
    background: #f5f5f5;
}

/* 홀수 번째 */
tr:nth-child(odd) {
    background: white;
}
```

**실무 활용 예시**:
```css
/* 테이블 행 번갈아 색상 */
table tr:nth-child(even) {
    background: #f9f9f9;
}

/* 네비게이션 메뉴 호버 효과 */
.nav a:hover {
    background: #007bff;
    color: white;
}
```

---

### 5. 가상 요소 선택자 (Pseudo-element)

```css
/* 요소 앞에 콘텐츠 추가 */
.quote::before {
    content: '"';
    font-size: 2em;
}

/* 요소 뒤에 콘텐츠 추가 */
.quote::after {
    content: '"';
}

/* 첫 번째 줄 */
p::first-line {
    font-weight: bold;
}

/* 첫 번째 글자 */
p::first-letter {
    font-size: 2em;
    color: red;
}
```

**실무 활용**:
```css
/* 아이콘 추가 */
.external-link::after {
    content: ' 🔗';
}

/* clearfix */
.clearfix::after {
    content: '';
    display: block;
    clear: both;
}
```

---

## 📚 CSS 우선순위 (Specificity)

### 강의 포인트
- CSS에서 가장 헷갈리는 부분!
- 같은 요소에 여러 스타일이 적용될 때 어떤 게 적용될까?
- 점수 계산법을 알면 쉽게 이해 가능

### 우선순위 점수 체계
```
!important       = 무한대 (최우선, 남용 금지!)
인라인 스타일     = 1000점
ID 선택자        = 100점
클래스/속성/가상  = 10점
태그 선택자       = 1점
전체 선택자(*)    = 0점
```

### 점수 계산 예시
```css
/* 1점 */
p { color: blue; }

/* 10점 */
.text { color: red; }

/* 100점 */
#title { color: green; }

/* 101점 (100 + 1) */
#title p { color: yellow; }

/* 111점 (100 + 10 + 1) */
#title .text p { color: purple; }

/* 1000점 */
<p style="color: orange;">

/* 무한대 */
p { color: pink !important; }
```

### 우선순위 실습
```html
<p id="special" class="text" style="color: red;">
    이 글자는 무슨 색일까요?
</p>
```

```css
p { color: blue; }              /* 1점 */
.text { color: green; }          /* 10점 */
#special { color: yellow; }      /* 100점 */
/* 인라인 스타일 = 1000점 */
/* 답: red (인라인 스타일이 가장 우선) */
```

---

## 🎯 선택자 선택 가이드

### ✅ 권장사항
1. **클래스 선택자를 기본으로 사용**
   ```css
   .button { /* 재사용 가능 */ }
   ```

2. **의미있는 이름 사용**
   ```css
   .user-profile { }  /* ✅ Good */
   .box1 { }          /* ❌ Bad */
   ```

3. **선택자는 간결하게**
   ```css
   .header .nav .item a { }  /* ❌ 너무 길음 */
   .nav-link { }              /* ✅ Good */
   ```

4. **ID는 JavaScript용으로**
   ```html
   <div id="app">  <!-- JS용 -->
       <div class="container">  <!-- CSS용 -->
   ```

### ❌ 피해야 할 것
1. **!important 남용**
   ```css
   .text { color: red !important; }  /* 최후의 수단! */
   ```

2. **지나치게 구체적인 선택자**
   ```css
   html body div.container div.row div.col p.text { }  /* ❌ */
   ```

3. **태그 선택자 남용**
   ```css
   div { }  /* 모든 div에 영향, 위험! */
   ```

---

## 💡 실무 팁

### 1. BEM 네이밍 규칙
```css
/* Block__Element--Modifier */
.card { }
.card__title { }
.card__body { }
.card--featured { }
```

### 2. CSS 구조화
```css
/* 1. 리셋 */
* { margin: 0; padding: 0; }

/* 2. 변수 */
:root {
    --primary-color: #007bff;
}

/* 3. 공통 스타일 */
.container { }
.btn { }

/* 4. 레이아웃 */
.header { }
.main { }
.footer { }

/* 5. 컴포넌트 */
.card { }
.modal { }

/* 6. 유틸리티 */
.text-center { }
.mt-10 { }
```

### 3. 주석 활용
```css
/* =================================
   헤더 스타일
   ================================= */

.header {
    /* 레이아웃 */
    display: flex;
    justify-content: space-between;
    
    /* 스타일링 */
    background: white;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
```

---

## 📝 자주하는 실수

### 실수 1: 세미콜론 빠뜨림
```css
❌ p {
    color: blue
    font-size: 16px
}

✅ p {
    color: blue;
    font-size: 16px;
}
```

### 실수 2: 중괄호 누락
```css
❌ p
    color: blue;

✅ p {
    color: blue;
}
```

### 실수 3: 선택자 오타
```css
❌ .buton { }  /* button 오타 */
✅ .button { }
```

---

## 🎯 강의 진행 팁

### 1교시: CSS 기초 (1시간)
- CSS란?
- 적용 방법 3가지
- 기본 문법
- **실습**: 텍스트 색상, 크기 변경

### 2교시: 선택자 (1.5시간)
- 기본 선택자
- 복합 선택자
- 우선순위
- **실습**: 선택자 점수 계산 게임

### 3교시: 가상 클래스/요소 (1시간)
- :hover, :focus 등
- ::before, ::after
- **실습**: 호버 효과, 아이콘 추가

---

## 📚 다음 단계 예고

CSS를 마스터한 후 배울 내용:
1. **박스 모델** (margin, padding, border)
2. **레이아웃** (Flexbox, Grid)
3. **반응형 웹** (미디어 쿼리)
4. **애니메이션** (transition, animation)

**2장 CSS 선택자를 완료했습니다! 다음은 박스 모델과 레이아웃입니다! 🎨**

