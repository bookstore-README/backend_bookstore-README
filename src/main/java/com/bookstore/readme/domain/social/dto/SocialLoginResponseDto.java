package com.bookstore.readme.domain.social.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialLoginResponseDto {

    String accessToken;
    String refreshToken;

}
