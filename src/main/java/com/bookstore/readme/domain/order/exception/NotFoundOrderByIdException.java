package com.bookstore.readme.domain.order.exception;

import com.bookstore.readme.domain.member.exception.MemberException;
import com.bookstore.readme.domain.member.exception.MemberStatus;
import lombok.Getter;

@Getter
public class NotFoundOrderByIdException extends OrderException {
    private final Long orderId;

    public NotFoundOrderByIdException(Long orderId) {
        super(OrderStatus.NOT_FOUND_ORDER_BY_ID);
        this.orderId = orderId;
    }
}
