package com.ch.basic.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * OAuth2 로그인 처리 서비스
 *
 * Security가 OAuth2 로그인 시 자동으로 호출하는 서비스.
 * DefaultOAuth2UserService를 상속하여 loadUser()를 재정의한다.
 *
 * 동작 흐름:
 *   1) Security가 인가코드 → access_token 교환 (자동)
 *   2) Security가 access_token으로 유저정보 요청 → OAuth2User 반환 (자동, super.loadUser())
 *   3) 이 클래스에서 OAuth2User의 attributes → UserEntity로 변환 (OAuthProvider enum)
 *   4) DB에 없으면 저장 (첫 로그인 = 자동 회원가입)
 *   5) CustomUserAccount(SessionUserDTO + attributes) 반환 → Security가 세션에 저장
 *
 * ※ security_oauth2 프로젝트와의 차이점 (프로젝트_구조_정리.md 원칙 적용):
 *   - security_oauth2: new CustomUserAccount(userEntity, attributes) → Entity 직접 보유
 *   - 이 프로젝트: CustomUserAccount.of(entity, attributes) → 내부에서 SessionUserDTO로 변환
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1) Security가 user-info-uri에서 유저정보를 가져옴 (access_token 사용)
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2) registrationId로 어떤 Provider인지 확인 ("google", "kakao")
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 3) Provider별 attributes → UserEntity 변환 (OAuthProvider enum)
        OAuthProvider currentProvider = OAuthProvider.from(registrationId);
        UserEntity userEntity = currentProvider.toUserEntity(attributes);

        // 4) DB에 없으면 저장 (첫 OAuth2 로그인 = 자동 회원가입)
        UserEntity existingUser = userRepository.findByUsername(userEntity.getUsername()).orElse(null);
        if (existingUser == null) {
            userRepository.save(userEntity);
            existingUser = userEntity;
        }

        // 5) CustomUserAccount 반환 — Entity가 아닌 SessionUserDTO를 내부에서 사용
        //    security_oauth2: new CustomUserAccount(userEntity, attributes)  → Entity 직접 보유
        //    이 프로젝트: CustomUserAccount.of(entity, attributes) → SessionUserDTO로 변환
        return CustomUserAccount.of(existingUser, attributes);
    }
}

