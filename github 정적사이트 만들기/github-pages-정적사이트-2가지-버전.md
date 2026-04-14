# GitHub Pages 정적사이트 만들기 (2가지 버전)

목표 URL: `https://yourname.github.io/reponame/`

`reponame` 저장소를 GitHub Pages로 배포하는 방법을 두 가지로 정리합니다.

- 버전 A: Jekyll + Markdown 문서 기반
- 버전 B: 순수 HTML 기반

---

## 먼저 확인할 것

1. GitHub에 저장소 생성: `reponame`
2. 기본 브랜치: `main`
3. 프로젝트 페이지 URL 규칙:
   - `https://yourname.github.io/reponame/`
4. (참고) 사용자 페이지 저장소인 `yourname.github.io`는 URL 규칙이 다릅니다.

---

## 버전 A) Jekyll + Markdown

문서 중심 관리가 필요할 때 적합합니다.

### 1) 폴더 구조 예시

```text
reponame/
└─ docs/
   ├─ _config.yml
   ├─ index.md
   └─ guide.md
```

### 2) `docs/_config.yml`

```yml
title: "My Lecture Site"
description: "Jekyll + Markdown on GitHub Pages"
theme: minima
baseurl: "/reponame"
url: "https://yourname.github.io"
```

### 3) `docs/index.md`

```md
---
layout: default
title: 홈
---

# 안녕하세요
이 사이트는 Jekyll + Markdown으로 배포했습니다.

- [가이드 문서 보기]({{ "/guide.html" | relative_url }})
```

### 4) `docs/guide.md`

```md
---
layout: default
title: 가이드
---

# 가이드
여기에 강의 문서를 Markdown으로 관리하면 됩니다.
```

### 5) GitHub Pages 설정

GitHub 저장소 > `Settings` > `Pages`

- Source: `Deploy from a branch`
- Branch: `main`
- Folder: `/docs`

저장 후 1~3분 뒤 접속:
`https://yourname.github.io/reponame/`

---

## 버전 B) 순수 HTML

가볍고 빠르게 랜딩/소개 페이지를 올릴 때 적합합니다.

### 1) 폴더 구조 예시

```text
reponame/
└─ docs/
   ├─ index.html
   └─ about.html
```

### 2) `docs/index.html`

```html
<!doctype html>
<html lang="ko">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>My Static Site</title>
</head>
<body>
  <h1>GitHub Pages HTML 버전</h1>
  <p>정적 HTML 사이트입니다.</p>
  <a href="./about.html">소개 페이지</a>
</body>
</html>
```

### 3) `docs/about.html`

```html
<!doctype html>
<html lang="ko">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>소개</title>
</head>
<body>
  <h1>소개</h1>
  <p>HTML 파일만으로도 GitHub Pages 배포가 가능합니다.</p>
  <a href="./index.html">홈으로</a>
</body>
</html>
```

### 4) GitHub Pages 설정

버전 A와 동일하게 설정:

- Source: `Deploy from a branch`
- Branch: `main`
- Folder: `/docs`

배포 URL:
`https://yourname.github.io/reponame/`

---

## 어떤 버전을 선택할까?

- 문서가 계속 늘어남: **버전 A (Jekyll + Markdown)**
- 단순 소개/랜딩 페이지: **버전 B (HTML)**

둘 다 같은 저장소에서 운영 가능하며, 필요하면 HTML에서 시작해 Jekyll 구조로 확장하면 됩니다.
