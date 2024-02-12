package com.bookstore.readme.domain.community.response;

import com.bookstore.readme.common.response.CommonResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommunityResponse extends CommonResponse {

    private final Object data;

    @Builder
    public CommunityResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

    public static CommunityResponse ok(Object data) {
        return CommunityResponse
                .builder()
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }

    public static CommunityResponse empty(Object data) {
        return CommunityResponse
                .builder()
                .status(204)
                .message("Success")
                .data(data)
                .build();
    }

}
