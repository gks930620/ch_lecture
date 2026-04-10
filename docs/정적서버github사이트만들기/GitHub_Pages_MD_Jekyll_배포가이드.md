# GitHub Pages + Jekyll MD 블로그 배포 가이드 (강의용)

## 1. 목표
- Markdown 문서 기반 사이트를 GitHub Pages로 배포한다.
- `docs` 폴더를 Pages 소스로 사용한다.
- `Settings > Pages`에서 배포 설정까지 완료한다.

## 2. 이 방식의 핵심
- 배포 방식: **GitHub Pages + Jekyll**
- 소스 폴더: `docs/`
- 현재 `ch_lecture`와 같은 계열의 레이아웃/스타일 구조 사용 가능

## 3. 표준 구조
```text
docs/
  _config.yml
  index.md
  _layouts/
    default.html
  assets/
    css/
      style.scss
```

## 4. 저장소 생성과 템플릿 복사
1. GitHub에서 새 저장소 생성 (예: `blog`)
2. 로컬에서 저장소 클론 또는 초기화
3. 위 구조(`docs/...`)를 새 저장소에 복사
4. 커밋/푸시

```powershell
git add .
git commit -m "init: jekyll md blog"
git branch -M main
git remote add origin https://github.com/YOUR_ID/blog.git
git push -u origin main
```

## 5. GitHub Pages 설정
1. 저장소 `Settings` 이동
2. 좌측 `Pages` 클릭
3. `Build and deployment > Source`에서 `Deploy from a branch` 선택
4. Branch: `main` (또는 `master`)
5. Folder: `/docs`
6. `Save`
7. 1~5분 대기 후 배포 URL 확인

## 6. `_config.yml` 필수 설정
프로젝트 페이지(`YOUR_ID.github.io/blog`) 기준:

```yml
title: "My Blog"
baseurl: "/blog"
url: "https://YOUR_ID.github.io"
repository_url: "https://github.com/YOUR_ID/blog"
```

## 6-1. 레포 이름이 `blog`가 아닐 때
- `baseurl`을 레포 이름에 맞게 수정  
  예: 레포가 `study-notes`면 `baseurl: "/study-notes"`
- `repository_url`도 동일하게 수정

## 6-2. 사용자 페이지(`YOUR_ID.github.io`)로 배포할 때
- `baseurl: ""`
- 저장소 이름은 반드시 `YOUR_ID.github.io`

## 7. 새 문서 작성 템플릿
```md
---
layout: default
title: 문서 제목
description: 문서 한 줄 설명
---

## 문서 본문

- 항목 1
- 항목 2
```

프론트매터(`---` 블록)가 있어야 레이아웃/메타정보가 안정적으로 적용된다.

## 8. AI 활용 포인트
## 8-1. 문서 초안 생성 프롬프트
```text
Jekyll 블로그용 Markdown 문서를 만들어줘.
front matter(layout, title, description) 포함하고,
개념 설명 + 실습 예제 + 체크리스트 형식으로 작성해줘.
```

## 8-2. 네비게이션/목차 개선 프롬프트
```text
현재 docs/index.md를 기준으로
카테고리형 목차와 문서 링크를 깔끔하게 재구성해줘.
```

## 8-3. 디자인 참고(Figma + AI) 적용
지금처럼 "피그마 레퍼런스를 보여주고 비슷한 톤으로 만들어 달라"는 방식이 맞다.  
MD 사이트도 레이아웃/색감/여백/카드 스타일은 동일하게 적용할 수 있다.

- 레퍼런스 화면을 캡처해 AI에 제공
- `docs/_layouts/default.html`, `docs/assets/css/style.scss`를 수정 대상으로 지정
- "같은 느낌 + 우리 콘텐츠 구조"를 동시에 요구

```text
첨부한 Figma 느낌으로 Jekyll 블로그 레이아웃을 개선해줘.
수정 파일은 docs/_layouts/default.html, docs/assets/css/style.scss.
모바일 반응형 유지, 가독성 높은 본문 폭, 카드형 목록 스타일로 정리해줘.
```

- 브랜드 자산/이미지는 라이선스 확인 후 사용

## 9. 자주 발생하는 문제와 해결
## 9-1. 스타일이 깨짐 / 링크가 404
- 원인: `baseurl` 불일치
- 해결: `_config.yml`의 `baseurl`을 레포 이름과 정확히 일치시킴

## 9-2. MD는 보이는데 레이아웃이 적용 안 됨
- 원인: 프론트매터 누락 또는 `layout` 오타
- 해결: 문서 상단에 `layout: default` 확인

## 9-3. 배포 URL이 열리지 않음
- 원인: `Pages` 소스가 `/docs`가 아님
- 해결: `Settings > Pages`에서 `main + /docs` 재설정

## 10. 강의 포인트 정리
- HTML 정적 배포보다 문서 확장성이 높다.
- 핵심은 `docs 구조 + _config.yml + Pages(main/docs)` 3가지다.
- 문서 추가는 `md 파일 추가 -> index 링크 연결 -> push` 순서로 반복한다.
