package com.bookstore.readme.domain.delivery.exception;

public class DeliveryException extends RuntimeException {

    private final DeliveryExceptionStatus deliveryStatus;

    public DeliveryException(DeliveryExceptionStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getStatus() {
        return deliveryStatus.getStatus();
    }

    public String getMessage() {
        return deliveryStatus.getMessage();
    }
}
