package com.bookstore.readme.domain.social.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class GoogleTokenDto {

    private String access_token;
    private Long expires_in;
    private String refresh_token;
    private String scope;
    private String token_type;
    private String id_token;

}
