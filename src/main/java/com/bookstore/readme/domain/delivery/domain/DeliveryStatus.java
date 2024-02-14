package com.bookstore.readme.domain.delivery.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

    READY("배송 준비중"),
    DELIVERING("배송 중"),
    COMPLETE("배송 완료");

    private final String status;

}
