# 📚 개발 강의 자료 (Lecture Repository)

> Java · JavaScript · Spring Boot · Git 강의에서 사용하는 프로젝트 및 학습 자료 모음

## 🌐 GitHub Pages

👉 **[강의 자료 페이지 바로가기](https://YOUR_GITHUB_USERNAME.github.io/ch_lecture/)**

> GitHub Pages 배포 후 위 링크의 `YOUR_GITHUB_USERNAME` 부분을 본인 GitHub 계정으로 변경해주세요.

---

## 🗂️ 프로젝트 구조

```
ch_lecture/
├── docs/                              ← GitHub Pages 소스 (Markdown)
│   ├── _config.yml
│   ├── index.md
│   └── (각 프로젝트별 .md 파일)
│
├── github_basic/                      ← Git & GitHub 기초
├── java_basic/                        ← Java 프로그래밍 기초
├── web_basic/                         ← HTML/CSS 웹 기초
├── javascript_basic/                  ← JavaScript 기초~심화
│
├── spring_ssr/                        ← Spring Boot SSR (Thymeleaf)
├── spring_ssr_security/               ← Spring Security (SSR)
├── spring_ssr_security_oauth2/        ← OAuth2 소셜 로그인 (SSR)
├── spring_ssr_security_oauth2_chat/   ← OAuth2 + WebSocket 채팅 (SSR)
│
├── spring_csr_jwt_access_refresh/     ← JWT Access/Refresh Token (CSR)
├── spring_csr_jwt_oauth2/            ← JWT + OAuth2 (CSR)
├── spring_csr_chat/                   ← WebSocket 채팅 (CSR)
└── spring_csr_final/                  ← CSR 최종 프로젝트 (Docker)
```

## ⚙️ GitHub Pages 설정 방법

1. GitHub에서 이 저장소의 **Settings → Pages** 로 이동
2. **Source** 에서 `Deploy from a branch` 선택
3. **Branch** 를 `main`, 폴더를 `/docs` 로 선택
4. **Save** 클릭

몇 분 후 `https://YOUR_USERNAME.github.io/ch_lecture/` 에서 확인 가능합니다.

