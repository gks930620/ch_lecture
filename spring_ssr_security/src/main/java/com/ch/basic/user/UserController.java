package com.ch.basic.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 관련 Controller — 로그인 페이지, 회원가입, 마이페이지, 관리자 페이지
 *
 * ※ Spring Security 적용 후 변경사항:
 *   - POST /login (로그인 처리) → Security가 자동 처리 (Controller 불필요)
 *   - POST /logout (로그아웃 처리) → Security가 자동 처리 (Controller 불필요)
 *   - 세션에서 loginUser 꺼내기 → @AuthenticationPrincipal로 Security 인증 정보 주입
 *
 * @Controller: Spring MVC 컨트롤러 (반환값 = 템플릿 이름)
 * @RequiredArgsConstructor: final 필드(userService)에 대한 생성자 자동 생성 → 의존성 주입
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 로그인 폼 페이지
     *
     * ※ GET /login만 있으면 된다.
     *   POST /loginProc은 만들지 않는다 — 로그인 처리는 Security가 한다.
     *
     * @param error 로그인 실패 시 Security가 리다이렉트하면서 전달하는 파라미터
     *              SecurityConfig: failureUrl("/login?error")
     */
    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return "login";
    }

    // ※ POST /login, POST /logout은 Security가 자동 처리하므로 Controller 메서드 없음
    //    - loginProcessingUrl("/loginProc") → Security가 /loginProc POST를 가로채서 인증 처리
    //    - logoutUrl("/logout") → Security가 /logout POST를 가로채서 세션 무효화

    /** 회원가입 폼 페이지 */
    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    /**
     * 회원가입 처리 (form submit → POST)
     * Service에서 BCrypt 인코딩하여 DB에 저장
     */
    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String email,
                         @RequestParam String nickname,
                         Model model) {
        try {
            userService.signup(username, password, email, nickname);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
        return "redirect:/login";
    }

    /**
     * 마이페이지 — 로그인 사용자 정보 표시
     *
     * @AuthenticationPrincipal: Security가 세션에 저장한 Authentication의 Principal을 주입
     *   Principal = CustomUserDetailsService에서 반환한 CustomUserAccount
     *
     * 접근 제한: SecurityConfig에서 /mypage → hasAnyAuthority("ADMIN","USER")
     *   → 비로그인 시 /login으로 자동 리다이렉트
     */
    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal CustomUserAccount userDetails, Model model) {
        model.addAttribute("user", userDetails);
        return "mypage";
    }

    /**
     * 관리자 페이지 — ADMIN 권한만 접근 가능
     *
     * 접근 제한: SecurityConfig에서 /admin → hasAuthority("ADMIN")
     *   → 비로그인 시 /login으로 리다이렉트
     *   → 로그인했지만 ADMIN이 아닌 경우 403 Forbidden
     */
    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal CustomUserAccount userDetails, Model model) {
        model.addAttribute("user", userDetails);
        return "admin";
    }
}
