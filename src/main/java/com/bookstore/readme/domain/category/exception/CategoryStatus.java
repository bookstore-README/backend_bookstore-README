package com.bookstore.readme.domain.category.exception;

import lombok.Getter;

@Getter
public enum CategoryStatus {
    DUPLICATE_CATEGORY_NAME(400, "중복된 카테고리 이름입니다."),
    NOT_FOUND_CATEGORY_ID(400, "존재 하지 않는 카테고리 아이디 입니다.");

    private final int status;
    private final String message;

    CategoryStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
