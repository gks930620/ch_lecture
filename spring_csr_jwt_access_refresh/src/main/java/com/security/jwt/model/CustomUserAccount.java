package com.security.jwt.model;

import com.security.jwt.entity.UserEntity;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
public class CustomUserAccount implements UserDetails {

    private final String username;
    private final String password;
    private final List<String> roles;

    private CustomUserAccount(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    // Entity → DTO 변환.  roles split 처리
    public static CustomUserAccount from(UserEntity userEntity) {
        List<String> roleList = (userEntity.getRoles() == null || userEntity.getRoles().isBlank())
            ? Collections.emptyList()
            : Arrays.stream(userEntity.getRoles().split(","))
                .map(String::trim)
                .toList();

        return new CustomUserAccount(
            userEntity.getUsername(),
            userEntity.getPassword(),
            roleList
        );
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(SimpleGrantedAuthority::new)
            .toList();
    }
}
