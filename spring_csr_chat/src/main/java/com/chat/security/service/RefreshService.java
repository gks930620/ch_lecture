package com.chat.security.service;


import com.chat.security.JwtUtil;
import com.chat.security.entity.RefreshEntity;
import com.chat.security.repository.RefreshRepository;
import com.chat.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshService {
    private  final RefreshRepository refreshRepository;
    private  final UserRepository userRepository;
    private  final JwtUtil jwtUtil;


    // 토큰이 DB에 존재하는지 확인
    @Transactional(readOnly = true)
    public boolean existsByToken(String token){
        return refreshRepository.findByToken(token) != null;
    }

    /**
     * Refresh Token 저장 (토큰에서 username 추출 → UserEntity 조회 → 저장)
     */
    @Transactional
    public void saveRefresh(String token){
        RefreshEntity refreshEntity = new RefreshEntity();
        String username = jwtUtil.extractUsername(token);
        refreshEntity.setUserEntity(userRepository.findByUsername(username));
        refreshEntity.setToken(token);
        refreshRepository.save(refreshEntity);
    }

    @Transactional
    public void deleteRefresh(String token){
        refreshRepository.deleteByToken(token);
    }
}
