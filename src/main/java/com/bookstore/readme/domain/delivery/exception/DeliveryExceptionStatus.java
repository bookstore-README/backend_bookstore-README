package com.bookstore.readme.domain.delivery.exception;

import lombok.Getter;

@Getter
public enum DeliveryExceptionStatus {

    DELIVERY_SAVE_ERROR(400, "배송 등록 에러가 발생했습니다."),
    NOT_FOUND_DELIVERY_BY_ID(400, "일치한 배송 번호가 존재하지 않습니다."),
    NOT_FOUND_DELIVERY_BY_MEMBER_ID(400, "회원의 배송 목록 중 일치한 배송 번호가 존재하지 않습니다."),
    NOT_EQUAL_DELIVERY_STATUS(400, "해당하는 배송 상태값이 없습니다.");

    private final int status;
    private final String message;

    DeliveryExceptionStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
