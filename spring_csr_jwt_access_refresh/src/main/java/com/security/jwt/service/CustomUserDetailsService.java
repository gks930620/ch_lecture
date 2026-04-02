package com.security.jwt.service;

import com.security.jwt.entity.UserEntity;
import com.security.jwt.repository.UserRepository;
import com.security.jwt.model.CustomUserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUsername(username);
        if (userData != null) {
            return CustomUserAccount.from(userData);
        }
        throw new UsernameNotFoundException(username+"에 대한 회원정보가 없습니다.");
    }
}