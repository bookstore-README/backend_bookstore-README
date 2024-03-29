package com.bookstore.readme.domain.member.dto;

import com.bookstore.readme.domain.member.model.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MemberUpdateDto {

    @NotEmpty(message = "회원 닉네임 수정 시 필수 입니다.")
    @Schema(description = "회원 닉네임입니다.", example = "ReadMe")
    private String nickname;

    public Member toUpdateEntity(Member member, String fileUrl) {
        return Member.builder()
                .id(member.getId())
                .nickname(nickname)
                .profileImage(fileUrl)
                .email(member.getEmail())
                .password(member.getPassword())
                .role(member.getRole())
                .refreshToken(member.getRefreshToken())
                .socialId(member.getSocialId())
                .build();
    }

}
