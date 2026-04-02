# 7장: jQuery - JavaScript를 쉽게 사용하기

## 📋 강의 개요
- **소요 시간**: 약 6-8시간
- **난이도**: ⭐⭐ 초급~중급
- **선수 지식**: HTML, CSS, JavaScript 기초
- **학습 목표**: jQuery를 사용하여 DOM 조작과 이벤트 처리를 쉽게 구현할 수 있다.

---

## 📚 7.1 jQuery란?

### 강의 포인트
- jQuery는 "라이브러리"입니다 (프레임워크 ❌)
- "Write Less, Do More" - 적게 쓰고 많이 하자!
- 2006년 출시, 한때 90% 이상의 웹사이트에서 사용
- 최근에는 Vanilla JS가 발전했지만 여전히 많은 레거시 프로젝트에서 사용 중

### jQuery의 역할
```
Vanilla JavaScript  →  복잡하고 길어질 수 있음
jQuery             →  간결하고 쉬움
```

### jQuery를 배워야 하는 이유
1. **레거시 프로젝트**: 많은 기존 프로젝트가 jQuery 사용 중
2. **빠른 프로토타이핑**: 간단한 인터랙션을 빠르게 구현
3. **플러그인 생태계**: 수많은 jQuery 플러그인 존재
4. **학습 곡선**: JavaScript를 배우기 전 입문용으로 좋음

### ⚠️ 중요한 점
```
최신 프로젝트에서는?
- React, Vue, Angular 등 모던 프레임워크 사용 추세
- Vanilla JS도 많이 발전하여 jQuery 없이 충분

그럼 왜 배우나?
- 기존 코드 유지보수
- 레거시 시스템 작업
- jQuery 코드 이해 능력
```

---

## 📚 7.2 jQuery 설치 및 사용법

### 강의 포인트
- CDN 방식이 가장 간편함
- jQuery를 로드한 후에 사용해야 함 (순서 중요!)
- jQuery 버전 선택 (1.x, 2.x, 3.x)

### 1. CDN 방식 (권장) ⭐

```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>jQuery 예제</title>
</head>
<body>
    <h1 id="title">안녕하세요</h1>
    
    <!-- jQuery CDN - body 끝부분에 추가 -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    
    <!-- 내 스크립트 - jQuery 다음에 로드 -->
    <script>
        // jQuery가 로드된 후 실행
        $(document).ready(function() {
            $('#title').css('color', 'red');
        });
    </script>
</body>
</html>
```

**💡 강의 팁**: 
- `jquery-3.7.1.min.js` - 압축된 버전 (실서버용)
- `jquery-3.7.1.js` - 압축 안된 버전 (개발용, 디버깅 쉬움)

### 2. 다운로드 방식

```html
<!-- 다운로드한 파일 사용 -->
<script src="js/jquery-3.7.1.min.js"></script>
```

**장점**: 
- 오프라인에서도 작동
- CDN 장애 시에도 안전

**단점**: 
- 파일 관리 필요
- 버전 업데이트 수동

### 3. npm 방식 (Node.js 프로젝트)

```bash
npm install jquery
```

```javascript
// ES6 모듈
import $ from 'jquery';
```

---

## 📚 7.3 $ 기호 - jQuery의 핵심

### 강의 포인트
- `$`는 jQuery의 별칭(alias)
- `jQuery`와 `$`는 완전히 동일
- `$`는 함수입니다!

### $ 기호의 의미

```javascript
// 이 둘은 완전히 같습니다
jQuery('div');
$('div');

// $는 함수입니다
typeof $;  // "function"

// jQuery 버전 확인
console.log($.fn.jquery);  // "3.7.1"
```

### $ 함수의 다양한 용도

#### 1. 요소 선택
```javascript
$('div')           // 모든 div 선택
$('.box')          // class="box" 선택
$('#header')       // id="header" 선택
```

#### 2. HTML 생성
```javascript
$('<div>')                      // 빈 div 생성
$('<p>안녕</p>')                // p 태그 생성
$('<button>클릭</button>')      // button 생성
```

#### 3. 문서 준비 이벤트
```javascript
$(document).ready(function() {
    // DOM이 준비된 후 실행
});
```

#### 4. jQuery 객체 생성
```javascript
$(domElement)  // DOM 요소를 jQuery 객체로 변환
```

---

## 📚 7.4 $(document).ready() - 문서 준비 이벤트

### 강의 포인트
- DOM이 로드된 후에 실행되도록 보장
- jQuery 코드는 **반드시** 이 안에 작성!
- `window.onload`보다 빠름

### 3가지 작성 방법

#### 1. 전체 버전 (권장 - 명확함)
```javascript
$(document).ready(function() {
    // DOM이 준비된 후 실행될 코드
    console.log('문서 준비 완료!');
    $('#title').css('color', 'red');
});
```

#### 2. 단축 버전 (많이 사용됨)
```javascript
$(function() {
    // 동일한 기능
    console.log('문서 준비 완료!');
});
```

#### 3. ES6 화살표 함수 버전
```javascript
$(() => {
    // 최신 문법
    console.log('문서 준비 완료!');
});
```

### ready() vs load() 차이

```javascript
// $(document).ready() - DOM 구조만 로드되면 실행 (빠름!) ⚡
$(document).ready(function() {
    console.log('DOM 준비 완료');
});

// window.onload - 이미지, CSS 등 모든 리소스 로드 후 실행 (느림) 🐢
$(window).on('load', function() {
    console.log('모든 리소스 로드 완료');
});
```

**실행 순서**:
```
1. HTML 파싱 시작
2. DOM 트리 구축 완료 → $(document).ready() 실행 ⚡
3. CSS, 이미지 등 로드 중...
4. 모든 리소스 로드 완료 → window.onload 실행 🐢
```

---

## 📚 7.5 jQuery 선택자

### 강의 포인트
- CSS 선택자와 거의 동일!
- CSS를 알면 jQuery 선택자도 안다
- jQuery만의 추가 선택자도 있음 (필터 선택자)

### 1. 기본 선택자

```javascript
// 태그 선택자
$('p')              // 모든 p 태그
$('div')            // 모든 div 태그

// 클래스 선택자
$('.box')           // class="box"
$('.btn-primary')   // class="btn-primary"

// ID 선택자
$('#header')        // id="header"
$('#main-content')  // id="main-content"

// 전체 선택자
$('*')              // 모든 요소

// 다중 선택자
$('h1, h2, h3')     // h1, h2, h3 모두
$('.box, .card')    // .box와 .card 모두
```

### 2. 계층 선택자

```javascript
// 자손 선택자 (모든 하위)
$('div p')          // div 안의 모든 p

// 자식 선택자 (직접 자식만)
$('div > p')        // div의 직접 자식 p만

// 인접 형제
$('h2 + p')         // h2 바로 다음의 p

// 일반 형제
$('h2 ~ p')         // h2 이후의 모든 형제 p
```

**예제로 이해하기**:
```html
<div>
    <p>자손 ⭕ 자식 ⭕</p>  <!-- $('div p'), $('div > p') -->
    <section>
        <p>자손 ⭕ 자식 ❌</p>  <!-- $('div p')만 선택 -->
    </section>
</div>
```

### 3. 속성 선택자

```javascript
// 속성 존재
$('[href]')              // href 속성이 있는 요소

// 속성 값 일치
$('[type="text"]')       // type이 "text"
$('[class="box"]')       // class가 정확히 "box"

// 속성 값 시작
$('[href^="https"]')     // https로 시작

// 속성 값 끝
$('[href$=".pdf"]')      // .pdf로 끝남

// 속성 값 포함
$('[class*="btn"]')      // class에 btn 포함
```

**실무 예제**:
```javascript
// 외부 링크에 아이콘 추가
$('a[href^="http"]').addClass('external-link');

// 이메일 링크 스타일
$('a[href^="mailto"]').css('color', 'blue');

// PDF 링크 표시
$('a[href$=".pdf"]').prepend('📄 ');
```

---

## 📚 7.6 필터 선택자 (jQuery 특화)

### 강의 포인트
- jQuery만의 강력한 기능!
- Vanilla JS보다 훨씬 간편함
- 위치, 상태 기반 선택 가능

### 위치 기반 필터

```javascript
// 첫 번째
$('li:first')           // 첫 번째 li
$('p:first')            // 첫 번째 p

// 마지막
$('li:last')            // 마지막 li

// n번째 (0부터 시작!)
$('li:eq(0)')           // 첫 번째 (인덱스 0)
$('li:eq(2)')           // 세 번째 (인덱스 2)

// n번째보다 큰 인덱스
$('li:gt(2)')           // 인덱스 2보다 큰 (3, 4, 5...)

// n번째보다 작은 인덱스
$('li:lt(2)')           // 인덱스 2보다 작은 (0, 1)

// 짝수 (0, 2, 4...)
$('tr:even')            // 짝수 번째 행

// 홀수 (1, 3, 5...)
$('tr:odd')             // 홀수 번째 행
```

**⚠️ 주의**: 
```javascript
// :even은 인덱스 0부터 시작이므로
$('tr:even')  // 0, 2, 4번째 = 1, 3, 5번째 행
$('tr:odd')   // 1, 3, 5번째 = 2, 4, 6번째 행

// CSS :nth-child()와 다름!
```

**실무 예제 - 테이블 줄무늬**:
```javascript
// 홀수 행에 배경색
$('table tr:odd').css('background', '#f5f5f5');

// 첫 번째와 마지막에 강조
$('ul li:first').css('font-weight', 'bold');
$('ul li:last').css('color', 'red');
```

### 제외 필터

```javascript
// 특정 요소 제외
$('li:not(.active)')           // active 클래스가 없는 li
$('input:not([type="hidden"])') // hidden이 아닌 input
```

### 콘텐츠 기반 필터

```javascript
// 텍스트 포함
$('div:contains("안녕")')      // "안녕"을 포함하는 div

// 자식이 있는 요소
$('div:has(p)')               // p를 포함하는 div

// 비어있는 요소
$('div:empty')                // 내용이 없는 div

// 부모 요소
$('div:parent')               // 자식이 있는 div
```

### 가시성 필터

```javascript
// 보이는 요소
$('div:visible')              // display:none이 아닌 div

// 숨겨진 요소
$('div:hidden')               // display:none인 div
```

---

## 📚 7.7-7.8 DOM 조작 메소드

### 강의 포인트
- CRUD: Create, Read, Update, Delete
- jQuery는 메소드 체이닝 지원!
- getter와 setter 역할을 동시에 함

### 1. 텍스트/HTML 조작

#### text() - 텍스트 가져오기/설정하기
```javascript
// Getter - 텍스트 가져오기
let txt = $('#title').text();
console.log(txt);  // "안녕하세요"

// Setter - 텍스트 설정하기
$('#title').text('새로운 텍스트');

// HTML 태그는 문자열로 처리됨
$('#title').text('<strong>굵게</strong>');  // <strong>굵게</strong> 그대로 출력
```

#### html() - HTML 가져오기/설정하기
```javascript
// Getter
let html = $('#content').html();

// Setter
$('#content').html('<strong>굵은 텍스트</strong>');

// HTML 태그가 적용됨
$('#content').html('<p>문단</p>');  // 실제 p 태그로 렌더링
```

#### val() - 입력 값 가져오기/설정하기
```javascript
// input 값 가져오기
let userName = $('#name').val();

// input 값 설정하기
$('#name').val('홍길동');

// select 값
$('#country').val('kr');

// checkbox 값
$('#agree').val();

// 여러 input 값 한번에 설정
$('#name').val('홍길동');
$('#email').val('hong@example.com');
```

**text() vs html() vs val() 비교**:
```javascript
// HTML 구조
<div id="box">안녕하세요</div>
<input id="name" value="홍길동">

// text() - 텍스트만
$('#box').text()        // "안녕하세요"
$('#box').text('Hi')    // 텍스트 변경

// html() - HTML 포함
$('#box').html()                    // "안녕하세요"
$('#box').html('<b>안녕하세요</b>') // HTML 변경

// val() - 입력 값
$('#name').val()        // "홍길동"
$('#name').val('김철수') // 값 변경
```

---

### 2. 요소 추가/삭제

#### append() - 마지막 자식으로 추가
```javascript
// 텍스트 추가
$('#list').append('<li>새 항목</li>');

// 여러 개 추가
$('#list').append('<li>항목1</li>')
         .append('<li>항목2</li>');

// 한번에 여러 개
$('#list').append('<li>항목1</li><li>항목2</li>');
```

#### prepend() - 첫 번째 자식으로 추가
```javascript
$('#list').prepend('<li>맨 앞 항목</li>');
```

#### after() - 요소 뒤에 추가 (형제로)
```javascript
$('#title').after('<p>제목 뒤에 추가</p>');
```

#### before() - 요소 앞에 추가 (형제로)
```javascript
$('#title').before('<p>제목 앞에 추가</p>');
```

**append vs after 차이**:
```html
<!-- 원본 -->
<div id="box">
    <p>내용</p>
</div>

<!-- append() 사용 -->
<div id="box">
    <p>내용</p>
    <span>추가됨</span>  <!-- 자식으로 추가 -->
</div>

<!-- after() 사용 -->
<div id="box">
    <p>내용</p>
</div>
<span>추가됨</span>  <!-- 형제로 추가 -->
```

#### remove() - 요소 제거
```javascript
// 특정 요소 제거
$('#title').remove();

// 선택자로 제거
$('.box').remove();

// 조건부 제거
$('li:odd').remove();  // 홀수 번째 제거
```

#### empty() - 자식 요소만 제거
```javascript
// 내용물만 비움
$('#box').empty();

// remove vs empty
$('#box').remove();  // #box 자체를 제거
$('#box').empty();   // #box는 남기고 내용만 제거
```

---

### 3. 속성 조작

#### attr() - 속성 가져오기/설정하기
```javascript
// Getter
let src = $('img').attr('src');
let href = $('a').attr('href');

// Setter
$('img').attr('src', 'new-image.jpg');
$('a').attr('href', 'https://www.google.com');

// 여러 속성 동시 설정
$('img').attr({
    'src': 'image.jpg',
    'alt': '이미지 설명',
    'width': '300'
});
```

#### removeAttr() - 속성 제거
```javascript
$('img').removeAttr('width');
$('a').removeAttr('target');
```

#### addClass() - 클래스 추가
```javascript
$('.box').addClass('active');
$('.box').addClass('active highlight');  // 여러 개 추가
```

#### removeClass() - 클래스 제거
```javascript
$('.box').removeClass('active');
$('.box').removeClass();  // 모든 클래스 제거
```

#### toggleClass() - 클래스 토글
```javascript
// 있으면 제거, 없으면 추가
$('.box').toggleClass('active');

// 클릭할 때마다 토글
$('.btn').click(function() {
    $(this).toggleClass('active');
});
```

#### hasClass() - 클래스 확인
```javascript
if ($('.box').hasClass('active')) {
    console.log('active 클래스가 있습니다');
}
```

---

## 🎯 Vanilla JS vs jQuery 비교

### 강의 포인트
- jQuery가 얼마나 간결한지 체감시키기
- 하지만 최신 Vanilla JS도 많이 개선됨을 언급

### 예제 1: 요소 선택
```javascript
// Vanilla JS
document.getElementById('box');
document.querySelector('.box');
document.querySelectorAll('.box');

// jQuery
$('#box');
$('.box');
$('.box');  // 항상 배열 형태로 반환
```

### 예제 2: 텍스트 변경
```javascript
// Vanilla JS
document.getElementById('title').textContent = '새 제목';

// jQuery
$('#title').text('새 제목');
```

### 예제 3: 이벤트 핸들링
```javascript
// Vanilla JS
document.querySelector('.btn').addEventListener('click', function() {
    alert('클릭!');
});

// jQuery
$('.btn').click(function() {
    alert('클릭!');
});
```

### 예제 4: 여러 요소에 스타일 적용
```javascript
// Vanilla JS
let boxes = document.querySelectorAll('.box');
boxes.forEach(function(box) {
    box.style.color = 'red';
});

// jQuery
$('.box').css('color', 'red');  // 한 줄!
```

### 예제 5: AJAX
```javascript
// Vanilla JS
fetch('https://api.example.com/data')
    .then(response => response.json())
    .then(data => console.log(data));

// jQuery
$.get('https://api.example.com/data', function(data) {
    console.log(data);
});
```

---

## 💡 실무 팁

### 1. 메소드 체이닝 활용
```javascript
// ❌ 비효율적
$('#box').css('color', 'red');
$('#box').addClass('active');
$('#box').fadeIn();

// ✅ 체이닝 사용
$('#box')
    .css('color', 'red')
    .addClass('active')
    .fadeIn();
```

### 2. 변수에 저장 (캐싱)
```javascript
// ❌ 매번 DOM 검색
$('#box').css('color', 'red');
$('#box').addClass('active');
$('#box').text('내용');

// ✅ 한번만 검색
let $box = $('#box');
$box.css('color', 'red');
$box.addClass('active');
$box.text('내용');

// 💡 jQuery 객체는 변수명 앞에 $를 붙이는 관례
```

### 3. 존재 여부 확인
```javascript
// 요소가 있는지 확인
if ($('#box').length > 0) {
    $('#box').css('color', 'red');
}

// 더 간단하게
if ($('#box').length) {
    $('#box').css('color', 'red');
}
```

---

## 📝 자주하는 실수

### 실수 1: jQuery 로드 전에 사용
```html
❌ <script>
    $('#title').text('안녕');  // jQuery가 아직 로드 안됨!
</script>
<script src="jquery.js"></script>

✅ <script src="jquery.js"></script>
<script>
    $(function() {
        $('#title').text('안녕');
    });
</script>
```

### 실수 2: $(document).ready() 없이 사용
```javascript
❌ $('#title').text('안녕');  // DOM이 아직 준비 안됨!

✅ $(function() {
    $('#title').text('안녕');  // 안전!
});
```

### 실수 3: 선택자 오타
```javascript
❌ $('#titel').text('안녕');  // title 오타!
❌ $('.bxo').css('color', 'red');  // box 오타!

✅ $('#title').text('안녕');
✅ $('.box').css('color', 'red');
```

---

## 🎯 강의 진행 팁

### 1교시: jQuery 소개 (1시간)
- jQuery란?
- CDN 설정
- $ 기호
- $(document).ready()

### 2교시: 선택자 (1.5시간)
- 기본 선택자
- 필터 선택자
- **실습**: 선택자 테스트

### 3교시: DOM 조작 (2시간)
- text(), html(), val()
- append(), remove()
- attr(), addClass()
- **실습**: 할 일 목록 만들기

### 4교시: 종합 실습 (1.5시간)
- 실전 프로젝트
- 디버깅 연습

---

## 🎓 다음 단계 예고

7장을 마스터한 후:
1. **이벤트 핸들링** (click, hover, on)
2. **효과와 애니메이션** (fadeIn, slideDown)
3. **AJAX** ($.ajax, $.get, $.post)

**7장 jQuery 기초를 완료했습니다! 다음은 이벤트와 AJAX입니다! 💎**

