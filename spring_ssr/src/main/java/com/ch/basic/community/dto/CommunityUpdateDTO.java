package com.ch.basic.community.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 게시글 수정 요청 DTO
 *
 * 수정 시에는 title, content만 변경 가능
 * (작성자, 생성일시 등은 변경 불가)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityUpdateDTO {

    @NotBlank(message = "제목은 필수입니다")
    @Size(max = 200, message = "제목은 200자 이하여야 합니다")
    private String title;

    @NotBlank(message = "내용은 필수입니다")
    private String content;

    // ※ toEntity() 없음 — 수정은 기존 Entity를 조회한 후 entity.update(title, content) 호출
}
