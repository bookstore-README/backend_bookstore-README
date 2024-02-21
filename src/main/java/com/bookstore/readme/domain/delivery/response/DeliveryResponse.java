package com.bookstore.readme.domain.delivery.response;

import com.bookstore.readme.common.response.CommonResponse;
import lombok.Builder;
import lombok.Getter;


@Getter
public class DeliveryResponse extends CommonResponse {

    private final Object data;

    @Builder
    public DeliveryResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

    public static DeliveryResponse ok(Object data) {
        return new DeliveryResponse(200, "Success", data);
    }
}
