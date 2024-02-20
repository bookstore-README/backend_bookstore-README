package com.bookstore.readme.domain.social.service;

import com.bookstore.readme.common.jwt.JwtTokenService;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.model.MemberRole;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import com.bookstore.readme.domain.social.domain.SocialType;
import com.bookstore.readme.domain.social.domain.authcode.AuthCodeRequestUrlProviderComposite;
import com.bookstore.readme.domain.social.domain.client.SocialMemberClientComposite;
import com.bookstore.readme.domain.social.dto.SocialLoginResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final SocialMemberClientComposite socialMemberClientComposite;
    private final MemberRepository memberRepository;
    private final JwtTokenService jwtTokenService;

    public String getAuthCodeRequestUrl(SocialType socialType) {
        return authCodeRequestUrlProviderComposite.provide(socialType);
    }

    public SocialLoginResponseDto login(SocialType socialType, String authCode) {
        Member socialMember = socialMemberClientComposite.fetch(socialType, authCode);

        Member member = memberRepository.findBySocialIdAndEmail(socialMember.getSocialId(), socialMember.getEmail())
                .orElseGet(() -> this.socialSave(socialMember));

        String accessToken = jwtTokenService.createAccessToken(member.getEmail());

        return SocialLoginResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .socialType(member.getSocialId().socialServer())
                .accessToken(accessToken)
                .refreshToken(member.getRefreshToken())
                .build();
    }

    private Member socialSave(Member socialMember) {
        String refreshToken = jwtTokenService.createRefreshToken(socialMember.getEmail());

        Member saved = Member.builder()
                .nickname(socialMember.getNickname())
                .profileImage(socialMember.getProfileImage())
                .email(socialMember.getEmail())
                .role(MemberRole.USER)
                .socialId(socialMember.getSocialId())
                .refreshToken(refreshToken)
                .build();

        return memberRepository.save(saved);
    }

}
