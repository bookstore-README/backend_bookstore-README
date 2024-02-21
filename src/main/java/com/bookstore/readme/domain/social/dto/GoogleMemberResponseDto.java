package com.bookstore.readme.domain.social.dto;

import com.bookstore.readme.common.utils.RandomUtils;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.social.domain.SocialId;
import com.bookstore.readme.domain.social.domain.SocialType;

public record GoogleMemberResponseDto(
        String id,
        String email,
        boolean verified_email,
        String name,
        String given_name,
        String family_name,
        String picture,
        String locale
) {

    public Member toDomain() {
        RandomUtils utils = new RandomUtils();

        return Member.builder()
                .socialId(new SocialId(id, SocialType.GOOGLE))
                .nickname(utils.RandomNickname()) // 닉네임 없음으로 인한 Default 값 추가
                .email(email)
                .profileImage(picture)
                .build();
    }
}
