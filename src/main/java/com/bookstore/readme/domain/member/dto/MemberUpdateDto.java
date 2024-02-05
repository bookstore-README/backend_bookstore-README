package com.bookstore.readme.domain.member.dto;

import com.bookstore.readme.domain.member.model.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MemberUpdateDto {

    @NotEmpty(message = "회원 아이디는 필수 입니다.")
    @Schema(description = "회원 아이디입니다.", example = "1")
    private Long member_id;

    @NotEmpty(message = "회원 닉네임 수정 시 필수 입니다.")
    @Schema(description = "회원 닉네임입니다.", example = "ReadMe")
    private String nickname;

    @Schema(description = "회원 프로필 이미지 URL입니다.", example = "123.jpg")
    private String profileImage;

    public Member toEntity(Member member) {
        return Member.builder()
                .id(member.getId())
                .name(member.getName())
                .nickname(nickname)
                .profileImage(profileImage)
                .email(member.getEmail())
                .password(member.getPassword())
                .role(member.getRole())
                .refreshToken(member.getRefreshToken())
                .socialId(member.getSocialId())
                .build();
    }

}
