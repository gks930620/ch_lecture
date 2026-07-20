package com.test.test.community.dto;

import com.test.test.community.CommunityEntity;
import com.test.test.jwt.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityCreateDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be at most 200 characters")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    /**
     * DTO -> Entity 변환
     */
    public CommunityEntity toEntity(UserEntity user) {
        return CommunityEntity.builder()
                .user(user)
                .title(this.title)
                .content(this.content)
                .viewCount(0)
                .isDeleted(false)
                .build();
    }
}

