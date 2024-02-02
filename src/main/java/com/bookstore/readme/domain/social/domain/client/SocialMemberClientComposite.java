package com.bookstore.readme.domain.social.domain.client;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.social.domain.SocialMember;
import com.bookstore.readme.domain.social.domain.SocialType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class SocialMemberClientComposite {

    private final Map<SocialType, SocialMemberClient> mapping;

    public SocialMemberClientComposite(Set<SocialMemberClient> clients) {
        mapping = clients.stream()
                .collect(toMap(
                        SocialMemberClient::supportServer,
                        identity()
                ));
    }

    public Member fetch(SocialType socialType, String authCode) {
        return getClient(socialType).fetch(authCode);
    }

    private SocialMemberClient getClient(SocialType socialType) {
        return Optional.ofNullable(mapping.get(socialType))
                .orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인입니다."));
    }

}
