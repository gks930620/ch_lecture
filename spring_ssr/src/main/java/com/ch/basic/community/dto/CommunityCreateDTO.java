package com.ch.basic.community.dto;

import com.ch.basic.community.CommunityEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 게시글 작성 요청 DTO
 *
 * 클라이언트 → 서버로 데이터를 받을 때 사용
 * toEntity(): DTO → Entity 변환 메서드 (DTO 측에서 변환 담당)
 *
 * @NotBlank, @Size: Validation 어노테이션 (현재 Controller에서 직접 @RequestParam으로 받아서 미사용)
 */
@Getter
@Setter  // form 바인딩 시 Setter 필요 (Spring이 setter로 값을 넣어줌)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityCreateDTO {

    @NotBlank(message = "제목은 필수입니다")
    @Size(max = 200, message = "제목은 200자 이하여야 합니다")
    private String title;

    @NotBlank(message = "내용은 필수입니다")
    private String content;

    /**
     * DTO → Entity 변환
     *
     * @param username 작성자 로그인 ID (세션에서 가져옴)
     * @param nickname 작성자 닉네임 (세션에서 가져옴)
     * @return CommunityEntity — JPA가 DB에 저장할 객체
     */
    public CommunityEntity toEntity(Long userId, String username, String nickname) {
        return CommunityEntity.builder()
                .userId(userId)
                .username(username)
                .nickname(nickname)
                .title(this.title)
                .content(this.content)
                .viewCount(0)       // 초기 조회수 0
                .build();
        // ※ id, createdAt, updatedAt은 JPA가 자동 생성 (@GeneratedValue, @PrePersist)
    }
}
