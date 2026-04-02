package com.ch.basic.user;

import com.ch.basic.user.dto.LoginUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 가입
     * @throws IllegalArgumentException 중복된 아이디가 존재할 경우
     */
    @Transactional
    public void signup(String username, String password, String email, String nickname) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        UserEntity user = UserEntity.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();
        userRepository.save(user);
    }

    /**
     * 로그인 - ID/PW 검증
     * @return 로그인 성공 시 LoginUserDTO, 실패 시 null
     */
    public LoginUserDTO login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .map(LoginUserDTO::from)
                .orElse(null);
    }

    /**
     * ID 중복 체크 (필요 시 사용)
     */
    public boolean checkUsernameDuplicate(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
