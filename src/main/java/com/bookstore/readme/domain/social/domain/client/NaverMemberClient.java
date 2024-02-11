package com.bookstore.readme.domain.social.domain.client;

import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.social.domain.SocialType;
import com.bookstore.readme.domain.social.dto.NaverMemberResponseDto;
import com.bookstore.readme.domain.social.dto.NaverTokenDto;
import com.bookstore.readme.domain.social.infra.naver.NaverApiClient;
import com.bookstore.readme.domain.social.infra.record.NaverOauthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class NaverMemberClient implements SocialMemberClient {

    private final NaverApiClient naverApiClient;
    private final NaverOauthConfig naverOauthConfig;

    @Override
    public SocialType supportServer() {
        return SocialType.NAVER;
    }

    @Override
    public Member fetch(String authCode) {
        NaverTokenDto token = naverApiClient.fetchToken(tokenRequestParams(authCode));
        NaverMemberResponseDto naverMemberResponseDto =
                naverApiClient.fetchMember(token.getToken_type() + " " + token.getAccess_token());
        return naverMemberResponseDto.toDomain();
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverOauthConfig.clientId());
        params.add("client_secret", naverOauthConfig.clientSecret());
        params.add("redirect_uri", naverOauthConfig.redirectUri());
        params.add("code", authCode);
        params.add("state", "sampleState");
        return params;
    }
}
