package com.bookstore.readme.domain.community.response;

import com.bookstore.readme.common.response.CommonResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(data)
                .build();
    }

    public static CommunityResponse empty(Object data) {
        return CommunityResponse
                .builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Success")
                .data(data)
                .build();
    }

}
