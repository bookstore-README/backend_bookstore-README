package com.bookstore.readme.domain.category.exception;

import lombok.Getter;

@Getter
public enum CategoryStatus {
    NOT_FOUND_CATEGORY_BY_ID(400, "존재하지 않는 카테고리 아이디입니다.");

    private final Integer status;
    private final String message;

    CategoryStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
