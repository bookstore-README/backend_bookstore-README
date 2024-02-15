package com.bookstore.readme.domain.order.exception;

public class OrderException extends RuntimeException {

    private final OrderStatus orderStatus;

    public OrderException(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getStatus() {
        return orderStatus.getStatus();
    }

    public String getMessage() {
        return orderStatus.getMessage();
    }
}
