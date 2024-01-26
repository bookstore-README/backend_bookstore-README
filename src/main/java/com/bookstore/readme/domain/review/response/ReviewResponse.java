package com.bookstore.readme.domain.review.response;

import com.bookstore.readme.common.response.CommonResponse;
import lombok.Builder;
import lombok.Getter;


@Getter
public class ReviewResponse extends CommonResponse {
    private final Object data;

    @Builder
    public ReviewResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

    public static ReviewResponse ok(Object data) {
        return ReviewResponse.builder()
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }
}
