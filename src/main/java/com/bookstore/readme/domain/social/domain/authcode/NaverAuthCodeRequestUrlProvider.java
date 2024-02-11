package com.bookstore.readme.domain.social.domain.authcode;

import com.bookstore.readme.domain.social.domain.SocialType;
import com.bookstore.readme.domain.social.infra.record.KakaoOauthConfig;
import com.bookstore.readme.domain.social.infra.record.NaverOauthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class NaverAuthCodeRequestUrlProvider implements AuthCodeRequestUrlProvider {

    private final NaverOauthConfig naverOauthConfig;

    @Override
    public SocialType supportServer() {
        return SocialType.NAVER;
    }

    @Override
    public String provide() {
        return UriComponentsBuilder
                .fromUriString("https://nid.naver.com/oauth2.0/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", naverOauthConfig.clientId())
                .queryParam("redirect_uri", naverOauthConfig.redirectUri())
                .queryParam("scope", String.join(" ", naverOauthConfig.scope()))
                .queryParam("state", "samplestate") // 나중에 수정 작업(인코딩?)
                .toUriString();
    }
}
