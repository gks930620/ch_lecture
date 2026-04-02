package com.ch.basic.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 페이징 응답 DTO — Page<>에서 화면에 필요한 필드만 추출
 *
 * Spring의 Page 객체는 pageable, sort, numberOfElements 등 불필요한 필드가 많아서
 * JSON 직렬화 시 불필요한 데이터가 덕지덕지 붙는다.
 * SSR(Thymeleaf)에서도 일관성을 위해 PageResponse로 통일하여 사용.
 *
 * @param <T> 목록의 각 항목 타입 (예: CommunityDTO, CommentDTO)
 */
@Getter
@Builder
@AllArgsConstructor
public class PageResponse<T> {

    private final List<T> content;       // 현재 페이지의 데이터 목록
    private final int number;            // 현재 페이지 번호 (0-based)
    private final int totalPages;        // 전체 페이지 수
    private final long totalElements;    // 전체 데이터 수
    private final boolean first;         // 첫 페이지 여부
    private final boolean last;          // 마지막 페이지 여부

    /**
     * Spring의 Page 객체에서 필요한 필드만 추출하여 PageResponse 생성
     *
     * @param page Spring Data의 Page 객체
     * @return PageResponse<T>
     */
    public static <T> PageResponse<T> from(Page<T> page) {
        return PageResponse.<T>builder()
                .content(page.getContent())
                .number(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}

