# 📖 챕터 2: Git 기본 사용법

## 🎯 학습 목표
- Git의 기본 명령어를 사용할 수 있다
- 브랜치를 생성하고 병합할 수 있다
- 변경사항을 되돌리는 방법을 이해한다
- stash를 활용할 수 있다

---

## 1. 기본 워크플로우: add → commit → log

### git status - 상태 확인
```bash
git status
```
파일의 상태를 확인합니다:
- **Untracked**: Git이 추적하지 않는 새 파일 (빨간색)
- **Modified**: 수정된 파일 (빨간색)
- **Staged**: 커밋 준비된 파일 (초록색)
- **Committed**: 저장소에 저장된 상태

### git add - 스테이징
```bash
# 특정 파일 추가
git add menu.txt

# 모든 변경 파일 추가
git add .

# 여러 파일 추가
git add menu.txt feature.txt
```

### git commit - 커밋
```bash
# 메시지와 함께 커밋
git commit -m "메뉴 목록 추가"

# add + commit 동시에 (이미 추적 중인 파일만)
git commit -am "메뉴 수정"
```

#### 좋은 커밋 메시지 작성법
```
# 좋은 예 ✅
feat: 로그인 기능 추가
fix: 비밀번호 검증 오류 수정
docs: README 업데이트

# 나쁜 예 ❌
수정
ㅋㅋ
asdf
```

### git log - 이력 확인
```bash
# 기본 로그
git log

# 한 줄로 보기
git log --oneline

# 그래프로 보기
git log --oneline --graph --all

# 최근 3개만 보기
git log -3
```

---

## 2. 변경사항 비교: diff

### git diff
```bash
# Working Directory vs Staging Area
git diff

# Staging Area vs 마지막 커밋
git diff --staged

# 두 커밋 비교
git diff 커밋해시1 커밋해시2

# 특정 파일만 비교
git diff menu.txt
```

---

## 3. 브랜치 (Branch)

### 브랜치란?
> 독립적인 작업 공간을 만드는 기능

```
main    ●───●───●───●───●
                 \       ↗
feature           ●───●
```

### 브랜치 기본 명령어
```bash
# 브랜치 목록 확인
git branch

# 새 브랜치 생성
git branch feature

# 브랜치 이동
git checkout feature
# 또는 (최신 방법)
git switch feature

# 생성 + 이동 동시에
git checkout -b feature
# 또는
git switch -c feature

# 브랜치 삭제
git branch -d feature
```

### 브랜치 병합 (Merge)
```bash
# main 브랜치로 이동
git checkout main

# feature 브랜치를 main에 병합
git merge feature
```

#### 병합 종류
| 종류 | 설명 |
|------|------|
| Fast-forward | 단순히 포인터 이동 (분기 없음) |
| 3-way merge | 새로운 병합 커밋 생성 |
| Conflict | 충돌 발생 → 수동 해결 필요 |

### 충돌(Conflict) 해결
충돌이 발생하면 파일에 아래와 같은 표시가 나타납니다:
```
<<<<<<< HEAD
메인 브랜치의 내용
=======
피처 브랜치의 내용
>>>>>>> feature
```

**해결 방법:**
1. 충돌 표시(`<<<<<<<`, `=======`, `>>>>>>>`) 제거
2. 원하는 내용으로 수정
3. `git add` → `git commit`

---

## 4. 되돌리기

### git restore - 파일 복원
```bash
# Working Directory의 변경 취소 (수정 전으로 되돌리기)
git restore menu.txt

# Staging Area에서 내리기 (add 취소)
git restore --staged menu.txt
```

### git reset - 커밋 되돌리기
```bash
# soft: 커밋만 취소 (변경사항은 Staged 상태로 유지)
git reset --soft HEAD~1

# mixed (기본값): 커밋 + add 취소 (변경사항은 Working Directory에 유지)
git reset HEAD~1

# hard: 커밋 + 변경사항 모두 삭제 ⚠️ 주의!
git reset --hard HEAD~1
```

#### reset 비교표
| 옵션 | 커밋 | Staging Area | Working Directory |
|------|------|-------------|-------------------|
| `--soft` | ↩️ 취소 | ✅ 유지 | ✅ 유지 |
| `--mixed` | ↩️ 취소 | ↩️ 취소 | ✅ 유지 |
| `--hard` | ↩️ 취소 | ↩️ 취소 | ❌ 삭제 |

### git revert - 안전한 되돌리기
```bash
# 특정 커밋을 취소하는 새로운 커밋 생성
git revert 커밋해시
```
> `reset`은 이력을 삭제하고, `revert`는 이력을 유지하면서 되돌립니다.

---

## 5. Stash - 임시 저장

### stash란?
> 작업 중인 변경사항을 임시로 저장하고 깨끗한 상태로 돌아가는 기능

```bash
# 현재 변경사항 임시 저장
git stash

# 메시지와 함께 저장
git stash save "작업 중인 메뉴 수정"

# stash 목록 확인
git stash list

# 가장 최근 stash 적용
git stash pop

# 특정 stash 적용 (삭제하지 않음)
git stash apply stash@{0}

# stash 삭제
git stash drop stash@{0}

# 전체 stash 삭제
git stash clear
```

**활용 시나리오:**
1. feature 브랜치에서 작업 중
2. 긴급하게 main 브랜치에서 버그 수정이 필요
3. `git stash`로 현재 작업 임시 저장
4. main으로 이동하여 버그 수정
5. feature로 돌아와서 `git stash pop`으로 작업 복원

---

## 🔬 실습

### 실습 파일
📁 `practice/menu.txt` - add/commit/status 실습  
📁 `practice/feature.txt` - branch/merge 실습  
📁 `practice/experiment.txt` - reset/stash 실습

### 실습 1: 기본 워크플로우 (menu.txt)

```bash
cd 02_git_commands/practice
git init
```

**1단계: 첫 커밋**
```bash
git add menu.txt
git commit -m "초기 메뉴 추가"
```

**2단계: 파일 수정 후 diff 확인**
- `menu.txt`를 열고 "4. 디저트" 추가
```bash
git diff
git add menu.txt
git commit -m "디저트 메뉴 추가"
```

**3단계: 로그 확인**
```bash
git log --oneline
```

### 실습 2: 브랜치 & 병합 (feature.txt)

**1단계: 브랜치 생성 및 이동**
```bash
git checkout -b new-feature
```

**2단계: feature.txt 수정**
- `feature.txt`에 "새로운 기능: 다크모드" 추가
```bash
git add feature.txt
git commit -m "다크모드 기능 추가"
```

**3단계: main으로 돌아가서 병합**
```bash
git checkout main
git merge new-feature
```

**4단계: 브랜치 삭제**
```bash
git branch -d new-feature
```

### 실습 3: reset & stash (experiment.txt)

**1단계: 여러 커밋 만들기**
- `experiment.txt` 수정 → 커밋 (3번 반복)

**2단계: soft reset 실습**
```bash
git log --oneline
git reset --soft HEAD~1
git status  # Staged 상태 확인
git commit -m "다시 커밋"
```

**3단계: stash 실습**
- `experiment.txt` 수정 (커밋하지 않음)
```bash
git stash save "실험 중인 변경사항"
git status       # 깨끗한 상태
git stash list   # stash 확인
git stash pop    # 복원
```

---

## 📝 핵심 정리

| 명령어 | 설명 |
|--------|------|
| `git status` | 파일 상태 확인 |
| `git add` | 스테이징 영역에 추가 |
| `git commit` | 변경사항 커밋 |
| `git log` | 커밋 이력 확인 |
| `git diff` | 변경사항 비교 |
| `git branch` | 브랜치 관리 |
| `git checkout` / `git switch` | 브랜치 이동 |
| `git merge` | 브랜치 병합 |
| `git restore` | 변경 취소 |
| `git reset` | 커밋 되돌리기 |
| `git revert` | 안전한 되돌리기 |
| `git stash` | 임시 저장 |

---

⬅️ 이전: [01. Git 기초 개념](../01_git_basics/01_git_basics.md)  
➡️ 다음: [03. GitHub 사용법](../03_github/03_github.md)

