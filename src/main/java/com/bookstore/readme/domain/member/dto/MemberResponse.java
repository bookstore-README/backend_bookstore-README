package com.bookstore.readme.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {
    private final int status;
    private final String message;
    private final Object data;

    @Builder
    public MemberResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
