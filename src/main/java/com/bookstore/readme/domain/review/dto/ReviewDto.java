package com.bookstore.readme.domain.review.dto;

import com.bookstore.readme.domain.review.domain.Review;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class ReviewDto {
    private Long reviewId;
    private String title;
    private String content;
    private Double reviewRating;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static ReviewDto of(Review review) {
        return ReviewDto.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .reviewRating(review.getReviewRating())
                .createDate(review.getCreateDate())
                .updateDate(review.getUpdateDate())
                .content(review.getContent())
                .build();
    }
}
