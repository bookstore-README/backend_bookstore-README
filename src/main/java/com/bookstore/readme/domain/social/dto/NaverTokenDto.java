package com.bookstore.readme.domain.social.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class NaverTokenDto {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private Integer expries_in;
    private String error;
    private String error_description;

}
