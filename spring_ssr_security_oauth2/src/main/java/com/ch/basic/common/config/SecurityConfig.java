package com.ch.basic.common.config;

import com.ch.basic.user.CustomOAuth2UserService;
import com.ch.basic.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * ※ 잘 되는 참조 코드(security_oauth2 프로젝트) 기반으로 정리
 *    이 프로젝트 URL에 맞게 설정 + OAuth2 에러 로깅 추가
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // ── URL별 접근 권한 ──
        // ※ hasAuthority vs hasRole 차이 (초심자 함정):
        //   - hasAuthority("ADMIN") : 권한 문자열을 그대로 비교 → 이 프로젝트가 부여한 "ADMIN"과 일치
        //   - hasRole("ADMIN")      : 내부적으로 "ROLE_ADMIN"을 찾음 → 이 프로젝트 권한엔 ROLE_ 접두어가 없어 항상 403
        //   이 프로젝트는 접두어 없이 hasAuthority로 통일했다.
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/login", "/signup").permitAll()
                .requestMatchers("/admin").hasAuthority("ADMIN")
                .requestMatchers("/mypage").hasAnyAuthority("ADMIN", "USER")
                .anyRequest().permitAll()   // 명시하지 않은 나머지 URL 전부 허용
                // ※ 학습 편의로 나머지를 permitAll 했지만, 실무에서는 anyRequest().authenticated()로
                //   기본 차단(화이트리스트 방식)한 뒤 열어줄 경로만 permitAll 하는 것이 안전하다.
        );

        // ── 일반 폼 로그인 ──
        http.formLogin(auth -> auth
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")   // form action URL (Controller 불필요 — Security가 처리)
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .permitAll()
        );

        // ── 일반 폼 로그인 사용자 처리 ──
        http.userDetailsService(customUserDetailsService);

        // ── OAuth2 로그인 ──
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/login")        // 폼 로그인과 같은 페이지
                .defaultSuccessUrl("/")
                .failureHandler((request, response, exception) -> {
                    // ★ OAuth2 로그인 실패 시 콘솔에 에러 출력 (디버깅용)
                    log.error("★ OAuth2 로그인 실패: {}", exception.getMessage(), exception);
                    response.sendRedirect("/login?error=true");
                })
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(customOAuth2UserService)   // OAuth2 사용자 로그인 처리
                )
        );

        // ── 로그아웃 ──
        http.logout((auth) -> auth
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );

        // CSRF 비활성화 (학습용)
        http.csrf((auth) -> auth.disable());

        return http.build();
    }
}
