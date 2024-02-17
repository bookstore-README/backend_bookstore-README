package com.bookstore.readme.domain.delivery.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

    READY("배송 준비중"),
    DELIVERING("배송 중"),
    COMPLETE("배송 완료"),
    CANCEL("배송 취소");

    private final String status;

    public static DeliveryStatus of(String status) {
        if(status == null) {
            throw new IllegalArgumentException();
        }

        for(DeliveryStatus ds : DeliveryStatus.values()) {
            if(ds.name().equals(status.toUpperCase())) {
                return ds;
            }
        }

        throw new IllegalArgumentException("일치하는 배송 상태값이 없습니다.");
    }

}
