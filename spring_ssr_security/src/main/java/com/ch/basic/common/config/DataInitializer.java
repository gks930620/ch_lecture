package com.ch.basic.common.config;

import com.ch.basic.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 초기 데이터 비밀번호 인코딩
 *
 * data-users.sql에서 평문(test1234)으로 INSERT된 비밀번호를
 * 서버 시작 시 BCrypt로 자동 인코딩한다.
 *
 * 실행 순서:
 *   1) JPA 테이블 생성 (ddl-auto: create)
 *   2) data-users.sql 실행 (평문 비밀번호로 INSERT)
 *   3) ApplicationContext 로딩 완료
 *   4) CommandLineRunner.run() 실행 ← 여기서 비밀번호 인코딩
 *
 * ※ 개발/학습 편의용. 운영 환경에서는 처음부터 인코딩된 비밀번호를 저장해야 함.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        userRepository.findAll().forEach(user -> {
            // BCrypt 해시는 "$2a$"로 시작 → 이미 인코딩된 비밀번호는 스킵
            if (!user.getPassword().startsWith("$2a$")) {
                String encoded = passwordEncoder.encode(user.getPassword());
                user.updatePassword(encoded);
                userRepository.save(user);
                log.info("비밀번호 BCrypt 인코딩 완료: {}", user.getUsername());
            }
        });
    }
}

