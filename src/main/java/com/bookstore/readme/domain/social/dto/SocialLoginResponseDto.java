package com.bookstore.readme.domain.social.dto;

import com.bookstore.readme.domain.social.domain.SocialType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialLoginResponseDto {

    Long memberId;
    String email;
    SocialType socialType;
    String accessToken;
    String refreshToken;

}
