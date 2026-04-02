# 👨‍🏫 강사용 진행 가이드

이 문서는 Git & GitHub 강의를 진행하는 강사를 위한 가이드입니다.  
각 챕터의 진행 방법, 실습 환경 초기화, 트러블슈팅 등을 안내합니다.

---

## 📅 전체 강의 계획

| 순서 | 챕터 | 예상 시간 | 핵심 내용 |
|------|------|----------|----------|
| 1 | [Git 기초 개념](01_git_basics/01_git_basics.md) | 30분 | 버전관리, Git 개념, 설치, 초기설정 |
| 2 | [Git 기본 사용법](02_git_commands/02_git_commands.md) | 50분 | add, commit, branch, merge, reset, stash |
| 3 | [GitHub 사용법](03_github/03_github.md) | 40분 | 원격저장소, push, pull, fork, PR |
| 4 | [GitKraken 사용법](04_gitkraken/04_gitkraken.md) | 30분 | GUI 도구 활용 |

**총 소요시간**: 약 2시간 30분 (휴식 포함 3시간)

---

## 🔄 실습 환경 초기화 방법

> ⚠️ **중요**: 각 챕터의 실습은 독립적으로 진행됩니다.  
> 챕터가 바뀔 때마다 Git 히스토리를 초기화하여 깨끗한 상태에서 시작하세요.

### 방법 1: .git 폴더 삭제 (가장 간단)

**Windows (PowerShell):**
```powershell
# 해당 practice 폴더로 이동
cd 01_git_basics\practice

# .git 폴더 삭제
Remove-Item -Recurse -Force .git

# 새로 초기화
git init
```

**Windows (CMD):**
```cmd
cd 01_git_basics\practice
rmdir /s /q .git
git init
```

**Mac / Linux:**
```bash
cd 01_git_basics/practice
rm -rf .git
git init
```

### 방법 2: Orphan 브랜치 (히스토리만 초기화)
```bash
git checkout --orphan temp_branch
git add -A
git commit -m "초기 상태"
git branch -D main
git branch -m main
```

### 방법 3: 전체 프로젝트 초기화 스크립트

**Windows (PowerShell):**
```powershell
# 모든 practice 폴더의 .git 삭제
$folders = @(
    "01_git_basics\practice",
    "02_git_commands\practice",
    "03_github\practice",
    "04_gitkraken\practice"
)

foreach ($folder in $folders) {
    $gitPath = Join-Path $folder ".git"
    if (Test-Path $gitPath) {
        Remove-Item -Recurse -Force $gitPath
        Write-Host "초기화 완료: $folder"
    }
}
```

**Mac / Linux:**
```bash
#!/bin/bash
for dir in 0*/practice; do
    if [ -d "$dir/.git" ]; then
        rm -rf "$dir/.git"
        echo "초기화 완료: $dir"
    fi
done
```

---

## 📋 챕터별 진행 가이드

### 📖 챕터 1: Git 기초 개념 (30분)

#### 강의 흐름
```
개념 설명 (15분) → 설치 확인 (5분) → 실습 (10분)
```

#### 진행 방법
1. **[5분] 아이스브레이킹**
   - "보고서_최종_진짜최종.docx" 경험 물어보기
   - 버전 관리가 왜 필요한지 공감대 형성

2. **[10분] 개념 설명**
   - 버전 관리 시스템 설명
   - Git vs SVN 차이 간단히
   - **핵심: Git의 3가지 영역** (Working Directory → Staging → Repository)
   - 이 부분을 화이트보드에 직접 그리면서 설명하면 효과적

3. **[5분] Git 설치 확인**
   - 수강생 모두 `git --version` 실행
   - 안 되는 분은 바로 설치 도와주기

4. **[10분] 실습: 첫 Git 저장소**
   - `practice/hello.txt` 이용
   - **반드시 한 단계씩** 따라하게 하기
   - 각 단계마다 `git status`로 상태 변화 확인 유도

#### 실습 전 초기화
```bash
cd 01_git_basics/practice
# .git 폴더가 없는 깨끗한 상태에서 시작
```

#### 체크리스트
- [ ] `git --version` 동작 확인
- [ ] `git config` 설정 완료
- [ ] `git init` → `add` → `commit` 흐름 이해
- [ ] `git status`로 상태 변화 확인

---

### 📖 챕터 2: Git 기본 사용법 (50분)

#### 강의 흐름
```
기본 명령어 (15분) → 실습1 (10분) → 브랜치 (10분) → 실습2 (10분) → reset/stash (5분)
```

#### 진행 방법
1. **[15분] 기본 명령어 설명**
   - `add`, `commit`, `log`, `diff` 시연
   - **강사가 먼저 시연하고**, 수강생이 따라하는 방식
   - 커밋 메시지 작성법 강조

2. **[10분] 실습 1: menu.txt**
   - 파일 수정 → diff 확인 → add → commit
   - `git log --oneline`으로 이력 확인

3. **[10분] 브랜치 설명**
   - 브랜치 개념을 **나무 가지**에 비유
   - `branch` → `checkout` → `merge` 시연
   - Fast-forward vs 3-way merge 차이

4. **[10분] 실습 2: feature.txt**
   - 브랜치 생성 → 작업 → 병합
   - 충돌 상황 만들어보기 (선택)

5. **[5분] reset & stash 간단 설명**
   - `reset --soft`, `--mixed`, `--hard` 차이 표로 설명
   - `stash` 활용 시나리오 설명
   - 시간이 되면 experiment.txt로 실습

#### 실습 전 초기화
```bash
cd 02_git_commands/practice
# .git 폴더가 있다면 삭제 후 시작
```

#### ⚠️ 주의사항
- `git reset --hard`는 위험하다는 것 반드시 강조
- 실습 중 `git status`를 습관적으로 치도록 유도
- 충돌 해결 시 당황하지 않도록 미리 안내

#### 체크리스트
- [ ] `add` → `commit` → `log` 흐름 숙달
- [ ] `git diff`로 변경사항 확인
- [ ] 브랜치 생성, 이동, 병합 가능
- [ ] `reset`의 3가지 모드 차이 이해

---

### 📖 챕터 3: GitHub 사용법 (40분)

#### 강의 흐름
```
GitHub 소개 (5분) → 저장소 생성 (5분) → push/pull 실습 (15분) → Fork & PR (15분)
```

#### 사전 준비
- ✅ 모든 수강생이 GitHub 계정을 가지고 있는지 확인
- ✅ GitHub 인증 방법 안내 (Token 또는 SSH)

#### 진행 방법
1. **[5분] GitHub 소개**
   - Git(도구) vs GitHub(서비스) 차이 명확히
   - GitHub 웹 인터페이스 둘러보기

2. **[5분] 저장소 생성**
   - GitHub에서 새 저장소 만들기 시연
   - 수강생도 각자 생성

3. **[15분] 실습: push & pull**
   - `remote_test.txt` 활용
   - 로컬 → 원격 push
   - GitHub 웹에서 수정 → 로컬 pull
   - **화면 공유하며 진행하면 좋음**

4. **[15분] Fork & PR 실습**
   - 강사의 저장소를 수강생이 Fork
   - `collaboration.txt`에 자기 이름 추가
   - PR 보내기 → 강사가 라이브로 Merge

#### 실습 전 초기화
```bash
cd 03_github/practice
# .git 폴더 삭제
# GitHub에 새 저장소 생성 필요
```

#### 💡 팁
- GitHub 인증 문제가 가장 많이 발생하므로 미리 준비
- Personal Access Token 생성 방법을 슬라이드로 준비
- SSH 설정은 시간이 부족하면 건너뛰어도 됨

#### 체크리스트
- [ ] GitHub 저장소 생성
- [ ] `push` / `pull` 성공
- [ ] Fork & PR 프로세스 이해

---

### 📖 챕터 4: GitKraken 사용법 (30분)

#### 강의 흐름
```
설치 & 연동 (5분) → 인터페이스 소개 (5분) → GUI 실습 (15분) → Q&A (5분)
```

#### 진행 방법
1. **[5분] GitKraken 설치 & GitHub 연동**
   - 미리 설치해오도록 사전 안내
   - GitHub 계정 연결

2. **[5분] 인터페이스 둘러보기**
   - 화면 구성 설명
   - 커밋 그래프 읽는 법

3. **[15분] GUI 실습**
   - `gui_test.txt` 수정 → 스테이징 → 커밋
   - 브랜치 생성 → 드래그 & 드롭 병합
   - 충돌 해결 도구 체험

4. **[5분] Q&A 및 마무리**
   - CLI vs GUI 각각의 장단점 정리
   - 개인 프로젝트에서 어떤 도구를 쓸지 추천

#### 실습 전 초기화
```bash
cd 04_gitkraken/practice
# .git 폴더 삭제 후 git init
```

#### 체크리스트
- [ ] GitKraken에서 저장소 열기
- [ ] GUI로 커밋 성공
- [ ] 브랜치 병합 (드래그 & 드롭)

---

## 🔧 자주 발생하는 문제 & 해결법

### 1. Git 설치 관련
| 문제 | 해결 |
|------|------|
| `git`이 인식되지 않음 | 환경변수 PATH에 Git 경로 추가, 터미널 재시작 |
| 설치가 안 됨 (Mac) | `xcode-select --install` 실행 |

### 2. GitHub 인증 관련
| 문제 | 해결 |
|------|------|
| push 시 인증 실패 | Personal Access Token 생성하여 비밀번호 대신 사용 |
| SSH 연결 실패 | `ssh -T git@github.com`으로 연결 테스트 |
| Token이 만료됨 | Settings → Developer settings → 새 토큰 생성 |

### 3. Git 사용 중 문제
| 문제 | 해결 |
|------|------|
| `fatal: not a git repository` | `git init` 실행 또는 올바른 디렉토리 확인 |
| detached HEAD 상태 | `git checkout main`으로 복귀 |
| merge conflict | 충돌 파일 열어서 수동 해결 → add → commit |
| push 거부 (non-fast-forward) | `git pull` 먼저 실행 후 push |
| 한글 파일명 깨짐 | `git config --global core.quotepath false` |
| LF/CRLF 경고 | `git config --global core.autocrlf true` (Windows) |

### 4. GitKraken 관련
| 문제 | 해결 |
|------|------|
| GitHub 연동 실패 | Preferences → Integrations → 다시 연결 |
| 저장소가 안 보임 | File → Open Repo로 직접 열기 |
| 무료 버전 제한 | Private 저장소는 Pro 필요 (학생 무료) |

---

## 📝 강의 준비 체크리스트

### 강의 전
- [ ] 모든 md 파일 내용 숙지
- [ ] 실습 환경 초기화 (모든 practice 폴더의 .git 삭제)
- [ ] Git 최신 버전 설치 확인
- [ ] GitHub 강의용 저장소 준비 (Fork/PR 실습용)
- [ ] GitKraken 설치 확인
- [ ] 인터넷 연결 확인 (GitHub 접속용)
- [ ] 수강생에게 사전 안내: Git 설치, GitHub 가입, GitKraken 설치

### 강의 중
- [ ] 화면 공유 시 터미널 글자 크기 키우기
- [ ] 각 명령어 실행 전 무엇을 하는 명령인지 설명
- [ ] `git status`를 자주 쳐서 상태 변화 보여주기
- [ ] 수강생 진행 상황 중간중간 확인

### 강의 후
- [ ] 강의 자료 배포 (이 저장소 링크 공유)
- [ ] 추가 학습 자료 안내
- [ ] 피드백 수집

---

## 📚 추가 학습 자료 (수강생에게 공유)

- [Git 공식 문서 (한글)](https://git-scm.com/book/ko/v2)
- [GitHub Skills](https://skills.github.com/) - 인터랙티브 학습
- [Learn Git Branching](https://learngitbranching.js.org/?locale=ko) - 시각적 학습
- [Oh My Git!](https://ohmygit.org/) - 게임으로 배우기
- [gitignore.io](https://www.toptal.com/developers/gitignore) - .gitignore 생성기

