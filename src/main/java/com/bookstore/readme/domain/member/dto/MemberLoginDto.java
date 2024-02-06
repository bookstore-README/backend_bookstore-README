package com.bookstore.readme.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MemberLoginDto {

    @NotEmpty(message = "이메일 주소는 필수입니다.")
    @Schema(description = "이메일 주소입니다.", example = "hello1@hello.net")
    private String email;

    @NotEmpty(message = "비밀번호 입력은 필수입니다.")
    @Schema(description = "비밀번호 입력란입니다.", example = "1234")
    private String password;

}
