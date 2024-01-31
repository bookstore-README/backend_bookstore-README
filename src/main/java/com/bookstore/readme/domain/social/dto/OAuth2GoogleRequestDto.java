package com.bookstore.readme.domain.social.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OAuth2GoogleRequestDto {

    private String authorizationCode;

}
