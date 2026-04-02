package com.chat.security.controller;


//Login은 /login  => JwtLoginFilter에서 처리


import com.chat.security.service.RefreshService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LogoutController {
    private final RefreshService refreshService;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("RefreshToken") String refreshToken) {
        refreshService.deleteRefresh(refreshToken);
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

}
