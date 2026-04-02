package com.ch.basic.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 관련 Service
 *
 * ※ Spring Security 적용 후 변경사항:
 *   - login() 메서드 제거 → Security가 로그인 인증을 자동 처리 (CustomUserDetailsService 사용)
 *   - signup()에서 BCryptPasswordEncoder로 비밀번호 암호화하여 DB 저장
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // SecurityConfig에서 등록한 BCryptPasswordEncoder Bean

    /**
     * 회원 가입
     * 비밀번호를 BCrypt로 인코딩하여 DB에 저장
     *
     * @throws IllegalArgumentException 중복된 아이디가 존재할 경우
     */
    @Transactional
    public void signup(String username, String password, String email, String nickname) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        UserEntity user = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))  // BCrypt 인코딩
                .email(email)
                .nickname(nickname)
                .build();
        userRepository.save(user);
    }

    /**
     * ID 중복 체크 (필요 시 사용)
     */
    public boolean checkUsernameDuplicate(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
