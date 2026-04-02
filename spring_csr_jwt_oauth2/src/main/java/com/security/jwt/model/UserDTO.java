package com.security.jwt.model;

import com.security.jwt.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private final String username;
    private final String password;
    private final String email;
    private final String nickname;
    private final String provider;
    private final String roles;    // "USER" or "USER,ADMIN"

    //Entity → DTO  변환메소드는 DTO측에서
    public static UserDTO from(UserEntity entity) {
        return UserDTO.builder()
            .username(entity.getUsername())
            .password(entity.getPassword())
            .email(entity.getEmail())
            .nickname(entity.getNickname())
            .provider(entity.getProvider())
            .roles(entity.getRoles())
            .build();
    }
}

