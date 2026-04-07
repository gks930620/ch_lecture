# REST API 리팩터링 정리

## 1. REST API 핵심 원칙 (먼저)

이 문서는 "현재 올바른 REST 형식으로 맞추는 기준"을 먼저 정리하고,
아래에 실제 프로젝트 적용 내역을 기록한다.

1. URI에는 동사 대신 **리소스 명사**를 사용한다.
2. 목록/생성 엔드포인트는 **복수형 컬렉션 경로**를 사용한다.
3. 자식 리소스는 **중첩 경로**로 표현한다.
4. 행위는 URI가 아니라 **HTTP Method(GET/POST/PUT/DELETE)** 로 표현한다.
5. 생성 성공 시 가능하면 **`201 Created` + `Location` 헤더**를 반환한다.
6. 인증/토큰 관련 경로도 의미가 드러나는 **리소스 기반 이름**으로 맞춘다.
7. 라우트 변경 시 컨트롤러만이 아니라 **보안 매처, 프론트 호출 경로, 테스트 코드**까지 함께 정렬한다.

## 2. 왜 변경했는가

기존 구현은 CRUD 동작 자체는 안정적이었지만,
일부 경로가 액션형(`upload`, `detail`, `reissue`)이거나 리소스 명이 섞여 있었다(`community` vs `rooms`).
그래서 위 원칙에 맞춰 URI를 일관되게 통일했다.

## 3. 우리 프로젝트 적용 내역 (Old -> New)

### Community
- `GET /api/community` -> `GET /api/communities`
- `POST /api/community` -> `POST /api/communities`
- `GET /api/community/{communityId}` -> `GET /api/communities/{communityId}`
- `PUT /api/community/{communityId}` -> `PUT /api/communities/{communityId}`
- `DELETE /api/community/{communityId}` -> `DELETE /api/communities/{communityId}`

### Comment
- `GET /api/comments/community/{communityId}` -> `GET /api/communities/{communityId}/comments`
- `POST /api/comments` -> `POST /api/communities/{communityId}/comments`
- `PUT /api/comments/{commentId}` (유지)
- `DELETE /api/comments/{commentId}` (유지)

### File
- `POST /api/files/upload` -> `POST /api/files`
- `GET /api/files/detail` -> `GET /api/files`
- `GET /api/files/download/{fileId}` -> `GET /api/files/{fileId}/content`
- `GET /api/files` (path list) -> `GET /api/files/paths`
- `DELETE /api/files/{fileId}` (유지)

### User/Auth
- `POST /api/join` -> `POST /api/users`
- `GET /api/my/info` -> `GET /api/users/me`
- `POST /api/refresh/reissue` -> `POST /api/tokens/refresh`
- `POST /api/oauth2/{provider}/app` -> `POST /api/oauth2/providers/{provider}/tokens`

### Chat Room API / Page Route
- `GET /api/room/{roomId}` -> `GET /api/rooms/{roomId}`
- Page route `/room/{roomId}` -> `/rooms/{roomId}`

## 4. 적용 범위
- 백엔드 컨트롤러 라우팅
- Security matcher 경로
- DTO 다운로드 URL 생성
- HTML/JS(`fetch`, `authFetch`) 요청 URL
- 채팅방 페이지 링크

## 5. 추가 정렬 사항
- `POST /api/communities`
- `POST /api/communities/{communityId}/comments`
- `POST /api/rooms`

위 생성 API들은 가능한 경우 `201 Created`와 `Location` 헤더를 반환하도록 정렬했다.
