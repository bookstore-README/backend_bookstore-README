package com.bookstore.readme.domain.review.request;

import com.bookstore.readme.domain.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReviewSaveRequest extends ReviewRequest {

    @NotNull(message = "책 아이디는 필수 입니다.")
    @Schema(example = "1")
    private Long bookId;

    private Double reviewRating;

    public ReviewSaveRequest(String content, Long bookId, Double reviewRating) {
        super(content);
        this.bookId = bookId;
        this.reviewRating = reviewRating;
    }

    public static Review toReview(ReviewSaveRequest request) {
        return Review.builder()
                .content(request.getContent())
                .reviewRating(request.getReviewRating())
                .build();
    }
}
