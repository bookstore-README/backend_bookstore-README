package com.bookstore.readme.domain.order.exception;

public class OrderSaveException extends OrderException {

    public OrderSaveException() {
        super(OrderStatus.ORDER_SAVE_ERROR);
    }
}
