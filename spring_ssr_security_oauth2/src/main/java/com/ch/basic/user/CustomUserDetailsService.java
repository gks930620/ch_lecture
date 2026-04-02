package com.ch.basic.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security의 UserDetailsService 구현체
 *
 * Security가 로그인 시 자동으로 호출하는 서비스.
 * 개발자가 직접 호출하지 않음 — Security가 내부적으로 호출함.
 *
 * 동작 흐름:
 *   1) 사용자가 username/password 입력 후 로그인 버튼 클릭
 *   2) Security가 /loginProc 요청을 가로챔 (UsernamePasswordAuthenticationFilter)
 *   3) 이 클래스의 loadUserByUsername(username) 자동 호출
 *   4) DB에서 사용자 조회 → CustomUserAccount로 감싸서 반환
 *   5) Security가 반환된 UserDetails의 password와 입력된 password를 BCrypt로 비교
 *   6) 일치 → 로그인 성공 (세션에 Authentication 저장)
 *   7) 불일치 → 로그인 실패 (/login?error 리다이렉트)
 *
 * 개발자는 DB 조회만 하면 됨. 비밀번호 비교, 세션 저장은 Security가 자동 처리.
 *
 * @Service: Bean 등록 → UserDetailsService 구현체가 1개이므로 Security가 자동으로 사용
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Security가 로그인 시 자동 호출
     * username으로 DB에서 사용자 조회 후 UserDetails(CustomUserAccount)로 반환
     *
     * @param username 사용자가 로그인 폼에 입력한 아이디 (name="username")
     * @return UserDetails — Security가 이 객체의 password와 입력 password를 비교
     * @throws UsernameNotFoundException 사용자를 찾을 수 없을 때 → 로그인 실패 처리
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        return CustomUserAccount.from(userEntity);  // Entity → CustomUserAccount 변환 (필드값만 복사)
    }
}

