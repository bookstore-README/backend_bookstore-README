package com.bookstore.readme.domain.delivery.exception;

public class DeliverySaveException extends DeliveryException {

    public DeliverySaveException() {
        super(DeliveryExceptionStatus.DELIVERY_SAVE_ERROR);
    }
}
