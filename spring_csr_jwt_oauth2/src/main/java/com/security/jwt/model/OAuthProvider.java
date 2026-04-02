package com.security.jwt.model;

import com.security.jwt.entity.UserEntity;
import java.util.Map;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;

public enum OAuthProvider {
    GOOGLE("google") {
        @Override
        public UserEntity toUserEntity(Map<String, Object> attributes, PasswordEncoder passwordEncoder) {
            UserEntity user = new UserEntity();
            user.setProvider(this.getRegistrationId());
            user.setUsername(extractUsername(attributes));  //google15432323
            user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString())); // 예측 불가능한 랜덤 비밀번호
            user.setEmail(extractEmail(attributes));
            user.setNickname(extractNickname(attributes));
            user.setRoles("USER");
            return user;
        }
        @Override
        public String extractUsername(Map<String, Object> attributes) {
            return getRegistrationId() + (String) attributes.get("sub");
        }
        @Override
        public String extractEmail(Map<String, Object> attributes) {
            return (String) attributes.get("email");
        }
        @Override
        public String extractNickname(Map<String, Object> attributes) {
            return (String) attributes.get("name");
        }
    },
    KAKAO("kakao") {
        @Override
        public UserEntity toUserEntity(Map<String, Object> attributes, PasswordEncoder passwordEncoder) {
            UserEntity user = new UserEntity();
            user.setProvider(this.getRegistrationId());  //kakao
            user.setUsername(extractUsername(attributes));  //kakao15432323
            user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString())); // 예측 불가능한 랜덤 비밀번호
            user.setEmail(extractEmail(attributes));
            user.setNickname(extractNickname(attributes));
            user.setRoles("USER");
            return user;
        }
        @Override
        public String extractUsername(Map<String, Object> attributes) {
            Long id = (Long) attributes.get("id");  //카카오는 id가 Long.
            return getRegistrationId() + id;
        }
        @Override
        public String extractEmail(Map<String, Object> attributes) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            return (String) kakaoAccount.get("email");
        }
        @Override
        public String extractNickname(Map<String, Object> attributes) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            return (String) profile.get("nickname");
        }
    };



    private final String registrationId;
    OAuthProvider(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public static OAuthProvider from(String registrationId) {
        for (OAuthProvider provider : OAuthProvider.values()) {
            if (provider.getRegistrationId().equalsIgnoreCase(registrationId)) { //대소문자구분없이.
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown provider: " + registrationId);
    }
    public abstract UserEntity toUserEntity(Map<String, Object> attributes, PasswordEncoder passwordEncoder);
    public abstract String extractUsername(Map<String, Object> attributes);
    public abstract String extractEmail(Map<String, Object> attributes);
    public abstract String extractNickname(Map<String, Object> attributes);

}