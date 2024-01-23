package com.bookstore.readme.domain.collection.response;

import com.bookstore.readme.common.response.CommonResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AladdinResponse extends CommonResponse {
    private final Object request;
    private final Object data;

    @Builder
    public AladdinResponse(int status, String message, Object data, Object request) {
        super(status, message);
        this.data = data;
        this.request = request;
    }
}
