package com.ch.basic;

import com.ch.basic.user.CustomUserAccount;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 메인 홈페이지 컨트롤러
 *
 * @Controller: Spring이 이 클래스를 컨트롤러 Bean으로 등록
 *              반환값(String)을 ViewResolver가 HTML 템플릿 이름으로 해석함
 *              (cf. @RestController는 반환값을 JSON으로 변환)
 */
@Controller
public class HomeController {

    // GET / 요청 시 실행 (브라우저에서 http://localhost:8080/ 접속)
    // 비로그인 시 userDetails는 null → model에 담지 않음
    // 로그인 시 userDetails 존재 → model에 담아서 Thymeleaf에서 ${user.nickname} 등으로 사용
    @GetMapping("/")
    public String home(@AuthenticationPrincipal CustomUserAccount userDetails, Model model) {
        if (userDetails != null) {
            model.addAttribute("user", userDetails);
        }
        // "index" → templates/index.html 을 찾아서 렌더링 (Thymeleaf ViewResolver)
        return "index";
    }
}
