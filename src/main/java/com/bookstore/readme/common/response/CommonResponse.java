package com.bookstore.readme.common.response;

import lombok.Getter;

@Getter
public class CommonResponse {
    private final int status;
    private final String message;

    public CommonResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
