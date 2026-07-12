---
layout: default
title: spring_ssr_security_oauth2_chat
---

# spring_ssr_security_oauth2_chat

원본 프로젝트 폴더 구조를 반영한 설명 문서 목록입니다.

## 문서 목록

- [`프로젝트_구조_정리.md`](프로젝트_구조_정리.md)
- [`OAuth2_로그인_설명.md`](OAuth2_로그인_설명.md)
- [`스프링채팅설명.md`](스프링채팅설명.md)

## 실행 전제 (로컬에서 따라 하려면)

이 프로젝트를 직접 실행하려면 아래가 준비돼 있어야 합니다.

1. **MariaDB** — `application.yml` 기준 접속 정보
   - URL: `jdbc:mariadb://localhost:3406/test`
   - 계정 / 비밀번호: `test` / `test`
   - (`ddl-auto: create`라 서버 시작 시 테이블이 매번 새로 생성됩니다. 개발용 설정)
2. **`.env` 파일** — OAuth2 키 (프로젝트 루트에 위치, git 미추적)
   - `KAKAO_CLIENT_ID` (카카오 REST API 키)
   - `GOOGLE_CLIENT_ID`, `GOOGLE_CLIENT_SECRET` (구글 OAuth 클라이언트)
   - 소셜 로그인을 쓰지 않고 폼 로그인만 테스트한다면 이 키가 없어도 앱은 뜨지만, 카카오/구글 버튼은 동작하지 않습니다.

> 카카오/구글 개발자 콘솔의 redirect URI는 `application.yml`과 동일하게
> `http://localhost:8080/login/oauth2/code/{kakao|google}`로 등록해야 합니다.
