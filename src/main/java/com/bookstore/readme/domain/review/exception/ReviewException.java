package com.bookstore.readme.domain.review.exception;

public class ReviewException extends RuntimeException {
    private final ReviewStatus reviewStatus;

    public ReviewException(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public int getStatus() {
        return reviewStatus.getStatus();
    }

    public String getMessage() {
        return reviewStatus.getMessage();
    }
}
