# STOMP 채팅방 구조와 생성 기능 가이드

## 1) 현재 채팅이 STOMP 방식인지

네. 현재 채팅은 STOMP + SockJS 기반입니다.

- 엔드포인트: `/ws-chat` (`WebSocketConfig.registerStompEndpoints`)
- 발행 prefix: `/pub` (`WebSocketConfig.configureMessageBroker`)
- 구독 prefix: `/sub` (`WebSocketConfig.configureMessageBroker`)
- 메시지 처리: `@MessageMapping("/room/{roomId}")` (`ChatController.sendMessage`)
- 브라우저 클라이언트:
  - 전송: `/pub/room/{roomId}`
  - 구독: `/sub/room/{roomId}`

## 2) 채팅방 데이터가 DB인지

채팅방은 DB 기반입니다.

- 엔티티: `RoomEntity` (`chat_room` 테이블)
- 리포지토리: `RoomRepository extends JpaRepository`
- 서비스: `RoomService`에서 DB 조회
- 시드 데이터: `src/main/resources/data-rooms.sql`
- `application.yml`에서 `spring.sql.init.data-locations`에 `data-rooms.sql` 포함

참고:
- 메시지 브로커(`enableSimpleBroker`)는 메모리 브로커입니다.
- 즉, "채팅방 목록/정보"는 DB, "실시간 메시지 중계"는 메모리 브로커입니다.

## 3) 추가된 채팅방 생성 기능

### 백엔드
- API: `POST /api/rooms`
- 요청 바디:

```json
{
  "name": "새 채팅방"
}
```

- 동작:
  - 공백 제거(trim)
  - 빈 이름 거부
  - 이름 중복(대소문자 무시) 거부
  - DB 저장 후 생성된 방 정보 반환

관련 파일:
- `RoomController`
- `RoomService`
- `RoomRepository`
- `RoomCreateRequest`

### 프론트
- `templates/stomp/rooms.html`에 "채팅방 만들기" 폼 추가
- 생성 성공 시 생성된 방(`/room/{id}`)으로 즉시 이동
- 실패 시 에러 메시지 표시

## 4) 테스트 방법

1. 로그인 후 `/rooms` 접속
2. "새 채팅방 이름" 입력 후 생성
3. 생성 직후 해당 채팅방으로 이동되는지 확인
4. 브라우저 새로고침 후 목록에서 생성한 방이 남아있는지 확인 (DB 반영 확인)

## 5) 주의 사항

- `ddl-auto: create` 환경에서는 서버 재시작 시 테이블이 재생성됩니다.
- 따라서 테스트 중 생성한 채팅방은 재시작 시 초기화될 수 있습니다.
- 운영 환경에서는 `ddl-auto: validate` 또는 `none` + 마이그레이션 도구(Flyway/Liquibase) 사용을 권장합니다.

