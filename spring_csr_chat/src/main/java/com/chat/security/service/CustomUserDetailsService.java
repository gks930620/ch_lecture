package com.chat.security.service;

import com.chat.security.entity.UserEntity;
import com.chat.security.repository.UserRepository;
import com.chat.security.model.CustomUserAccount;
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
            return new CustomUserAccount(userData);
        }
        throw new UsernameNotFoundException(username+"에 대한 회원정보가 없습니다.");
    }
}