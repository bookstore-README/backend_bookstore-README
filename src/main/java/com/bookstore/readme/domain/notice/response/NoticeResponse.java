package com.bookstore.readme.domain.notice.response;

import com.bookstore.readme.common.response.CommonResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeResponse extends CommonResponse {

    private final Object data;

    @Builder
    public NoticeResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

    public static NoticeResponse ok(Object data) {
        return NoticeResponse
                .builder()
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }

    public static NoticeResponse empty(Object data) {
        return NoticeResponse
                .builder()
                .status(204)
                .message("Success")
                .data(data)
                .build();
    }

}
