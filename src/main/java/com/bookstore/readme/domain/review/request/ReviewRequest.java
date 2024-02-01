package com.bookstore.readme.domain.review.request;

import com.bookstore.readme.domain.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReviewRequest {
    @NotEmpty(message = "리뷰 제목은 필수입니다.")
    @Schema(example = "리뷰 제목")
    private String title;

    @NotEmpty(message = "리뷰 글은 필수입니다.")
    @Size(max = 255, message = "리뷰의 최대 글자 수는 255자입니다.")
    @Schema(example = "리뷰 내용")
    private String content;

    public ReviewRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Review toReview(ReviewRequest request) {
        return Review.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }
}
