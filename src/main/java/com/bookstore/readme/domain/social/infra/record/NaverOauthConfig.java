package com.bookstore.readme.domain.social.infra.record;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2.user.naver")
public record NaverOauthConfig(
        String redirectUri,
        String clientId,
        String clientSecret,
        String[] scope,
        String state
) {

}
