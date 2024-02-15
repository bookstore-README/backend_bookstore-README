package com.bookstore.readme.domain.order.exception;

import lombok.Getter;

@Getter
public enum OrderStatus {

    ORDER_SAVE_ERROR(400, "주문 등록 에러가 발생했습니다."),
    NOT_FOUND_ORDER_BY_ID(400, "일치한 주문 번호가 존재하지 않습니다.");

    private final int status;
    private final String message;

    OrderStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
