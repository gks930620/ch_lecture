# GitHub Pages HTML 정적 배포 가이드 (강의용)

## 1. 목표
- AI로 기본 사이트 파일을 만든다.
- `HTML/CSS/JS` 정적 파일만으로 GitHub Pages에 배포한다.
- GitHub `Settings > Pages`에서 배포 설정까지 완료한다.

## 2. 완료 후 결과
- 프로젝트 페이지 URL 예시: `https://YOUR_ID.github.io/REPO_NAME/`
- `main` 브랜치에 푸시하면 자동으로 페이지가 반영된다.

## 3. 준비물
- GitHub 계정
- Git 설치
- VS Code 또는 IntelliJ
- 기본 파일: `index.html` (필수), 필요 시 `style.css`, `app.js`

## 4. AI로 파일 초안 만들기
아래처럼 AI에 요청하면 시작 속도가 빨라진다.

```text
HTML/CSS/JS만 사용하는 1페이지 포트폴리오 사이트를 만들어줘.
파일은 index.html, style.css, app.js로 분리하고,
모바일 반응형과 섹션(소개/프로젝트/연락처)을 포함해줘.
```

생성된 파일은 로컬에서 먼저 열어 레이아웃/링크/한글 깨짐을 확인한다.

## 4-1. 디자인 참고(Figma + AI) 방법
결론부터 말하면, 지금 하신 방식이 맞다.  
좋은 레퍼런스를 보여주고 "이 느낌으로 구현"을 요청하는 방식은 실무에서도 가장 많이 쓰는 방법이다.

- Figma 화면(레이아웃/간격/타이포/컬러)을 캡처해서 AI에 함께 제공
- "완전 복제"가 아니라 "분위기와 원칙 참고"로 요청
- 기능 요구사항(섹션 구성, 반응형, 버튼 동작)을 반드시 같이 전달

```text
첨부한 Figma 레퍼런스 느낌으로 랜딩 페이지를 만들어줘.
Hero/Feature/CTA 구조, 모바일 반응형, 파일 분리(index.html/style.css/app.js)로 작성해줘.
색감/여백/타이포 톤은 참고하되, 문구와 구성은 우리 서비스에 맞게 바꿔줘.
```

- 저작권 있는 로고/이미지는 그대로 복제하지 않고 대체 리소스 사용 권장

## 5. 저장소 생성 및 코드 업로드
## 5-1. GitHub 저장소 생성
1. GitHub에서 `New repository` 클릭
2. 저장소 이름 입력 (예: `my-static-site`)
3. `Public` 선택
4. `Create repository`

## 5-2. 로컬에서 커밋/푸시
```powershell
git init
git add .
git commit -m "init: html static site"
git branch -M main
git remote add origin https://github.com/YOUR_ID/REPO_NAME.git
git push -u origin main
```

## 6. GitHub Pages 설정 (핵심)
1. 저장소 `Settings` 이동
2. 좌측 `Pages` 클릭
3. `Build and deployment > Source`에서 `Deploy from a branch` 선택
4. Branch: `main`
5. Folder: `/(root)` 또는 `/docs`
6. `Save`
7. 1~5분 대기 후 배포 URL 접속

## 7. 페이지 추가 방법
- 새 파일 추가: `about.html`
- `index.html`에 링크 추가:

```html
<a href="./about.html">About</a>
```

- 커밋/푸시하면 자동 배포됨

## 8. 자주 발생하는 문제
## 8-1. 404 페이지가 뜸
- 원인: `index.html`이 배포 소스 폴더에 없음
- 해결: `main/(root)` 또는 `main/docs`에 `index.html` 위치 확인

## 8-2. CSS/JS가 안 불러와짐
- 원인: 경로 오류
- 해결: 프로젝트 페이지에서는 상대경로(`./style.css`) 사용 권장

## 8-3. `_` 폴더 파일이 누락됨
- 원인: Jekyll 처리 영향
- 해결: 소스 루트에 `.nojekyll` 파일 추가

## 8-4. 배포가 시작되지 않음
- 원인: Pages 소스 미설정/브랜치 오설정
- 해결: `Settings > Pages`에서 브랜치/폴더 재확인

## 9. 강의 포인트 정리
- HTML 정적 배포는 서버 코드 없이도 즉시 배포 가능
- 핵심은 `index.html` 위치 + `Settings > Pages` 설정
- 업데이트는 `파일 수정 -> commit -> push` 3단계로 반복
