package com.bookstore.readme.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MemberPasswordUpdateDto {

    @NotEmpty(message = "회원 아이디는 필수 입니다.")
    @Schema(description = "회원 아이디입니다.", example = "1")
    private Long member_id;

    @NotEmpty(message = "새로운 비밀번호 값은 필수 입니다.")
    @Schema(description = "새로운 비밀번호 입력값입니다.", example = "1234")
    private String new_password;

}
