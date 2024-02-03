package com.bookstore.readme.domain.member.dto;

import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.model.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;

@Getter
public class MemberSaveDto {
    @NotEmpty(message = "회원 이름은 필수 입니다.")
    @Schema(description = "회원 이름입니다.", example = "회원 이름")
    private String name;

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    @Schema(description = "회원의 닉네임 입니다", example = "익명1")
    private String nickname;

    @Schema(description = "회원의 프로필 이미지입니다.", example = "파일...")
    private String profileImage;

    @NotEmpty(message = "이메일 주소는 필수 입니다.")
    @Schema(description = "이메일 주소입니다.", example = "이메일 주소")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입니다.")
    @Schema(description = "비밀번호 입니다.", example = "비밀번호 입니다.")
    private String password;


    public Member toEntity() {
        return Member.builder()
                .name(name)
                .nickname(nickname)
                .profileImage(profileImage)
                .email(email)
                .password(password)
                .role(MemberRole.USER)
                .build();
    }
}
