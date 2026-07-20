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

    // /api/logout 은 SecurityConfig에서 .authenticated() 이므로 헤더 2개를 사용한다.
    //  - Authorization: Bearer {accessToken}  → 인증 통과용 (JwtAccessTokenCheckAndSaveUserInfoFilter)
    //  - RefreshToken: {refreshToken}          → 실제로 폐기(삭제)할 대상
    // reissue(/api/refresh/reissue)는 permitAll 이라 Authorization에 refresh를 넣어도 되지만,
    // logout은 authenticated 라 access로 인증하고 refresh는 별도 헤더로 받아야 정상 동작한다.
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("RefreshToken") String refreshToken) {
        refreshService.deleteRefresh(refreshToken);
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

}
