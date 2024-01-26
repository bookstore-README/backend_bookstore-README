package com.bookstore.readme.domain.book.response;

import com.bookstore.readme.common.response.CommonResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookResponse extends CommonResponse {
    private final Object data;

    @Builder
    public BookResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

    public static BookResponse ok(Object data) {
        return BookResponse
                .builder()
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }
}
