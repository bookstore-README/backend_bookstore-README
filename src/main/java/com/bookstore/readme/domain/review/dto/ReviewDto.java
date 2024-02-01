package com.bookstore.readme.domain.review.dto;

import com.bookstore.readme.domain.review.domain.Review;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
public class ReviewDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ReviewDto(Long id, String title, String content, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public static ReviewDto of(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .createDate(review.getCreateDate())
                .updateDate(review.getUpdateDate())
                .content(review.getContent())
                .build();
    }
}
