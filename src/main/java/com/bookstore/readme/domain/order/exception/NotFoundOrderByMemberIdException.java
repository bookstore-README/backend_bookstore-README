package com.bookstore.readme.domain.order.exception;

import lombok.Getter;

@Getter
public class NotFoundOrderByMemberIdException extends OrderException {
    private final Long memberId;

    public NotFoundOrderByMemberIdException(Long memberId) {
        super(OrderStatus.NOT_FOUND_ORDER_BY_MEMBER_ID);
        this.memberId = memberId;
    }
}
