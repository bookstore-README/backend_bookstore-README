package com.bookstore.readme.domain.social.dto;

import com.bookstore.readme.common.utils.RandomUtils;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.social.domain.SocialId;
import com.bookstore.readme.domain.social.domain.SocialType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record NaverMemberResponseDto(
        String resultcode,
        String message,
        Response response
) {

    public Member toDomain() {
        RandomUtils utils = new RandomUtils();

        return Member.builder()
                .socialId(new SocialId(String.valueOf(response.id), SocialType.NAVER))
                .nickname(utils.RandomNickname()) // 닉네임 없음으로 인한 Default 값 추가
                .email(response.email)
                .profileImage(response.profileImage)
                .build();
    }

    @JsonNaming(value = SnakeCaseStrategy.class)
    public record Response(
            String id,
            String nickname,
            String name,
            String email,
            String gender,
            String age,
            String birthday,
            String profileImage,
            String birthyear,
            String mobile
    ) {
    }
}
