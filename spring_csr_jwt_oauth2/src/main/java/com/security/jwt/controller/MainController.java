package com.security.jwt.controller;


import com.security.jwt.model.CustomUserAccount;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {

    //로그인 후
    @GetMapping("/my/info")
    public ResponseEntity<?> myInfo(@AuthenticationPrincipal CustomUserAccount customUserAccount) {
        Map<String, Object> info = Map.of(
            "username", customUserAccount.getUsername(),
            "email", customUserAccount.getEmail() != null ? customUserAccount.getEmail() : "",
            "nickname", customUserAccount.getNickname() != null ? customUserAccount.getNickname() : "",
            "roles", customUserAccount.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList()
        );
        return ResponseEntity.ok(info);
    }
}
