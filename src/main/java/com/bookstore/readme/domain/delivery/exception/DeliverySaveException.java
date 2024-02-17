package com.bookstore.readme.domain.delivery.exception;

import com.bookstore.readme.domain.order.exception.OrderException;
import com.bookstore.readme.domain.order.exception.OrderStatus;

public class DeliverySaveException extends DeliveryException {

    public DeliverySaveException() {
        super(DeliveryStatus.DELIVERY_SAVE_ERROR);
    }
}
