package com.test.test.jwt.service;

import com.test.test.common.exception.DuplicateResourceException;
import com.test.test.jwt.entity.UserEntity;
import com.test.test.jwt.model.JoinDTO;
import com.test.test.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void joinProcess(JoinDTO joinDTO) {
        // 중복 체크: 이미 존재하면 예외 발생
        if (userRepository.existsByUsername(joinDTO.getUsername())) {
            throw new DuplicateResourceException("Username already in use: " + joinDTO.getUsername());
        }

        // 이메일 중복 체크 (선택)
        if (joinDTO.getEmail() != null && userRepository.existsByEmail(joinDTO.getEmail())) {
            throw new DuplicateResourceException("Email already in use: " + joinDTO.getEmail());
        }

        UserEntity user = new UserEntity();
        user.setUsername(joinDTO.getUsername());
        user.setPassword(passwordEncoder.encode(joinDTO.getPassword()));
        user.setEmail(joinDTO.getEmail());
        user.setNickname(joinDTO.getNickname());
        user.setProvider("LOCAL");
        user.getRoles().add("USER");

        userRepository.save(user);
    }
}