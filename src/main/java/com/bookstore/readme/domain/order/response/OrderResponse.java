package com.bookstore.readme.domain.order.response;

import com.bookstore.readme.common.response.CommonResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponse extends CommonResponse {

    private final Object data;

    @Builder
    public OrderResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

    public static OrderResponse ok(Object order) {
        return new OrderResponse(200, "Success", order);
    }
}
