package com.bookstore.readme.domain.social.domain.authcode;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import com.bookstore.readme.domain.social.domain.SocialType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class AuthCodeRequestUrlProviderComposite {

    private final Map<SocialType, AuthCodeRequestUrlProvider> mapping;

    public AuthCodeRequestUrlProviderComposite(Set<AuthCodeRequestUrlProvider> providers) {
        mapping = providers.stream()
                .collect(toMap(
                        AuthCodeRequestUrlProvider::supportServer,
                        identity()
                ));
    }

    public String provide(SocialType socialType) {
        return getProvider(socialType).provide();
    }

    private AuthCodeRequestUrlProvider getProvider(SocialType socialType) {
        return Optional.ofNullable(mapping.get(socialType))
                .orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인 입니다."));
    }

}
