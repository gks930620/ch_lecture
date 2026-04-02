 package com.ch.basic.community.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 댓글 수정 요청 DTO
 * JavaScript fetch에서 JSON body로 전달받음 (content만)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentUpdateDTO {

    @NotBlank(message = "댓글 내용은 필수입니다")
    private String content;
}
