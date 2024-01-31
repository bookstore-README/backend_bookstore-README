package com.bookstore.readme.domain.social.domain.client;

import com.bookstore.readme.domain.social.domain.SocialMember;
import com.bookstore.readme.domain.social.domain.SocialType;
import com.bookstore.readme.domain.social.dto.GoogleMemberResponseDto;
import com.bookstore.readme.domain.social.dto.GoogleTokenDto;
import com.bookstore.readme.domain.social.infra.google.GoogleApiClient;
import com.bookstore.readme.domain.social.infra.record.GoogleOauthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class GoogleMemberClient implements SocialMemberClient {

    private final GoogleApiClient googleApiClient;
    private final GoogleOauthConfig googleOauthConfig;

    @Override
    public SocialType supportServer() {
        return SocialType.GOOGLE;
    }

    @Override
    public SocialMember fetch(String authCode) {
        GoogleTokenDto token = googleApiClient.fetchToken(tokenRequestParams(authCode));
        GoogleMemberResponseDto googleMemberResponse =
                googleApiClient.fetchMember(token.getToken_type() + " " + token.getAccess_token());
        return googleMemberResponse.toDomain();
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", googleOauthConfig.clientId());
        params.add("client_secret", googleOauthConfig.clientSecret());
        params.add("redirect_uri", googleOauthConfig.redirectUri());
        params.add("code", authCode);
        return params;
    }
}
