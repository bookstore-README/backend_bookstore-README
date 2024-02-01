package com.bookstore.readme.domain.social.service;

import com.bookstore.readme.common.jwt.JwtTokenService;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.model.MemberRole;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import com.bookstore.readme.domain.social.domain.SocialMember;
import com.bookstore.readme.domain.social.domain.SocialType;
import com.bookstore.readme.domain.social.domain.authcode.AuthCodeRequestUrlProviderComposite;
import com.bookstore.readme.domain.social.domain.client.SocialMemberClientComposite;
import com.bookstore.readme.domain.social.dto.SocialLoginResponseDto;
import com.bookstore.readme.domain.social.repository.SocialMemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final SocialMemberClientComposite socialMemberClientComposite;
    // private final SocialMemberRepository socialMemberRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;


    public String getAuthCodeRequestUrl(SocialType socialType) {
        return authCodeRequestUrlProviderComposite.provide(socialType);
    }

    public SocialLoginResponseDto login(SocialType socialType, String authCode) {
        Member socialMember = socialMemberClientComposite.fetch(socialType, authCode);

        Member member = memberRepository.findBySocialIdAndEmail(socialMember.getSocialId(), socialMember.getEmail())
                .orElseGet(() -> this.socialSave(socialMember));

        String accessToken = jwtTokenService.createAccessToken(member.getEmail());
        String refreshToken = jwtTokenService.createRefreshToken(member.getEmail());

        return SocialLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Member socialSave(Member socialMember) {

        Member saved = Member.builder()
                .name(socialMember.getName())
                .nickname(socialMember.getNickname())
                .profileImage(socialMember.getProfileImage())
                .email(socialMember.getEmail())
                .password(passwordEncoder.encode("1234"))
                .role(MemberRole.USER)
                .socialId(socialMember.getSocialId())
                .build();

        return memberRepository.save(saved);
    }

}
