package com.ch.basic.user.dto;

import com.ch.basic.user.UserEntity;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 세션에 저장하는 로그인 사용자 정보 DTO
 *
 * Entity를 직접 세션에 저장하지 않는 이유:
 *   1) Entity는 JPA가 관리하는 영속성 객체 — 세션에 넣으면 예기치 않은 동작 가능
 *   2) password 같은 민감 정보가 세션에 저장되면 보안 위험
 *   3) Entity 변경 시 세션에 영향 — DTO로 분리하면 독립적
 *
 * Serializable: HttpSession에 저장되는 객체는 직렬화 가능해야 함
 *   - 세션 복제(클러스터링), 세션 저장소(Redis 등) 사용 시 필수
 *
 * Entity → DTO 변환: LoginUserDTO.from(entity) (정적 팩토리 메서드)
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;          // 사용자 PK (DB 식별용)
    private String username;  // 로그인 ID
    private String email;     // 이메일
    private String nickname;  // 닉네임 (화면 표시용)
    // ※ password는 의도적으로 제외 — 세션에 비밀번호 저장하면 안 됨

    /**
     * Entity → DTO 변환 메서드
     * 로그인 성공 후 UserEntity에서 필요한 정보만 추출하여 DTO 생성
     */
    public static LoginUserDTO from(UserEntity entity) {
        return LoginUserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .build();
    }
}
