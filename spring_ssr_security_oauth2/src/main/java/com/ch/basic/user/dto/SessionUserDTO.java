package com.ch.basic.user.dto;

import com.ch.basic.user.UserEntity;
import lombok.*;

import java.util.List;

/**
 * 인증된 사용자 정보 DTO (세션 저장용)
 *
 * Security 세션(Authentication의 Principal)에 저장되는 사용자 정보.
 *
 * ※ 목적별 DTO 분리 원칙 (프로젝트_구조_정리.md):
 *   - 세션 사용자 DTO: id, username, nickname, email, roles (이 DTO)
 *   - 로그인 요청: Security가 자동 처리 (별도 DTO 불필요)
 *   - 회원가입 요청: username, password, nickname, email (별도 분리 가능)
 *
 * Entity를 직접 세션에 저장하지 않는 이유:
 *   1) Entity는 JPA가 관리하는 영속성 객체 — 세션에 넣으면 예기치 않은 동작 가능
 *   2) password 같은 민감 정보가 세션에 저장되면 보안 위험
 *   3) Entity 변경 시 세션에 영향 — DTO로 분리하면 독립적
 *
 * Entity → DTO 변환: SessionUserDTO.from(entity) (정적 팩토리 메서드)
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionUserDTO {

    private Long id;              // 사용자 PK (DB 식별용)
    private String username;      // 로그인 ID
    private String email;         // 이메일
    private String nickname;      // 닉네임 (화면 표시용)
    private List<String> roles;   // 권한 목록 (예: ["USER"], ["ADMIN","USER"])
    private String provider;      // OAuth2 provider (kakao, google 등 / 폼 로그인 시 null)
    // ※ password는 의도적으로 제외 — 세션에 비밀번호 저장하면 안 됨

    /**
     * Entity → DTO 변환 메서드
     * DB의 쉼표 구분 문자열("ADMIN,USER")을 List로 변환
     */
    public static SessionUserDTO from(UserEntity entity) {
        return SessionUserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .roles(List.of(entity.getRoles().split(",")))
                .provider(entity.getProvider())
                .build();
    }
}
