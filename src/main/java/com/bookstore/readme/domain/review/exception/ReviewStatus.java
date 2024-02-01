package com.bookstore.readme.domain.review.exception;

import lombok.Getter;

@Getter
public enum ReviewStatus {
    NOT_FOUND_REVIEW_BY_ID(400, "리뷰 아이디와 일치하는 정보가 없습니다.");

    private final int status;
    private final String message;

    ReviewStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
