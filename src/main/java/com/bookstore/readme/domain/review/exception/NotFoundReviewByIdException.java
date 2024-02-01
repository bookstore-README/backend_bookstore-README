package com.bookstore.readme.domain.review.exception;

import lombok.Getter;

@Getter
public class NotFoundReviewByIdException extends ReviewException {
    private final Long reviewId;

    public NotFoundReviewByIdException(Long reviewId) {
        super(ReviewStatus.NOT_FOUND_REVIEW_BY_ID);
        this.reviewId = reviewId;
    }
}
