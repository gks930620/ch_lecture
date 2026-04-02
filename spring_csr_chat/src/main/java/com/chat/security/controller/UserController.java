package com.chat.security.controller;

import com.chat.security.model.CustomUserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/me")
    public Map<String, Object> getMyInfo(@AuthenticationPrincipal CustomUserAccount customUserAccount) {
        if (customUserAccount == null) {  //사실 security에서 잘 처리할거임.
            return Map.of("error", "인증되지 않은 사용자입니다.");
        }
        return Map.of(
            "username", customUserAccount.getUsername(),
            "roles", customUserAccount.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList()
        );
    }
}
