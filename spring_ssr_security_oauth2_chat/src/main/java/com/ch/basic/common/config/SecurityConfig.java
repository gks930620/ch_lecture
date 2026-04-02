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
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/login", "/signup").permitAll()
                .requestMatchers("/ws-stomp/**").permitAll()   // SockJS 핸드셰이크 URL (WebSocket 보안은 미적용)
                .requestMatchers("/admin").hasAuthority("ADMIN")
                .requestMatchers("/mypage").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/chat/**").authenticated()   // 채팅은 로그인 필수
                .anyRequest().permitAll()
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
