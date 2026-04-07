# 📚 PART 3: 웹 기초 (프론트엔드)

> HTML, CSS, JavaScript 기초부터 HTTP 통신까지

[← 목차로 돌아가기](#)

---

## 1장. HTML
- 1.1 HTML이란?
- 1.2 HTML 문서 구조 (DOCTYPE, html, head, body)
- 1.3 텍스트 태그 (h1~h6, p, span, br, hr)
- 1.4 목록 태그 (ul, ol, li)
- 1.5 링크 (a 태그)
- 1.6 이미지 (img 태그)
- 1.7 테이블 (table, tr, td, th, thead, tbody)
- 1.8 폼 요소 (form, input, button, select, textarea)
- 1.9 input 타입 (text, password, email, number, date, file, checkbox, radio)
- 1.10 시맨틱 태그 (header, nav, main, section, article, aside, footer)
- 1.11 div와 span
- 1.12 속성 (id, class, name, data-*)
- 1.13 HTML5 새로운 요소

## 2장. CSS
- 2.1 CSS란?
- 2.2 CSS 적용 방법 (인라인, 내부, 외부)
- 2.3 선택자 (태그, 클래스, ID, 속성, 자식, 자손, 인접)
- 2.4 가상 클래스 (:hover, :focus, :nth-child 등)
- 2.5 가상 요소 (::before, ::after)
- 2.6 박스 모델 (margin, border, padding, content)
- 2.7 display 속성 (block, inline, inline-block, none)
- 2.8 position 속성 (static, relative, absolute, fixed, sticky)
- 2.9 z-index
- 2.10 float과 clear
- 2.11 Flexbox 레이아웃
- 2.12 Grid 레이아웃
- 2.13 반응형 웹 (미디어 쿼리)
- 2.14 CSS 변수 (Custom Properties)
- 2.15 CSS 애니메이션 (transition, animation, keyframes)
- 2.16 CSS 프레임워크 소개 (Bootstrap, Tailwind CSS)

## 3장. JavaScript 기초
- 3.1 JavaScript란?
- 3.2 변수 선언 (var, let, const)
- 3.3 자료형 (number, string, boolean, null, undefined, symbol, bigint)
- 3.4 typeof 연산자
- 3.5 산술/비교/논리 연산자
- 3.6 == vs === (동등 vs 일치)
- 3.7 조건문 (if, switch)
- 3.8 반복문 (for, while, for...in, for...of)
- 3.9 함수 선언문 vs 함수 표현식
- 3.10 화살표 함수 (Arrow Function)
- 3.11 배열 (Array)
- 3.12 배열 메소드 (push, pop, shift, unshift, splice, slice)
- 3.13 객체 (Object)
- 3.14 JSON (JSON.parse, JSON.stringify)

## 4장. JavaScript 심화
- 4.1 스코프 (전역, 함수, 블록)
- 4.2 호이스팅 (Hoisting)
- 4.3 클로저 (Closure)
- 4.4 this 바인딩
- 4.5 call, apply, bind
- 4.6 프로토타입 (Prototype)
- 4.7 클래스 (ES6 class)
- 4.8 모듈 (import, export)
- 4.9 구조 분해 할당 (Destructuring)
- 4.10 스프레드 연산자 (Spread Operator)
- 4.11 Rest 파라미터
- 4.12 템플릿 리터럴 (Template Literal)
- 4.13 Symbol
- 4.14 Map과 Set
- 4.15 이터레이터와 제너레이터

## 5장. DOM 조작
- 5.1 DOM이란?
- 5.2 요소 선택 (getElementById, querySelector, querySelectorAll)
- 5.3 요소 생성/추가/삭제 (createElement, appendChild, remove)
- 5.4 속성 조작 (getAttribute, setAttribute, classList)
- 5.5 스타일 조작 (style, classList)
- 5.6 innerHTML vs textContent vs innerText
- 5.7 이벤트 리스너 (addEventListener)
- 5.8 이벤트 객체 (event)
- 5.9 이벤트 버블링과 캡처링
- 5.10 이벤트 위임 (Event Delegation)
- 5.11 폼 이벤트 (submit, input, change)
- 5.12 키보드/마우스 이벤트

## 6장. 비동기 처리
- 6.1 동기 vs 비동기
- 6.2 콜백 함수
- 6.3 콜백 지옥
- 6.4 Promise 기초
- 6.5 Promise 체이닝
- 6.6 Promise.all, Promise.race, Promise.allSettled
- 6.7 async / await
- 6.8 try-catch로 에러 처리
- 6.9 Fetch API 기초
- 6.10 Fetch로 GET/POST/PUT/DELETE 요청
- 6.11 FormData 전송
- 6.12 AJAX란? (XMLHttpRequest)
- 6.13 axios 라이브러리

## 7장. jQuery
- 7.1 jQuery란?
- 7.2 jQuery 설치 및 사용법 (CDN, 다운로드, npm)
- 7.3 $ 기호와 jQuery 객체
- 7.4 $(document).ready()
- 7.5 jQuery 선택자
- 7.6 필터 선택자 (jQuery 특화)
- 7.7 DOM 조작 메소드 (text, html, val, append, remove)
- 7.8 속성 조작 (attr, addClass, removeClass, toggleClass)
- 7.9 이벤트 핸들링 (click, on, hover)
- 7.10 효과와 애니메이션 (fadeIn, slideDown, animate)
- 7.11 AJAX ($.ajax, $.get, $.post)

## 8장. HTTP 통신 이해
- 8.1 HTTP 프로토콜이란?
- 8.2 HTTP 버전 (HTTP/1.1, HTTP/2, HTTP/3)
- 8.3 요청 구조 (Request Line, Header, Body)
- 8.4 응답 구조 (Status Line, Header, Body)
- 8.5 HTTP 메서드 (GET, POST, PUT, PATCH, DELETE, OPTIONS)
- 8.6 멱등성과 안전성
- 8.7 상태 코드 (1xx, 2xx, 3xx, 4xx, 5xx)
- 8.8 주요 상태 코드 (200, 201, 204, 301, 302, 400, 401, 403, 404, 409, 500, 502, 503)
- 8.9 헤더 종류 (Content-Type, Authorization, Accept, Cookie, Set-Cookie)
- 8.10 쿠키 (Cookie)
- 8.11 세션 (Session)
- 8.12 로컬 스토리지 / 세션 스토리지
- 8.13 HTTPS와 SSL/TLS
- 8.14 CORS (Cross-Origin Resource Sharing)

## 9장. 웹 브라우저 동작 원리
- 9.1 브라우저 구조
- 9.2 렌더링 엔진
- 9.3 DOM 트리 / CSSOM 트리
- 9.4 렌더 트리
- 9.5 리플로우(Reflow)와 리페인트(Repaint)
- 9.6 브라우저 캐싱
- 9.7 개발자 도구 (DevTools) 사용법

## 10장. JavaScript 프로젝트 가이드
- 10.1 프로젝트 기본 구조 (폴더, 파일 구성)
- 10.2 입문 프로젝트 (계산기, 가위바위보, 랜덤 생성기)
- 10.3 초급 프로젝트 (Todo List, 스톱워치, 퀴즈 앱)
- 10.4 중급 프로젝트 (날씨 앱, 영화 검색, 메모 앱)
- 10.5 고급 프로젝트 (게시판 CRUD, 가계부, 상품 관리)
- 10.6 프로젝트 진행 가이드 (기획 → 구현 → 테스트)
- 10.7 활용 가능한 무료 API 목록
- 10.8 자주 쓰는 유틸리티 코드 모음
- 10.9 프로젝트 난이도 & 기술 매핑

---

**이전: [PART 2: 데이터베이스](./PART2_데이터베이스.md)**  
**다음: [PART 4: Java 웹 개발](./PART4_Java웹개발.md)**

