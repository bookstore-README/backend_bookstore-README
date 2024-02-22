package com.bookstore.readme.domain.delivery.exception;

import lombok.Getter;

@Getter
public class NotEqualDeliveryStatusException extends DeliveryException {

    private final String deliveryStatus;

    public NotEqualDeliveryStatusException(String deliveryStatus) {
        super(DeliveryExceptionStatus.NOT_EQUAL_DELIVERY_STATUS);
        this.deliveryStatus = deliveryStatus;
    }
}
