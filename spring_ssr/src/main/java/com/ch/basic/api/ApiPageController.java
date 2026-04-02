package com.ch.basic.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * API 테스트 화면 컨트롤러
 * 
 * /api/test 접속 시 → templates/api.html 렌더링
 */
@Controller
public class ApiPageController {

    @GetMapping("/api/test")
    public String apiTestPage() {
        return "api";
    }
}

