package com.bookstore.readme.common.security.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtTokenResponse {

    private final String accessToken;

    @Builder
    public JwtTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
