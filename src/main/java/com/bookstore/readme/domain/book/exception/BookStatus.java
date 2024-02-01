package com.bookstore.readme.domain.book.exception;

import lombok.Getter;

@Getter
public enum BookStatus {
    NOT_FOUND_BOOK_BY_ID(400, "존재하지 않는 상품 아이디입니다.");

    private final Integer status;
    private final String message;

    BookStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
