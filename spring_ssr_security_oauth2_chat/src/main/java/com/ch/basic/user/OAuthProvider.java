package com.ch.basic.user;

import java.util.Map;

/**
 * OAuth2 Provider별 유저정보 → UserEntity 변환 로직
 *
 * 각 OAuth2 Provider(카카오, 구글 등)마다 유저정보(attributes)의 구조가 다르므로
 * Provider별로 변환 로직을 분리한다.
 *
 * ※ security_oauth2 프로젝트와 동일한 역할이지만
 *    이 프로젝트의 UserEntity는 Builder 패턴 + roles가 쉼표 구분 String인 점이 다름
 *
 * attributes 구조 예시:
 *
 * 구글:
 * {
 *     "sub": "112233445566778899001",
 *     "name": "John Doe",
 *     "email": "johndoe@gmail.com",
 *     ...
 * }
 *
 * 카카오:
 * {
 *     "id": 1234567890,     ← Long 타입
 *     "kakao_account": {
 *         "email": "user@example.com"
 *     },
 *     "properties": {
 *         "nickname": "홍길동"
 *     }
 * }
 */
public enum OAuthProvider {

    GOOGLE("google") {
        @Override
        public UserEntity toUserEntity(Map<String, Object> attributes) {
            return UserEntity.builder()
                    .provider(this.getRegistrationId())
                    .username(this.getRegistrationId() + attributes.get("sub"))   // google112233...
                    .password("{noop}oauth2user")   // OAuth2 사용자는 비밀번호 불필요 — Security 형식 맞추기용
                    .email((String) attributes.get("email"))
                    .nickname((String) attributes.get("name"))
                    .roles("USER")
                    .build();
        }
    },

    KAKAO("kakao") {
        @Override
        @SuppressWarnings("unchecked")
        public UserEntity toUserEntity(Map<String, Object> attributes) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            Long id = (Long) attributes.get("id");  // 카카오는 id가 Long

            // email, nickname은 동의항목에 따라 null일 수 있음 → null-safe 처리
            String email = (kakaoAccount != null) ? (String) kakaoAccount.get("email") : null;
            String nickname = (properties != null) ? (String) properties.get("nickname") : null;
            if (nickname == null && kakaoAccount != null) {
                // properties에 없으면 kakao_account.profile.nickname에서 시도
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                if (profile != null) {
                    nickname = (String) profile.get("nickname");
                }
            }

            return UserEntity.builder()
                    .provider(this.getRegistrationId())
                    .username(this.getRegistrationId() + id)   // kakao1234567890
                    .password("{noop}oauth2user")
                    .email(email)
                    .nickname(nickname != null ? nickname : "카카오사용자")  // 닉네임 없으면 기본값
                    .roles("USER")
                    .build();
        }
    };

    private final String registrationId;

    OAuthProvider(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    /**
     * registrationId 문자열("google", "kakao")로 enum 찾기
     * application.yml의 registration 이름과 매칭
     */
    public static OAuthProvider from(String registrationId) {
        for (OAuthProvider provider : OAuthProvider.values()) {
            if (provider.getRegistrationId().equalsIgnoreCase(registrationId)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown OAuth provider: " + registrationId);
    }

    /**
     * OAuth2 attributes → UserEntity 변환
     * Provider마다 attributes 구조가 다르므로 각 enum 상수에서 구현
     */
    public abstract UserEntity toUserEntity(Map<String, Object> attributes);
}

