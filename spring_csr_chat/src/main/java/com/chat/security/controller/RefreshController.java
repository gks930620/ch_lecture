package com.chat.security.controller;

import com.chat.security.JwtUtil;
import com.chat.security.service.RefreshService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/refresh")
@RequiredArgsConstructor
public class RefreshController {

    private final JwtUtil jwtUtil;
    private final RefreshService refreshService;

    @PostMapping("/reissue")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("Authorization") String refreshToken) {
        //refresh토큰은 Authroization 헤더에..
        String token = refreshToken.replace("Bearer ", "");




        //폐기된 토큰(로그아웃)인지도 검증 = DB에 있냐 없냐 !     ( 클라이언트가 폐기된 토큰을 사용할 수도 있음. 탈취됐을 수도 있고..)
        if(!refreshService.existsByToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error","Refresh Token discarded"));
        }

        // Refresh Token 만료 검증 (삭제 전에 먼저 검증)
        if (!jwtUtil.validateToken(token)) {
            refreshService.deleteRefresh(token);  //만료된 토큰도 DB에서 정리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error","Refresh Token expired"));
        }

        //기존 refresh 토큰 삭제 (Rotation)
        refreshService.deleteRefresh(token);

        //만료 안되었을 때 ,  폐기된 적도 없을 때..

        String username = jwtUtil.extractUsername(token);

        String newAccessToken = jwtUtil.createAccessToken(username);
        String newRefreshToken=jwtUtil.createRefreshToken(username);

        refreshService.saveRefresh(newRefreshToken);  //refresh토큰은 저장
        return ResponseEntity.ok(Map.of("access_token", newAccessToken , "refresh_token",newRefreshToken) );

    }
}
