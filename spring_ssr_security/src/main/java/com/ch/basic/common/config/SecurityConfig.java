package com.ch.basic.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정 클래스
 *
 * @Configuration: Spring 설정 파일
 * @EnableWebSecurity: Security 보안 설정 활성화 (Boot 3에선 생략 가능하지만 명시 권장)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 비밀번호 인코더 Bean 등록
     * BCrypt: 단방향 해시 함수 — 같은 비밀번호도 매번 다른 해시값 생성 (salt 내장)
     * 회원가입 시 encode(), 로그인 시 matches()를 Security가 자동 호출
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Security 필터 체인 설정
     * HTTP 요청에 대한 보안 정책을 정의
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // ── URL별 접근 권한 설정 ──
        // 순서대로 매칭 — 먼저 매칭된 규칙이 적용됨
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/login", "/signup").permitAll()           // 비로그인 허용
                .requestMatchers("/admin").hasAuthority("ADMIN")                // ADMIN 권한만 접근
                .requestMatchers("/mypage").hasAnyAuthority("ADMIN", "USER")    // ADMIN 또는 USER
                .anyRequest().permitAll()                                        // 나머지는 허용
        );

        // ── 로그인 설정 ──
        http.formLogin((auth) -> auth
                .loginPage("/login")              // 로그인 폼 페이지 URL (GET /login → Controller 필요)
                .loginProcessingUrl("/loginProc")  // form action URL (Controller 불필요 — Security가 자동 처리)
                .defaultSuccessUrl("/")            // 로그인 성공 후 리다이렉트
                .failureUrl("/login?error")        // 로그인 실패 후 리다이렉트
                .permitAll()                       // 로그인 관련 URL은 비로그인 상태에서도 접근 가능
        );

        // ── 로그아웃 설정 ──
        http.logout((auth) -> auth
                .logoutUrl("/logout")         // 로그아웃 요청 URL (Controller 불필요 — Security가 자동 처리)
                .logoutSuccessUrl("/")        // 로그아웃 성공 후 리다이렉트
        );

        // CSRF 비활성화 (학습용 — 운영 환경에서는 활성화 권장)
        http.csrf((auth) -> auth.disable());

        return http.build();
    }
}

