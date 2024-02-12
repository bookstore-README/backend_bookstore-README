package com.bookstore.readme.domain.basket.response;

import com.bookstore.readme.common.response.CommonResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BasketResponse extends CommonResponse {
    private final Object data;

    @Builder
    public BasketResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

    public static BasketResponse of(Object data) {
        return BasketResponse.builder()
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }
}
