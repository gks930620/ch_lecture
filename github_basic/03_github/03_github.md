# 📖 챕터 3: GitHub 사용법

## 🎯 학습 목표
- GitHub의 역할과 Git과의 차이를 이해한다
- 원격 저장소를 생성하고 연결할 수 있다
- push, pull, clone을 사용할 수 있다
- Fork와 Pull Request로 협업할 수 있다

---

## 1. GitHub란?

### Git vs GitHub
| | Git | GitHub |
|---|---|---|
| 종류 | 버전 관리 **도구** | 웹 기반 **서비스** |
| 위치 | 내 컴퓨터 (로컬) | 인터넷 (원격) |
| 역할 | 버전 관리 | 코드 호스팅 + 협업 |
| 비용 | 무료 | 무료 (기본) / 유료 (팀) |

> **Git**은 도구, **GitHub**는 그 도구를 활용한 서비스입니다.

### GitHub 대안 서비스
- **GitLab**: 자체 서버 설치 가능
- **Bitbucket**: Atlassian 제품군과 연동
- **Gitea**: 경량 자체 호스팅

---

## 2. GitHub 가입 및 저장소 생성

### 가입
1. [https://github.com](https://github.com) 접속
2. "Sign up" 클릭
3. 이메일, 비밀번호, 사용자명 입력
4. 이메일 인증 완료

### 새 저장소(Repository) 만들기
1. 우측 상단 "+" → "New repository" 클릭
2. Repository name: `my-first-repo`
3. Description: `깃허브 실습 저장소` (선택)
4. Public / Private 선택
5. "Create repository" 클릭

### README, .gitignore, LICENSE
| 파일 | 설명 |
|------|------|
| `README.md` | 프로젝트 소개 문서 (자동 표시) |
| `.gitignore` | Git이 무시할 파일 목록 |
| `LICENSE` | 오픈소스 라이선스 |

---

## 3. 원격 저장소 연결

### 로컬 → GitHub (기존 프로젝트를 올리기)
```bash
# 1. 원격 저장소 추가
git remote add origin https://github.com/사용자명/my-first-repo.git

# 2. 원격 저장소 확인
git remote -v

# 3. 푸시 (처음에는 -u 옵션 사용)
git push -u origin main
```

### GitHub → 로컬 (저장소 복제)
```bash
# HTTPS 방식
git clone https://github.com/사용자명/my-first-repo.git

# SSH 방식
git clone git@github.com:사용자명/my-first-repo.git

# 특정 폴더명으로 클론
git clone https://github.com/사용자명/my-first-repo.git my-project
```

---

## 4. Push & Pull

### git push - 로컬 → 원격
```bash
# 기본 push
git push origin main

# 첫 push (업스트림 설정)
git push -u origin main

# 이후부터는 간단히
git push
```

### git pull - 원격 → 로컬
```bash
# 기본 pull (fetch + merge)
git pull origin main

# 간단히
git pull
```

### git fetch - 원격 정보만 가져오기
```bash
# 원격 정보 가져오기 (merge 하지 않음)
git fetch origin

# 차이 확인
git diff origin/main

# 병합
git merge origin/main
```

### push vs pull vs fetch
```
로컬 ────push────▶ GitHub
로컬 ◀───pull───── GitHub (fetch + merge)
로컬 ◀───fetch──── GitHub (정보만 가져옴)
```

---

## 5. GitHub 웹 기능

### Issues (이슈)
> 버그 리포트, 기능 요청, 질문 등을 관리하는 게시판

- 라벨(Label)로 분류: `bug`, `enhancement`, `question`
- 담당자(Assignee) 지정
- 마일스톤(Milestone)으로 그룹화

### Projects (프로젝트)
> 칸반 보드 스타일의 프로젝트 관리 도구

```
┌──────────┐  ┌──────────┐  ┌──────────┐
│  To Do   │  │  Doing   │  │   Done   │
├──────────┤  ├──────────┤  ├──────────┤
│ 기능A    │  │ 기능B    │  │ 기능C    │
│ 기능D    │  │          │  │ 기능E    │
└──────────┘  └──────────┘  └──────────┘
```

### GitHub Pages
> 정적 웹사이트를 무료로 호스팅

Settings → Pages → Source 설정 → `https://사용자명.github.io/저장소명`

---

## 6. Fork & Pull Request (PR)

### 오픈소스 협업 워크플로우

```
원본 저장소 (upstream)
        │
        │ Fork
        ▼
내 저장소 (origin)  ◀── 내 PC에서 clone
        │
        │ Push
        ▼
내 저장소에 변경사항 반영
        │
        │ Pull Request
        ▼
원본 저장소에 기여 요청
```

### Fork (포크)
> 다른 사람의 저장소를 내 계정으로 복사

1. 원본 저장소 페이지에서 "Fork" 버튼 클릭
2. 내 계정에 복사된 저장소 생성
3. 내 저장소를 clone하여 작업

### Pull Request (풀 리퀘스트)
> 변경사항을 원본 저장소에 반영해달라고 요청

**PR 생성 방법:**
1. Fork한 저장소에서 코드 수정 후 push
2. GitHub에서 "Pull requests" → "New pull request"
3. 변경 내용 설명 작성
4. "Create pull request" 클릭

**코드 리뷰:**
- 리뷰어가 코드를 검토
- 수정 요청 또는 승인
- 승인 후 Merge

---

## 7. SSH 키 설정 (선택)

비밀번호 대신 SSH 키로 인증하면 더 편리합니다.

```bash
# SSH 키 생성
ssh-keygen -t ed25519 -C "your_email@example.com"

# 공개키 복사
cat ~/.ssh/id_ed25519.pub
```

GitHub → Settings → SSH and GPG keys → New SSH key → 공개키 붙여넣기

---

## 🔬 실습

### 실습 파일
📁 `practice/remote_test.txt` - push/pull 실습  
📁 `practice/collaboration.txt` - 협업 시뮬레이션

### 실습 1: 원격 저장소에 Push하기

**사전 준비:** GitHub에서 `git-practice`라는 빈 저장소를 생성하세요.

**1단계: 로컬 저장소 초기화**
```bash
cd 03_github/practice
git init
git add remote_test.txt
git commit -m "원격 저장소 테스트 파일 추가"
```

**2단계: 원격 저장소 연결 및 Push**
```bash
git remote add origin https://github.com/사용자명/git-practice.git
git push -u origin main
```

**3단계: GitHub에서 확인**
- 브라우저에서 저장소 페이지 새로고침
- `remote_test.txt` 파일이 보이는지 확인

### 실습 2: Pull 받기

**1단계: GitHub 웹에서 파일 수정**
- GitHub에서 `remote_test.txt` 클릭 → 연필 아이콘(Edit)
- 내용 추가 → Commit changes

**2단계: 로컬에서 Pull**
```bash
git pull origin main
cat remote_test.txt  # 변경 내용 확인
```

### 실습 3: 협업 시뮬레이션

**1단계: collaboration.txt 수정**
- 자신의 이름과 역할을 추가
```bash
git add collaboration.txt
git commit -m "팀원 정보 추가"
git push
```

**2단계: Issue 만들기**
- GitHub에서 Issues 탭 → "New issue"
- 제목: "새로운 기능 제안"
- 내용 작성 후 Submit

---

## 📝 핵심 정리

| 명령어/개념 | 설명 |
|------------|------|
| `git remote add` | 원격 저장소 연결 |
| `git push` | 로컬 → 원격 업로드 |
| `git pull` | 원격 → 로컬 다운로드 |
| `git clone` | 원격 저장소 복제 |
| `git fetch` | 원격 정보만 가져오기 |
| Fork | 다른 사람의 저장소 복사 |
| Pull Request | 변경사항 반영 요청 |
| Issue | 이슈 관리 |

---

⬅️ 이전: [02. Git 기본 사용법](../02_git_commands/02_git_commands.md)  
➡️ 다음: [04. GitKraken 사용법](../04_gitkraken/04_gitkraken.md)

