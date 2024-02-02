package com.bookstore.readme.domain.review.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReviewUpdateRequest extends ReviewRequest {

    @NotNull(message = "수정할 리뷰 아이디는 필수 입니다.")
    @Schema(example = "1")
    private final Long reviewId;

    public ReviewUpdateRequest(String title, String content, Long reviewId) {
        super(title, content);
        this.reviewId = reviewId;
    }
}
