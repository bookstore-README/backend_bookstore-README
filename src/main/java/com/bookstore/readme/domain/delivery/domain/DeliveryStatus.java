package com.bookstore.readme.domain.delivery.domain;

import com.bookstore.readme.domain.delivery.exception.NotEqualDeliveryStatusException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Locale.ENGLISH;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

    READY("배송 준비중"),
    DELIVERING("배송 중"),
    COMPLETE("배송 완료"),
    EXCHANGE_AND_REFUND("교환/환불"),
    CONFIRM("구매확정"),
    CANCEL("배송 취소"),
    ALL("전체");

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

        throw new NotEqualDeliveryStatusException(status);
    }
}
