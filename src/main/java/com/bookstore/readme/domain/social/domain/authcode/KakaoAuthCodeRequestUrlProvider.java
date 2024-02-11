package com.bookstore.readme.domain.social.domain.authcode;

import com.bookstore.readme.domain.social.domain.SocialType;
import com.bookstore.readme.domain.social.infra.record.GoogleOauthConfig;
import com.bookstore.readme.domain.social.infra.record.KakaoOauthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoAuthCodeRequestUrlProvider implements AuthCodeRequestUrlProvider {

    private final KakaoOauthConfig kakaoOauthConfig;

    @Override
    public SocialType supportServer() {
        return SocialType.KAKAO;
    }

    @Override
    public String provide() {
        return UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/authroize")
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoOauthConfig.clientId())
                .queryParam("redirect_uri", kakaoOauthConfig.redirectUri())
                .queryParam("scope", String.join(" ", kakaoOauthConfig.scope()))
                .toUriString();
    }
}
