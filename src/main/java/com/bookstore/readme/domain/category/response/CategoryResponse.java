package com.bookstore.readme.domain.category.response;

import com.bookstore.readme.common.response.CommonResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryResponse extends CommonResponse {
    private final Object data;

    @Builder
    public CategoryResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

    public static CategoryResponse ok(Object data) {
        return CategoryResponse
                .builder()
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }
}
