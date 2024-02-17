package com.bookstore.readme.common.jwt;

import lombok.Getter;

@Getter
public enum JwtErrorStatus {

    MALFORMED_JWT(401, "잘못된 JWT 서명입니다."),
    EXPIRED_JWT(401, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT(401, "지원하지 않는 JWT 토큰입니다."),
    ILLEGAL_ARGUMENT(401, "JWT 토큰이 잘못되었습니다.");

    private final int status;
    private final String message;

    JwtErrorStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
