package com.bookstore.readme.domain.social.domain.client;

import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.social.domain.SocialType;
import com.bookstore.readme.domain.social.dto.KakaoMemberResponseDto;
import com.bookstore.readme.domain.social.dto.KakaoTokenDto;
import com.bookstore.readme.domain.social.infra.kakao.KakaoApiClient;
import com.bookstore.readme.domain.social.infra.record.KakaoOauthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class KakaoMemberClient implements SocialMemberClient {

    private final KakaoApiClient kakaoApiClient;
    private final KakaoOauthConfig kakaoOauthConfig;

    @Override
    public SocialType supportServer() {
        return SocialType.KAKAO;
    }

    @Override
    public Member fetch(String authCode) {
        KakaoTokenDto token = kakaoApiClient.fetchToken(tokenRequestParams(authCode));
        KakaoMemberResponseDto kakaoMemberResponseDto =
                kakaoApiClient.fetchMember(token.getToken_type() + " " + token.getAccess_token());
        return kakaoMemberResponseDto.toDomain();
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoOauthConfig.clientId());
        params.add("client_secret", kakaoOauthConfig.clientSecret());
        params.add("redirect_uri", kakaoOauthConfig.redirectUri());
        params.add("code", authCode);
        return params;
    }
}
