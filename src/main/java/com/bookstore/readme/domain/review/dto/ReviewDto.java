package com.bookstore.readme.domain.review.dto;

import com.bookstore.readme.domain.review.domain.Review;
import lombok.Builder;
import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private String title;
    private String content;

    @Builder
    public ReviewDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static ReviewDto toReviewDto(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .build();
    }
}
