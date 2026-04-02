# 10장: JavaScript 프로젝트 가이드

## 📋 개요

> **1~9장에서 배운 HTML, CSS, JavaScript만으로 만들 수 있는 프로젝트 모음**
>
> 이 문서는 "뭘 만들지?" 고민하는 학생들을 위한 **프로젝트 아이디어 + 기술 가이드**입니다.
> 코드를 떠먹여 주는 것이 아니라, **어떤 기술이 필요하고 어떻게 접근해야 하는지**를 정리했습니다.
> 직접 구글링하고, 삽질하고, 완성해보세요. 그게 진짜 실력입니다.

### 이 문서 사용법

1. 자기 수준에 맞는 프로젝트를 고른다
2. "필요한 기술" 목록을 보고 해당 챕터를 복습한다
3. "구현 힌트"를 참고하여 직접 설계하고 코딩한다
4. 막히면 검색하고, 그래도 안 되면 질문한다

### 공통 규칙

```
✅ 허용
- HTML, CSS, JavaScript (Vanilla JS)
- jQuery (선택)
- 외부 CSS 라이브러리 (Bootstrap 등, 선택)
- 공개 API (무료)
- 강사가 제공하는 API 프록시 서버

❌ 금지
- React, Vue, Angular 등 프레임워크
- TypeScript
- Node.js 서버 직접 구축 (프록시 서버는 강사가 제공)
```

---

## 📁 프로젝트 기본 구조

어떤 프로젝트든 이 구조에서 시작하세요.

```
my-project/
├── index.html          ← 메인 페이지
├── css/
│   └── style.css       ← 스타일
├── js/
│   └── app.js          ← 메인 로직
│   └── api.js          ← API 통신 (필요 시)
└── images/             ← 이미지 (필요 시)
```

### index.html 기본 뼈대

```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>프로젝트 이름</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header>
        <h1>프로젝트 이름</h1>
    </header>
    
    <main id="app">
        <!-- 여기에 내용 -->
    </main>
    
    <footer>
        <p>© 2026 내이름</p>
    </footer>

    <script src="js/app.js"></script>
</body>
</html>
```

---

---

# 🟢 입문 프로젝트 (1~3장 수준)

> HTML/CSS 기초 + JavaScript 기초만 알면 만들 수 있는 프로젝트

---

## 프로젝트 1: 계산기

### 📝 설명
윈도우/맥 기본 계산기처럼 사칙연산이 되는 웹 계산기

### 🎯 완성하면 이런 것
- 숫자 버튼을 클릭하면 화면에 숫자가 표시됨
- +, -, ×, ÷ 연산 가능
- = 버튼으로 결과 계산
- C 버튼으로 초기화
- 소수점, 음수 처리

### 🔧 필요한 기술

| 기술 | 배운 곳 | 용도 |
|------|---------|------|
| HTML 폼/버튼 | 1장 | 계산기 버튼 UI |
| CSS Flexbox/Grid | 2장 | 버튼 격자 배치 |
| 변수, 연산자 | 3장 3.2~3.5 | 계산 로직 |
| 조건문 | 3장 3.7 | 연산자 분기 |
| 함수 | 3장 3.9 | 로직 분리 |
| DOM 조작 | 5장 | 버튼 클릭 → 화면 업데이트 |
| 이벤트 리스너 | 5장 5.7 | 버튼 클릭 처리 |

### 💡 구현 힌트
- 계산기 상태를 변수로 관리: `currentNumber`, `previousNumber`, `operator`
- 버튼마다 `addEventListener`를 달지 말고 **이벤트 위임** 활용
- `eval()` 쓰지 마세요! 직접 계산 로직을 만들어야 실력이 늘어요
- CSS Grid로 4×5 버튼 배치하면 깔끔

### ⭐ 난이도: ★☆☆☆☆
### ⏰ 예상 소요: 4~6시간

---

## 프로젝트 2: 가위바위보 게임

### 📝 설명
컴퓨터와 가위바위보를 하고 전적을 기록하는 게임

### 🎯 완성하면 이런 것
- 가위/바위/보 버튼 클릭
- 컴퓨터가 랜덤으로 선택
- 승/패/무 판정 + 결과 표시
- 전적 기록 (5승 3패 2무)
- 연승 기록

### 🔧 필요한 기술

| 기술 | 배운 곳 | 용도 |
|------|---------|------|
| 조건문 (if/switch) | 3장 3.7 | 승패 판정 |
| 배열 | 3장 3.11 | 선택지 목록 |
| Math.random() | - | 컴퓨터 랜덤 선택 |
| 객체 | 3장 3.13 | 전적 데이터 관리 |
| DOM 조작 | 5장 | 결과 화면 업데이트 |
| classList | 5장 5.4 | 승/패에 따른 스타일 변경 |

### 💡 구현 힌트
- `Math.floor(Math.random() * 3)`으로 0, 1, 2 중 랜덤 선택
- 승패 판정은 규칙표를 객체로 만들면 깔끔
- 전적은 객체로: `{ win: 0, lose: 0, draw: 0 }`
- CSS 애니메이션으로 결과 표시 시 효과 주면 더 좋음

### ⭐ 난이도: ★☆☆☆☆
### ⏰ 예상 소요: 3~5시간

---

## 프로젝트 3: 랜덤 명언/색상 생성기

### 📝 설명
버튼을 클릭하면 랜덤 명언이 표시되고 배경색이 바뀌는 페이지

### 🎯 완성하면 이런 것
- 버튼 클릭 → 랜덤 명언 표시
- 배경색도 랜덤으로 변경
- 명언 복사 버튼
- 트위터/SNS 공유 버튼 (URL 링크)

### 🔧 필요한 기술

| 기술 | 배운 곳 | 용도 |
|------|---------|------|
| 배열 | 3장 3.11 | 명언 데이터 저장 |
| Math.random() | - | 랜덤 선택 |
| 문자열 메소드 | 3장 3.3 | 텍스트 처리 |
| DOM 조작 (textContent) | 5장 5.6 | 명언 표시 |
| style 조작 | 5장 5.5 | 배경색 변경 |
| Clipboard API | - | 복사 기능 |

### 💡 구현 힌트
- 명언 데이터는 배열 안에 객체로: `[{ text: "명언", author: "저자" }, ...]`
- 랜덤 색상: `` `#${Math.floor(Math.random()*16777215).toString(16)}` ``
- 글자색은 배경 밝기에 따라 자동 전환 (밝으면 검정, 어두우면 흰색)
- `navigator.clipboard.writeText()`로 복사 기능

### ⭐ 난이도: ★☆☆☆☆
### ⏰ 예상 소요: 3~4시간

---

---

# 🟡 초급 프로젝트 (3~5장 수준)

> JavaScript 기초 + DOM 조작을 활용하는 프로젝트

---

## 프로젝트 4: Todo List (할 일 관리)

### 📝 설명
할 일을 추가/완료/삭제할 수 있는 앱. 새로고침해도 데이터 유지.

### 🎯 완성하면 이런 것
- 할 일 입력 → 추가
- 체크박스로 완료 표시 (취소선)
- 삭제 버튼
- 전체/완료/미완료 필터링
- **LocalStorage에 저장** (새로고침해도 유지!)
- 할 일 개수 표시

### 🔧 필요한 기술

| 기술 | 배운 곳 | 용도 |
|------|---------|------|
| 배열 메소드 (filter, map) | 3장 3.12 | 데이터 필터링 |
| 객체 | 3장 3.13 | todo 항목 구조 |
| JSON | 3장 3.14 | LocalStorage 저장/불러오기 |
| DOM 생성/삭제 | 5장 5.3 | 동적 리스트 |
| 이벤트 위임 | 5장 5.10 | 동적 요소 이벤트 처리 |
| 폼 이벤트 (submit) | 5장 5.11 | 입력 처리 |
| LocalStorage | 8장 8.12 | 데이터 영구 저장 |

### 💡 구현 힌트
- todo 데이터 구조: `{ id: Date.now(), text: "할 일", completed: false }`
- 배열에 todo를 관리하고, 변경될 때마다 화면을 다시 그리기 (render 함수)
- LocalStorage에 JSON으로 저장/불러오기
- 이벤트 위임: `todoList.addEventListener('click', (e) => { ... })`
- 필터: `todos.filter(todo => !todo.completed)` 같은 식으로

### 🔑 핵심 설계

```
[데이터 흐름]
사용자 입력 → 배열 업데이트 → LocalStorage 저장 → 화면 다시 그리기

[주요 함수]
addTodo(text)     → 배열에 추가
toggleTodo(id)    → completed 토글
deleteTodo(id)    → 배열에서 삭제
render()          → 화면 그리기
saveTodos()       → LocalStorage 저장
loadTodos()       → LocalStorage 불러오기
```

### ⭐ 난이도: ★★☆☆☆
### ⏰ 예상 소요: 6~10시간

---

## 프로젝트 5: 스톱워치 & 타이머

### 📝 설명
스톱워치(경과 시간 측정)와 카운트다운 타이머를 모두 갖춘 시계 앱

### 🎯 완성하면 이런 것
- **스톱워치**: 시작/정지/리셋, 랩 타임 기록
- **타이머**: 시간 설정 → 카운트다운 → 알림
- 탭으로 스톱워치/타이머 전환
- 깔끔한 디지털 시계 UI

### 🔧 필요한 기술

| 기술 | 배운 곳 | 용도 |
|------|---------|------|
| setInterval / clearInterval | - | 시간 업데이트 |
| 변수, 조건문 | 3장 | 상태 관리, 시간 계산 |
| 배열 | 3장 3.11 | 랩 타임 기록 |
| DOM 조작 | 5장 | 시간 표시 업데이트 |
| classList.toggle | 5장 5.4 | 탭 전환 |
| CSS transition | 2장 2.15 | 부드러운 탭 전환 |

### 💡 구현 힌트
- `setInterval(() => { ... }, 10)` → 10ms 단위로 업데이트
- 시간 표시: `mm:ss.ms` 형식으로 포맷팅
- 시작/정지 상태를 boolean으로 관리
- 타이머 완료 시 `alert()` 또는 화면 깜빡임 효과

### ⭐ 난이도: ★★☆☆☆
### ⏰ 예상 소요: 5~8시간

---

## 프로젝트 6: 퀴즈 앱

### 📝 설명
객관식 퀴즈를 풀고 점수를 확인하는 앱

### 🎯 완성하면 이런 것
- 여러 문제를 순서대로 표시
- 4지선다 객관식
- 정답/오답 즉시 표시 (색상 변경)
- 다음 문제로 이동
- 최종 점수 + 오답 노트

### 🔧 필요한 기술

| 기술 | 배운 곳 | 용도 |
|------|---------|------|
| 배열 + 객체 | 3장 | 퀴즈 데이터 관리 |
| 반복문 | 3장 3.8 | 문제/보기 렌더링 |
| 조건문 | 3장 3.7 | 정답 체크 |
| DOM 생성 | 5장 5.3 | 동적 보기 생성 |
| classList | 5장 5.4 | 정답/오답 스타일 |
| setTimeout | - | 다음 문제 딜레이 |

### 💡 구현 힌트
- 퀴즈 데이터 구조:
```javascript
const quizData = [
    {
        question: "JavaScript는 어떤 종류의 언어인가요?",
        options: ["컴파일 언어", "인터프리터 언어", "마크업 언어", "쿼리 언어"],
        answer: 1  // 인덱스
    },
    // ...
];
```
- 현재 문제 인덱스를 변수로 관리: `let currentIndex = 0;`
- 문제당 하나의 `render` 함수로 화면 갱신

### ⭐ 난이도: ★★☆☆☆
### ⏰ 예상 소요: 6~10시간

---

---

# 🟠 중급 프로젝트 (5~7장 수준)

> DOM 조작 + 비동기 처리 + API 통신을 활용하는 프로젝트

---

## 프로젝트 7: 날씨 앱 🌤️

### 📝 설명
도시 이름을 검색하면 현재 날씨와 예보를 보여주는 앱

### 🎯 완성하면 이런 것
- 도시 검색 → 현재 날씨 표시 (온도, 습도, 바람)
- 날씨에 따른 아이콘/배경 변경
- 5일 예보
- 최근 검색 기록 (LocalStorage)

### 🔧 필요한 기술

| 기술 | 배운 곳 | 용도 |
|------|---------|------|
| Fetch API | 6장 6.9 | 날씨 API 호출 |
| async/await | 6장 6.7 | 비동기 처리 |
| try-catch | 6장 6.8 | API 에러 처리 |
| DOM 조작 | 5장 | 날씨 정보 표시 |
| 객체 다루기 | 3장 3.13 | API 응답 데이터 파싱 |
| LocalStorage | 8장 8.12 | 검색 기록 저장 |
| 구조 분해 할당 | 4장 4.9 | API 응답 데이터 추출 |

### 🌐 사용 가능한 API
- **OpenWeatherMap** (무료 키 발급): `https://api.openweathermap.org/`
- **WeatherAPI** (무료): `https://www.weatherapi.com/`
- 강사 제공 프록시 서버 (API 키 노출 방지용)

### 💡 구현 힌트
- API 호출: `fetch(\`https://api.openweathermap.org/data/2.5/weather?q=\${city}&appid=\${API_KEY}&units=metric&lang=kr\`)`
- API 키를 프론트엔드에 직접 넣으면 노출됨 → **프록시 서버 활용**
- 응답에서 필요한 데이터 구조 분해: `const { main: { temp, humidity }, weather } = data;`
- 로딩 중 스피너 표시 → 데이터 도착 후 결과 표시

### 🔑 핵심 설계

```
[데이터 흐름]
도시 입력 → API 호출 (프록시) → 응답 파싱 → 화면 렌더링

[API 호출 구조]
async function getWeather(city) {
    const response = await fetch(`프록시서버/weather?city=${city}`);
    const data = await response.json();
    renderWeather(data);
}
```

### ⭐ 난이도: ★★★☆☆
### ⏰ 예상 소요: 8~12시간

---

## 프로젝트 8: 영화 검색 앱 🎬

### 📝 설명
영화 제목을 검색하면 포스터, 줄거리, 평점 등을 보여주는 앱

### 🎯 완성하면 이런 것
- 영화 제목 검색 → 결과 목록 (카드 형태)
- 영화 클릭 → 상세 정보 (모달 또는 새 화면)
- 인기 영화 목록 (초기 화면)
- 즐겨찾기 기능 (LocalStorage)
- 무한 스크롤 또는 페이지네이션

### 🔧 필요한 기술

| 기술 | 배운 곳 | 용도 |
|------|---------|------|
| Fetch API + async/await | 6장 | API 호출 |
| DOM 동적 생성 | 5장 5.3 | 영화 카드 생성 |
| 이벤트 위임 | 5장 5.10 | 카드 클릭 처리 |
| CSS Flexbox/Grid | 2장 | 카드 레이아웃 |
| 배열 메소드 (map, filter) | 3장 3.12 | 데이터 가공 |
| LocalStorage | 8장 8.12 | 즐겨찾기 저장 |
| 디바운싱 | - | 검색 입력 최적화 |

### 🌐 사용 가능한 API
- **TMDB (The Movie Database)** (무료 키 발급): `https://api.themoviedb.org/3/`
- **OMDB API** (무료 제한): `https://www.omdbapi.com/`
- **한국영화데이터베이스 (KMDB)**: `https://www.kmdb.or.kr/`
- 강사 제공 프록시 서버

### 💡 구현 힌트
- 카드 레이아웃: CSS Grid `grid-template-columns: repeat(auto-fill, minmax(200px, 1fr))`
- 검색 시 디바운싱: 타이핑 멈춘 후 300ms 후에 API 호출
```javascript
let timer;
searchInput.addEventListener('input', (e) => {
    clearTimeout(timer);
    timer = setTimeout(() => searchMovies(e.target.value), 300);
});
```
- 이미지 로딩 실패 시 대체 이미지 표시
- 모달 창: `position: fixed` + `z-index`로 오버레이

### ⭐ 난이도: ★★★☆☆
### ⏰ 예상 소요: 10~15시간

---

## 프로젝트 9: 메모/노트 앱 📝

### 📝 설명
마크다운 또는 간단한 텍스트 메모를 작성/편집/삭제하는 노트 앱

### 🎯 완성하면 이런 것
- 새 메모 생성
- 메모 목록 (사이드바) + 편집 영역
- 메모 제목/내용 실시간 저장
- 메모 삭제, 검색
- 색상 태그/카테고리 분류
- LocalStorage 저장

### 🔧 필요한 기술

| 기술 | 배운 곳 | 용도 |
|------|---------|------|
| 객체 배열 관리 | 3장 | 메모 데이터 CRUD |
| DOM 동적 조작 | 5장 | 메모 목록/편집 화면 |
| 이벤트 (input, click) | 5장 5.7, 5.11 | 실시간 입력, 메모 선택 |
| LocalStorage + JSON | 3장 3.14, 8장 | 데이터 영구 저장 |
| CSS Flexbox | 2장 | 사이드바 + 편집영역 레이아웃 |
| 배열 메소드 (filter, sort, find) | 3장 3.12 | 검색, 정렬 |
| Date 객체 | - | 생성/수정 시각 |

### 💡 구현 힌트
- 메모 데이터 구조:
```javascript
{
    id: Date.now(),
    title: "제목",
    content: "내용...",
    color: "#ffeb3b",
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString()
}
```
- 사이드바: 메모 목록 (제목 + 날짜)
- 메인 영역: 선택된 메모의 편집 화면
- 검색: `memos.filter(m => m.title.includes(keyword) || m.content.includes(keyword))`
- 자동 저장: `input` 이벤트에 디바운스 걸어서 저장

### ⭐ 난이도: ★★★☆☆
### ⏰ 예상 소요: 10~15시간

---

---

# 🔴 고급 프로젝트 (6~9장 종합)

> API 통신 + 풀 CRUD + 상태 관리를 포함하는 실전형 프로젝트

---

## 프로젝트 10: 게시판 (CRUD 게시판) 📋

### 📝 설명
게시글 목록 조회 → 상세 보기 → 작성 → 수정 → 삭제가 되는 게시판.
**REST API 서버와 통신하는 풀 CRUD 프로젝트.**

### 🎯 완성하면 이런 것
- 게시글 목록 (제목, 작성자, 날짜)
- 게시글 상세 보기
- 게시글 작성 (폼)
- 게시글 수정
- 게시글 삭제
- 페이지네이션
- (선택) 댓글 기능

### 🔧 필요한 기술

| 기술 | 배운 곳 | 용도 |
|------|---------|------|
| Fetch API (GET/POST/PUT/DELETE) | 6장 6.9~6.10 | 서버 CRUD 통신 |
| async/await + try-catch | 6장 6.7~6.8 | 비동기 에러 처리 |
| DOM 동적 생성 | 5장 | 목록/상세 화면 렌더링 |
| 이벤트 위임 | 5장 5.10 | 동적 요소 클릭 처리 |
| 폼 이벤트 (submit) | 5장 5.11 | 게시글 작성/수정 |
| HTTP 메서드 이해 | 8장 8.5 | CRUD 매핑 |
| 상태 코드 처리 | 8장 8.7 | 에러 처리 |
| 클래스 (ES6) | 4장 4.7 | API 클래스, 데이터 모델 |

### 🌐 사용 가능한 API
- **JSONPlaceholder** (연습용, 실제 저장 안 됨): `https://jsonplaceholder.typicode.com/`
- **json-server** (로컬에서 간단한 REST API): `npx json-server db.json`
- **강사 제공 프록시/API 서버** ← 가장 실전적

### 💡 구현 힌트
- **화면 전환**: SPA처럼 하려면 해시 라우팅 `window.location.hash`
```javascript
window.addEventListener('hashchange', () => {
    const hash = location.hash;  // "#/posts", "#/posts/1", "#/write"
    if (hash === '#/posts') renderPostList();
    else if (hash.startsWith('#/posts/')) renderPostDetail(id);
    else if (hash === '#/write') renderWriteForm();
});
```
- **API 클래스로 통신 코드 정리**:
```javascript
class PostAPI {
    static BASE = '프록시서버주소/api';
    
    static async getAll(page = 1) { /* GET */ }
    static async getById(id) { /* GET */ }
    static async create(data) { /* POST */ }
    static async update(id, data) { /* PUT */ }
    static async delete(id) { /* DELETE */ }
}
```
- 목록 → 상세: 클릭 시 `location.hash = '#/posts/' + id`
- 삭제: `confirm('정말 삭제하시겠습니까?')` 후 DELETE 요청

### 🔑 핵심 설계

```
[화면 구성]
1. 목록 화면 (GET /posts)         → 테이블 또는 카드
2. 상세 화면 (GET /posts/:id)     → 제목, 내용, 버튼(수정/삭제)
3. 작성 화면 (POST /posts)        → 폼 (제목, 내용, 작성자)
4. 수정 화면 (PUT /posts/:id)     → 폼 (기존 데이터 채워짐)

[데이터 흐름]
사용자 행동 → API 호출 → 응답 처리 → 화면 갱신
```

### ⭐ 난이도: ★★★★☆
### ⏰ 예상 소요: 15~25시간

---

## 프로젝트 11: 가계부 / 지출 관리 앱 💰

### 📝 설명
수입/지출을 기록하고 통계를 보여주는 개인 가계부 앱

### 🎯 완성하면 이런 것
- 수입/지출 항목 등록 (날짜, 카테고리, 금액, 메모)
- 월별 목록 조회
- 수입/지출 합계, 잔액
- 카테고리별 통계 (원형 차트는 어려우면 막대 그래프)
- 월별 필터링
- LocalStorage 또는 API 서버 저장

### 🔧 필요한 기술

| 기술 | 배운 곳 | 용도 |
|------|---------|------|
| 배열 메소드 (filter, reduce, map) | 3장 | 통계 계산, 필터링 |
| 객체 | 3장 3.13 | 항목 데이터 관리 |
| Date 객체 | - | 날짜 처리 |
| DOM 동적 생성 | 5장 | 목록 렌더링 |
| 폼 이벤트 | 5장 5.11 | 입력 처리 |
| LocalStorage + JSON | 8장 | 데이터 저장 |
| (선택) Fetch API | 6장 | 서버 저장 시 |
| CSS로 차트 | 2장 | 간단한 막대 그래프 |

### 💡 구현 힌트
- 지출 항목 구조:
```javascript
{
    id: Date.now(),
    type: "expense",  // "income" | "expense"
    category: "식비",
    amount: 12000,
    memo: "점심",
    date: "2026-04-01"
}
```
- 월별 필터: `items.filter(item => item.date.startsWith('2026-04'))`
- 카테고리별 합계: `reduce`로 카테고리별 금액 누적
- 간단한 막대 그래프: CSS `width` 를 금액 비율로 계산해서 적용
```css
.bar { height: 20px; background: #3498db; }
```
```javascript
bar.style.width = (amount / maxAmount * 100) + '%';
```

### ⭐ 난이도: ★★★★☆
### ⏰ 예상 소요: 15~25시간

---

## 프로젝트 12: 도서/상품 관리 시스템 📚

### 📝 설명
도서 또는 상품의 CRUD + 검색 + 정렬 + 필터가 있는 관리 시스템.
**API 서버 연동 필수.**

### 🎯 완성하면 이런 것
- 상품/도서 목록 (테이블 형태)
- 추가 (모달 폼)
- 수정 (인라인 편집 또는 모달)
- 삭제 (확인 후)
- 검색 (제목, 저자)
- 정렬 (가격순, 이름순, 최신순)
- 카테고리 필터
- 페이지네이션

### 🔧 필요한 기술

| 기술 | 배운 곳 | 용도 |
|------|---------|------|
| Fetch API 풀 CRUD | 6장 | 서버 통신 |
| async/await + 에러 처리 | 6장 | 안정적 API 호출 |
| 클래스 | 4장 4.7 | API 서비스, 데이터 모델 |
| DOM 조작 (전체) | 5장 | 테이블, 모달, 폼 |
| 이벤트 위임 | 5장 5.10 | 테이블 행 이벤트 |
| 배열 메소드 (sort, filter, find) | 3장 | 정렬, 검색, 필터 |
| CSS 모달 | 2장 | 추가/수정 모달 창 |
| HTTP 상태 코드 | 8장 | 에러 메시지 표시 |

### 💡 구현 힌트
- 테이블 정렬: 헤더 클릭 시 해당 컬럼으로 정렬
```javascript
let sortKey = 'title';
let sortOrder = 'asc';

function sortBooks(books) {
    return [...books].sort((a, b) => {
        if (sortOrder === 'asc') return a[sortKey] > b[sortKey] ? 1 : -1;
        return a[sortKey] < b[sortKey] ? 1 : -1;
    });
}
```
- 모달 열기/닫기: CSS 클래스 토글
- 수정 시 기존 데이터를 폼에 채워넣기 (populate)
- 삭제 실패 시 상태 코드별 에러 메시지 분기

### ⭐ 난이도: ★★★★★
### ⏰ 예상 소요: 20~30시간

---

---

# 📌 프로젝트 진행 가이드

## 1단계: 기획 (30분~1시간)

시작하기 전에 이것부터 정하세요:

```
□ 프로젝트 이름
□ 한 줄 설명
□ 핵심 기능 3~5개 (욕심부리지 마세요!)
□ 화면 구성 (종이에 대충 그려보기)
□ 데이터 구조 (어떤 데이터가 필요한가?)
```

### 예시: Todo List 기획

```
프로젝트: 할 일 관리 앱
설명: 할 일을 추가하고 완료/삭제할 수 있는 앱

핵심 기능:
1. 할 일 추가
2. 완료 표시 (체크)
3. 삭제
4. LocalStorage 저장
5. 필터 (전체/완료/미완료)

화면 구성:
┌─────────────────────┐
│  📝 나의 할 일       │
│ ┌─────────────┬───┐ │
│ │ 입력...      │추가│ │
│ └─────────────┴───┘ │
│ [전체] [완료] [미완료] │
│                     │
│ □ 장보기        [🗑️] │
│ ☑ 운동하기      [🗑️] │
│ □ 공부하기      [🗑️] │
│                     │
│ 남은 할 일: 2개      │
└─────────────────────┘

데이터:
{ id, text, completed, createdAt }
```

---

## 2단계: HTML 구조 만들기 (1~2시간)

```
1. index.html에 시맨틱 태그로 뼈대 잡기
2. 각 섹션에 가짜(하드코딩) 데이터 넣기
3. 브라우저에서 열어서 구조 확인
```

**이 단계에서 JavaScript는 건드리지 마세요!**
HTML만으로 화면이 나와야 합니다.

---

## 3단계: CSS 스타일링 (2~3시간)

```
1. 리셋 CSS 적용 (* { margin: 0; padding: 0; box-sizing: border-box; })
2. 전체 레이아웃 (Flexbox 또는 Grid)
3. 컴포넌트별 스타일 (버튼, 입력창, 카드 등)
4. 반응형 (미디어 쿼리, 선택)
5. 호버/포커스 효과
```

**CSS를 완성하기 전에 JavaScript를 시작하지 마세요.**

---

## 4단계: JavaScript 기능 구현 (핵심)

### 순서가 중요합니다!

```
1. 데이터 구조 정의 (변수/배열/객체)
2. 화면 렌더링 함수 (데이터 → DOM)
3. 사용자 입력 처리 (이벤트 리스너)
4. 데이터 조작 함수 (추가/수정/삭제)
5. 저장/불러오기 (LocalStorage 또는 API)
6. 에러 처리
7. 마무리 (로딩 상태, 빈 상태 처리)
```

### 핵심 패턴: 데이터 → 화면

```javascript
// 1. 데이터 (Single Source of Truth)
let todos = [];

// 2. 렌더 함수 (데이터 → 화면)
function render() {
    const list = document.getElementById('todo-list');
    list.innerHTML = '';
    
    todos.forEach(todo => {
        const li = document.createElement('li');
        li.textContent = todo.text;
        list.appendChild(li);
    });
}

// 3. 데이터 변경 → 다시 렌더
function addTodo(text) {
    todos.push({ id: Date.now(), text, completed: false });
    save();    // 저장
    render();  // 화면 갱신
}
```

**이 패턴을 지키면 버그가 줄어듭니다:**
> 화면을 직접 수정하지 말고, 항상 **데이터를 먼저 수정** → **render() 호출**

---

## 5단계: 테스트 & 디버깅

```
□ 모든 기능이 정상 작동하는가?
□ 빈 입력을 넣으면 어떻게 되는가?
□ 데이터가 없을 때 화면이 괜찮은가?
□ 새로고침해도 데이터가 유지되는가? (LocalStorage)
□ 콘솔에 에러가 없는가? (F12)
□ 네트워크 에러 시 적절한 메시지가 나오는가?
```

---

## 6단계: 리팩토링 & 발표 준비

```
□ 코드 정리 (불필요한 console.log 삭제)
□ 변수명/함수명이 의미 있는가?
□ 반복 코드는 함수로 분리했는가?
□ 주석 추가 (핵심 로직)
□ README 작성 (프로젝트 설명, 사용법)
```

---

---

# 🌐 활용 가능한 무료 API 목록

> API 키가 필요한 경우 직접 발급받거나, 강사가 프록시 서버를 제공합니다.

## API 키 불필요 (바로 사용 가능)

| API | URL | 용도 |
|-----|-----|------|
| JSONPlaceholder | jsonplaceholder.typicode.com | 연습용 CRUD (게시글, 유저, 댓글) |
| Random User | randomuser.me/api | 랜덤 사용자 프로필 |
| Dog API | dog.ceo/api | 랜덤 강아지 이미지 |
| Cat Facts | catfact.ninja | 고양이 상식 |
| Joke API | v2.jokeapi.dev | 랜덤 개그 |
| 공공데이터포털 | data.go.kr | 한국 공공 데이터 |
| PokeAPI | pokeapi.co | 포켓몬 데이터 |

## API 키 필요 (무료 발급)

| API | URL | 용도 | 무료 제한 |
|-----|-----|------|-----------|
| OpenWeatherMap | openweathermap.org | 날씨 | 60회/분 |
| TMDB | themoviedb.org | 영화/TV | 무제한 |
| NewsAPI | newsapi.org | 뉴스 | 100회/일 |
| Unsplash | unsplash.com/developers | 고품질 사진 | 50회/시간 |
| Google Books | developers.google.com/books | 도서 검색 | 무제한 |
| Kakao REST API | developers.kakao.com | 검색, 지도, 번역 | 무제한(일부 제한) |
| Naver Open API | developers.naver.com | 검색, 번역, 지도 | 25,000회/일 |

## 프록시 서버가 필요한 이유

```
문제: CORS 에러!
  브라우저 (localhost) → 외부 API 서버
  → "Access-Control-Allow-Origin" 없으면 차단!

해결: 프록시 서버 경유
  브라우저 → 프록시 서버 (강사 제공) → 외부 API 서버
  → 프록시 서버가 CORS 헤더를 추가해줌!

추가 이점:
  - API 키를 서버에서만 관리 (프론트엔드 노출 방지)
  - 요청 횟수 제한 관리
  - 응답 캐싱으로 속도 향상
```

---

---

# 🛠️ 자주 쓰는 유틸리티 코드 모음

프로젝트에서 자주 사용하는 패턴들입니다. 복사해서 쓰세요.

## 날짜 포맷팅

```javascript
function formatDate(dateStr) {
    const date = new Date(dateStr);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// 상대 시간 (1시간 전, 3일 전)
function timeAgo(dateStr) {
    const diff = Date.now() - new Date(dateStr).getTime();
    const minutes = Math.floor(diff / 60000);
    if (minutes < 1) return '방금 전';
    if (minutes < 60) return `${minutes}분 전`;
    const hours = Math.floor(minutes / 60);
    if (hours < 24) return `${hours}시간 전`;
    const days = Math.floor(hours / 24);
    return `${days}일 전`;
}
```

## 디바운스 (검색 입력 최적화)

```javascript
function debounce(func, delay = 300) {
    let timer;
    return function(...args) {
        clearTimeout(timer);
        timer = setTimeout(() => func.apply(this, args), delay);
    };
}

// 사용
searchInput.addEventListener('input', debounce((e) => {
    searchMovies(e.target.value);
}, 300));
```

## LocalStorage 헬퍼

```javascript
const storage = {
    get(key) {
        try {
            return JSON.parse(localStorage.getItem(key));
        } catch {
            return null;
        }
    },
    set(key, value) {
        localStorage.setItem(key, JSON.stringify(value));
    },
    remove(key) {
        localStorage.removeItem(key);
    }
};

// 사용
storage.set('todos', [{ id: 1, text: '할 일' }]);
const todos = storage.get('todos') || [];
```

## API 호출 헬퍼

```javascript
async function api(endpoint, options = {}) {
    const BASE_URL = '프록시서버주소/api';
    
    try {
        const response = await fetch(`${BASE_URL}${endpoint}`, {
            headers: { 'Content-Type': 'application/json' },
            ...options,
            body: options.body ? JSON.stringify(options.body) : undefined
        });
        
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('API 에러:', error);
        alert('데이터를 불러오는 데 실패했습니다.');
        throw error;
    }
}

// 사용
const posts = await api('/posts');
const newPost = await api('/posts', {
    method: 'POST',
    body: { title: '제목', content: '내용' }
});
```

## 고유 ID 생성

```javascript
function generateId() {
    return Date.now().toString(36) + Math.random().toString(36).substr(2);
}
// 예: "m1abc23_def456gh"
```

---

---

# 📊 프로젝트 난이도 & 기술 매핑 총정리

## 프로젝트별 사용 기술 매트릭스

```
                        | HTML | CSS  | JS기초 | JS심화 | DOM | 비동기 | jQuery | HTTP | 브라우저
                        | 1장  | 2장  | 3장    | 4장    | 5장 | 6장    | 7장    | 8장  | 9장
─────────────────────────────────────────────────────────────────────────────────────────────
1. 계산기               |  ✅  |  ✅  |  ✅    |        |  ✅ |        |        |      |
2. 가위바위보           |  ✅  |  ✅  |  ✅    |        |  ✅ |        |        |      |
3. 랜덤 명언 생성기     |  ✅  |  ✅  |  ✅    |        |  ✅ |        |        |      |
4. Todo List           |  ✅  |  ✅  |  ✅    |        |  ✅ |        |        |  ✅  |
5. 스톱워치 & 타이머    |  ✅  |  ✅  |  ✅    |        |  ✅ |        |        |      |
6. 퀴즈 앱             |  ✅  |  ✅  |  ✅    |        |  ✅ |        |        |      |
7. 날씨 앱             |  ✅  |  ✅  |  ✅    |  ✅    |  ✅ |  ✅    |        |  ✅  |
8. 영화 검색 앱         |  ✅  |  ✅  |  ✅    |  ✅    |  ✅ |  ✅    |        |  ✅  |
9. 메모/노트 앱         |  ✅  |  ✅  |  ✅    |        |  ✅ |        |        |  ✅  |
10. 게시판 (CRUD)       |  ✅  |  ✅  |  ✅    |  ✅    |  ✅ |  ✅    |        |  ✅  | ✅
11. 가계부              |  ✅  |  ✅  |  ✅    |  ✅    |  ✅ | (선택) |        |  ✅  |
12. 도서/상품 관리       |  ✅  |  ✅  |  ✅    |  ✅    |  ✅ |  ✅    |        |  ✅  | ✅
```

## 난이도별 추천

```
🟢 입문 (첫 프로젝트)     → 프로젝트 1~3 중 택 1
🟡 초급 (DOM 연습)        → 프로젝트 4~6 중 택 1
🟠 중급 (API 연동)        → 프로젝트 7~9 중 택 1
🔴 고급 (종합 프로젝트)   → 프로젝트 10~12 중 택 1

추천 순서: 2(가위바위보) → 4(Todo) → 8(영화검색) → 10(게시판)
```

---

## 💡 강의 팁 (강사용)

### 프로젝트 수업 운영 방법

```
1주차: 프로젝트 선정 + 기획서 작성
       - 이 문서를 학생에게 공유
       - 자기 수준에 맞는 프로젝트 선택
       - 기획서 제출 (기능 목록, 화면 설계)

2주차: HTML/CSS 완성
       - 화면 뼈대 + 스타일 완성
       - 가짜 데이터로 화면 확인

3주차: JavaScript 핵심 기능
       - 데이터 구조 정의
       - CRUD 로직 구현
       - 이벤트 연결

4주차: 마무리 + 발표
       - 에러 처리, 예외 케이스
       - 코드 정리, 주석
       - 발표 (2~3분, 화면 시연)
```

### 평가 기준 (예시)

```
기능 완성도     40%  - 핵심 기능이 작동하는가?
코드 품질       20%  - 변수명, 함수 분리, 가독성
UI/UX          20%  - 보기 좋은가? 사용하기 편한가?
발표/설명       10%  - 어떻게 만들었는지 설명할 수 있는가?
도전 정신       10%  - 추가 기능, 새로운 시도
```

---

**🎓 10장 JavaScript 프로젝트 가이드를 완료했습니다!**

**이제 직접 만들어보세요. 완벽하지 않아도 됩니다. 완성이 완벽보다 중요합니다. 🚀**

