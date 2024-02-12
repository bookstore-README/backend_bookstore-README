package com.bookstore.readme.domain.community.dto;

import com.bookstore.readme.domain.member.model.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommunityMemberDto {
    private final Long memberId;
    private final String nickname;
    private final String profileImg;

    @Builder
    public CommunityMemberDto(Long memberId, String nickname, String profileImg) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

    public static CommunityMemberDto of(Member member) {
        return CommunityMemberDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .profileImg(member.getProfileImage())
                .build();
    }
}
