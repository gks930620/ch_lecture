# 📖 챕터 1: Git 기초 개념

## 🎯 학습 목표
- 버전 관리가 무엇인지 이해한다
- Git의 개념과 특징을 설명할 수 있다
- Git을 설치하고 초기 설정을 할 수 있다

---

## 1. 버전 관리란?

### 버전 관리가 필요한 이유
여러분은 이런 경험이 있으신가요?

```
보고서_최종.docx
보고서_최종_수정.docx
보고서_진짜최종.docx
보고서_진짜진짜최종.docx
보고서_제출용.docx
```

이렇게 파일을 관리하면 어떤 문제가 발생할까요?
- ❌ 어떤 파일이 진짜 최종인지 모름
- ❌ 언제, 무엇을 변경했는지 기록이 없음
- ❌ 이전 버전으로 되돌리기 어려움
- ❌ 여러 사람이 동시에 작업하기 어려움

### 버전 관리 시스템 (VCS: Version Control System)
> 파일의 변경 이력을 체계적으로 관리해주는 시스템

**버전 관리 시스템이 해결해주는 것:**
- ✅ 모든 변경 이력을 자동으로 기록
- ✅ 원하는 시점으로 언제든 되돌리기 가능
- ✅ 여러 사람이 동시에 작업 가능
- ✅ 누가, 언제, 무엇을 변경했는지 추적 가능

---

## 2. Git이란?

### Git의 정의
> **Git**은 분산 버전 관리 시스템(DVCS: Distributed Version Control System)입니다.

- 2005년 **리누스 토르발즈**(Linux 창시자)가 개발
- 전 세계에서 가장 널리 사용되는 버전 관리 시스템
- **무료**, **오픈소스**

### 중앙집중식 vs 분산형

| | 중앙집중식 (SVN) | 분산형 (Git) |
|---|---|---|
| 저장소 | 서버에 1개 | 각자 PC에 전체 복사본 |
| 인터넷 | 항상 필요 | 오프라인 작업 가능 |
| 속도 | 서버 의존 | 로컬이라 빠름 |
| 안전성 | 서버 장애 시 위험 | 모든 PC가 백업 |

### Git의 3가지 영역

```
┌─────────────┐     git add     ┌──────────────┐   git commit   ┌────────────┐
│  Working     │ ──────────────▶│   Staging     │ ─────────────▶│ Repository │
│  Directory   │                │   Area        │               │ (.git)     │
│  (작업 디렉토리)│                │  (스테이징 영역) │               │ (저장소)    │
└─────────────┘                └──────────────┘               └────────────┘
```

1. **Working Directory (작업 디렉토리)**: 실제 파일을 수정하는 공간
2. **Staging Area (스테이징 영역)**: 커밋할 파일을 준비하는 공간
3. **Repository (저장소)**: 커밋된 파일이 저장되는 공간 (`.git` 폴더)

---

## 3. Git 설치

### Windows
1. [https://git-scm.com/downloads](https://git-scm.com/downloads) 접속
2. "Windows" 클릭하여 설치 파일 다운로드
3. 설치 시 기본 옵션 그대로 "Next" 클릭
4. 설치 완료 후 확인:
```bash
git --version
# 출력 예: git version 2.43.0.windows.1
```

### Mac
```bash
# Homebrew 사용
brew install git

# 또는 Xcode Command Line Tools
xcode-select --install
```

### Linux (Ubuntu/Debian)
```bash
sudo apt-get update
sudo apt-get install git
```

---

## 4. Git 초기 설정

Git을 처음 설치하면 사용자 정보를 설정해야 합니다.

```bash
# 사용자 이름 설정
git config --global user.name "홍길동"

# 이메일 설정 (GitHub 가입 이메일과 동일하게)
git config --global user.email "hong@example.com"

# 기본 브랜치 이름 설정
git config --global init.defaultBranch main

# 설정 확인
git config --list
```

### 설정 범위
| 옵션 | 범위 | 설정 파일 위치 |
|------|------|--------------|
| `--system` | 시스템 전체 | `/etc/gitconfig` |
| `--global` | 현재 사용자 | `~/.gitconfig` |
| `--local` | 현재 저장소 | `.git/config` |

---

## 🔬 실습: 첫 번째 Git 저장소 만들기

### 실습 파일
📁 `practice/hello.txt`

### 실습 순서

**1단계: 실습 폴더로 이동**
```bash
cd 01_git_basics/practice
```

**2단계: Git 저장소 초기화**
```bash
git init
```
> `.git` 폴더가 생성된 것을 확인하세요!

**3단계: 파일 상태 확인**
```bash
git status
```
> `hello.txt`가 빨간색(Untracked)으로 표시됩니다.

**4단계: 파일을 스테이징 영역에 추가**
```bash
git add hello.txt
```

**5단계: 다시 상태 확인**
```bash
git status
```
> `hello.txt`가 초록색(Staged)으로 표시됩니다.

**6단계: 첫 번째 커밋**
```bash
git commit -m "첫 번째 커밋: hello.txt 추가"
```

**7단계: 로그 확인**
```bash
git log
```
> 커밋 이력이 표시됩니다. 🎉

---

## 📝 핵심 정리

| 개념 | 설명 |
|------|------|
| 버전 관리 | 파일의 변경 이력을 체계적으로 관리 |
| Git | 분산 버전 관리 시스템 |
| Working Directory | 실제 파일을 수정하는 공간 |
| Staging Area | 커밋할 파일을 준비하는 공간 |
| Repository | 커밋된 파일이 저장되는 공간 |
| `git init` | Git 저장소 초기화 |
| `git add` | 스테이징 영역에 추가 |
| `git commit` | 변경사항 저장 (커밋) |

---

➡️ 다음 챕터: [02. Git 기본 사용법](../02_git_commands/02_git_commands.md)

