package com.security.jwt.service;

import com.security.jwt.entity.UserEntity;
import com.security.jwt.model.CustomUserAccount;
import com.security.jwt.model.OAuthProvider;
import com.security.jwt.model.UserDTO;
import com.security.jwt.repository.UserRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = false)
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 카카오로그인이든 구글로그인이든 폼로그인이든 똑같은 서비스를 제공하기 위해 DB 저장

        OAuth2User oAuth2User = super.loadUser(userRequest);//  user-info-uri에서 유저정보를 가져옴.

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //"kakao" or "google"

        Map<String, Object> attributes = oAuth2User.getAttributes();

        // ✅ OAuthProvider enum으로 provider별 분기 처리 (카카오/구글 등 각 provider마다 응답 구조가 다름)
        OAuthProvider provider = OAuthProvider.from(registrationId);

        //  DB에서 사용자 조회 (없으면 생성)
        String providerUsername = provider.extractUsername(attributes);  // "kakao123456" or "google123456"
        UserEntity user = userRepository.findByUsername(providerUsername);
        if (user == null) {  //해당 provider로 처음 로그인하는 분
            user = provider.toUserEntity(attributes, passwordEncoder);
            userRepository.save(user);
        }else{  //처음 로그인은 아님. provider에서 nickname 등 변경됐다면 우리 DB에도 반영해야지
            //username password는 안 바뀜.
            user.setEmail(provider.extractEmail(attributes));
            user.setNickname(provider.extractNickname(attributes));
        }

        //CustomUserAccount로 반환 (OAuth2User + UserDetails 통합)
        return new CustomUserAccount(UserDTO.from(user), attributes);  //Entity → DTO 변환 후 전달
    }
}