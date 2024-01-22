package com.bookstore.readme.domain.member.response;

import com.bookstore.readme.common.response.CommonResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse extends CommonResponse {

    private final Object data;

    @Builder
    public MemberResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }
}
