package com.ch.basic.user;

import com.ch.basic.user.dto.SessionUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Spring Security의 UserDetails 구현체
 *
 * SessionUserDTO를 재사용하여 중복 제거 (composition 패턴)
 *   - SessionUserDTO: id, username, email, nickname, roles (세션 저장용 사용자 정보)
 *   - CustomUserAccount: SessionUserDTO + password (Security 인증 정보)
 *
 * ※ SessionUserDTO에 password가 없는 이유: 세션에 비밀번호 저장 금지 (보안)
 *    Security에서는 Authentication 내부에서 password를 관리하므로 여기서만 추가
 *
 * Entity → DTO 변환을 DTO 측에서 하는 프로젝트 원칙 준수:
 *   SessionUserDTO.from(entity) 재사용 + password 추가
 */
public class CustomUserAccount implements UserDetails {

    private final SessionUserDTO sessionUser;  // 세션 사용자 정보 (id, username, email, nickname, roles)
    private final String password;              // Security 인증용 (UserDetails.getPassword() 구현 필요)

    /**
     * Entity → CustomUserAccount 변환 (정적 팩토리 메서드)
     * SessionUserDTO.from()을 재사용하여 변환, password만 추가
     */
    public static CustomUserAccount from(UserEntity entity) {
        return new CustomUserAccount(
                SessionUserDTO.from(entity),
                entity.getPassword()
        );
    }

    private CustomUserAccount(SessionUserDTO sessionUser, String password) {
        this.sessionUser = sessionUser;
        this.password = password;
    }

    // ─── UserDetails 인터페이스 구현 ───

    @Override
    public String getUsername() {
        return sessionUser.getUsername();
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * 사용자 권한 목록 반환
     * Security는 권한을 Collection<GrantedAuthority>로 관리하므로
     * SessionUserDTO의 roles(List<String>)를 GrantedAuthority 컬렉션으로 변환
     *
     * SecurityConfig의 hasAuthority("ADMIN")은 이 컬렉션에 "ADMIN"이 포함되어 있는지 확인
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        for (String role : sessionUser.getRoles()) {
            collection.add(() -> role);
        }
        return collection;
    }

    // ─── 커스텀 getter (SessionUserDTO에 위임) ───
    // Controller에서 @AuthenticationPrincipal로 주입받아 model에 담은 뒤
    // Thymeleaf에서 ${user.nickname}, ${user.email} 등으로 사용

    public Long getId() {
        return sessionUser.getId();
    }

    public String getNickname() {
        return sessionUser.getNickname();
    }

    public String getEmail() {
        return sessionUser.getEmail();
    }

    public List<String> getRoles() {
        return sessionUser.getRoles();
    }

    /**
     * SessionUserDTO 자체를 반환하는 메서드
     * 향후 SessionUserDTO를 직접 사용해야 하는 코드와의 호환을 위해
     */
    public SessionUserDTO getSessionUser() {
        return sessionUser;
    }
}
