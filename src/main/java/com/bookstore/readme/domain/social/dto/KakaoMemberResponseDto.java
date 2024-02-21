package com.bookstore.readme.domain.social.dto;

import com.bookstore.readme.common.utils.RandomUtils;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.social.domain.SocialId;
import com.bookstore.readme.domain.social.domain.SocialType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoMemberResponseDto(
        Long id,
        boolean hasSignedUp,
        LocalDateTime connectedAt,
        KakaoAccount kakaoAccount
) {

    public Member toDomain() {
        RandomUtils utils = new RandomUtils();

        return Member.builder()
                .socialId(new SocialId(String.valueOf(id), SocialType.KAKAO))
                .nickname(utils.RandomNickname()) // 닉네임 없음으로 인한 Default 값 추가
                .email(kakaoAccount.email)
                .profileImage(kakaoAccount.profile.profileImageUrl)
                .build();
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record KakaoAccount(
            boolean profileNeedsAgreement,
            boolean profileNicknameNeedsAgreement,
            boolean profileImageNeedsAgreement,
            Profile profile,
            boolean nameNeedsAgreement,
            String name,
            boolean emailNeedsAgreement,
            boolean isEmailValid,
            boolean isEmailVerified,
            String email,
            boolean ageRangeNeedsAgreement,
            String ageRange,
            boolean birthyearNeedsAgreement,
            String birthyear,
            boolean birthdayNeedsAgreement,
            String birthday,
            String birthdayType,
            boolean genderNeedsAgreement,
            String gender,
            boolean phoneNumberNeedsAgreement,
            String phoneNumber,
            boolean ciNeedsAgreement,
            String ci,
            LocalDateTime ciAuthenticatedAt
    ) {
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record Profile(
            String nickname,
            String thumbnailImageUrl,
            String profileImageUrl,
            boolean isDefaultImage
    ) {
    }
}
