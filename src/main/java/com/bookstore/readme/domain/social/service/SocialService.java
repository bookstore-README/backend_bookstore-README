package com.bookstore.readme.domain.social.service;

import com.bookstore.readme.domain.social.domain.SocialMember;
import com.bookstore.readme.domain.social.domain.SocialType;
import com.bookstore.readme.domain.social.domain.authcode.AuthCodeRequestUrlProviderComposite;
import com.bookstore.readme.domain.social.domain.client.SocialMemberClientComposite;
import com.bookstore.readme.domain.social.repository.SocialMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final SocialMemberClientComposite socialMemberClientComposite;
    private final SocialMemberRepository socialMemberRepository;

    public String getAuthCodeRequestUrl(SocialType socialType) {
        return authCodeRequestUrlProviderComposite.provide(socialType);
    }

    public Long login(SocialType socialType, String authCode) {
        SocialMember socialMember = socialMemberClientComposite.fetch(socialType, authCode);
        SocialMember saved = socialMemberRepository.findBySocialId(socialMember.getSocialId())
                .orElseGet(() -> socialMemberRepository.save(socialMember));
        return saved.getId();
    }

}
