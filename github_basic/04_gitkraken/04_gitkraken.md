# 📖 챕터 4: GitKraken 사용법

## 🎯 학습 목표
- GitKraken의 장점과 인터페이스를 이해한다
- GitKraken으로 기본 Git 작업을 수행할 수 있다
- GUI 환경에서 브랜치, 머지, 충돌 해결을 할 수 있다
- GitKraken에서 GitHub를 연동할 수 있다

---

## 1. GitKraken이란?

### Git GUI 도구
> GitKraken은 Git을 시각적으로 사용할 수 있는 **GUI(그래픽 사용자 인터페이스) 도구**입니다.

### CLI vs GUI
| | CLI (터미널) | GUI (GitKraken) |
|---|---|---|
| 사용법 | 명령어 입력 | 클릭 & 드래그 |
| 학습 곡선 | 높음 | 낮음 |
| 시각화 | 텍스트 기반 | 그래프 시각화 |
| 세밀한 제어 | 모든 기능 가능 | 일부 제한 |
| 속도 | 빠름 (익숙하면) | 직관적 |

### GitKraken의 장점
- 🎨 **아름다운 커밋 그래프**: 브랜치 구조를 한눈에 파악
- 🖱️ **드래그 & 드롭**: 브랜치 병합을 드래그로 수행
- 🔀 **내장 충돌 해결 도구**: 충돌을 시각적으로 해결
- 🔗 **GitHub/GitLab 연동**: 원격 저장소 관리 편리
- 📱 **크로스 플랫폼**: Windows, Mac, Linux 지원

### 대안 GUI 도구
| 도구 | 특징 | 가격 |
|------|------|------|
| **GitKraken** | 아름다운 UI, 크로스 플랫폼 | 무료/유료 |
| **Sourcetree** | Atlassian 제품, 무료 | 무료 |
| **GitHub Desktop** | GitHub 공식 도구, 심플 | 무료 |
| **Fork** | 빠르고 가벼움 | 유료 |
| **VS Code Git** | 에디터 내장 | 무료 |

---

## 2. GitKraken 설치

### 다운로드
1. [https://www.gitkraken.com](https://www.gitkraken.com) 접속
2. "Free Download" 클릭
3. 설치 파일 실행

### 계정 연결
1. GitKraken 실행
2. GitHub 계정으로 로그인 (또는 이메일 가입)
3. GitHub 연동 허용

### 라이선스
| 버전 | 기능 | 가격 |
|------|------|------|
| Free | 공개 저장소, 기본 기능 | 무료 |
| Pro | 비공개 저장소, 고급 기능 | 유료 |
| Teams | 팀 협업 기능 | 유료 |

> 학생은 [GitHub Education](https://education.github.com)을 통해 Pro 버전을 무료로 사용할 수 있습니다!

---

## 3. GitKraken 인터페이스

### 메인 화면 구성
```
┌──────────────────────────────────────────────────┐
│  [메뉴바]  도구모음                                │
├────────┬─────────────────────────┬───────────────┤
│        │                         │               │
│  왼쪽   │    중앙: 커밋 그래프      │   오른쪽       │
│  패널   │    (메인 영역)           │   패널        │
│        │                         │               │
│ - Local │  ●──●──●──●            │  커밋 상세     │
│ - Remote│       \   /             │  - 변경 파일   │
│ - Stash │        ●──●            │  - Diff       │
│ - Tags  │                        │               │
│        │                         │               │
├────────┴─────────────────────────┴───────────────┤
│  하단: Staging / Commit 영역                       │
│  [Unstaged Files]  →  [Staged Files]  → [Commit]  │
└──────────────────────────────────────────────────┘
```

### 주요 영역
1. **왼쪽 패널**: 브랜치, 원격, 스태시, 태그 목록
2. **중앙 그래프**: 커밋 이력을 시각적으로 표시
3. **오른쪽 패널**: 선택한 커밋의 상세 정보
4. **하단 영역**: 파일 스테이징 및 커밋 메시지 입력

---

## 4. 기본 작업 (GUI)

### 저장소 열기 / 클론
- **열기**: File → Open Repo → 폴더 선택
- **클론**: File → Clone Repo → URL 입력
- **새로 만들기**: File → Init Repo → 폴더 선택

### 스테이징 & 커밋
1. 파일 수정 → 하단에 "Unstaged Files" 표시
2. 파일을 "Staged Files"로 드래그 (또는 "Stage all changes")
3. 커밋 메시지 입력
4. "Commit changes" 버튼 클릭

### 브랜치 생성 & 전환
- **생성**: 좌측 패널 "Local" 우클릭 → "Create branch here"
- **전환**: 브랜치 이름 더블클릭
- **삭제**: 브랜치 우클릭 → "Delete"

### 브랜치 병합 (Merge)
> 🎯 **드래그 & 드롭!**
1. 소스 브랜치를 타겟 브랜치 위로 드래그
2. "Merge feature into main" 선택
3. 완료!

### Push & Pull
- **Push**: 상단 도구모음에서 "Push" 버튼
- **Pull**: 상단 도구모음에서 "Pull" 버튼
- 또는 좌측 패널에서 원격 브랜치 우클릭

---

## 5. 충돌 해결 (Merge Conflict)

GitKraken의 내장 충돌 해결 도구는 매우 직관적입니다.

### 충돌 발생 시
1. 병합 시 충돌이 발생하면 파일에 ⚠️ 아이콘 표시
2. 해당 파일 클릭 → 충돌 해결 도구 열림

### 충돌 해결 화면
```
┌──────────────────────────────────────┐
│          [충돌 해결 도구]              │
├─────────────────┬────────────────────┤
│   왼쪽 (HEAD)    │   오른쪽 (Branch)  │
│                 │                    │
│  현재 브랜치의    │  병합하려는         │
│  내용            │  브랜치의 내용      │
│                 │                    │
├─────────────────┴────────────────────┤
│           하단: 결과 미리보기          │
│                                      │
│   체크박스로 원하는 내용 선택           │
│   또는 직접 편집                      │
└──────────────────────────────────────┘
```

3. 왼쪽/오른쪽 체크박스로 원하는 내용 선택
4. 또는 하단 결과 영역에서 직접 편집
5. "Save" → 파일 스테이징 → 커밋

---

## 6. GitHub 연동

### PR(Pull Request) 생성
1. 좌측 패널에서 원격 브랜치 확인
2. 브랜치 우클릭 → "Create pull request"
3. 제목, 설명 입력 → 생성

### Issue 확인
- 좌측 패널 하단 "GitHub" 섹션에서 Issue 목록 확인 가능

### 프로필 관리
- Preferences → Integrations → GitHub 연결 상태 확인

---

## 7. 유용한 기능

### Interactive Rebase
- 커밋을 우클릭 → "Interactive Rebase"
- 커밋 순서 변경, 합치기(Squash), 메시지 수정 가능

### Undo / Redo
- **Ctrl+Z**: 실수한 작업 되돌리기!
- GitKraken만의 강력한 기능

### Git Flow 지원
- Preferences → Git Flow → Initialize
- feature, release, hotfix 브랜치를 체계적으로 관리

### 단축키
| 단축키 | 기능 |
|--------|------|
| `Ctrl+P` | 커맨드 팔레트 |
| `Ctrl+T` | 새 탭 |
| `Ctrl+Shift+P` | 저장소 검색 |
| `Ctrl+/` | 키보드 단축키 목록 |

---

## 🔬 실습

### 실습 파일
📁 `practice/gui_test.txt`

### 실습 1: GitKraken으로 기본 작업

**1단계: 저장소 열기**
- GitKraken 실행 → File → Open Repo
- `04_gitkraken/practice` 폴더 선택

**2단계: 파일 수정 및 커밋**
- `gui_test.txt`를 텍스트 에디터에서 수정
- GitKraken 하단에서 변경사항 확인
- "Stage all changes" 클릭
- 커밋 메시지 입력: "GitKraken으로 첫 커밋"
- "Commit changes" 클릭

**3단계: 커밋 그래프 확인**
- 중앙 그래프에서 커밋 이력 확인
- 커밋을 클릭하여 변경 내용 확인

### 실습 2: 브랜치 작업

**1단계: 브랜치 생성**
- 좌측 "Local" 우클릭 → "Create branch here"
- 이름: `gui-feature`

**2단계: 브랜치에서 작업**
- `gui_test.txt` 수정 → 커밋

**3단계: 병합 (드래그 & 드롭)**
- `gui-feature` 브랜치를 `main` 위로 드래그
- "Merge gui-feature into main" 선택

### 실습 3: 충돌 해결

**1단계: 충돌 상황 만들기**
- `main` 브랜치에서 `gui_test.txt` 1번 줄 수정 → 커밋
- `gui-feature` 브랜치로 이동
- 같은 1번 줄을 다르게 수정 → 커밋

**2단계: 병합 시도**
- `gui-feature`를 `main`에 병합 → 충돌 발생!

**3단계: 충돌 해결 도구 사용**
- ⚠️ 표시된 파일 클릭
- 충돌 해결 도구에서 원하는 내용 선택
- Save → Stage → Commit

---

## 📝 핵심 정리

| 기능 | GitKraken 사용법 |
|------|-----------------|
| 저장소 열기 | File → Open Repo |
| 클론 | File → Clone Repo |
| 스테이징 | 하단에서 드래그 또는 Stage All |
| 커밋 | 메시지 입력 → Commit changes |
| 브랜치 생성 | Local 우클릭 → Create branch |
| 병합 | 드래그 & 드롭 |
| 충돌 해결 | 내장 도구로 시각적 해결 |
| Push/Pull | 상단 Push/Pull 버튼 |
| Undo | Ctrl+Z |

---

⬅️ 이전: [03. GitHub 사용법](../03_github/03_github.md)  
🏠 처음으로: [README](../README.md)

