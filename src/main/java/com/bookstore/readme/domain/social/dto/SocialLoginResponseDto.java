package com.bookstore.readme.domain.social.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialLoginResponseDto {

    Long memberId;
    String email;
    String accessToken;
    String refreshToken;

}
