package com.ch.basic.user;

import com.ch.basic.user.dto.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 관련 Controller — 로그인, 로그아웃, 회원가입, 마이페이지
 *
 * @Controller: Spring MVC 컨트롤러 (반환값 = 템플릿 이름)
 * @RequiredArgsConstructor: final 필드(userRepository)에 대한 생성자 자동 생성 → 의존성 주입
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    // UserService를 생성자 주입으로 받음 (Controller -> Service -> Repository 구조)
    private final UserService userService;

    /**
     * 로그인 폼 페이지
     *
     * @param error 로그인 실패 시 리다이렉트에서 전달되는 파라미터
     *              예: /login?error → error 파라미터가 존재하면 에러 메시지 표시
     */
    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error, Model model) {
        // error 파라미터가 있으면 → 로그인 실패 후 돌아온 것이므로 에러 메시지를 model에 담음
        if (error != null) model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
        // templates/login.html 렌더링
        return "login";
    }

    /**
     * 로그인 처리 (form submit → POST)
     *
     * @param username 사용자가 입력한 아이디 (form의 name="username")
     * @param password 사용자가 입력한 비밀번호 (form의 name="password")
     * @param session  HTTP 세션 — 로그인 성공 시 사용자 정보를 세션에 저장
     * @param model    로그인 실패 시 에러 메시지를 화면에 전달
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        // Service를 통해 로그인 처리 (반환값: DTO or null)
        LoginUserDTO loginUser = userService.login(username, password);

        // 로그인 실패 시
        if (loginUser == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login"; // 로그인 페이지 다시 렌더링 (에러 메시지 포함)
        }

        // 로그인 성공 → 세션에 저장
        // "loginUser"라는 key로 세션에 저장 → 다른 Controller에서 session.getAttribute("loginUser")로 꺼내 씀
        session.setAttribute("loginUser", loginUser);

        // 홈페이지로 리다이렉트 (redirect: → 새로운 GET 요청 발생. PRG 패턴)
        return "redirect:/";
    }

    /**
     * 로그아웃 처리
     * POST 방식 — GET으로 하면 URL 입력만으로 로그아웃되므로 보안상 POST 권장
     */
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // session.invalidate(): 현재 세션을 완전히 무효화 (세션에 저장된 모든 데이터 삭제)
        // → "loginUser" 포함 모든 세션 속성이 사라짐 = 로그아웃
        session.invalidate();
        return "redirect:/"; // 홈페이지로 리다이렉트
    }

    /** 회원가입 폼 페이지 */
    @GetMapping("/signup")
    public String signupForm() {
        return "signup"; // templates/signup.html 렌더링
    }

    /**
     * 회원가입 처리 (form submit → POST)
     *
     * @param username 아이디
     * @param password 비밀번호
     * @param email    이메일
     * @param nickname 닉네임
     */
    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String email,
                         @RequestParam String nickname,
                         Model model) {
        try {
            // Service를 통해 회원가입 처리 (중복 체크 및 저장)
            userService.signup(username, password, email, nickname);
        } catch (IllegalArgumentException e) {
            // 중복 아이디 등 예외 발생 시 에러 메시지 전달
            model.addAttribute("error", e.getMessage());
            return "signup"; // 회원가입 페이지로 다시 돌아감 (에러 메시지 포함)
        }


        // 회원가입 완료 후 로그인 페이지로 리다이렉트
        return "redirect:/login";
    }

    /**
     * 마이페이지
     * ※ 로그인 체크는 LoginCheckInterceptor에서 처리 (비로그인 시 /login으로 리다이렉트됨)
     *    따라서 이 메서드에 도달했다면 반드시 로그인 상태임
     */
    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        // 세션에서 로그인 사용자 정보(DTO) 꺼냄
        LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("loginUser");

        // ※ 아래 코드는 Interceptor 적용 전에 Controller에서 직접 로그인 체크하던 방식 (현재 제거됨)
        // if (loginUser == null) {
        //     return "redirect:/login";
        // }

        // model에 담아서 mypage.html에서 사용 (th:text="${user.nickname}" 등)
        model.addAttribute("user", loginUser);
        return "mypage"; // templates/mypage.html 렌더링
    }
}
