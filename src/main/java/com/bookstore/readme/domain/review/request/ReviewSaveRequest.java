package com.bookstore.readme.domain.review.request;

import com.bookstore.readme.domain.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReviewSaveRequest extends ReviewRequest {

    @NotNull(message = "회원 아이디는 필수 입니다.")
    @Schema(example = "1")
    private Long memberId;

    @NotNull(message = "책 아이디는 필수 입니다.")
    @Schema(example = "1")
    private Long bookId;

    private Double reviewRating;

    public ReviewSaveRequest(String title, String content, Long memberId, Long bookId, Double reviewRating) {
        super(title, content);
        this.memberId = memberId;
        this.bookId = bookId;
        this.reviewRating = reviewRating;
    }

    public static Review toReview(ReviewSaveRequest request) {
        return Review.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .reviewRating(request.getReviewRating())
                .build();
    }
}
