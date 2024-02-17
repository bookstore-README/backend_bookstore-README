package com.bookstore.readme.domain.delivery.exception;

import lombok.Getter;

@Getter
public class NotFoundDeliveryByMemberIdException extends DeliveryException {
    private final Long memberId;

    public NotFoundDeliveryByMemberIdException(Long memberId) {
        super(DeliveryStatus.NOT_FOUND_DELIVERY_BY_MEMBER_ID);
        this.memberId = memberId;
    }
}
