package com.bookstore.readme.domain.delivery.exception;

public class DeliveryException extends RuntimeException {

    private final DeliveryStatus orderStatus;

    public DeliveryException(DeliveryStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getStatus() {
        return orderStatus.getStatus();
    }

    public String getMessage() {
        return orderStatus.getMessage();
    }
}
