package com.bookstore.readme.domain.delivery.exception;

import lombok.Getter;

@Getter
public class NotFoundDeliveryByIdException extends DeliveryException {

    private final Long deliveryId;

    public NotFoundDeliveryByIdException(Long deliveryId) {
        super(DeliveryStatus.NOT_FOUND_DELIVERY_BY_ID);
        this.deliveryId = deliveryId;
    }
}
