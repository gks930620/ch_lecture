package com.ch.basic.user;

import com.ch.basic.user.dto.SessionUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Spring Security의 UserDetails + OAuth2User 통합 구현체
 *
 * OAuth2 로그인과 일반 폼 로그인에서 같은 타입을 사용하기 위해
 * UserDetails와 OAuth2User를 동시에 구현한다.
 * 따로 구현하면 Controller에서 instanceof로 분기해야 하므로 통합이 편리하다.
 *
 * SessionUserDTO를 재사용하여 중복 제거 (composition 패턴)
 *   - SessionUserDTO: id, username, email, nickname, roles, provider (세션 저장용)
 *   - CustomUserAccount: SessionUserDTO + password + attributes (Security 인증 정보)
 *
 * ※ security_oauth2 프로젝트와의 차이점 (프로젝트_구조_정리.md 원칙 적용):
 *   - security_oauth2: Entity를 직접 세션에 저장 (CustomUserAccount가 UserEntity 직접 보유)
 *   - 이 프로젝트: SessionUserDTO를 composition으로 사용 (Entity 세션 저장 금지 원칙)
 */
public class CustomUserAccount implements UserDetails, OAuth2User {

    private final SessionUserDTO sessionUser;          // 세션 사용자 정보 (id, username, email, nickname, roles, provider)
    private final String password;                      // Security 인증용 (UserDetails.getPassword() 구현 필요)
    private final Map<String, Object> attributes;       // OAuth2 전용 (폼 로그인 시 null)

    /**
     * 일반 폼 로그인용 — Entity → CustomUserAccount 변환
     * SessionUserDTO.from()을 재사용하여 변환, password만 추가, attributes는 null
     */
    public static CustomUserAccount from(UserEntity entity) {
        return new CustomUserAccount(
                SessionUserDTO.from(entity),
                entity.getPassword(),
                null
        );
    }

    /**
     * OAuth2 로그인용 — Entity + OAuth2 attributes → CustomUserAccount 변환
     * SessionUserDTO.from()을 재사용하여 변환, password + attributes 추가
     */
    public static CustomUserAccount of(UserEntity entity, Map<String, Object> attributes) {
        return new CustomUserAccount(
                SessionUserDTO.from(entity),
                entity.getPassword(),
                attributes
        );
    }

    private CustomUserAccount(SessionUserDTO sessionUser, String password, Map<String, Object> attributes) {
        this.sessionUser = sessionUser;
        this.password = password;
        this.attributes = attributes;
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
     * 사용자 권한 목록 반환 — UserDetails + OAuth2User 공통
     * SecurityConfig의 hasAuthority("ADMIN")은 이 컬렉션에서 확인
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        for (String role : sessionUser.getRoles()) {
            collection.add(() -> role);
        }
        return collection;
    }

    // ─── OAuth2User 인터페이스 구현 ───

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * OAuth2User는 사용자를 getName()으로 식별하지만,
     * 통합 User 객체를 사용하기 때문에 UserDetails의 getUsername()과 맞춤
     */
    @Override
    public String getName() {
        return getUsername();
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

    public String getProvider() {
        return sessionUser.getProvider();
    }

    /**
     * SessionUserDTO 자체를 반환하는 메서드
     * 향후 SessionUserDTO를 직접 사용해야 하는 코드와의 호환을 위해
     */
    public SessionUserDTO getSessionUser() {
        return sessionUser;
    }
}
