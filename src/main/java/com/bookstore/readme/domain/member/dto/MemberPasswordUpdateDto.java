package com.bookstore.readme.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberPasswordUpdateDto {

    @NotEmpty(message = "새로운 비밀번호 값은 필수 입니다.")
    @Schema(description = "새로운 비밀번호 입력값입니다.", example = "1234")
    private String newPassword;

}
